package org.mcast.dc.data;

import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

import org.mcast.dc.model.DeviceInfo;
import org.mcast.dc.model.Feedback;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.KeyboardInfo;
import org.mcast.dc.model.TestingData;
import org.mcast.dc.model.TypedKey;
import org.mcast.dc.model.TypingSession;
import org.mcast.dc.model.User;

/**
 * Created by Darren on 09/12/2016.
 */

public class JsonSerializer {

    public static JSONArray usersToJson(final Set<User> users)
    {
        JSONArray result = new JSONArray();
        try
        {
            // Loop all Users in Set
            for (User user : users)
            {
                // Change User to JSON Object
                JSONObject obj = new JSONObject();
                obj.put("alias",user.getAlias());
                obj.put("email", user.getEmail());
                obj.put("age", user.getAge());
                obj.put("isMale", user.getIsMale());
                obj.put("isRightHanded", user.getIsRightHanded());

                // Add JSON Object to Array
                result.put(obj);
            }
            return result;
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Users to JSON", ex);
            return null;
        }
    }


    public static String testingDataToJsonString(final TestingData testingData)
    {
        JSONArray result = new JSONArray();
        try {
            // Put typed keys into json array
            JSONArray typedKeysArray = new JSONArray();
            for (TypedKey key : testingData.getTypedKeys())
            {
                JSONObject typedKeyObj = new JSONObject();
                typedKeyObj.put("keyCounter",key.getKeyCounter());
                typedKeyObj.put("keyCode", key.getKeyCode());
                typedKeyObj.put("time", key.getTime());
                typedKeyObj.put("xPos", key.getxPos());
                typedKeyObj.put("yPos", key.getyPos());
                typedKeyObj.put("surfaceArea", key.getSurfaceArea());
                typedKeyObj.put("isDown", key.isDown());

                typedKeysArray.put(typedKeyObj);
            }
            // Put all typed keys into main json array after session details
            return typedKeysArray.toString();
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Testing Data to JSON", ex);
            return null;
        }
    }

    public static JSONArray typingSessionToJson(final TypingSession typingSession)
    {
        JSONArray result = new JSONArray();
        try {
            // Put Session details in json object and than in json array
            JSONObject detailsObj = new JSONObject();
            detailsObj.put("deviceId", Globals.getGlobals().getDeviceId());
            detailsObj.put("userEmail", typingSession.getUser().getEmail());
            detailsObj.put("userAlias", typingSession.getUser().getAlias());
            detailsObj.put("keyCounter", typingSession.getKeyCounter());
            detailsObj.put("sentenceCounter", typingSession.getSentenceCounter());
            detailsObj.put("startTime", typingSession.getStartTime());
            detailsObj.put("endTime", typingSession.getEndTime());
            detailsObj.put("typingMode", typingSession.getTypingMode());

            result.put(detailsObj);

            // Put typed keys into json array
            JSONArray typedKeysArray = new JSONArray();
            for (TypedKey key : typingSession.getTypedKeys())
            {
                JSONObject typedKeyObj = new JSONObject();
                typedKeyObj.put("keyCounter",key.getKeyCounter());
                typedKeyObj.put("keyCode", key.getKeyCode());
                typedKeyObj.put("time", key.getTime());
                typedKeyObj.put("xPos", key.getxPos());
                typedKeyObj.put("yPos", key.getyPos());
                typedKeyObj.put("surfaceArea", key.getSurfaceArea());
                typedKeyObj.put("isDown", key.isDown());

                typedKeysArray.put(typedKeyObj);
            }
            // Put all typed keys into main json array after session details
            result.put(typedKeysArray);

            return result;
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Typing Session to JSON", ex);
            return null;
        }
    }

    public static JSONObject deviceInfoToJson(DeviceInfo dInfo)
    {
        JSONObject result = new JSONObject();
        try
        {
            result.put("deviceId", Globals.getGlobals().getDeviceId());
            result.put("model", dInfo.getModel());
            result.put("manufacturer", dInfo.getManufacturer());
            result.put("device", dInfo.getDevice());
            result.put("osName", dInfo.getOsName());
            result.put("osVersion", dInfo.getOsVersion());
            result.put("screenWidth", dInfo.getScreenWidth());
            result.put("screenHeight", dInfo.getScreenHeight());

            return result;
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Device Info to JSON", ex);
            return null;
        }
    }

    public static JSONArray keyboardInfoToJson(KeyboardInfo kInfo)
    {
        JSONArray result = new JSONArray();
        try {
            // Put Keyboard details in json object and than in json array
            JSONObject detailsObj = new JSONObject();
            detailsObj.put("deviceId", Globals.getGlobals().getDeviceId());
            detailsObj.put("fullWidth", kInfo.getFullWidth());
            detailsObj.put("fullHeight", kInfo.getFullHeight());
            result.put(detailsObj);

            // Put keyboard keys into json array
            JSONArray keyboardKeysArray = new JSONArray();
            for (Keyboard.Key k : kInfo.getKeys())
            {
                // Every seperate code will be a JSON object
                for (int code: k.codes ) {
                    JSONObject kKeyObj = new JSONObject();
                    kKeyObj.put("code", code);
                    kKeyObj.put("x", k.x);
                    kKeyObj.put("y", k.y);
                    kKeyObj.put("width", k.width);
                    kKeyObj.put("height", k.height);
                    kKeyObj.put("label", k.label);
                    keyboardKeysArray.put(kKeyObj);
                }
            }

            // Put all keyboard keys into main json array after keyboard other details
            result.put(keyboardKeysArray);

            return result;
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Keyboard Info to JSON", ex);
            return null;
        }
    }

    public static JSONObject feedbackToJson(final Feedback feedback)
    {
        try
        {
            // Change User to JSON Object
            JSONObject obj = new JSONObject();
            obj.put("gripComfort",feedback.getGripComfort());
            obj.put("keyboardLayoutSimilarity", feedback.getKeybaordLayoutSimilarity());
            obj.put("isVerticalOrientation", feedback.isVerticalOrientation());
            obj.put("sentenceEase", feedback.getSentenceEase());
            obj.put("sessionTirednessStart", feedback.getSessionTirednessStart());
            obj.put("otherFeedback", feedback.getOtherFeedback());

            // Add JSON Object to Array
            return obj;
        }
        catch (Exception ex)
        {
            Log.e("JSON Serializer", "Failed to serialize Feedback to JSON", ex);
            return null;
        }
    }

}
