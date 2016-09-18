/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller;

import hu.gaborkolozsy.dictionary.model.Search;
import java.util.List;

/**
 * {@code SearchService} object.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.model.Search
 * @see java.util.List
 * @since 0.1.1
 */
public class SearchService {
    
    /** {@ Search} object. */
    private final Search search = new Search();
    
    /** Maximum displayed hits. */
    private final int FIRSTTENDISPLAYEDHIT = 10;
    
    /**
     * Return the list of hits.
     * @return hits list
     */
    public List<String> getHits() {
        return search.getHits();
    }
    
    /**
     * Set the hits list.
     * @param s the specified {@code String}
     * @param keyArray the key array
     */
    public void setHits(String s, String[] keyArray) {
        int hitsFirstIdx = searchFirstMatch(s, keyArray);
        int hitsLastIdx = searchFirstMatch(s + '\uFFFF', keyArray);
        
        if (hitsLastIdx - hitsFirstIdx > FIRSTTENDISPLAYEDHIT) {
            hitsLastIdx = hitsFirstIdx + FIRSTTENDISPLAYEDHIT;
        }

        search.clear();
        for (int i = hitsFirstIdx; i < hitsLastIdx; i++) { 
            search.addHit(keyArray[i]);
        }
    }
    
    /**
     * Return the counting hits.
     * @return number of hits
     */
    public int getMaxHits() {
        return search.getMaxHits();
    }
    
    /**
     * The maximum number of hits by the specified {@code String}.
     * @param s the specified {@code String}
     * @param keyArray the key array
     */
    public void setMaxHits(String s, String[] keyArray) {
        int hitsFirstIdx = searchFirstMatch(s, keyArray);
        int hitsLastIdx = searchFirstMatch(s + '\uFFFF', keyArray);
        
        search.setMaxHits(hitsLastIdx - hitsFirstIdx);
    }
    
    /**
     * Search the first index of specified {@code String} in the key array.
     * @param str the specified {@code String}
     * @param keyArray the key array
     * @return the first index by the specified {@code String}
     */
    private int searchFirstMatch(String str, String[] keyArray) {
        int first = 0;
        int last = keyArray.length - 1;
        int result;
        
        while (first <= last) {
            int middle = (first + last) / 2;
            
            if ((result = keyArray[middle].compareTo(str)) < 0) {
                first = middle + 1;
            } else {
                if (result > 0) {
                    last = middle - 1;
                } else {
                    return middle;
                }
            }
        } 
        
        return first;
    }
}
