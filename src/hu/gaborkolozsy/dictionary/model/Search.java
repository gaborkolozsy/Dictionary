/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Search and return the hits.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see java.util.ArrayList
 * @see java.util.List
 * @since 0.1.0
 */
public class Search {
    
    /** Hits list. */
    private List<String> hits = new ArrayList<>();
    
    /** Maximum number of hits. */
    private int maxHits;

    /**
     * Add hit for hits list.
     * @param hit the hit
     */
    public void addHit(String hit) {
        hits.add(hit);
    }
    
    /**
     * Return the list of hits.
     * @return hits list
     */
    public List<String> getHits() {
        return hits;
    }

    /**
     * Set the hits list.
     * @param hits the hits list
     */
    public void setHits(List<String> hits) {
        this.hits = hits;
    }
    
    /**
     * Clear hits list.
     */
    public void clear() {
        hits.clear();
    }

    /**
     * Return counting hits.
     * @return number of hits
     */
    public int getMaxHits() {
        return maxHits;
    }

    /**
     * Set counting hits.
     * @param maxHits number of hits
     */
    public void setMaxHits(int maxHits) {
        this.maxHits = maxHits;
    }
    
    
}