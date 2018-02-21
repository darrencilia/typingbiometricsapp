package org.mcast.dc.model;

/**
 * Created by Darren on 08/12/2016.
 */

public class TypedKey implements Comparable {

    private Integer keyCounter;
    private int keyCode;
    private long time;
    private float xPos, yPos, surfaceArea;
    private boolean isDown;

    // Constructor
    public TypedKey(int keyCode, long time, float xPos, float yPos, float surfaceArea, boolean isDown) {
        this.keyCounter=0;
        this.keyCode = keyCode;
        this.time = time;
        this.xPos = xPos;
        this.yPos = yPos;
        this.surfaceArea = surfaceArea;
        this.isDown = isDown;
    }

    // Setters
    public void setKeyCounter(int ctr) {
        this.keyCounter = ctr;
    }

    // Getters
    public Integer getKeyCounter() { return keyCounter; }

    public int getKeyCode() {
        return keyCode;
    }

    public long getTime() {
        return time;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public float getSurfaceArea() {
        return surfaceArea;
    }

    public boolean isDown() {
        return isDown;
    }

    @Override
    public String toString() {
        return "TypedKey{" +
                "keyCounter=" + keyCounter +
                ", keyCode=" + keyCode +
                ", time=" + time +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", surfaceArea=" + surfaceArea +
                ", isDown=" + isDown +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypedKey)) return false;

        TypedKey typedKey = (TypedKey) o;

        if (keyCounter != typedKey.keyCounter) return false;
        if (keyCode != typedKey.keyCode) return false;
        if (time != typedKey.time) return false;
        if (Float.compare(typedKey.xPos, xPos) != 0) return false;
        if (Float.compare(typedKey.yPos, yPos) != 0) return false;
        if (Float.compare(typedKey.surfaceArea, surfaceArea) != 0) return false;
        return isDown == typedKey.isDown;

    }

    @Override
    public int hashCode() {
        int result = keyCounter;
        result = 31 * result + keyCode;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (xPos != +0.0f ? Float.floatToIntBits(xPos) : 0);
        result = 31 * result + (yPos != +0.0f ? Float.floatToIntBits(yPos) : 0);
        result = 31 * result + (surfaceArea != +0.0f ? Float.floatToIntBits(surfaceArea) : 0);
        result = 31 * result + (isDown ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (! (o instanceof TypedKey))
            return  -1;

        TypedKey other = (TypedKey)o;
        return this.keyCounter.compareTo(other.getKeyCounter());
    }
}
