package nicolas.fossourier.com.controller;

/**
 * Listes des imports:
 * Écrire un programme en utilisant toujours les noms qualifiés complets peut très vite le rendre complètement illisible.
 * Pour rendre les choses plus claires, on peut utiliser une déclaration d'import.
 * Une telle déclaration se fait avec le mot réservé import suivi d'un nom qualifié complet de classe.
 * Une fois une classe importée, on peut utiliser son nom court dans le programme.
 * Les déclarations d'import doivent se trouver au tout début du fichier, en dehors de toute classe, et on peut en avoir autant qu'on veut.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import nicolas.fossourier.com.R;
import nicolas.fossourier.com.model.Partie;

import static java.lang.System.out;

/**
 * Déclaration de classe:
 * Les visibilités d'une classe, d'une méthode ou d'un attributs: par défaut, public, private ou protected.
 * Par défaut (ou visibilité de paquetage): accessible de partout dans le paquetage mais de nulle part ailleurs.
 * Public: accessible de partout dans le paquetage et, si c'est public, de partout ailleurs.
 * Private: accessible uniquement depuis sa propre classe.
 * Protected: accessible de partout dans le paquetage de la classe concernée et, si la classe est publique, grosso modo dans les classes héritant de cette dernière dans d'autres paquetages.
 * Mot réservé "extends": Dans le cas présent, la classe MainActivity hérite de toutes les méthodes de la classe AppCompatActivity.
 * Mainactivity étend AppCompatActivity.
 */

public class MainActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE=42;
    /**
     * Déclaration des variables d'instance (Attributs)
     */
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Partie mPartie;
    private SharedPreferences mPreferences;
    private Button mRankingButton;


    /**
     * Déclarations des méthodes d'instance (fonctionnalités)
     * La méthode onCreate() est appelée lorsque l'activité est créée
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * La méthode setContentView() permet de déterminer quel fichier layout utiliser
         */
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        /**
         * Initialisation de l'attribut Partie
         */
        mPartie=new Partie();

        mPreferences=getPreferences(MODE_PRIVATE);

        /**
         * Référencement des éléments graphiques via la méthode findViewById()
         */
        mGreetingText=(TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput=(EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton=(Button) findViewById(R.id.activity_main_play_btn);
        mRankingButton=(Button) findViewById(R.id.activity_main_ranking_btn);

        /**
         * Ici nous désactivons le bouton "JOUER" afin, dans la méthode qui suit la désactivation, d'obliger l'utilsateur à saisir un prénom
         */
        mPlayButton.setEnabled(false);


        /**
         * Méthode pour être notifié lorsque l'utilisateur commence à saisir du texte dans le champ EditText correspondant
         */
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**
         * Une fois le bouton activé, l'utilisateur peut cliquer dessus pour lancer le jeu, cette méthode sert à détecter que l'utilisateur a cliqué sur le bouton
         */
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname=mNameInput.getText().toString();
                /**
                 * Permet de mémoriser le nom de l'utilisateur au moment où il clique sur le bouton
                 */
                mPartie.setFirstname(firstname);
                /**
                 * Le bouton "JOUER" fait passer l'utilisateur de la MainActivity à la GameActivity
                 */
                Intent gameActivityIntent=new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

            }
        });

        mRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * L'utilisateur appuie sur le bouton pour accéder au classement
                 */
                Intent rankingActivityIntent=new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(rankingActivityIntent);
            }
        });
    }

    @Override
    public void finish() {
        /**
         * Préparer les données dans l'intent
         */
        Intent data=new Intent();
        data.putExtra("returnKey1", "Swinging on a star. ");
        data.putExtra("returnKey2", "You could be better then you are. ");
        /**
         * Activité terminée ok, retourner les données
         */
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    /**
     * Ici on récupère le score de la GameActivity vers la MainActivity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            /**
             * Aller chercher le score de l'intent
             */
            int score=data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            saveParty(score);


        }
    }


//    private void greetUser() {
//        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);
//
//        if (null != firstname) {
//            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);
//
//            String fulltext = "Bienvenue, " + firstname
//                    + "!\nTon dernier score est " + score
//                    + ", Seras tu meilleur cette fois ci ?";
//            mGreetingText.setText(fulltext);
//            mNameInput.setText(firstname);
//            mNameInput.setSelection(firstname.length());
//            mPlayButton.setEnabled(true);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }

    public Partie getPartie() {
        return mPartie;
    }

    public void setPartie(Partie partie) {
        mPartie=partie;
    }

    /**
     * Création d'une sauvegarde de parties avec récupération du pseudo et du score dans mPartie via mPartie.setFirstname et mPartie.setScore.
     * @param score
     */
    private void saveParty(int score) {
        mPartie.setScore(score);
        ArrayList<Partie> parties=new ArrayList<>();
        parties=Prefs.getInstance(this).getObjects();
        if (parties.size() > 0) {
            Log.d("before", "saveParty: " + parties.get(parties.size() - 1).getFirstname());
        }
        parties.add(mPartie);
        Prefs.getInstance(this).storeObjects(parties);
        if (Prefs.getInstance(this).getObjects().size() > 0) {
            Log.d("after", "saveParty: " + Prefs.getInstance(this).getObjects().get(Prefs.getInstance(this).getObjects().size() - 1).getFirstname());
        }


    }
}