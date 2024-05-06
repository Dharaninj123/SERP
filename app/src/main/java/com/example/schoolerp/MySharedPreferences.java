package com.example.schoolerp;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static final String PREF_NAME = "MyPrefs";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    // Private constructor to prevent instantiation
    private MySharedPreferences() {
    }

    // Initialize SharedPreferences in the application context
    public static void initialize(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    // Save a string value to SharedPreferences
    public static void saveString(String key, String value) {
        checkInitialization();
        editor.putString(key, value);
        editor.apply();
    }

    // Retrieve a string value from SharedPreferences
    public static String getString(String key, String defaultValue) {
        checkInitialization();
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save an integer value to SharedPreferences
    public static void saveInt(String key, int value) {
        checkInitialization();
        editor.putInt(key, value);
        editor.apply();
    }

    // Retrieve an integer value from SharedPreferences
    public static int getInt(String key, int defaultValue) {
        checkInitialization();
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save a boolean value to SharedPreferences
    public static void saveBoolean(String key, boolean value) {
        checkInitialization();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Retrieve a boolean value from SharedPreferences
    public static boolean getBoolean(String key, boolean defaultValue) {
        checkInitialization();
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Add more methods for saving and retrieving different types of data as needed

    // Check if SharedPreferences has been initialized
    private static void checkInitialization() {
        if (sharedPreferences == null || editor == null) {
            throw new IllegalStateException("MySharedPreferences is not initialized. Call initialize(context) first.");
        }
    }
}
