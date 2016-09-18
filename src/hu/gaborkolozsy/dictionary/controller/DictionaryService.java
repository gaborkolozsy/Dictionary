/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller;

import hu.gaborkolozsy.dictionary.model.Dictionary;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * {@code DictionaryService} object.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.model.Dictionary
 * @see java.io.BufferedReader
 * @see java.io.FileInputStream
 * @see java.io.IOException
 * @see java.io.InputStreamReader
 * @see java.util.Arrays
 * @since 0.1.1
 */
public class DictionaryService {
    
    /** {@code Dictionary} object. */
    private final Dictionary reader = new Dictionary();
    
    /**
     * Read the dictionary by specified file name.
     * @param fileName dictionary file name
     * @throws IOException If an I/O error occurs
     */
    public void ReadFile(String fileName) throws IOException { 
        if (!reader.isEmpty()) {
            reader.clear();
        }
        
        String file = "lang/"+fileName+".txt";
        
        // on mac
        //BufferedReader input = new BufferedReader(new FileReader(f));
        
        // on mac and windows too
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String line;
        Boolean firstColumn = true;
        String from = "";
        String to = "";

        while ((line = input.readLine()) != null) {
            if (line.length() > 0) {
                if (firstColumn) {
                    from = line;
                    firstColumn = false;
                } else {
                    if (to.length() > 0) {
                        to = to + "/" + line;
                    } else {
                        to = line;
                    }
                }
            } else {
                reader.put(from, to);
                to = "";
                firstColumn = true;
            }
        }
        
        setKeyArray();
    }
    
    /**
     * Return the value.
     * @param key the specified key
     * @return the value by the specified key
     */
    public String getValue(String key) {
        return reader.getValue(key);
    }
    
    /**
     * Return the key array.
     * @return key array
     */
    public String[] getKeyArray() {
        return reader.getKeyArray();
    }
    
    /**
     * Set key array.
     */
    private void setKeyArray() {
        String[] keyArray = new String[reader.getSize()];
        
        int i = 0;
        for (Object s : reader.getKeySet()) {
            keyArray[i] = (String) s;
            i++;
        }
        
        Arrays.sort(keyArray);
        reader.setKeyArray(keyArray);
    }
    
    /**
     * Return dictionary's size.
     * @return the size
     */
    public int getSize() {
        return reader.getSize();
    }
}
