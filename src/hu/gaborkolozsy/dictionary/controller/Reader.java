/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * Dictionary loader.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see java.io.BufferedReader
 * @see java.io.FileNotFoundException
 * @see java.io.FileReader
 * @see java.io.IOException
 * @see java.util.Arrays
 * @see java.util.HashMap
 * @see java.util.Map
 * @since 0.1.0
 */
public class Reader {

    /** Dictionary map. */
    private final Map<String, String> dictionary = new HashMap<>();
    /** Key array. */
    private String[] keyArray;

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
    
    /**
     * Return the {@code keyArray} data member.
     * @return key array
     */
    public String[] getKeyArray() {
        return keyArray;
    }

    /**
     * Read the dictionary by specified file name.
     * @param file dictionary file name
     */
    public void ReadFile(String file) { 
        if (!dictionary.isEmpty()) {
            dictionary.clear();
        }
        
        String f = "lang/"+file+".txt";
        try (BufferedReader input = new BufferedReader(new FileReader(f))) {
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
                    dictionary.put(from, to);
                    to = "";
                    firstColumn = true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        // make a key array and sort it
        keyArray = new String[dictionary.size()];
        int i = 0;
        for (Object s : dictionary.keySet()) {
            keyArray[i] = (String) s;
            i++;
        }
        
        Arrays.sort(keyArray);
    }
}
