/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

/**
 * File.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @since 0.1.1
 */
public class File {
    
    /** File. */
    private String file;
    
    /**
     * Return file.
     * @return the actual dictionary's file name or ID number
     */
    public String getFile() {
        return file;
    }

    /**
     * Set file.
     * @param file the actual dictionary's file name or ID number
     */
    public void setFile(String file) {
        this.file = file;
    }
}
