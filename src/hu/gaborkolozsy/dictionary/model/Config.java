/*
 * Copyright © 2016, Gábor Kolozsy
 */

package hu.gaborkolozsy.dictionary.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Get and store properties.
 * 
 * @author Gabor Kolozsy (gabor.kolozsy.development@gmail.com)
 * 
 * @see java.io.FileInputStream
 * @see java.io.FileOutputStream
 * @see java.io.IOException
 * @see java.util.Properties
 * @since 0.1.0
 */
public class Config {
    
    /** 
     * Config file name. 
     */
    private final String FILE = "config.ini";
    
    /** 
     * {@code Properties} object. 
     */
    private final Properties properties = new Properties();
    
    /**
     * Load properties.
     * @param key the key
     * @return property by specified key
     * @throws IOException by file error
     */
    public String getPropertie(String key) throws IOException {
        this.properties.load(new FileInputStream(FILE));
        return properties.getProperty(key);
    }
    
    /**
     * Store properties.
     * @param key the key
     * @param value the value
     * @throws IOException by file error
     */
    public void storePropertie(String key, String value) throws IOException {
        this.properties.setProperty(key, value);
        this.properties.store(new FileOutputStream(FILE), "Config");
    }
}
