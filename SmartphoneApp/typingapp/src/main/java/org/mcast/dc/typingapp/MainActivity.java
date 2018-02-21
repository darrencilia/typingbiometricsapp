package org.mcast.dc.typingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.HashSet;

import org.mcast.dc.data.FileIO;
import org.mcast.dc.data.JsonDeserializer;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.DeviceInfo;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.TypingSentence;
import org.mcast.dc.model.User;

public class MainActivity extends AppCompatActivity {

    // Files
    private File usersFile;
    private File dInfoFile;
    private File sentencesFile;

    // Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set android ID first thing
        Globals.getGlobals().setDeviceId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        // Initialize files
        usersFile = new File(getExternalFilesDir(null), Globals.USERS_FILE_NAME);
        dInfoFile = new File(getExternalFilesDir(null), Globals.DEVICEINFO_FILE_NAME);
        sentencesFile = new File(getExternalFilesDir(null), Globals.SENTENCES_FILE_NAME);

        // Check shared preferences to see if this android device's details are saved on the device
        SharedPreferences prefs = getSharedPreferences(Globals.SHARED_PREFS_NAME, 0);
        if (!prefs.getBoolean("isDeviceSaved", false))
        {
            // Get Device info and save it's details in JSON file

            // Get display metrics
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // Serialize and Save to file device info
            JSONObject dInfoJsonObj = JsonSerializer.deviceInfoToJson(new DeviceInfo(metrics));
            FileIO.saveJsonFile(dInfoFile, dInfoJsonObj);

            // Mark as saved
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isDeviceSaved", true);
            editor.commit();

            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Device Info saved", Toast.LENGTH_SHORT).show();
        }

        // Load registered users from file
        if (Globals.getGlobals().getRegisteredUsers().isEmpty()){
            // Load users from File
            String json = FileIO.loadJsonFile(usersFile);
            // If JSON file is null, there's nothing to load
            if (json != null)
            {
                // Deserialize JSON file into a Users HashSet
                HashSet<User> tmp = JsonDeserializer.jsonToUsers(json);
                // Add loaded users hashSet to registered users
                Globals.getGlobals().getRegisteredUsers().addAll(tmp);
            }
        }

        // Load sentences from file
        if (Globals.getGlobals().getTypingSentences().isEmpty()) {
            // Load sentences from File
            String json = FileIO.loadJsonFile(sentencesFile);
            // If JSON file is null, there's nothing to load
            if (json != null)
            {
                // Deserialize JSON file into a Sentences HashSet
                HashSet<TypingSentence> tmp = JsonDeserializer.jsonToSentences(json);
                // Add loaded sentences hashSet to typing sentences
                Globals.getGlobals().getTypingSentences().addAll(tmp);
            }
        }

        RefreshUserButtons();
    }

    @Override
    protected void onResume() {
        // Resume activity
        super.onResume();

        // Update current user
        TextView tv_user = (TextView) findViewById(R.id.tvCurrentUser);
        if (Globals.getGlobals().getCurrentUser() != null)
            tv_user.setText(String.format("User: %s", Globals.getGlobals().getCurrentUser().getAlias()));
        else
            tv_user.setText("User: N/A");

        // Update Buttons Enabled for user
        RefreshUserButtons();
    }

    // Methods
    public void ClickRegister(View view)
    {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void ClickUsers(View view)
    {
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        startActivity(intent);
    }

    public void ClickTraining(View view)
    {
        Intent intent = new Intent(MainActivity.this, TrainingSetupActivity.class);
        startActivity(intent);
    }

    public void ClickTesting(View view)
    {
        Intent intent = new Intent(MainActivity.this, TestingActivity.class);
        startActivity(intent);
    }

    public void ClickFeedback(View view)
    {
        Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void ClickDebugTouch(View view)
    {
        Intent intent = new Intent(MainActivity.this, DebugTouchActivity.class);
        startActivity(intent);
    }

    public void RefreshUserButtons()
    {
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnTesting = (Button) findViewById(R.id.btnTesting);
        Button btnTraining = (Button) findViewById(R.id.btnTraining);
        Button btnFeedback = (Button) findViewById(R.id.btnFeedback);

        if (Globals.getGlobals().getCurrentUser() != null) {
            btnRegister.setEnabled(false);
            btnTesting.setEnabled(true);
            btnTraining.setEnabled(true);
            btnFeedback.setEnabled(true);
        }
        else {
            btnRegister.setEnabled(true);
            btnTesting.setEnabled(false);
            btnTraining.setEnabled(false);
            btnFeedback.setEnabled(false);
        }
    }
}
