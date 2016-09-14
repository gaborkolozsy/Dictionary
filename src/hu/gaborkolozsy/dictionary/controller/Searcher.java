/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller;

/**
 * Search and return the hits.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @since 0.1.0
 */
public class Searcher {
    
    /** Maximum displayed hits. */
    private final int FIRSTTENDISPLAYEDHIT = 10;
    
    /**
     * Return the hits in array.
     * @param s the specified {@code String}
     * @param keyArray the key array
     * @return the array of hits object
     */
    public Object[] displayedMatch(String s, String[] keyArray) {
        int startIdx = binarySearch(s, keyArray);
        int endIdx = binarySearch(s + '\uFFFF', keyArray);
        
        if (endIdx - startIdx > FIRSTTENDISPLAYEDHIT) {
            endIdx = startIdx + FIRSTTENDISPLAYEDHIT;
        }

        Object[] hits = new Object[endIdx - startIdx];
        for (int i = startIdx; i < endIdx; i++) { 
            hits[i - startIdx] = keyArray[i];
        }
        return hits;
    }
    
    /**
     * The maximum number of hits by the specified {@code String}.
     * @param s the specified {@code String}
     * @param keyArray the key array
     * @return count of hits
     */
    public int maxMatch(String s, String[] keyArray) {
        int startIdx = binarySearch(s, keyArray);
        int endIdx = binarySearch(s + '\uFFFF', keyArray);
        return endIdx - startIdx;
    }

    /**
     * Search the first index of specified {@code String} in the key array.
     * @param s the specified {@code String}
     * @param keyArray the key array
     * @return the first index by the specified {@code String}
     */
    private int binarySearch(String s, String[] keyArray) {
        int fromIndex = 0;
        int toIndex = keyArray.length - 1;
        int result;
        while (fromIndex <= toIndex) {
            int midIndex = (fromIndex + toIndex) / 2;
            
            if ((result = keyArray[midIndex].compareTo(s)) < 0) {
                fromIndex = midIndex + 1;
            } else {
                if (result > 0) {
                    toIndex = midIndex - 1;
                } else {
                    return midIndex;
                }
            }
        } 
        return fromIndex;
    }
}
