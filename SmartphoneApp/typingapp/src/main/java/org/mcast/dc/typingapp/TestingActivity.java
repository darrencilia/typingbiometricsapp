package org.mcast.dc.typingapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.mcast.dc.data.JsonSerializer;
import org.mcast.dc.model.Globals;
import org.mcast.dc.model.TestingData;
import org.mcast.dc.model.TypingSentence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TestingActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private static boolean isFocused;
    public static boolean isFocused() { return isFocused; }
    public static TextView tv_classresult;

    Camera mCamera;
    SurfaceView mCameraView;
    EditText et_testing;
    TextView tv_sentence;
    int sentenceCtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        // Initialize
        isFocused = true;
        Globals.getGlobals().setTestData(new TestingData());

        // Shift layout based on keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Request permissions
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 0);

        // Store layout views
        et_testing = (EditText) findViewById(R.id.et_testing);
        tv_sentence = (TextView)findViewById(R.id.tv_testsentence);
        tv_classresult = (TextView)findViewById(R.id.tv_classresult);

        // reset sentence counter
        sentenceCtr = 0;

        //Check if any sentences are loaded
        if (!Globals.getGlobals().getTypingSentences().isEmpty())
        {
            // Load first sentence in sentence text view
            NextSentence();
        }
        else
            tv_sentence.setText("No sentences currently available! Press done key to exit!");

        et_testing.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){

                // Check if Keycode is Enter/Done key
                if((keyCode == event.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))
                {
                    tv_classresult.setText("......");

                    // Get class mode radio button id
                    int selectedId;
                    RadioGroup rg_1 = (RadioGroup) findViewById(R.id.radioClassMode);
                    selectedId = rg_1.getCheckedRadioButtonId();
                    RadioButton rb_1 = (RadioButton) findViewById(selectedId);
                    String selClassMode = rb_1.getText().toString();

                    // Send testing data to Web API after each sentence
                    // This data is than classified on PC model and the results are displayed on computer
                    String json = JsonSerializer.testingDataToJsonString(Globals.getGlobals().getTestData());
                    if (selClassMode.contentEquals("One"))
                        new HttpAsyncTask(json).execute(Globals.CLASSIFIER_URL+"/ClassifyOneHand");
                    else if (selClassMode.contentEquals("Two"))
                        new HttpAsyncTask(json).execute(Globals.CLASSIFIER_URL+"/ClassifyTwoHand");

                    Globals.getGlobals().setTestData(new TestingData());

                    // Display next sentence
                    NextSentence();

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: { // Requested Camera permissions
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Setup camera
                    // Create an instance of Camera
                    mCamera = getCameraInstance();

                    if (mCamera != null)
                    {
                        // Create our Preview view and set it as the content of our activity.
                        mCamera.setDisplayOrientation(90);
                        mCameraView = (SurfaceView) findViewById(R.id.sv_camera);
                        SurfaceHolder surfaceHolder = mCameraView.getHolder();
                        surfaceHolder.addCallback(this);
                        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

                        try {
                            mCamera.setPreviewDisplay(surfaceHolder);
                            mCamera.startPreview();
                        }
                        catch (Exception e)
                        {
                            Log.e("Camera", "Error setting holder");
                        }
                    }
                }
                else { finish(); }
            }
        }
    }

    private void NextSentence()
    {
        TypingSentence typSent = Globals.getGlobals().getTypingSentences().getByIdx(sentenceCtr);
        if  (typSent != null) {
            tv_sentence.setText(typSent.getSentence());
            sentenceCtr++;
            et_testing.setText("");
        }
        else
        {
            sentenceCtr = 0;
            NextSentence();
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public Camera getCameraInstance(){
        Camera c = null;
        try {
            releaseCameraAndPreview();
            int camIdx = getFrontCameraIdx();
            if (camIdx != -1)
                c = Camera.open(camIdx);
            else
                c = Camera.open();
        }
        catch (Exception e){
            Log.e("Camera Error", "Camera in use or doesn't exist!");
        }
        return c;
    }

    private void releaseCameraAndPreview() {
        mCameraView = null;
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public int getFrontCameraIdx() {
        int cameraIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        for (int i = 0; i < cameraCount && cameraIndex == -1; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraIndex = i;
            }
        }

        return cameraIndex;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.e("Camera Error", "Error on surfaceChanged " + e.getMessage());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        isFocused = true;

        if (mCamera == null) {
            try {
                mCamera = Camera.open(getFrontCameraIdx());
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(mCameraView.getHolder());
                mCamera.startPreview();
            } catch (Exception e) {
                Log.e("Camera Error", "Unable to open camera! " + e.getMessage());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        isFocused = false;

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}

class HttpAsyncTask extends AsyncTask<String, String, String> {

    static String JSON_TO_PASS = "";

    public HttpAsyncTask(String jsonToPass)
    {
        this.JSON_TO_PASS = jsonToPass;
    }

    @Override
    protected String doInBackground(String... urls) {
        return POST(urls[0]);
    }

    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(JSON_TO_PASS);
            httpPost.setEntity(se);

            httpPost.setHeader("accept", "json");
            httpPost.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
            {
                result = convertInputStreamToString(inputStream);

                Log.d("json","inputStream result"+result);
            }
            else
                result = "Did not work!";

            Log.d("json","result"+result);

        } catch (Exception e) {
            Log.d("json","e.getLocalizedMessage()"+ e.getLocalizedMessage());
        }

        //  return result
        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        if (TestingActivity.tv_classresult != null) {
            if (result.contains("Owner"))
                TestingActivity.tv_classresult.setText("Owner");
            else if (result.contains("Intruder"))
                TestingActivity.tv_classresult.setText("Intruder");
            else
                TestingActivity.tv_classresult.setText("Error!");
        }
        Log.d("POST", "Post succesfull with result: " + result );
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}