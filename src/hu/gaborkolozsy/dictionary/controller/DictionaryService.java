/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller;

import hu.gaborkolozsy.dictionary.model.DictionaryBox;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Dictionary service.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.model.DictionaryBox
 * @see java.io.BufferedReader
 * @see java.io.FileInputStream
 * @see java.io.IOException
 * @see java.io.InputStreamReader
 * @see java.util.Arrays
 * @since 0.1.1
 */
public class DictionaryService {
    
    /** {@code DictionaryBox} object. */
    private final DictionaryBox dictionaryBox;
    
    /** New line in dictionary. */
    private String line;
    
    /** First(from) read. */
    private Boolean first;
    
    /** Translate it. */
    private String from;
    
    /** What's mean. */
    private String to;

    /**
     * Constructor.
     */
    public DictionaryService() {
        this.dictionaryBox = new DictionaryBox();
        this.first = true;
        this.from = "";
        this.to = "";
    }
    
    /**
     * Read the dictionary by specified file name.
     * just on Mac => input = new BufferedReader(new FileReader(file)
     * @param fileName dictionary file name
     * @throws IOException If an I/O error occurs
     */
    public void readFile(String fileName) throws IOException { 
        final String file = "lang/"+fileName+".txt";
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        readDictionary(input);
    }

    /**
     * Read dictionary and put it to the map.
     * @param input {@code BufferedReader} object
     * @throws IOException 
     */
    private void readDictionary(BufferedReader input) throws IOException {
        dictionaryClear();
        while ((line = input.readLine()) != null) {
            if (line.length() > 0) {
                if (first) {
                    from = line;
                    first = false;
                } else { if (to.length() > 0) {
                    to = to + "/" + line;
                } else {
                    to = line;
                }}
            } else {
                dictionaryBox.put(from, to);
                to = "";
                first = true;
            }
        }
    }

    /**
     * Clear dictionary if not empty.
     */
    private void dictionaryClear() {
        if (!dictionaryBox.isEmpty()) {
            dictionaryBox.clear();
        }
    }
    
    /**
     * Return the dictionary's key array.
     * @return key array
     */
    public String[] getKeyArray() {
        String[] keyArray = new String[dictionaryBox.getSize()];
        int i = 0;
        for (Object s : dictionaryBox.getKeySet()) {
            keyArray[i] = (String) s;
            i++;
        }
        Arrays.sort(keyArray);
        return keyArray;
    }
    
    /**
     * Return the value by the specified key.
     * @param key the specified key
     * @return the value
     */
    public String getValue(String key) {
        return dictionaryBox.getValue(key);
    }
    
    /**
     * Return dictionary's size.
     * @return the size
     */
    public int getSize() {
        return dictionaryBox.getSize();
    }
}
