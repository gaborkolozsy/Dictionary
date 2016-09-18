/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.controller.interfaces.impl;

import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.Theme;
import hu.gaborkolozsy.dictionary.view.Dictionary;
import java.lang.reflect.Method;

/**
 * Theme service.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.model.Theme
 * @see hu.gaborkolozsy.dictionary.view.Dictionary
 * @see java.lang.reflect.Method
 * @since 0.1.1
 */
public class ThemeServiceServiceImpl implements Service<Method> {
    
    /** {@code Theme} object. */
    private final Theme theme = new Theme();
    
    /**
     * Return theme.
     * @return the actual theme
     */
    @Override
    public String get() {
        return this.theme.getTheme();
    }
    
    /**
     * Set theme.
     * @param theme the actual theme
     */
    @Override
    public void set(String theme) {
        this.theme.setTheme(theme);
    }
    
    /**
     * Choose method for theme.
     * @return the callable method
     * @throws NoSuchMethodException if a matching method is not found
     */
    @Override
    public Method choose() throws NoSuchMethodException {
        Method m = null;
        
        switch(get()) { 
            case "1": m = Dictionary.class.getDeclaredMethod("midnight"); break;
            case "2": m = Dictionary.class.getDeclaredMethod("dark");     break;
            case "3": m = Dictionary.class.getDeclaredMethod("light");    break;
            case "Midnight": m = Dictionary.class.getDeclaredMethod("midnight");
                                                                          break;
            case "Dark": m = Dictionary.class.getDeclaredMethod("dark");  break;
            case "Light": m = Dictionary.class.getDeclaredMethod("light");break;
        }
        
        return m;
    }
}
