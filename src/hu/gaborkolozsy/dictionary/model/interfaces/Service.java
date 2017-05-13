/*
 * Copyright © 2016, Gábor Kolozsy
 */

package hu.gaborkolozsy.dictionary.model.interfaces;

import java.io.IOException;

/**
 * Service interface.
 * 
 * @author Gabor Kolozsy (gabor.kolozsy.development@gmail.com)
 * @param <T>  return type
 * @since 0.1.1
 */
public interface Service<T> {
    
    /**
     * Get data.
     * @return the object data member
     * @throws IOException by failed I/O operations
     * @throws NoSuchMethodException if method not found with specified name
     */
    T get() throws IOException, NoSuchMethodException;
    
    /**
     * Set data.
     * @param text user typed text
     * @throws java.io.IOException by failed I/O operations
     */
    void set(String text) throws IOException;
    
    /**
     * Return a {@code T} object.
     * @param text user typed text
     * @return {@code T} object
     * @throws IOException by failed I/O operations
     * @throws NoSuchMethodException if method not found with specified name
     */
    T choose(String text) throws IOException, NoSuchMethodException;
}
