package org.mcast.dc.model;

/**
 * Created by Darren on 08/03/2017.
 */

public class Feedback {

    private int gripComfort;
    private int keybaordLayoutSimilarity;
    private boolean isVerticalOrientation;
    private int sentenceEase;
    private int sessionTirednessStart;
    private String otherFeedback;

    public Feedback(int gripComfort, int keybaordLayoutSimilarity, boolean isVerticalOrientation, int sentenceEase, int sessionTirednessStart, String otherFeedback) {
        this.gripComfort = gripComfort;
        this.keybaordLayoutSimilarity = keybaordLayoutSimilarity;
        this.isVerticalOrientation = isVerticalOrientation;
        this.sentenceEase = sentenceEase;
        this.sessionTirednessStart = sessionTirednessStart;
        this.otherFeedback = otherFeedback;
    }

    public int getGripComfort() {
        return gripComfort;
    }

    public int getKeybaordLayoutSimilarity() {
        return keybaordLayoutSimilarity;
    }

    public boolean isVerticalOrientation() {
        return isVerticalOrientation;
    }

    public int getSentenceEase() {
        return sentenceEase;
    }

    public int getSessionTirednessStart() {
        return sessionTirednessStart;
    }

    public String getOtherFeedback() {
        return otherFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;

        Feedback feedback = (Feedback) o;

        if (gripComfort != feedback.gripComfort) return false;
        if (keybaordLayoutSimilarity != feedback.keybaordLayoutSimilarity) return false;
        if (isVerticalOrientation != feedback.isVerticalOrientation) return false;
        if (sentenceEase != feedback.sentenceEase) return false;
        if (sessionTirednessStart != feedback.sessionTirednessStart) return false;
        return otherFeedback.equals(feedback.otherFeedback);

    }

    @Override
    public int hashCode() {
        int result = gripComfort;
        result = 31 * result + keybaordLayoutSimilarity;
        result = 31 * result + (isVerticalOrientation ? 1 : 0);
        result = 31 * result + sentenceEase;
        result = 31 * result + sessionTirednessStart;
        result = 31 * result + otherFeedback.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "   gripComfort=" + gripComfort +
                ",  keybaordLayoutSimilarity=" + keybaordLayoutSimilarity +
                ",  isVerticalOrientation=" + isVerticalOrientation +
                ",  sentenceEase=" + sentenceEase +
                ",  sessionTirednessStart=" + sessionTirednessStart +
                ",  otherFeedback='" + otherFeedback + '\'' +
                '}';
    }
}
