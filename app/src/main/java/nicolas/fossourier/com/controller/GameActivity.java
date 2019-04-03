package nicolas.fossourier.com.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import nicolas.fossourier.com.model.QuestionBank;
import nicolas.fossourier.com.R;
import nicolas.fossourier.com.model.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView; // Texte de la question
    private Button mAnswerButton1; // Bouton réponse 1
    private Button mAnswerButton2; // Bouton réponse 2
    private Button mAnswerButton3; // Bouton réponse 3
    private Button mAnswerButton4; // Bouton réponse 4

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore; // Score de l'utilisateur
    private int mNumberOfQuestions; // Nombre de questions à poser

    public static final String BUNDLE_EXTRA_SCORE="BUNDLE_EXTRA_SCORE"; // Permet de récupérer le score
    public static final String BUNDLE_STATE_SCORE="currentScore";
    public static final String BUNDLE_STATE_QUESTION="currentQuestion";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        /**
         * Méthode pour générer les questions
         */

        mQuestionBank=this.generateQuestions();

        if (savedInstanceState != null) {
            mScore=savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions=savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            /**
             * Score par défaut
             */
            mScore=0;
            /**
             * Détermine le nombre de questions à poser
             */
            mNumberOfQuestions=4;
        }

        mEnableTouchEvents=true;

        /**
         * Référencement des éléments graphiques via la méthode findViewById()
         */
        mQuestionTextView=(TextView) findViewById(R.id.activity_game_question_text);
        mAnswerButton1=(Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2=(Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3=(Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4=(Button) findViewById(R.id.activity_game_answer4_btn);

        /**
         * Pour distinguer le bouton sur lequel le joueur a appuyé, on assigne un identifiant différent pour chaque bouton avec la méthode setTag()
         */
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        /**
         * Utilisez le même listener pour les quatre boutons
         * La valeur de la balise sera utilisée pour distinguer le bouton déclenché
         * La méthode onClick() sera désormais appelée à chaque fois que l'utilisateur cliquera sur un des quatre boutons de réponse
         */
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion=mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex=(int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            /**
             * Bonne réponse
             */
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            /**
             * Mauvaise réponse
             */
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents=false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents=true;

                /**
                 * Le nombre de question détermine le début du décompte, arrivé à 0 la partie est terminée
                 */
                if (--mNumberOfQuestions == 0) {
                    /**
                     * Fin de partie
                     */
                    endGame();
                } else {
                    mCurrentQuestion=mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        /**
         * Boite de dialogue de fin de partie
         */
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * Fin de l'activité
                         */
                        Intent intent=new Intent();
                        /**
                         * Le score est mis dans cet intent
                         */
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        /**
                         * Il faut maintenant faire appel à setResult pour enregistrer le score auprès d'Android
                         */
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    /**
     * Générateur de question, cette arraylist contient les questions, leurs réponses ainsi que les index des bonnes réponses
     *
     * @return
     */
    private QuestionBank generateQuestions() {
        Question question1=new Question("Quel est le nom du président français?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2=new Question("Combien de pays compte l'Union Européenne?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3=new Question("Qui est le créateur d'Android operating system?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4=new Question("En quelle année l'homme a t'il marché, pour la première fois, sur la lune?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5=new Question("Quelle est la capitale de la Roumanie?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6=new Question("Qui a peint Mona Lisa?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7=new Question("Dans quelle ville repose Frédéric Chopin?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8=new Question("Quelle est l'extension de nom de domaine de la Belgique?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9=new Question("Quel est le numéro de la maison des Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}