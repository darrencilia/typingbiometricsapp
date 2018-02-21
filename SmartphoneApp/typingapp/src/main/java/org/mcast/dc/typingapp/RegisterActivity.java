package org.mcast.dc.typingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.File;

import org.mcast.dc.data.FileIO;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.User;

public class RegisterActivity extends AppCompatActivity {


    private File usersFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set users file directory
        usersFile = new File(getExternalFilesDir(null), Globals.USERS_FILE_NAME);

        NumberPicker nPickerAge = (NumberPicker) findViewById(R.id.nPickerAge);
        nPickerAge.setMaxValue(60);
        nPickerAge.setMinValue(16);
        nPickerAge.setValue(25);
    }

    public void ClickRegisterUser(View view)
    {
        EditText et_alias = (EditText) findViewById(R.id.txtAlias);
        EditText et_email = (EditText) findViewById(R.id.txtEmail);
        NumberPicker np_age = (NumberPicker) findViewById(R.id.nPickerAge);
        RadioButton rb_isMale = (RadioButton) findViewById((R.id.radioM));
        RadioButton rb_isRightHanded = (RadioButton) findViewById(R.id.radioHandR);

        String alias = et_alias.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        int age = np_age.getValue();
        boolean isMale = rb_isMale.isChecked();
        boolean isRightHanded = rb_isRightHanded.isChecked();

        // Validation
        boolean valid = true;
        if ( alias.isEmpty()) {
            et_alias.setError("* Required!");
            valid = false;
        }
        else if ( Globals.getGlobals().getRegisteredUsers().getByAlias(alias) != null) {
            et_alias.setError("* ALIAS already exists!");
            valid = false;
        }

        if ( email.isEmpty()) {
            et_email.setError("* Required!");
            valid = false;
        }
        else if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("* Must be a valid EMAIL address!");
            valid = false;
        }
        else if ( Globals.getGlobals().getRegisteredUsers().getByEmail(email) != null) {
            et_email.setError("* EMAIL address already exists!");
            valid = false;
        }


        // Check that all validation was correct
        if (valid) {
            // Add User
            User newUser = new User(alias, email, age, isMale, isRightHanded);
            Globals.getGlobals().getRegisteredUsers().add(newUser);

            SaveNewUsersFile();

            // Post toast to UI
            Toast.makeText(getApplicationContext(), String.format("Registered User: %s", newUser.getAlias()), Toast.LENGTH_SHORT).show();

            // Finish this activity
            finish();
        }
        // If not valid, show errors and let user fix them
        return;
    }

    public void SaveNewUsersFile()
    {
        // Save registered users to file
        if (!Globals.getGlobals().getRegisteredUsers().isEmpty()){
            // Serialize all registered users into JSON
            JSONArray jsonUsers = JsonSerializer.usersToJson(Globals.getGlobals().getRegisteredUsers().getAll());
            // Save JSON to external storage file
            FileIO.saveJsonFile(usersFile, jsonUsers);
        };
    }
}
