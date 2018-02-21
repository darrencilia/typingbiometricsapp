package org.mcast.dc.typingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import org.mcast.dc.data.FileIO;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.TypingSession;
import org.mcast.dc.model.enums.TypingMode;

public class TrainingSetupActivity extends AppCompatActivity {

    // File for saving training session
    File sessionFile;
    // This is the Adapter being used to display the list's org.mcast.dc.data
    ArrayAdapter<String> adapter;
    // Check if a session is required to be continued (maybe was closed by mistake)
    boolean continueSession;
    // Check if a session is required to be saved
    boolean saveSession;
    // / Store view references
    Button btnSessionAction;
    TextView tv_memo;
    TextView tv_spinner;
    Spinner spinnerTypMode;

    // Memo strings
    String continueMemo = "A session has already been started and needs to be finished before you can" +
            "save the org.mcast.dc.data.\n\nClick Continue Session to continue the previously started session!";
    String saveMemo = "Session was finished successfully. Please press Save to save the Typing Session to JSON file.";
    String startMemo = "Hi and thanks for taking the time to help me in my research." +
    "\n\nThe task is quite simple. All you need to do is Start the test and finish it in all 4 different modes (selectable below). " +
        "\n\nOnce a task is started you will be presented with sentence after sentence, simply type the sentence in the most natural way" +
            "possible and than press key {done/enter} for the next sentence. When all sentences are finished, you will be able to save the results." +
            "\n\nAt the end of this task, kindly take the feedback questionaire found in a separate section of the Main Menu." +
            "\n\nThank you again for your time in helping me with my research!!";

    // Variables for checking if the Typing Session finished saving variables (synchronized)
    private boolean canSave;
    Handler handlerCanSave = new Handler();
    Runnable runnableCanSave = new Runnable() {

        // Some variables to check if session can be saved
        int oldSessionHashCode = 0;
        int newSessionHashCode;
        int ctr = 0;

        @Override
        public void run() {
            newSessionHashCode = Globals.getGlobals().getTypSession().hashCode();
            // Check if the typing session hashcode changed
            if (newSessionHashCode != oldSessionHashCode) {
                oldSessionHashCode = newSessionHashCode;
                // Show a loading dot by dot string
                if (ctr < 3) {
                    String btnText = "Finalizing ";
                    for (int i = 0; i < ctr + 1; i++)
                        btnText += ".";
                    ctr++;
                    btnSessionAction.setText(btnText);
                    btnSessionAction.setEnabled(false);
                }
                else
                    ctr = 0;

                // Post this every 1 second to check can save
                handlerCanSave.postDelayed(this, 1000);
            }
            else
            {
                btnSessionAction.setText("Save Session");
                btnSessionAction.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_setup);

        // Store view references
        tv_memo = (TextView) findViewById(R.id.tv_memo);
        tv_spinner = (TextView) findViewById(R.id.tv_spinner);

        tv_memo.setText(startMemo);

        // Setup handler for can save
        handlerCanSave = new Handler();

        // Setup Typing Mode spinner
        // ArrayList of type modes
        ArrayList<String> typModesList = new ArrayList<>();
        // Store spinner reference
        spinnerTypMode = (Spinner) findViewById(R.id.spinnerTypMode);
        // Store button reference
        btnSessionAction = (Button) findViewById(R.id.btnSessionAction);
        // Fill List of Typing Modes from TypingMode custom enum
        for (TypingMode typMode : TypingMode.values()) {
            typModesList.add(typMode.toString());
        }

        // Load adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                typModesList);

        // Set adapter to list view
        spinnerTypMode.setAdapter(adapter);
    }

    public void ClickSessionAction(View view)
    {
        // Check that there was no previous session that is still unfinished or not saved
        if ( continueSession )
        {
            // Continue session if was previously interrupted
            Intent intent = new Intent(TrainingSetupActivity.this, TrainingActivity.class);
            startActivity(intent);

            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Session Continued", Toast.LENGTH_SHORT).show();

        }
        else if (saveSession)
        {
            // Start saving to file JSON
            tv_memo.setText(saveMemo);

            btnSessionAction.setText("Saving...");

            // Create file to save in
            sessionFile = new File(getExternalFilesDir(null), "session-" + Globals.getGlobals().getDeviceId() +
                                                                    "-" + Globals.getGlobals().getCurrentUser().getEmail() +
                                                                    "-" + System.nanoTime() + ".json");

            // Save typing session in serialized by json to file
            FileIO.saveJsonFile(sessionFile, JsonSerializer.typingSessionToJson(Globals.getGlobals().getTypSession()));

            Globals.getGlobals().getTypSession().setSaved();

            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Session Saved to file", Toast.LENGTH_SHORT).show();

            finish();
        }
        else {
            // Get current selected TypingMode
            TypingMode currTypMode = TypingMode.getTypingModeByName(spinnerTypMode.getSelectedItem().toString());

            // Setup new Typing Session
            Globals.getGlobals().setTypSession(new TypingSession(Globals.getGlobals().getCurrentUser(), currTypMode, System.nanoTime()));

            Intent intent = new Intent(TrainingSetupActivity.this, TrainingActivity.class);
            startActivity(intent);


            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Session Started", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Globals.getGlobals().getTypSession() != null) {
            continueSession = !Globals.getGlobals().getTypSession().isFinished() && !Globals.getGlobals().getTypingSentences().isEmpty();
            saveSession = !Globals.getGlobals().getTypSession().isSaved() && !Globals.getGlobals().getTypingSentences().isEmpty();

            tv_spinner.setVisibility(View.INVISIBLE);
            spinnerTypMode.setVisibility(View.INVISIBLE);

            if (continueSession) {
                tv_memo.setText(continueMemo);
                btnSessionAction.setText("Continue Session");
            }
            else if (saveSession)
                handlerCanSave.postDelayed(runnableCanSave, 0);
            else
            {
                tv_spinner.setVisibility(View.VISIBLE);
                spinnerTypMode.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            tv_memo.setText(startMemo);
            tv_spinner.setVisibility(View.VISIBLE);
            spinnerTypMode.setVisibility(View.VISIBLE);
        }
    }
}