package nicolas.fossourier.com.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import nicolas.fossourier.com.R;
import nicolas.fossourier.com.model.Partie;
import nicolas.fossourier.com.view.MyAdapter;


public class HistoryActivity extends AppCompatActivity {

    private Button mReturnButton;
    private RecyclerView mRecyclerView;
    ArrayList<Partie> mParties;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /**
         * Ici on précise que l'Arraylist mParties est sauvegarde dans Prefs et on l'appelle via getObjects().
         */
        mParties = Prefs.getInstance(this).getObjects();
        /**
         * Ici on précise la création d'un RecyclerView.
         */
        buildRecyclerView();


        /**
         * Boutton servant à revenir à l'accueil
         */
        mReturnButton=(Button) findViewById(R.id.activity_ranking_return_btn);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * L'utilisateur clique pour retourner à l'accueil
                 */
                Intent MainActivityIntent=new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(MainActivityIntent);

            }
        });
//        /**
//         * J'instancie mon RecyclerView
//         */
//        final RecyclerView rv=(RecyclerView) findViewById(R.id.list);
//         /**
//         * Ce LayoutManager reste très simple et nous propose les deux orientations possibles (VERTICAL et HORIZONTAL), ainsi que la possibilité d'inverser l'ordre de l'affichage.
//         */
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new MyAdapter(mParties));


    }

    private void buildRecyclerView() {
        mRecyclerView=findViewById(R.id.list);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new MyAdapter(mParties);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("HistoryActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("HistoryActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("HistoryActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("HistoryActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("HistoryActivity::onDestroy()");
    }
}

