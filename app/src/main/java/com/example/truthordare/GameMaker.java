package com.example.truthordare;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;
import java.io.*;
import java.util.*;


public class GameMaker {

    private static final String DARE_FILE_NAME = "storageDare.txt";
    private static final String TRUTH_FILE_NAME = "storageTruth.txt";
    private static final String DARES_LIST = "DaresList";
    private static final String TRUTHS_LIST = "TruthsList";
    private ArrayList<String> dares = new ArrayList<>(); // array that holds questions for use in-game
    private ArrayList<String> truths = new ArrayList<>(); // array that holds questions for use in-game
    private Context cntx;
    private TinyDB tinyDB;



    // Constructor
    public GameMaker(Context cntx) {
        this.cntx = cntx; //class's context
        this.tinyDB = new TinyDB(cntx); //class's DB
        initWriteToDB(cntx); // fills arraylists with base questions
        ArrayList<String> test = tinyDB.getListString(DARES_LIST);

        if (test != null) {
            return;
        }
        tinyDB.putListString(DARES_LIST, dares);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }


//    // Adds all the initial questions to internal storage and the ArrayLists.
//    public void writeBaseQuestions (Context c) {
//        AssetManager am = c.getAssets();
//        FileOutputStream fos = null;
//        try {
//            InputStream is = am.open("dare.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            fos = c.openFileOutput(DARE_FILE_NAME, Context.MODE_PRIVATE);
//            String value = br.readLine();
//            while (value != null) {
//                dares.add(value);
//                value = value + "\n";
//                fos.write(value.getBytes());
//                value = br.readLine();
//                if  (value == null) break;
//            }
//            is = c.getAssets().open("truth.txt");
//            br = new BufferedReader(new InputStreamReader(is));
//            fos = c.openFileOutput(TRUTH_FILE_NAME, Context.MODE_PRIVATE);
//            value = br.readLine();
//            while (value != null) {
//                truths.add(value);
//                value = value + "\n";
//                fos.write(value.getBytes());
//                value = br.readLine();
//                if  (value == null) break;
//            }
//            br.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    // Return the size of the truth ArrayList
    private int getTruthSize() {
        return truths.size();
    }


    // Return the size of the dare ArrayList
    private int getDareSize() {
        return dares.size();
    }


    // Retrieve a random Truth from the ArrayList and remove it.
    public String getTruth(){
        if (getTruthSize() == 0) {
            Toast.makeText(cntx, "Out of truth questions! Using a dare question instead.", Toast.LENGTH_LONG).show();
            return getDare();
        }
        double finder = Math.random() * getTruthSize();
        String phrase = truths.get((int)finder);
        truths.remove((int)finder);
        return phrase;
    }


    // Retrieve a random Dare from the ArrayList and remove it.
    public String getDare() {
        if (getDareSize() == 0) {
            Toast.makeText(cntx, "Out of dare questions! Using a truth question instead.", Toast.LENGTH_LONG).show();
            return getTruth();
        }
        double finder = Math.random() * getDareSize();
        String phrase = dares.get((int)finder);
        dares.remove((int)finder);
        return phrase;
    }


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



    public void test() {
        Log.d("BEEP", dares.size() + "");
    }


    // Fills the arrays from shared preferences
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
    public void updateDaresDB(String sentence) {
        dares = tinyDB.getListString(DARES_LIST);
        tinyDB.remove(DARES_LIST);
        dares.add(sentence);
        tinyDB.putListString(DARES_LIST, dares);
    }


    // Update the tinyDB with a new question from the user
    // @param sentence - the new question to be added to
    public void updateTruthsDB(String sentence) {
        truths = tinyDB.getListString(TRUTHS_LIST);
        tinyDB.remove(TRUTHS_LIST);
        truths.add(sentence);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }


    public void removeDareFromDB(String sentence) {
        dares = tinyDB.getListString(DARES_LIST);
        for (int i = 0; i < dares.size(); i++) {
            if (dares.get(i).equals(sentence)) {
                dares.remove(i);
            }
        }
        tinyDB.remove(DARES_LIST);
        tinyDB.putListString(DARES_LIST, dares);
    }


    public void removeTruthFromDB(String sentence) {
        truths = tinyDB.getListString(TRUTHS_LIST);
        for (int i = 0; i < dares.size(); i++) {
            if (truths.get(i).equals(sentence)) {
                truths.remove(i);
            }
        }
        tinyDB.remove(TRUTHS_LIST);
        tinyDB.putListString(TRUTHS_LIST, truths);
    }

    // Fills the array lists
    public void initWriteToDB(Context c) {
        AssetManager am = c.getAssets();
        try {
            InputStream is = am.open("dare.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String value = br.readLine();
            while (value != null) {
                dares.add(value);
                value = br.readLine();
                if  (value == null) break;
            }
            is = c.getAssets().open("truth.txt");
            br = new BufferedReader(new InputStreamReader(is));
            value = br.readLine();
            while (value != null) {
                truths.add(value);
                value = br.readLine();
                if  (value == null) break;
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
