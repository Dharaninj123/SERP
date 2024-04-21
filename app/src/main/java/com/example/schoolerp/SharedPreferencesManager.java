package com.example.schoolerp;



import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String SHARED_PREF_NAME = "your_shared_pref_name";
    private static final String KEY_TOKEN = "token";

    private static SharedPreferencesManager mInstance;
    private final SharedPreferences sharedPreferences;
    private final Context mContext;

    private SharedPreferencesManager(Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public static void initialize(Context context) {
    }

    public static FCMTokenRequest getInstance() {
        return null;
    }

    public static void createToken(SignupActivity signupActivity, String token) {
    }

    // Method to save token
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // Method to retrieve token
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    // Method to clear token
    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    public class Editor {
    }
}
