/*
 * Copyright © 2016, Gábor Kolozsy
 */

package hu.gaborkolozsy.dictionary.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * DictionaryBox box.
 * 
 * @author Kolozsy Gábor (gabor.kolozsy.development@gmail.com)
 * @see HashMap
 * @see Map
 * @see Set
 * @since 0.1.0
 */
public class DictionaryBox {

    /** 
     * DictionaryBox map. 
     */
    private final Map<String, String> dictionary = new HashMap<>();
    
    /**
     * Put the key-value paar in the dictionary.
     * @param key the key
     * @param value the value
     */
    public void put(String key, String value) {
        dictionary.put(key, value);
    }

    /**
     * Return true if dictionary is empty.
     * @return <b>true</b> if dictionary is empty <b>false</b> otherwise
     */
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }
    
    /**
     * Clear dictionary.
     */
    public void clear() {
        dictionary.clear();
    }
    
    /**
     * Return the key set.
     * @return the dictionary's key set
     */
    public Set getKeySet() {
        return dictionary.keySet();
    }
    
    /**
     * Return the value by specified key.
     * @param key the specified key
     * @return value
     */
    public String getValue(String key) {
        return dictionary.get(key);
    }
    
    /**
     * Return the size of {@code dictionary} data member.
     * @return {@code dictionary} size
     */
    public int getSize() {
        return dictionary.size();
    }
}
