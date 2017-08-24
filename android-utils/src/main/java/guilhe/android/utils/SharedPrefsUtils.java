package guilhe.android.utils;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by gdelgado on 18/08/2017.
 */

public class SharedPrefsUtils {

    private static final String TAG = SharedPrefsUtils.class.getSimpleName();

    /**
     * This method stores an object into SharedPreferences.
     *
     * @param prefs  SharedPreferences instance to store the object.
     * @param key    used to store the object.
     * @param object to be stored.
     * @return true if success, false otherwise.
     *
     * @throws IllegalArgumentException if prefs, key or gson are null.
     */
    public static boolean putObject(SharedPreferences prefs, String key, Object object) {
        return putObject(prefs, key, object, new Gson());
    }

    /**
     * This method stores an object into SharedPreferences.
     *
     * @param prefs  SharedPreferences instance to store the object.
     * @param key    used to store the object.
     * @param object to be stored.
     * @param gson   custom instance.
     * @return true if success, false otherwise.
     *
     * @throws IllegalArgumentException if prefs, key or gson are null.
     */
    public static boolean putObject(SharedPreferences prefs, String key, Object object, Gson gson) {
        if (prefs == null) {
            throw new IllegalArgumentException("SharedPreferences must not be null");
        }
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("Key must not be empty or null");
        }
        if (gson == null) {
            throw new IllegalArgumentException("Gson must not be null");
        }
        Log.v(TAG, "> putObject, storing " + (object == null ? "null object" : object.toString()) + " with key \"" + key + "\"");
        return prefs.edit().putString(key, gson.toJson(object)).commit();
    }

    /**
     * This method returns an object with a specified Type stored in SharedPreferences.
     * For non-generic objects use {@link #getObject(SharedPreferences, String, Class, T)} instead.
     *
     * @param prefs        SharedPreferences instance where the object is stored.
     * @param key          used to store the object.
     * @param type         type of the desired object.
     * @param defaultValue to be returned when {@link #assertNotNull(SharedPreferences, String, Object, Gson)} returns false.
     * @return an object of type T.
     */
    public static <T> T getObject(SharedPreferences prefs, String key, TypeToken<T> type, T defaultValue) {
        return getObject(prefs, key, type, defaultValue, new Gson());
    }

    /**
     * This method returns an object with a specified Type stored in SharedPreferences.
     * For non-generic objects use {@link #getObject(SharedPreferences, String, Class, T)} instead.
     *
     * @param prefs        SharedPreferences instance where the object is stored.
     * @param key          used to store the object.
     * @param type         type of the desired object.
     * @param defaultValue to be returned when {@link #assertNotNull(SharedPreferences, String, Object, Gson)} returns false.
     * @param gson         custom instance.
     * @return an object of type T.
     */
    public static <T> T getObject(SharedPreferences prefs, String key, TypeToken<T> type, T defaultValue, Gson gson) {
        if (!assertNotNull(prefs, key, type, gson)) {
            return defaultValue;
        }
        String json = prefs.getString(key, null);
        if (json == null) {
            Log.w(TAG, "> getObject, json is null, returning defaultValue");
            return defaultValue;
        } else {
            try {
                return gson.fromJson(json, type.getType());
            } catch (JsonSyntaxException e) {
                throw new JsonParseException("> getObject, Object stored with Key " + key + " is instance of other class.");
            }
        }
    }

    /**
     * This method returns an object with a specified Type stored in SharedPreferences.
     * For generic objects use {@link #getObject(SharedPreferences, String, TypeToken, T)} instead.
     *
     * @param prefs        SharedPreferences instance where the object is stored.
     * @param key          used to store the object.
     * @param object       class of the desired object.
     * @param defaultValue to be returned when {@link #assertNotNull(SharedPreferences, String, Object, Gson)} returns false.
     * @return an object of type T.
     */
    public static <T> T getObject(SharedPreferences prefs, String key, Class<T> object, T defaultValue) {
        return getObject(prefs, key, object, defaultValue, new Gson());
    }

    /**
     * This method returns an object with a specified Class<T> stored in SharedPreferences.
     * For generic objects use {@link #getObject(SharedPreferences, String, TypeToken, T, Gson)} instead.
     *
     * @param prefs        SharedPreferences instance where the object is stored.
     * @param key          used to store the object.
     * @param object       class of the desired object.
     * @param defaultValue to be returned when {@link #assertNotNull(SharedPreferences, String, Object, Gson)} returns false.
     * @param gson         custom instance.
     * @return an object of type T.
     */
    public static <T> T getObject(SharedPreferences prefs, String key, Class<T> object, T defaultValue, Gson gson) {
        if (!assertNotNull(prefs, key, object, gson)) {
            return defaultValue;
        }
        String json = prefs.getString(key, null);
        if (json == null) {
            Log.w(TAG, "> getObject, json is null, returning defaultValue");
            return defaultValue;
        } else {
            try {
                return gson.fromJson(json, object);
            } catch (JsonSyntaxException e) {
                throw new JsonParseException("> getObject, Object stored with Key " + key + " is instance of other class.");
            }
        }
    }

    /**
     * This method validates if the input types are valid.
     *
     * @param prefs  SharedPreferences instance.
     * @param key    used to store the object.
     * @param object return type.
     * @param gson   Gson instance.
     * @return true if all instances are not null, false otherwise.
     */
    private static boolean assertNotNull(SharedPreferences prefs, String key, Object object, Gson gson) {
        if (prefs == null) {
            Log.w(TAG, "> getObject, SharedPreferences must not be null");
            return false;
        }
        if (key == null || key.equals("")) {
            Log.w(TAG, "> getObject, Key must not be null or empty");
            return false;
        }
        if (object == null) {
            Log.w(TAG, "> getObject, Type or Object must not be null");
            return false;
        }
        if (gson == null) {
            Log.w(TAG, "> getObject, Gson must not be null");
            return false;
        }
        return true;
    }
}