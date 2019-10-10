package com.example.truthordare;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;
import java.io.*;
import java.util.*;


// Handles all of the Truth or Dare game functions, as well as the TinyDB functions.
public class GameMaker {

    private static final String DARE_FILE_NAME = "dare.txt";
    private static final String TRUTH_FILE_NAME = "truth.txt";
    private static final String DARES_LIST = "DaresList";
    private static final String TRUTHS_LIST = "TruthsList";
    private static final String CACHED_DARES = "CachedDares";
    private static final String CACHED_TRUTHS = "CachedTruths";
    private ArrayList<String> dares = new ArrayList<>(); // array that holds questions for use in-game
    private ArrayList<String> truths = new ArrayList<>(); // array that holds questions for use in-game
    private Context cntx;
    private TinyDB tinyDB;


    public GameMaker(Context cntx) {
        this.cntx = cntx; //class's context
        this.tinyDB = new TinyDB(cntx); //class's DB
        initArrayFill(cntx); // fills arraylists with base questions from .txt files
        tinyDB.putListString(DARES_LIST, dares);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }


    // Fills the array lists
    // This method is only called whenever a new GameMaker is made, which should only be once.
    public void initArrayFill(Context c) {
        AssetManager am = c.getAssets();
        try {
            InputStream is = am.open(DARE_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String value = br.readLine();
            while (value != null) {
                dares.add(value);
                value = br.readLine();
                if  (value == null) break;
            }
            is = c.getAssets().open(TRUTH_FILE_NAME);
            br = new BufferedReader(new InputStreamReader(is));
            value = br.readLine();
            while (value != null) {
                truths.add(value);
                value = br.readLine();
                if (value == null) break;
            }
            tinyDB.putListString(CACHED_DARES, dares);
            tinyDB.putListString(CACHED_TRUTHS, truths);
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Return the size of the truth ArrayList
    private int getTruthSize() {
        return truths.size();
    }


    // Return the size of the dare ArrayList
    private int getDareSize() {
        return dares.size();
    }


    // Retrieve a random Truth from the ArrayList and remove it.
    public String getTruth() {
        if (getTruthSize() == 0) {
            Toast.makeText(cntx, "Out of truth questions! Using a dare question instead.", Toast.LENGTH_LONG).show();
            return getDare();
        }
        double finder = Math.random() * getTruthSize();
        String phrase = truths.get((int) finder);
        truths.remove((int) finder);
        return phrase;
    }


    // Retrieve a random Dare from the ArrayList and remove it.
    public String getDare() {
        if (getDareSize() == 0) {
            Toast.makeText(cntx, "Out of dare questions! Using a truth question instead.", Toast.LENGTH_LONG).show();
            return getTruth();
        }
        double finder = Math.random() * getDareSize();
        String phrase = dares.get((int) finder);
        dares.remove((int) finder);
        return phrase;
    }

    // Getters for the ArrayLists
    public ArrayList<String> getDareList() {
        return dares;
    }


    public ArrayList<String> getTruthList() {
        return truths;
    }


    // Called each time truth or dare is selected to check size for toast message
    public String switchTruth() {
        if (truths.size() == 0) {
            return "Dare:";
        }
        return "Truth:";
    }


    // Called each time truth or dare is selected to check size for toast message
    public String switchDare() {
        if (dares.size() == 0) {
            return "Truth:";
        }
        return "Dare:";
    }


    // Fills the arrays from shared preferences by emptying and re-adding the lists to the DB
    public void reFillQuestions(Context c) {
        ArrayList<String> userDares = tinyDB.getListString(DARES_LIST);
        ArrayList<String> userTruths = tinyDB.getListString(TRUTHS_LIST);
        dares.clear();
        truths.clear();
        for (int i = 0; i < userDares.size(); i++) {
            dares.add(userDares.get(i));
        }
        for (int i = 0; i < userTruths.size(); i++) {
            truths.add(userTruths.get(i));
        }
    }


    // Update the tinyDB with a new question from the user
    // @param sentence - the new question to be added to dares
    public void addToDaresDB(String sentence) {
        dares = tinyDB.getListString(DARES_LIST);
        tinyDB.remove(DARES_LIST);
        dares.add(sentence);
        tinyDB.putListString(DARES_LIST, dares);
    }


    // Update the tinyDB with a new question from the user
    // @param sentence - the new question to be added to
    public void addToTruthsDB(String sentence) {
        truths = tinyDB.getListString(TRUTHS_LIST);
        tinyDB.remove(TRUTHS_LIST);
        truths.add(sentence);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }


    // Add a different ArrayList to the Truths DB.
    // For use when deleting a question
    public void updateTruthDB(ArrayList<String> t) {
        tinyDB.remove(TRUTHS_LIST);
        tinyDB.putListString(TRUTHS_LIST, t);
    }

    // Add a different ArrayList to the Dares DB.
    // For use when deleting a question.
    public void updateDareDB(ArrayList<String> d) {
        tinyDB.remove(DARES_LIST);
        tinyDB.putListString(DARES_LIST, d);
    }


    // Grab the original questions and store them in the database.
    // This method is only for use when the Restore Questions button is pressed
    public void restoreOriginalQuestions() {
        ArrayList<String> userDares = tinyDB.getListString(CACHED_DARES);
        ArrayList<String> userTruths = tinyDB.getListString(CACHED_TRUTHS);
        dares.clear();
        truths.clear();
        tinyDB.remove(DARES_LIST);
        tinyDB.remove(TRUTHS_LIST);
        for (int i = 0; i < userDares.size(); i++) {
            dares.add(userDares.get(i));
        }
        for (int i = 0; i < userTruths.size(); i++) {
            truths.add(userTruths.get(i));
        }
        tinyDB.putListString(DARES_LIST, dares);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }
}
