package nicolas.fossourier.com.view;

/**
 * L'adapter permet de contenir l'ensemble des données à afficher dans le RecyclerView en gérant également ses mises à jour. Nous retrouvons le même principe que les adapters des ListView, GridView , etc.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import nicolas.fossourier.com.R;
import nicolas.fossourier.com.model.Partie;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private ArrayList<Partie> mParties;

    /**
     * Notre constructeur MyAdapter prend en parametre l'Arraylist parties afin d'en récupérer le contenu à afficher.
     * @param parties
     */
    public MyAdapter(ArrayList<Partie> parties) {
        mParties= parties;
    }



    @Override

    public int getItemCount() {

        return mParties.size();

    }



    /**
     * Cette méthode nous permet de créer la vue à afficher, et de retourner, associé à ce composant, un objet ViewHolder ;
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(view);

    }


    /**
     * Cette méthode , nous permet d'afficher les données de l'article (l'item) dans la sous-vue courante.
     *
     * @param holder
     * @param position
     */
    @Override

    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textTv.setText(String.format(Locale.getDefault(),"%s: %d", mParties.get(position).getFirstname(), mParties.get(position).getScore()));

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView textTv;

        public MyViewHolder(final View itemView) {

            super(itemView);


            textTv=((TextView) itemView.findViewById(R.id.list_item));


        }
    }
}