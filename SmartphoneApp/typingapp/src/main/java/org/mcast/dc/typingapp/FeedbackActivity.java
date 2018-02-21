package org.mcast.dc.typingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONObject;
import org.mcast.dc.data.FileIO;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.Feedback;
import org.mcast.dc.model.Globals;

import java.io.File;

public class FeedbackActivity extends AppCompatActivity {


    private File feedbackFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Set feedback file directory
        feedbackFile = new File(getExternalFilesDir(null), "Feedback-" + Globals.getGlobals().getCurrentUser().getEmail() + ".json");
    }

    public void ClickSubmitFeedback(View view)
    {
        // Get feedback values
        int selectedId;
        RadioGroup rg_1 = (RadioGroup) findViewById(R.id.radioFeed1);
        selectedId = rg_1.getCheckedRadioButtonId();
        RadioButton rb_1 = (RadioButton) findViewById(selectedId);
        int feed1 = Integer.parseInt(rb_1.getText().toString());

        RadioGroup rg_2 = (RadioGroup) findViewById(R.id.radioFeed2);
        selectedId = rg_2.getCheckedRadioButtonId();
        RadioButton rb_2 = (RadioButton) findViewById(selectedId);
        int feed2 = Integer.parseInt(rb_2.getText().toString());

        RadioButton rb_3 = (RadioButton) findViewById(R.id.radioFeed3_1);
        boolean feed3 = rb_3.isChecked();


        RadioGroup rg_4 = (RadioGroup) findViewById(R.id.radioFeed4);
        selectedId = rg_4.getCheckedRadioButtonId();
        RadioButton rb_4 = (RadioButton) findViewById(selectedId);
        int feed4 = Integer.parseInt(rb_4.getText().toString());

        RadioGroup rg_5 = (RadioGroup) findViewById(R.id.radioFeed5);
        selectedId = rg_5.getCheckedRadioButtonId();
        RadioButton rb_5 = (RadioButton) findViewById(selectedId);
        int feed5 = Integer.parseInt(rb_5.getText().toString());

        EditText et_other = (EditText) findViewById(R.id.et_feedOther);
        String feed6 = et_other.getText().toString();

        // Create feedback instance from values
        Feedback fb = new Feedback(feed1, feed2, feed3, feed4, feed5, feed6);

        // Transform feedback to JSON
        JSONObject json = JsonSerializer.feedbackToJson(fb);

        // Save to JSON file
        FileIO.saveJsonFile(feedbackFile, json);

        finish();
    }
}
