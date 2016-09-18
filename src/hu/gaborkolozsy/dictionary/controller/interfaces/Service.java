/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces;

/**
 * Service interface.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @param <T>  return type
 * @since 0.1.1
 */
public interface Service<T> {
    
    /**
     * Getter.
     * @return the object data member
     */
    String get();
    
    /**
     * Setter.
     * @param s the new data member of object
     */
    void set(String s);
    
    /**
     * Return a {@code T} object.
     * @return {@code T} object
     * @throws NoSuchMethodException if method not found with specified name
     */
    T choose() throws NoSuchMethodException;
}
