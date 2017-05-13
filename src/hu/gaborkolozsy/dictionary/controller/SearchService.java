/*
 * Copyright © 2016, Gábor Kolozsy
 */

package hu.gaborkolozsy.dictionary.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code SearchService} object.
 * 
 * @author Kolozsy Gábor (gabor.kolozsy.development@gmail.com)
 * 
 * @see ArrayList
 * @see Arrays
 * @see List
 * @since 0.1.1
 */
public class SearchService {
    
    /** 
     * The first hit index. 
     */
    private int hitsFirstIdx;
    
    /** 
     * The last hit index. 
     */
    private int hitsLastIdx;
    
    /** 
     * Maximum displayed hits. 
     */
    private final int FIRST_TEN_DISPLAYED_HIT = 10;

    /**
     * Constructor.
     */
    public SearchService() {
        this.hitsFirstIdx = 0;
        this.hitsLastIdx = 0;
    }
    
    /**
     * Get the hits list.
     * @param keyArray the key array
     * @param key the specified {@code String}
     * @return the list of hits
     */
    public List<String> getHits(String[] keyArray, String key) {
        setIndexes(keyArray, key);
        if (hitsLastIdx - hitsFirstIdx > FIRST_TEN_DISPLAYED_HIT) {
            hitsLastIdx = hitsFirstIdx + FIRST_TEN_DISPLAYED_HIT;
        }
        
        List<String> hits = new ArrayList<>();
        for (int i = hitsFirstIdx; i < hitsLastIdx; i++) { 
            hits.add(keyArray[i]);
        }
        return hits;
    }
    
    /**
     * Return the counting hits.
     * @param keyArray the key array
     * @param key the specified {@code String}
     * @return number of hits
     */
    public int getMaxHits(String[] keyArray, String key) {
        setIndexes(keyArray, key);
        return hitsLastIdx - hitsFirstIdx;
    }
    
    /**
     * Set the first hit's index and last hit's index, where the word begin 
     * with the specified key.
     * @param keyArray the actual dictionary's key array
     * @param key the specified key
     */
    private void setIndexes(String[] keyArray, String key) {
        hitsFirstIdx = searchMatch(keyArray, key);
        hitsLastIdx = searchMatch(keyArray, key + '\uFFFF');
    }
    
    /**
     * Search the first/"last" index of specified {@code String} 
     * in the key array.
     * @param key the specified {@code String}
     * @param keyArray the key array
     * @return the first index by the specified {@code String}
     */
    private int searchMatch(String[] keyArray, String key) {
        int index = Arrays.binarySearch(keyArray, key);
        if (index < 0) {
            index *= -1;
            index -= 1;
        }
        return index;
    }
}
