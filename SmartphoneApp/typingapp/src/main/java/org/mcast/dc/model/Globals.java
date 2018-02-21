package org.mcast.dc.model;

import android.os.Build;

/**
 * Created by Darren on 12/12/2016.
 */

public class Globals {

    // Singleton
    protected Globals(){}
    private static Globals mInstance = null;

    public static synchronized Globals getGlobals(){
        if(null == mInstance){
            mInstance = new Globals();
        }
        return mInstance;
    }

    //List of Registered Users
    private RegisteredUsers registeredUsers = new RegisteredUsers();
    // List of Typing Sentences
    private TypingSentences typingSentences = new TypingSentences();
    // The current user that is using the app
    private User currentUser = null;
    // The current typing session org.mcast.dc.data
    private TypingSession typSession = null;
    // The current Testing Data registered
    private TestingData testData = null;

    // Methods
    public RegisteredUsers getRegisteredUsers() { return registeredUsers; }

    public TypingSentences getTypingSentences() { return typingSentences; }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User u)
    {
        if (u == null)
            currentUser = null;
        else
            currentUser = u;
    }

    public TypingSession getTypSession() { return typSession; }

    public void setTypSession(TypingSession typSession) {
        this.typSession = typSession;
    }

    public TestingData getTestData() {
        return testData;
    }

    public void setTestData(TestingData testData) {
        this.testData = testData;
    }

    private String devId;
    public void setDeviceId(String id){
        devId = id;
        USERS_FILE_NAME = "users-" + id + ".json";
        DEVICEINFO_FILE_NAME = "deviceinfo-" + id + ".json";
        KEYBOARDINFO_FILE_NAME = "keyboardinfo-" + id + ".json";
    }
    public String getDeviceId(){ return devId; }


    // File names
    public static final String CLASSIFIER_URL = "http://192.168.0.13:45455/api/classify";
    public static String USERS_FILE_NAME;
    public static String DEVICEINFO_FILE_NAME;
    public static String KEYBOARDINFO_FILE_NAME;
    public static final String SENTENCES_FILE_NAME = "sentences.json";

    // Shared preferences name
    public static final String SHARED_PREFS_NAME = "TypAppSharedPrefs";
}
