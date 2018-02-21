package org.mcast.dc.typingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import org.mcast.dc.model.Globals;
import org.mcast.dc.model.User;

public class UsersActivity extends AppCompatActivity{

    // This is the Adapter being used to display the list's org.mcast.dc.data
    ArrayAdapter<String> adapter;
    // This si the list view in the activity
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ArrayList<String> usersList = new ArrayList<>();
        // Store list view reference
        listView = (ListView) findViewById(R.id.listUsers);

        // Check if there are any users to display
        if (!Globals.getGlobals().getRegisteredUsers().isEmpty())
        {
            // Create List of Users from Registered Users Set
            for (User user : Globals.getGlobals().getRegisteredUsers().getAll()) {
                usersList.add(user.getAlias());
            }
        }

        // Add 'N/A' object in list of users to select no user
        usersList.add("N/A");

        // Load adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                usersList);

        // Set adapter to list view
        listView.setAdapter(adapter);

        // Code Item click listener for list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                // Get which alias was selected
                String selectedAlias = (String)adapter.getItemAtPosition(position);

                // Check if the alias selected was not N/A (N/A is used to go back and select no user)
                if (selectedAlias != "N/A") {
                    // Get User by selected alias
                    User selectedUser = Globals.getGlobals().getRegisteredUsers().getByAlias(selectedAlias);
                    // If selected alias exists
                    if (selectedUser != null) {
                        Globals.getGlobals().setCurrentUser(selectedUser);

                        // Post toast to UI
                        Toast.makeText(getApplicationContext(), String.format("Selected User: %s", selectedAlias), Toast.LENGTH_SHORT).show();
                    }
                    else
                        Globals.getGlobals().setCurrentUser(null);

                }
                else {
                    // No user selected, change user to null
                    Globals.getGlobals().setCurrentUser(null);


                    // Post toast to UI
                    Toast.makeText(getApplicationContext(), "No User Selected", Toast.LENGTH_SHORT).show();
                }


                finish();
            }
        });
    }

}