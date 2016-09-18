/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

/**
 * The theme.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @since 0.1.1
 */
public class Theme {
    
    /** Theme. */
    private String theme;
    
    /**
     * Return the theme.
     * @return the actual theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Set the theme.
     * @param theme the actual theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }
}
