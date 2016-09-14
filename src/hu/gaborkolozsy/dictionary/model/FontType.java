/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

import java.awt.Font;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Set the font type.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see java.awt.Font
 * @see java.io.IOException
 * @see javax.swing.JTable
 * @see javax.swing.JTextField
 * @since 0.1.0
 */
public class FontType {
    
    /** {@code JTextField}. */
    private final JTextField search;
    /** {@code JTable}. */
    private final JTable table;
    /** {@code JTextField}. */
    private final JTextField status;
    /** {@code Config}. */
    private Config config = new Config();

    /**
     * Constructor.
     * @param search the search text field
     * @param table the table
     * @param status the satus text field
     * @param config the {@code Config} object
     */
    public FontType(JTextField search, JTable table, JTextField status, Config config) {
        this.search = search;
        this.table = table;
        this.status = status;
        this.config = config;
    }
    
    /**
     * Set font type in component.
     * @throws IOException by file error
     */
    public void repaint() throws IOException {
        String fontType = config.getFont();
        int style = 1;
        switch (fontType) {
            case "1": fontType  = "HERCULANUM";               break;
            case "2": fontType  = "Dialog";        style = 0; break;
            case "3": fontType  = "Comic Sans MS"; style = 0; break;
        }
        search.setFont(new Font(fontType, style, 13));
        table.setFont(new Font(fontType, style, 12));
        status.setFont(new Font(fontType, style, 13));
    }
}
