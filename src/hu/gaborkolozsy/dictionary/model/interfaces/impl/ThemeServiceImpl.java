/*
 * Copyright © 2016, Gábor Kolozsy
 */

package hu.gaborkolozsy.dictionary.model.interfaces.impl;

import hu.gaborkolozsy.dictionary.model.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.Config;
import hu.gaborkolozsy.dictionary.view.Dictionary;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Theme service.
 * 
 * @author Kolozsy Gábor (gabor.kolozsy.development@gmail.com)
 * @see Service
 * @see Config
 * @see Dictionary
 * @see IOException
 * @see Method
 * @since 0.1.1
 */
public class ThemeServiceImpl implements Service<Method> {
    
    /** {@code Config} object. */
    private final Config config;
    
    /**
     * Constructor.
     * @param config the {@code Config} object
     * @throws IOException by failed I/O operations
     */
    public ThemeServiceImpl(Config config) throws IOException {
        this.config = config;
    }
    
    /**
     * Return theme method's name.
     * @return the actual theme method
     * @throws IOException by failed I/O operations
     * @throws NoSuchMethodException if method not found with specified name
     */
    @Override
    public Method get() throws IOException, NoSuchMethodException {
        return choose(config.getPropertie("Theme"));
    }
    
    /**
     * Set theme as an ID.
     * @param theme the actual theme ID
     */
    @Override
    public void set(String theme) throws IOException {
        this.config.storePropertie("Theme", theme);
    }
    
    /**
     * Choose method for theme.
     * @return the callable method
     * @throws NoSuchMethodException if a matching method is not found
     */
    @Override
    public Method choose(String text) throws NoSuchMethodException {
        Method m = null;
        switch(text) { 
            case "1": m = Dictionary.class.getDeclaredMethod("midnight"); break;
            case "2": m = Dictionary.class.getDeclaredMethod("dark");     break;
            case "3": m = Dictionary.class.getDeclaredMethod("light");    break;
        }
        return m;
    }
}
