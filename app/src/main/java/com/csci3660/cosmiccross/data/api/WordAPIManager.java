package com.csci3660.cosmiccross.data.api;

import android.content.Context;
import android.content.SharedPreferences;
public class WordAPIManager {
    private static final String APIKEY_PREF = "APIKeyPref";
    private static final String USER_API_KEY = "userAPIKey";

    public static void saveApiKey(Context context, String userAPIKey) {
        // Saves key to preferences
        SharedPreferences prefs = context.getSharedPreferences(APIKEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_API_KEY, userAPIKey);
        editor.apply();
    }

    public static String getApiKey(Context context) {
        // Gets key from preferences
        SharedPreferences prefs = context.getSharedPreferences(APIKEY_PREF, Context.MODE_PRIVATE);
        return prefs.getString(USER_API_KEY, null);
    }
}
