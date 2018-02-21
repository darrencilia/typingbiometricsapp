package org.mcast.dc.model;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

/**
 * Created by Darren on 13/12/2016.
 */

public class DeviceInfo {

    private String id;
    private String model;
    private String manufacturer;
    private String device;
    private String osName;
    private String osVersion;
    private int screenWidth;
    private int screenHeight;

    public DeviceInfo (DisplayMetrics dMetrics)
    {
        this.id = Globals.getGlobals().getDeviceId();
        this.model = Build.MODEL;
        this.manufacturer = Build.MANUFACTURER;
        this.device = Build.DEVICE;

        this.osName = System.getProperty("os.name");
        this.osVersion = System.getProperty("os.version");

        this.screenWidth = dMetrics.widthPixels;
        this.screenHeight = dMetrics.heightPixels;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDevice() {
        return device;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public int getScreenWidth() { return screenWidth; }

    public int getScreenHeight() { return screenHeight; }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id='" + id + '\'' +
                ", org.mcast.dc.model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", device='" + device + '\'' +
                ", osName='" + osName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}';
    }
}
