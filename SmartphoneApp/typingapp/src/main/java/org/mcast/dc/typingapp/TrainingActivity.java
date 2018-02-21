package org.mcast.dc.typingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mcast.dc.model.Globals;
import org.mcast.dc.model.TypingSentence;

public class TrainingActivity extends AppCompatActivity {

    private static boolean isFocused;
    public static boolean isFocused() { return isFocused; }

    private TextView tv_sentence;
    private EditText et_training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        // Shift layout based on keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Store layout views
        et_training = (EditText) findViewById(R.id.et_training);
        tv_sentence = (TextView)findViewById(R.id.tv_trainsentence);

        //Check if any sentences are loaded
        if (!Globals.getGlobals().getTypingSentences().isEmpty())
        {
            // Load first sentence in sentence text view
            NextSentence();
        }
        else
            tv_sentence.setText("No sentences currently available! Press done key to exit!");

        et_training.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){

                // Check if Keycode is Enter/Done key
                if((keyCode == event.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))
                {
                    // Display next sentence
                    NextSentence();
                    return true;
                }

                return false;
            }
        });
    }

    private void NextSentence()
    {
        TypingSentence typSent = Globals.getGlobals().getTypingSentences().getByIdx(Globals.getGlobals().getTypSession().getSentenceCounter());
        if  (typSent != null) {
            tv_sentence.setText(typSent.getSentence());
            Globals.getGlobals().getTypSession().incrementSentenceCounter();
            et_training.setText("");
        }
        else
        {
            // If no more sentences exist end session
            Globals.getGlobals().getTypSession().endTypingSession();
            Globals.getGlobals().getTypSession().setFinished();

            // Post toast to UI
            Toast.makeText(getApplicationContext(), "Session Finished", Toast.LENGTH_SHORT).show();

            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Flag activity as focused
        this.isFocused = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Flag activity as not focused
        this.isFocused = false;
    }
}
