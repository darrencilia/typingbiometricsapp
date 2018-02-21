package org.mcast.dc.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Darren on 16/12/2016.
 */

public class TypingSentences {
    private HashSet<TypingSentence> entries;
    private HashMap<Integer, TypingSentence> byIdx;

    // Constructor
    public TypingSentences() {
        this.entries = new HashSet<>();
        this.byIdx = new HashMap<>();
    }

    // Methods
    public boolean add(TypingSentence sentence) {
        if (sentence.getSentence() != null) {
            byIdx.put(sentence.getCounterIdx(), sentence);
        }

        return entries.add(sentence);
    }

    public boolean addAll(Set<TypingSentence> sentences) {
        for (TypingSentence sentence : sentences) {
            if (!add(sentence))
                return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return entries.isEmpty();
    }

    public TypingSentence getByIdx(int idx) {
        return byIdx.get(idx);
    }


}
