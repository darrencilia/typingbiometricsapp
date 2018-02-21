package org.mcast.dc.model;

import java.util.TreeSet;

import org.mcast.dc.model.enums.TypingMode;

/**
 * Created by Darren on 08/12/2016.
 */

public class TypingSession {

    // Holds the counter of keys typed
    private int keyCounter;
    // Holds the counter of sentences finished
    private int sentenceCounter;
    // Set of typed keys in the session
    private TreeSet<TypedKey> typedKeys;
    // User of the session
    private User user;
    // Typing Mode of the session
    private TypingMode typingMode;
    // Timings
    private long startTime;
    private long endTime;

    // Session security variables to make sure org.mcast.dc.data was saved succesfully
    // These variables will be used and checked before a new session starts
    // They must be both true for a new session to start.
    private boolean isSaved;
    private boolean isFinished;

    // Constructor
    public TypingSession(User user, TypingMode typingMode, long startTime) {
        // Setup initial variables that will be final
        this.keyCounter =0;
        this.sentenceCounter=0;
        this.user = user;
        this.typingMode = typingMode;
        this.startTime = startTime;

        // Security variables
        this.isSaved = false;
        this.isFinished = false;

        // Setup variables that will update throughout the session
        typedKeys = new TreeSet<TypedKey>();
    }

    // Getters
    public int getKeyCounter() { return keyCounter; }

    public int getSentenceCounter() { return sentenceCounter; }

    public TreeSet<TypedKey> getTypedKeys() {
        return typedKeys;
    }

    public User getUser() {
        return user;
    }

    public TypingMode getTypingMode() {
        return typingMode;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isFinished() { return isFinished; }

    public boolean isSaved() { return isSaved; }

    // Setters
    public void setSaved() { isSaved = true; }

    public void setFinished() { isFinished = true; }

    // Methods
    public void endTypingSession()
    {
        endTime = System.nanoTime();
        isFinished = true;
    }

    public synchronized void addTypedKey(TypedKey key)
    {
        key.setKeyCounter(++keyCounter);
        typedKeys.add(key);
    }

    public void incrementSentenceCounter() { this.sentenceCounter++; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypingSession)) return false;

        TypingSession that = (TypingSession) o;

        if (keyCounter != that.keyCounter) return false;
        if (sentenceCounter != that.sentenceCounter) return false;
        if (startTime != that.startTime) return false;
        if (endTime != that.endTime) return false;
        if (typedKeys != null ? !typedKeys.equals(that.typedKeys) : that.typedKeys != null)
            return false;
        if (!user.equals(that.user)) return false;
        return typingMode == that.typingMode;

    }

    @Override
    public int hashCode() {
        int result = keyCounter;
        result = 31 * result + sentenceCounter;
        result = 31 * result + (typedKeys != null ? typedKeys.hashCode() : 0);
        result = 31 * result + user.hashCode();
        result = 31 * result + typingMode.hashCode();
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (endTime ^ (endTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TypingSession{" +
                "keyCounter=" + keyCounter +
                ", sentenceCounter=" + sentenceCounter +
                ", typedKeys=" + typedKeys +
                ", user=" + user +
                ", typingMode=" + typingMode +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
