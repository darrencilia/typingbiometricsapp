package org.mcast.dc.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

import org.mcast.dc.model.TypingSentence;
import org.mcast.dc.model.User;

/**
 * Created by Darren on 09/12/2016.
 */

public class JsonDeserializer {


    public static HashSet<User> jsonToUsers(String json){
        JSONArray jsonArray;
        HashSet<User> users;

        try
        {
            jsonArray = new JSONArray(json);
            users = new HashSet<>();

            // Loop JSON array
            for(int i=0; i < jsonArray.length(); i++)
            {
                // Get JSON Object from array one by one
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Change JSON to User Object
                User user = new User(jsonObject.getString("alias"), jsonObject.getString("email"), jsonObject.getInt("age"),
                        jsonObject.getBoolean("isMale"), jsonObject.getBoolean("isRightHanded"));
                // Add user to HashSet
                users.add(user);
            }

            // Return the User HashSet
            return users;
        }
        catch(Exception ex)
        {
            Log.e("JSON Deserializer", String.format("Failed to deserialize users: %s", json), ex);
            return null;
        }
    }

    public static HashSet<TypingSentence> jsonToSentences(String json){
        JSONArray jsonArray;
        HashSet<TypingSentence> sentences;

        try
        {
            jsonArray = new JSONArray(json);
            sentences = new HashSet<>();

            // Loop JSON array
            for(int i=0; i < jsonArray.length(); i++)
            {
                // Get JSON Object from array one by one
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Change JSON to User Object
                TypingSentence typSen = new TypingSentence(jsonObject.getInt("counterIdx"), jsonObject.getString("sentence"));
                // Add sentence to HashSet
                sentences.add(typSen);
            }

            // Return the Sentences HashSet
            return sentences;
        }
        catch(Exception ex)
        {
            Log.e("JSON Deserializer", String.format("Failed to deserialize sentences: %s", json), ex);
            return null;
        }
    }
}
