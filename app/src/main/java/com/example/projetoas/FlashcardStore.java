package com.example.projetoas;
import android.content.Context;
import android.content.SharedPreferences;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FlashcardStore {


    private static final String PREFS = "flashcards_prefs";


    public static class Summary {
        public String title;
        public String text;


        public Summary(String t, String x) {
            title = t;
            text = x;
        }
    }


    // ---------- FLASHCARDS ----------
    public static List<Flashcard> loadCards(Context ctx, String subject) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = prefs.getString(keyCards(subject), null);


        List<Flashcard> list = new ArrayList<>();
        if (raw == null) return list;


        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                list.add(new Flashcard(
                        o.optString("q", ""),
                        o.optString("a", "")
                ));
            }
        } catch (JSONException e) { e.printStackTrace(); }


        return list;
    }


    public static void saveCards(Context ctx, String subject, List<Flashcard> list) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        JSONArray arr = new JSONArray();


        try {
            for (Flashcard f : list) {
                JSONObject o = new JSONObject();
                o.put("q", f.getQuestion());
                o.put("a", f.getAnswer());
                arr.put(o);
            }
        } catch (JSONException e) { e.printStackTrace(); }


        prefs.edit().putString(keyCards(subject), arr.toString()).apply();
    }


    public static void shuffleList(List<Flashcard> list) {
        Collections.shuffle(list);
    }


    // ---------- RESUMOS PEQUENOS ----------
    public static List<Summary> loadSummaries(Context ctx, String subject) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = prefs.getString(keySummary(subject), null);


        List<Summary> list = new ArrayList<>();
        if (raw == null) return list;


        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                list.add(new Summary(
                        o.optString("title", ""),
                        o.optString("text", "")
                ));
            }
        } catch (JSONException e) { e.printStackTrace(); }


        return list;
    }


    public static void saveSummaries(Context ctx, String subject, List<Summary> list) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        JSONArray arr = new JSONArray();


        try {
            for (Summary s : list) {
                JSONObject o = new JSONObject();
                o.put("title", s.title);
                o.put("text", s.text);
                arr.put(o);
            }
        } catch (JSONException e) { e.printStackTrace(); }


        prefs.edit().putString(keySummary(subject), arr.toString()).apply();
    }


    // ---------- KEYS ----------
    private static String keyCards(String subject) {
        return "cards_" + subject.toLowerCase().replace(" ","_");
    }


    private static String keySummary(String subject) {
        return "summ_" + subject.toLowerCase().replace(" ","_");
    }
}
