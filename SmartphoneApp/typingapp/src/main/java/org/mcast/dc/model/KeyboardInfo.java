package org.mcast.dc.model;

import android.inputmethodservice.Keyboard.Key;

import java.util.List;

/**
 * Created by Darren on 13/12/2016.
 */

public class KeyboardInfo {

    private List<Key> keys;
    private int fullWidth;
    private int fullHeight;

    public KeyboardInfo(List<Key> keys, int fullWidth, int fullHeight) {
        this.keys = keys;
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
    }

    public List<Key> getKeys() { return keys; }

    public int getFullWidth() { return fullWidth; }

    public int getFullHeight() { return fullHeight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyboardInfo)) return false;

        KeyboardInfo that = (KeyboardInfo) o;

        if (fullWidth != that.fullWidth) return false;
        if (fullHeight != that.fullHeight) return false;
        return keys.equals(that.keys);
    }

    @Override
    public int hashCode() {
        int result = keys.hashCode();
        result = 31 * result + fullWidth;
        result = 31 * result + fullHeight;
        return result;
    }

    @Override
    public String toString() {
        return "KeyboardInfo{" +
                "keys=" + keys +
                ", fullWidth=" + fullWidth +
                ", fullHeight=" + fullHeight +
                '}';
    }
}
