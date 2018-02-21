package org.mcast.dc.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Darren on 07/12/2016.
 */

public class FileIO {


    public static void saveJsonFile(File f, JSONObject jsonObj)
    {
        // Save JSON Obj to File
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(jsonObj.toString().getBytes());
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJsonFile(File f, JSONArray jsonArray)
    {
        // Save JSON Array to File
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(jsonArray.toString().getBytes());
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadJsonFile(File f)
    {
        // Open JSON file
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                String json = "";
                while ((strLine = br.readLine()) != null) {
                    json = json + strLine;
                }
                in.close();

                return json;
            }
            catch (IOException e) {
                Log.e("FileIO", "Error loading Users JSON file", e);
            }
        }

        return null;
    }
}
