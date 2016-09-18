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
     * @return the actual file
     */
    public String getFile() {
        return file;
    }

    /**
     * Set file.
     * @param file the actual file
     */
    public void setFile(String file) {
        this.file = file;
    }
}
