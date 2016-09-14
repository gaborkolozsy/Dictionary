/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.model;

import java.awt.Color;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Set the color of theme.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see java.awt.Color
 * @see java.io.IOException
 * @see javax.swing.BorderFactory
 * @see javax.swing.JPanel
 * @see javax.swing.JScrollPane
 * @see javax.swing.JTable
 * @see javax.swing.JTextField
 * @since 0.1.0
 */
public class Theme {
    
    /** {@code JScrollPane}. */
    private final JScrollPane jScrollPane;
    /** {@code JTextField}. */
    private final JTextField search;
    /** {@code JTable}. */
    private final JTable table;
    /** {@code JPanel} */
    private final JPanel jPanel;
    /** {@code JTextField}. */
    private final JTextField status;
    /** {@code Config}. */
    private Config config = new Config();

    /**
     * Constructor.
     * 
     * @param jScrollPane the scroll pane
     * @param search the search text field
     * @param table the table
     * @param jPanel the panel
     * @param status the status text field
     * @param config the {@code Config} object.
     */
    public Theme(JScrollPane jScrollPane, JTextField search, JTable table,
            JPanel jPanel, JTextField status, Config config) {
        
        this.jScrollPane = jScrollPane;
        this.search = search;
        this.table = table;
        this.jPanel = jPanel;
        this.status = status;
        this.config = config;
    }
    
    /**
     * Repaint the color of theme.
     * @throws IOException
     */
    public void repaint() throws IOException {
        String theme = config.getTheme();
        switch(theme) {
            case "1":        midnight(); break;
            case "2":        dark();     break;
            case "3":        light();    break;
            case "Midnight": midnight(); break;
            case "Dark":     dark();     break;
            case "Light":    light();    break;
        }
    }
    
    /**
     * Midnight theme.
     */
    public void midnight() {
        search.setBorder(BorderFactory
                .createLineBorder(Color.black));
        search.setBackground(Color.black);
        search.setForeground(Color.white);
        jPanel.setBackground(Color.black);
        jScrollPane.setBorder(BorderFactory
                .createLineBorder(Color.black));
        table.getTableHeader().setBorder(BorderFactory
                .createLineBorder(Color.black));
        table.getTableHeader()
                .setBackground(Color.black);
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        table.setGridColor(Color.black);
        table.setSelectionBackground(Color.black);
        table.setSelectionForeground(Color.white);
        status.setBorder(BorderFactory
                .createLineBorder(Color.black));
        status.setBackground(Color.black);
        status.setForeground(Color.white);
    }
    
    /**
     * Dark theme.
     */
    public void dark() {
        search.setBorder(BorderFactory
                .createLineBorder(new Color(32, 96, 200)));
        search.setBackground(Color.black);
        search.setForeground(Color.white);
        jPanel.setBackground(Color.black);
        jScrollPane.setBorder(BorderFactory
                .createLineBorder(Color.black));
        table.getTableHeader().setBorder(BorderFactory
                .createLineBorder(new Color(32, 96, 200)));
        table.getTableHeader()
                .setBackground(Color.black);
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        table.setGridColor(new Color(25, 25, 25));
        table.setSelectionBackground(Color.black);
        table.setSelectionForeground(Color.white);
        status.setBorder(BorderFactory
                .createLineBorder(new Color(32, 96, 200)));
        status.setBackground(Color.black);
        status.setForeground(Color.white);
    }
    
    /**
     * Light theme.
     */
    public void light() {
        search.setBorder(BorderFactory
                .createLineBorder(new Color(255, 249, 236)));
        search.setBackground(new Color(255, 249, 236));
        search.setForeground(Color.black);
        jPanel.setBackground(new Color(255, 249, 236));
        jScrollPane.setBorder(BorderFactory
                .createLineBorder(Color.lightGray));
        table.getTableHeader().setBorder(BorderFactory
                .createLineBorder(Color.lightGray));
        table.getTableHeader()
                .setBackground(new Color(255, 249, 236));
        table.setBackground(new Color(255, 249, 236));
        table.setForeground(Color.black);
        table.setGridColor(Color.lightGray);
        table.setSelectionBackground(new Color(255, 249, 236));
        table.setSelectionForeground(Color.black);
        status.setBorder(BorderFactory
                .createLineBorder(new Color(255, 249, 236)));
        status.setBackground(new Color(255, 249, 236));
        status.setForeground(Color.black);
    }
}
