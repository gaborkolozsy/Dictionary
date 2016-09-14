/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Load and store properties.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see java.io.FileInputStream
 * @see java.io.FileOutputStream
 * @see java.io.IOException
 * @see java.io.InputStream
 * @see java.io.OutputStream
 * @see java.util.Properties
 * @since 0.1.0
 */
public class Config {
    
    /** Config file name. */
    private final String FILE = "config.ini";
    /** {@code Properties} objetc. */
    private final Properties prop = new Properties();
    /** Dictionary. */
    private String dictionary = "";
    /** Theme. */
    private String theme = "";
    /** Font. */
    private String font = "";
    
    /**
     * Load properties.
     * @throws IOException by file error
     */
    public void loadProperties() throws IOException {
        try (InputStream input = new FileInputStream(FILE)) {
            this.prop.load(input);
            this.dictionary = prop.getProperty("Dictionary");
            this.theme = prop.getProperty("Theme");
            this.font = prop.getProperty("Font");
        }
    }
    
    /**
     * Store properties.
     * @throws IOException by file error
     */
    public void storeProperties() throws IOException {
        try (OutputStream out = new FileOutputStream(FILE)) {
            prop.setProperty("Dictionary", dictionary);
            prop.setProperty("Theme", theme);
            prop.setProperty("Font", font);
            prop.store(out, "Config");
        }
    }

    /**
     * Return dictionary.
     * @return dictionary
     */
    public String getDictionary() {
        return dictionary;
    }

    /**
     * Set dictionary.
     * @param dictionary the actual dictionary
     */
    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Return theme.
     * @return theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Set theme.
     * @param theme the actual theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Return font.
     * @return font
     */
    public String getFont() {
        return font;
    }

    /**
     * Set font.
     * @param font the actual font
     */
    public void setFont(String font) {
        this.font = font;
    }
}
