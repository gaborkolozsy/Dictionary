/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

/**
 * Font type.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @since 0.1.0
 */
public class FontType {
    
    /** Font. */
    private String font;

    /**
     * Return font.
     * @return the actual font type's name or ID number
     */
    public String getFont() {
        return font;
    }

    /**
     * Set font.
     * @param font the actual font type's name or ID number
     */
    public void setFont(String font) {
        this.font = font;
    }
}
