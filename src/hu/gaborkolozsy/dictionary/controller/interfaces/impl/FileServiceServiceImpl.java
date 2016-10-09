/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces.impl;

import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.Config;
import java.io.IOException;

/**
 * File service.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.model.Config
 * @since 0.1.1
 */
public class FileServiceServiceImpl implements Service<String> {
    
    /** {@code Config} object. */
    private final Config config;
    
    /**
     * Constructor.
     * @param config the {@code Config} object
     * @throws IOException by failed I/O operations
     */
    public FileServiceServiceImpl(Config config) throws IOException {
        this.config = config;
    }
    
    /**
     * Return the language name by the specifid index.
     * @param fileName the actual dictionary's name
     * @param index array index
     * @return language name
     * @throws IOException by failed I/O operations
     */
    public String split(String fileName, int index) throws IOException {
        String[] languages = fileName.split("-");
        return languages[index];
    }

    /**
     * Return file name.
     * @return the actual dictionary's file name
     * @throws IOException by failed I/O operations
     */
    @Override
    public String get() throws IOException {
        return choose(config.getPropertie("Dictionary"));
    }

    /**
     * Set file.
     * @param file the actual ditionary ID 
     */
    @Override
    public void setConfig(String file) throws IOException {
        this.config.storePropertie("Dictionary", file);
    }
    
    /**
     * Choose dictionary's name.
     * @throws IOException by failed I/O operations
     */
    @Override
    public String choose(String text) throws IOException {
        String fileName = "";
        switch (text) {
            case "1": fileName = "English-Hungarian"; break;
            case "2": fileName = "Hungarian-English"; break;
            case "3": fileName = "German-Hungarian";  break;
            case "4": fileName = "Hungarian-German";  break;
            case "5": fileName = "English-German";    break;
            case "6": fileName = "German-English";    break;
        }
        return fileName;
    }
}
