/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces.impl;

import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.Config;
import java.io.IOException;

/**
 * Font service.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.model.Config
 * @since 0.1.1
 */
public class FontServiceServiceImpl implements Service<String> {
    
    /** {@code Config} object. */
    private final Config config;
    
    /**
     * Constructor.
     * @param config the {@code Config} object
     * @throws IOException by failed I/O operations
     */
    public FontServiceServiceImpl(Config config) throws IOException {
        this.config = config;
    }
    
    /**
     * Return font type.
     * @return the actual font type
     * @throws IOException by failed I/O operations
     */
    @Override
    public String get() throws IOException {
        return choose(config.getPropertie("Font"));
    }
    
    /**
     * Set font type as an ID.
     * @param font the actual font ID
     */
    @Override
    public void setConfig(String font) throws IOException {
        this.config.storePropertie("Font", font);
    }
    
    /**
     * Return font type.
     * @return the actual font type
     */
    @Override
    public String choose(String text) {
        String fontType = "";
        switch (text) {
            case "1": fontType = "HERCULANUM";    break;
            case "2": fontType = "Dialog";        break;
            case "3": fontType = "Comic Sans MS"; break;
        }
        return fontType;
    }
}
