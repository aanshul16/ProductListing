package com.productlisting.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class to Access Shared preference operations
 */
public class SharedPreferenceUtil {

    private static final String TAG = "SharedPreferencesUtils";
    private static final String SHARED_PREFS_FILE = "prefs_file";

    /**
     * Use to put List of images in Shared Preference for offline usage
     *
     * @param key
     * @param images
     * @param context
     */
    public static void putList(String key, List<String> images, final Context context) {
        try {
            SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(images);
            editor.putString(key, json);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }

    /**
     * Use to Access list of images in case of user want to see last search result
     *
     * @param key
     * @param context
     * @return
     */
    public static List<String> getList(String key, final Context context) {
        List<String> listValues = null;

        try {
            SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            listValues = getArrayListByKey(mSharedPreferences, key);
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }

        return listValues;
    }

    private static List<String> getArrayListByKey(SharedPreferences mSharedPreferences, String key) {
        List<String> listValues = null;
        try {
            if (mSharedPreferences.contains(key)) {
                String jsonListValues = mSharedPreferences.getString(key, null);
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                listValues = gson.fromJson(jsonListValues, type);
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return listValues;
    }

    /**
     * This function serves last search results
     *
     * @param context
     * @return
     */
    public static List<String> getKeys(final Context context) {
        List<String> keysList = new ArrayList<>();
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = mSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            keysList.add(entry.getKey());
        }
        return keysList;
    }

    /**
     * This function removes last search from list on user action
     *
     * @param key
     * @param context
     */
    public static void removeKey(String key, final Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}