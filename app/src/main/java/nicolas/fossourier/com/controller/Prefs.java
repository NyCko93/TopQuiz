package nicolas.fossourier.com.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import nicolas.fossourier.com.model.Partie;

public class Prefs {

    private static Prefs instance;
    private static String PREFS="PREFS";
    private static String PARTIES="PARTIES";
    private SharedPreferences prefs;

    private Prefs(Context context) {

        prefs=context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);

    }

    /**
     * Initialisation de la sauvegarde.
     * Si aucune sauvegarde, on en créé une.
     * @param context
     * @return
     */
    public static Prefs getInstance(Context context) {
        if (instance == null)
            instance=new Prefs(context);
        return instance;
    }

    public void storeObjects(ArrayList<Partie> objects) {
        /**
         * Edition des données à sauvegarder.
         */
        SharedPreferences.Editor editor=prefs.edit();
        /**
         * On stock les données.
         */
        Gson gson=new Gson();
        String json=gson.toJson(objects);
        editor.putString(PARTIES, json);
        /**
         * On referme le fichier puis on applique l'édition.
         */
        editor.apply();
    }

    /**
     * Ici on récupère les données sauvegardées.
     * Si null, on créé une nouvelle liste.
     * @return
     */
    public ArrayList<Partie> getObjects() {
        Gson gson=new Gson();
        String json=prefs.getString(PARTIES, "");

        ArrayList<Partie> objects;

        if (json.length() < 1) {
            objects=new ArrayList<>();
        } else {
            Type type=new TypeToken<ArrayList<Partie>>() {
            }.getType();
            objects=gson.fromJson(json, type);
        }
        return objects;
    }

}