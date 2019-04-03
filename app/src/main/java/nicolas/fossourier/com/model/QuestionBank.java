package nicolas.fossourier.com.model;

/**
 * QuestionBank va gérer la liste de questions
 */

import java.util.Collections;
import java.util.List;


public class QuestionBank {
    private List<Question> mQuestionList; // Liste de questions
    private int mNextQuestionIndex; // Index de la prochaine question

    public QuestionBank(List<Question> questionList) {
        mQuestionList=questionList;

        /**
         * Mélangez la liste de questions avant de la stocker
         */
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex=0;
    }

    public Question getQuestion() {
        /**
         * Création d'une boucle pour les questions afin de retourner une nouvelle à chaque appel
         */
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex=0;
        }

        /**
         * Ajoute 1 à chaque question (permet de stopper une fois le nombre max déterminé atteint)
         */
        return mQuestionList.get(mNextQuestionIndex++);
    }
}