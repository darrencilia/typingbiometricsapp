package org.mcast.dc.model;

/**
 * Created by Darren on 16/12/2016.
 */

public class TypingSentence {

    private int counterIdx;
    private String sentence;

    // Constructor
    public TypingSentence(int counterIdx, String sentence) {
        this.counterIdx = counterIdx;
        this.sentence = sentence;
    }

    // Getters
    public int getCounterIdx() { return counterIdx; }

    public String getSentence() { return sentence; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypingSentence)) return false;

        TypingSentence that = (TypingSentence) o;

        if (counterIdx != that.counterIdx) return false;
        return sentence.equals(that.sentence);
    }

    @Override
    public int hashCode() {
        int result = counterIdx;
        result = 31 * result + sentence.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TypingSentence{" +
                "counterIdx=" + counterIdx +
                ", sentence='" + sentence + '\'' +
                '}';
    }
}
