/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces.impl;

import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.FontType;

/**
 * Font service.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.model.FontType
 * @since 0.1.1
 */
public class FontServiceServiceImpl implements Service<String> {
    
    /** {@code FontType} object. */
    private final FontType font = new FontType();
    
    /**
     * Return font.
     * @return the actual font type's name or ID number
     */
    @Override
    public String get() {
        return this.font.getFont();
    }
    
    /**
     * Set font.
     * @param font the actual font type's name or ID number
     */
    @Override
    public void set(String font) {
        this.font.setFont(font);
    }
    
    /**
     * Return font type.
     * @return the actual font type
     */
    @Override
    public String choose() {
        String fontType = get();
        
        switch (fontType) {
            case "1": fontType = "HERCULANUM";    break;
            case "2": fontType = "Dialog";        break;
            case "3": fontType = "Comic Sans MS"; break;
        }
        
        return fontType;
    }
}
