package org.mcast.dc.model;

import org.mcast.dc.model.enums.TypingMode;

import java.util.TreeSet;

/**
 * Created by Darren on 08/12/2016.
 */

public class TestingData {

    // Holds the counter of keys typed
    private int keyCounter;
    // Set of typed keys in the session
    private TreeSet<TypedKey> typedKeys;


    // Constructor
    public TestingData() {
        // Setup initial variables that will be final
        this.keyCounter =0;

        // Setup variables that will update throughout the session
        typedKeys = new TreeSet<TypedKey>();
    }

    // Getters
    public int getKeyCounter() { return keyCounter; }

    public TreeSet<TypedKey> getTypedKeys() {
        return typedKeys;
    }

    public synchronized void addTypedKey(TypedKey key)
    {
        key.setKeyCounter(++keyCounter);
        typedKeys.add(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestingData)) return false;

        TestingData that = (TestingData) o;

        if (keyCounter != that.keyCounter) return false;
        return typedKeys != null ? typedKeys.equals(that.typedKeys) : that.typedKeys == null;

    }

    @Override
    public int hashCode() {
        int result = keyCounter;
        result = 31 * result + (typedKeys != null ? typedKeys.hashCode() : 0);
        return result;
    }
}
