/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces.impl;

import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.File;

/**
 * {@code FileServiceServiceImpl} object.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.model.File
 * @since 0.1.1
 */
public class FileServiceServiceImpl implements Service<String> {
    
    /** {@code File} object. */
    private final File file = new File();

    /**
     * Return file.
     * @return the actual dictionary's file name or ID number
     */
    @Override
    public String get() {
        return file.getFile();
    }

    /**
     * Set file.
     * @param file the actual file's name or ID number
     */
    @Override
    public void set(String file) {
        this.file.setFile(file);
    }
    
    /**
     * Choose dictionary's name.
     * @return the actual dictionary's name
     */
    @Override
    public String choose() {
        String fileName = get();
        
        switch (fileName) {
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
