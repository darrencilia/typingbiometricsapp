package org.mcast.dc.typingapp;

import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.blackcj.customkeyboard.SoftKeyboard;

import org.json.JSONArray;
import org.mcast.dc.data.FileIO;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.KeyboardInfo;
import org.mcast.dc.model.TypedKey;

import java.io.File;

/**
 * Created by Darren on 24/12/2016.
 */

public class BiometricsIME extends SoftKeyboard implements KeyboardView.OnTouchListener {

    File kInfoFile;
    float currTouchX, currTouchY, currSize;

    @Override
    public void showWindow(boolean showInput) {

        super.showWindow(showInput);

        mPredictionOn = false;
        mCompletionOn = false;

        // Check shared preferences to see if this keyboard info is saved on the device
        SharedPreferences prefs = getSharedPreferences(Globals.SHARED_PREFS_NAME, 0);
        if (!prefs.getBoolean("isKeyboardSaved", false))
        {
            // Set keyboard info file directory
            kInfoFile = new File(getExternalFilesDir(null), Globals.KEYBOARDINFO_FILE_NAME);
            Keyboard keyboard = mInputView.getKeyboard();

            // Get Keyboard info and save it's details in JSON file
            KeyboardInfo kInfo = new KeyboardInfo(keyboard.getKeys(), keyboard.getMinWidth(), keyboard.getHeight());
            JSONArray tmp = JsonSerializer.keyboardInfoToJson(kInfo);
            FileIO.saveJsonFile(kInfoFile, tmp);

            // Mark prefs as saved
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isKeyboardSaved", true);
            editor.commit();

            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Keyboard Info saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPress(int primaryCode) {

        // Only if in training activity
        if (TrainingActivity.isFocused())
        {
            // Save typed key
            TypedKey key = new TypedKey(primaryCode, System.nanoTime(), currTouchX, currTouchY, currSize, true);
            // Add key to session
            Globals.getGlobals().getTypSession().addTypedKey(key);
        }

        if (TestingActivity.isFocused())
        {
            // Save typed key
            TypedKey key = new TypedKey(primaryCode, System.nanoTime(), currTouchX, currTouchY, currSize, true);
            // Add key to testing data
            Globals.getGlobals().getTestData().addTypedKey(key);
        }

        super.onPress(primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
        // Only if in training activity
        if (TrainingActivity.isFocused())
        {
            // Save typed key
            TypedKey key = new TypedKey(primaryCode,  System.nanoTime(), currTouchX, currTouchY, currSize, false);
            // Add key to session
            Globals.getGlobals().getTypSession().addTypedKey(key);
        }

        if (TestingActivity.isFocused())
        {
            // Save typed key
            TypedKey key = new TypedKey(primaryCode,  System.nanoTime(), currTouchX, currTouchY, currSize, false);
            // Add key to session
            Globals.getGlobals().getTestData().addTypedKey(key);
        }

        super.onRelease(primaryCode);
    }

    @Override
    public View onCreateInputView() {
        super.onCreateInputView();
        super.mInputView.setOnTouchListener(this);

        return mInputView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Store current event action type
        int eventAction = event.getAction();

        if (DebugTouchActivity.isFocused()) { // DEBUGGING STUFF
            float tmpX, tmpY, tmpSize, tmpToolMa, tmpToolMi, tmpTouchMa, tmpTouchMi, tmpDownTime, tmpPrecX, tmpPrecY, tmpRawX, tmpRawY;

            tmpX = event.getX();
            tmpY = event.getY();
            tmpRawX = event.getRawX();
            tmpRawY = event.getRawY();
            tmpPrecX = event.getXPrecision();
            tmpPrecY = event.getYPrecision();
            tmpSize = event.getSize();
            tmpToolMa = event.getToolMinor();
            tmpToolMi = event.getToolMajor();
            tmpTouchMa = event.getTouchMajor();
            tmpTouchMi = event.getTouchMinor();
            tmpDownTime = event.getDownTime();

            if (eventAction == MotionEvent.ACTION_DOWN) {
                Log.i("TouchDown", String.format("x='%f' y='%f'  rawX='%f' rawY='%f' precX='%f' precY='%f' size='%f' toolMinor='%f' toolMajor='%f'" +
                        " touchMinor='%f' touchMajor='%f' downtime='%f'"
                        , tmpX, tmpY, tmpRawX, tmpRawY, tmpPrecX, tmpPrecY, tmpSize, tmpToolMi, tmpToolMa, tmpTouchMi, tmpTouchMa, tmpDownTime));
            }

            if (eventAction == MotionEvent.ACTION_UP) {
                Log.i("TouchUp", String.format("x='%f' y='%f'  rawX='%f' rawY='%f' precX='%f' precY='%f' size='%f' toolMinor='%f' toolMajor='%f'" +
                                " touchMinor='%f' touchMajor='%f' downtime='%f'"
                        , tmpX, tmpY, tmpRawX, tmpRawY, tmpPrecX, tmpPrecY, tmpSize, tmpToolMi, tmpToolMa, tmpTouchMi, tmpTouchMa, tmpDownTime));
            }

            DebugTouchActivity.UpdateTouchData(tmpX,tmpY,tmpRawX,tmpRawY,tmpSize,tmpPrecX,tmpPrecY,tmpToolMa);
        }


        currTouchX = event.getRawX();
        currTouchY = event.getRawY();
        currSize = event.getSize();

        return false;
    }
}
