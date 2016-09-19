/*
 * Copyright © 2016, Gábor Kolozsy. All rights reserved.
 */
package hu.gaborkolozsy.dictionary.view;

import hu.gaborkolozsy.dictionary.controller.Config;
import hu.gaborkolozsy.dictionary.controller.DictionaryService;
import hu.gaborkolozsy.dictionary.controller.SearchService;
import hu.gaborkolozsy.dictionary.controller.interfaces.Service;
import hu.gaborkolozsy.dictionary.controller.interfaces.impl.FileServiceServiceImpl;
import hu.gaborkolozsy.dictionary.controller.interfaces.impl.FontServiceServiceImpl;
import hu.gaborkolozsy.dictionary.controller.interfaces.impl.ThemeServiceServiceImpl;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Dictionary.
 * (editing on Mac OS X and look better on it)
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @version 0.1.1
 * 
 * @see hu.gaborkolozsy.dictionary.controller.Config
 * @see hu.gaborkolozsy.dictionary.controller.DictionaryService
 * @see hu.gaborkolozsy.dictionary.controller.SearchService
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.Service
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.impl.FileServiceServiceImpl
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.impl.FontServiceServiceImpl
 * @see hu.gaborkolozsy.dictionary.controller.interfaces.impl.ThemeServiceServiceImpl
 * @see java.awt.Color
 * @see java.awt.Desktop
 * @see java.awt.EventQueue
 * @see java.awt.Font
 * @see java.awt.event.ActionEvent
 * @see java.awt.event.KeyEvent
 * @see java.awt.event.KeyListener
 * @see java.awt.event.MouseAdapter
 * @see java.awt.event.MouseEvent
 * @see java.awt.event.MouseListener
 * @see java.io.IOException
 * @see java.lang.reflect.InvocationTargetException
 * @see java.lang.reflect.Method
 * @see java.net.URI
 * @see java.net.URISyntaxException
 * @see java.util.List
 * @see java.util.concurrent.TimeUnit
 * @see javax.swing.BorderFactory
 * @see javax.swing.JFrame
 * @see javax.swing.JLabel
 * @see javax.swing.JOptionPane
 * @see javax.swing.ToolTipManager
 * @see javax.swing.UIManager
 * @see javax.swing.UnsupportedLookAndFeelException
 * @see javax.swing.table.DefaultTableCellRenderer
 * @see javax.swing.table.DefaultTableModel
 */
public class Dictionary extends JFrame {

    /** A felhasználónév. */
    private final String name = System.getProperty("user.name");
    
    /** {@code Config} object. */
    private static final Config config = new Config();
    
    /** {@code Reader} object. */
    private static final DictionaryService dictionaryService = new DictionaryService();
    
    /** {@code FileServiceServiceImpl} object. */
    private static final Service fileService = new FileServiceServiceImpl();
    
    /** {@code ThemeServiceServiceImpl} object. */
    private static final Service themeService = new ThemeServiceServiceImpl();
    
    /** {@code FontServiceServiceImpl} object. */
    private static final Service fontService = new FontServiceServiceImpl();
    
    /** {@code Search} object. */
    private final SearchService searchService = new SearchService();
    
    /**
     * Creates new form Dictionarys.
     * 
     * @throws IOException by I/O error
     * @throws IllegalAccessException if an application tries to reflectively create an instance ..
     * @throws InvocationTargetException is a checked exception ..
     * @throws NoSuchMethodException if a particular method not found
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Dictionary() throws IOException, 
                               IllegalAccessException, 
                               IllegalArgumentException, 
                               InvocationTargetException, 
                               NoSuchMethodException {
        
        initComponents();
        
        // set table header
        table.getTableHeader().setForeground(new Color(32, 96, 200));
        table.getTableHeader().setFont(new Font("HERCULANUM", 1, 12));
        
        // set table header identifier in center
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) 
                table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        // load default dictionary
        fileService.set(config.getPropertie("Dictionary"));
        
        // read file
        String fileName = (String) fileService.choose();
        dictionaryService.ReadFile(fileName);
        
        // set table header
        String[] language = fileName.split("-");
        
        table.getColumnModel().getColumn(0).setHeaderValue(language[0]);
        table.getColumnModel().getColumn(1).setHeaderValue(language[1]);
        table.getTableHeader().resizeAndRepaint();
        
        // set theme
        themeService.set(config.getPropertie("Theme"));
        Method method = (Method) themeService.choose();
        method.invoke(this);
        
        // set font
        fontService.set(config.getPropertie("Font"));
        setFont((String) fontService.choose());
        
        // info text
        this.sumOf.setText(String.valueOf(dictionaryService.getSize()));
        this.status.setText("dictionary read in");
        
        // search KeyListeners
        this.search.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = search.getText();
                
                if (text.equals("")) {
                    return;
                }
                
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                
                // remove unnecessary rows and values
                int rowCount = tableModel.getRowCount();
                for (int i = rowCount - 1; i >= 0 ; i--) {
                    if (i < 10) {
                        tableModel.setValueAt("", i, 0);
                        tableModel.setValueAt("", i, 1);
                    } else {
                        tableModel.removeRow(i);
                    }
                }
                
                String[] keys = dictionaryService.getKeyArray();
                searchService.setHits(text, keys);
                
                List<String> hits = searchService.getHits();
                
                int numberOfHits = hits.size();
                for (int i = 0; i < numberOfHits; i++) {
                    String hit = dictionaryService.getValue(hits.get(i));
                    String from = "";
                    String to = "";
                    
                    // origin
                    if ((!hit.contains("~") && !hit.contains("/")) ||
                          hit.contains("/")) {
                        
                        from = hits.get(i) + "";
                        to = hit;
                    } 
                    
                    // if contains '~' but '/' no, than cut and join to from
                    if (hit.contains("~") && !hit.contains("/")) {
                        
                        String[] add = hit.split(":");
                        
                        from = hits.get(i) + add[0].substring(1);
                        to = add[1];
                        
                        if (add[1].startsWith(" ")) {
                            to = add[1].substring(1);
                        }
                    }
                    
                    // write hits
                    int rows = tableModel.getRowCount();
                    if (i >= rows) {
                        tableModel.addRow(new Object[] { from, to });
                    } else {
                        tableModel.setValueAt(from, i, 0);
                        tableModel.setValueAt(to, i, 1);
                    }
                }
                
                // set status text field
                searchService.setMaxHits(text, keys);
                int max = searchService.getMaxHits();
                if (max > 10) {
                    status.setText("first 10 / " + max + " words");
                } else {
                    status.setText(max + " words");
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
        });
        
        // search ActionListener
        this.search.addActionListener((ActionEvent e) -> {
            String text = search.getText();
            
            try {
                // dictionary
                if (text.endsWith("#1") ||
                    text.endsWith("#2") ||
                    text.endsWith("#3") ||
                    text.endsWith("#4") ||
                    text.endsWith("#5") ||
                    text.endsWith("#6")) {

                    String dictionaryIDNumber = text.substring(text.length() - 1);

                    // set dictionary
                    fileService.set(dictionaryIDNumber);

                    String dictionaryName = (String) fileService.choose();

                    // set properties
                    config.storePropertie("Dictionary", dictionaryName);

                    // load dictionary by specified file name
                    dictionaryService.ReadFile(dictionaryName);

                    // set table header
                    String[] languages = dictionaryName.split("-");

                    table.getColumnModel().getColumn(0).setHeaderValue(languages[0]);
                    table.getColumnModel().getColumn(1).setHeaderValue(languages[1]);
                    table.getTableHeader().resizeAndRepaint();

                    // displayed number of words of dictionary
                    sumOf.setText(String.valueOf(dictionaryService.getSize()));

                } else {
                    String[] result;

                    if (text.endsWith(":Midnight") ||
                        text.endsWith(":Dark") ||
                        text.endsWith(":Light") ||
                        text.endsWith(":1") ||
                        text.endsWith(":2") ||
                        text.endsWith(":3")) {

                        result = text.split("(:\\w+:|::|:)");

                        // set theme
                        themeService.set(result[1]);

                        // set properties
                        config.storePropertie("Theme", result[1]);

                        // paint the theme
                        Method meth = (Method) themeService.choose();
                        meth.invoke(this);
                    
                    } else {

                        // font
                        if (text.endsWith("@HERCULANUM") ||
                            text.endsWith("@Dialog") ||
                            text.endsWith("@Comic Sans MS") ||
                            text.endsWith("@1") ||
                            text.endsWith("@2") ||
                            text.endsWith("@3")) {

                            result = text.split("(@\\w+@|@@|@)");

                            // set font
                            fontService.set(result[1]);

                            // set properties
                            config.storePropertie("Font", result[1]);

                            // paint the font
                            setFont((String) fontService.choose());
                        }
                    }
                }
            } catch (IOException |
                     NoSuchMethodException |
                     IllegalArgumentException | 
                     InvocationTargetException | 
                     IllegalAccessException ex) {
                
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
            // focus first time pass and then take back
            this.sumOf.requestFocus();
            this.search.requestFocus();
            
            // search text here enough
            this.search.setText("type something..");
            
            if (System.getProperty("os.name").startsWith("Win")) {
                this.search.setText("");
            }
        });
        
        // table MouseListener
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                DefaultTableModel tableM = (DefaultTableModel) table.getModel();
                int row = table.getSelectedRow();
                
                if (row != -1) { 
                    int rows = tableM.getRowCount();
                    Object valueFrom = tableM.getValueAt(row, 0);
                    Object valueTo = tableM.getValueAt(row, 1);

                    if ((String) valueTo != null) {
                        if (((String) valueTo).contains("/")) {
                            String[] hits = ((String) valueTo).split("/");
                            int numberOfHits = hits.length;
                            
                            for (int i = 0; i < numberOfHits; i++) {
                                String from, to;

                                // origin
                                if (!hits[i].contains("~")) {
                                    from = valueFrom+"";
                                    to = hits[i];
                                } else {

                                    // cut and paste to from
                                    String[] meanings = hits[i].split(":");
                                    
                                    if (meanings[0].startsWith("~")) {
                                        from = valueFrom + meanings[0].substring(1);
                                        to = meanings[1];
                                        
                                        if (meanings[1].startsWith(" ")) {
                                            to = meanings[1].substring(1);
                                        }
                                    } else {
                                        String[] pre = meanings[0].split("~");
                                        from = pre[0] + valueFrom + pre[1];
                                        to = meanings[1];
                                        
                                        if (meanings[1].startsWith(" ")) {
                                            to = meanings[1].substring(1);
                                        }
                                    }
                                }

                                // write hits
                                if (i >= rows) {
                                    tableM.addRow(new Object[] { from, to });
                                } else {
                                    tableM.setValueAt(from, i, 0);
                                    tableM.setValueAt(to, i, 1);
                                }
                            }

                            // remove irrelevante rows and values
                            for (int i = numberOfHits; i < rows ; i++) {
                                if (i < 10) {
                                    tableM.setValueAt("", i, 0);
                                    tableM.setValueAt("", i, 1);
                                } else {
                                    tableM.removeRow(numberOfHits);
                                }
                            }

                            // set status text field
                            status.setText(numberOfHits + " words");
                        }
                        search.requestFocus();
                    }
                }
            }
        });
        
        // google web MouseListener
        this.google.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWWW("http://www.google.hu/");
            }
        });
        
        // hacker web MouseListener
        this.hacker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWWW("http://www.hackerrank.com/");
            }
        });
        
        // dictInfo time delay MouseListener
        this.dictInfo.addMouseListener(getMouseListener());
        
        // themeInfo time delay MouseListener
        this.themeInfo.addMouseListener(getMouseListener());
        
        // fontInfo time delay MouseListener
        this.fontInfo.addMouseListener(getMouseListener());
        
        // copyright email MouseListener
        this.copyright.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:kolozsygabor@gmail.com"
                            + "?subject=Dictionary&body=Hi!"));
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        status = new javax.swing.JTextField();
        copyright = new javax.swing.JLabel();
        sumOf = new javax.swing.JLabel();
        dictInfo = new javax.swing.JLabel();
        themeInfo = new javax.swing.JLabel();
        google = new javax.swing.JLabel();
        hacker = new javax.swing.JLabel();
        fontInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(name + "'s Dictionary");
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(0, 22, 500, 240));
        setMinimumSize(new java.awt.Dimension(500, 240));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 240));

        jPanel.setBackground(new java.awt.Color(0, 0, 0));
        jPanel.setToolTipText("");
        jPanel.setPreferredSize(new java.awt.Dimension(500, 240));

        search.setBackground(new java.awt.Color(0, 0, 0));
        search.setFont(new java.awt.Font("Herculanum", 0, 13)); // NOI18N
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        search.setText("type something..");
        
	if (System.getProperty("os.name").startsWith("Win")) {
            this.search.setText("");
        }

	search.setToolTipText("type something..");
        search.setBorder(null);
        search.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        search.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        search.setSelectionColor(new java.awt.Color(0, 0, 0));

        table.setBackground(new java.awt.Color(0, 0, 0));
        table.setFont(new java.awt.Font("Herculanum", 0, 12)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "English", "Hungarian"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(0, 0, 0));
        table.setMaximumSize(new java.awt.Dimension(500, 160));
        table.setRowMargin(0);
        table.setSelectionBackground(new java.awt.Color(0, 0, 0));
        table.setShowGrid(true);
        jScrollPane.setViewportView(table);

        status.setEditable(false);
        status.setBackground(new java.awt.Color(0, 0, 0));
        status.setFont(new java.awt.Font("Herculanum", 0, 13)); // NOI18N
        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        status.setText("reading dictionary..");
        status.setToolTipText("hit of search");
        status.setBorder(null);
        status.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        status.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        status.setSelectionColor(new java.awt.Color(0, 0, 0));

        copyright.setBackground(new java.awt.Color(0, 0, 0));
        copyright.setFont(new java.awt.Font("Herculanum", 1, 12)); // NOI18N
        copyright.setForeground(new java.awt.Color(32, 96, 200));
        copyright.setText("© KG");
        copyright.setToolTipText("<html>Copyright © 2016 Gábor Kolozsy<br>\n<a href=\"http ://blank\">kolozsygabor@gmail.com</a>");

        sumOf.setBackground(new java.awt.Color(0, 0, 0));
        sumOf.setFont(new java.awt.Font("Herculanum", 0, 12)); // NOI18N
        sumOf.setForeground(new java.awt.Color(32, 96, 200));
        sumOf.setText("sum of ");
        sumOf.setToolTipText("sum of words of dictionary");
        sumOf.setSize(new java.awt.Dimension(45, 13));

        dictInfo.setBackground(new java.awt.Color(0, 0, 0));
        dictInfo.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        dictInfo.setForeground(new java.awt.Color(32, 96, 200));
        dictInfo.setText("D");
        dictInfo.setToolTipText("<html><h4><a href=http://balnk>How to choose dictionary</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"3\"><b>#</b></font> and an <b>ID</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Dictionary</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><center><b>English-Hungarian<font color=\"rgb(32,96,200)\"> ¹</font></b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><center><b>Hungarian-English</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><center><b>German-Hungarian</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>4</b></center></td>\n       <td style=\"color:white\"><center><b>Hungarian-German</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>5</b></center></td>\n       <td style=\"color:white\"><center><b>English-German</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>6</b></center></td>\n       <td style=\"color:white\"><center><b>German-English</b></center></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200) \">#1</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\">#1</b></font></li>\n</ol>\n\n<font color=\"rgb(32,96,200)\"><b>[1]</b></font>. <b>is default</b><br>\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        themeInfo.setBackground(new java.awt.Color(0, 0, 0));
        themeInfo.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        themeInfo.setForeground(new java.awt.Color(32, 96, 200));
        themeInfo.setText("T");
        themeInfo.setToolTipText("<html><h4><a href=http://blank>How to choose theme</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"4\"><b>:</b></font> and an <b>ID</b> or <b>Theme</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Theme</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Description</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><center><b>Midnight<font color=\"rgb(32,96,200)\"> ¹</font></b></center></td>\n       <td style=\"color:white\"><font size=\"3\"><center>full black</center></font></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><center><b>Dark</b></center></td>\n       <td style=\"color:white\"><font size=\"3\"><center>dark</center></font></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><center><b>Light</b></center></td>\n       <td style=\"color:white\"><font size=\"3\"><center>light</center></font></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200) \"><font size=\"4\">:</font>2</font></b></li>\n   <li><b><font color=\"rgb(32,96,200)\"><font size=\"4\">:</font>Dark</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\"><font size=\"4\">:</font>Dark</b></font></li>\n</ol>\n\n<font color=\"rgb(32,96,200)\"><b>[1]</b></font>. <b>is default</b><br>\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        google.setBackground(new java.awt.Color(0, 0, 0));
        google.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        google.setForeground(new java.awt.Color(32, 96, 200));
        google.setText("G");
        google.setToolTipText("<html><a href=http://>google.hu</a></html>");

        hacker.setBackground(new java.awt.Color(0, 0, 0));
        hacker.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        hacker.setForeground(new java.awt.Color(32, 96, 200));
        hacker.setText("H");
        hacker.setToolTipText("<html><a href=http://>hackerrank.com</a></html>");

        fontInfo.setBackground(new java.awt.Color(0, 0, 0));
        fontInfo.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        fontInfo.setForeground(new java.awt.Color(32, 96, 200));
        fontInfo.setText("F");
        fontInfo.setToolTipText("<html><h4><a href=http://blank>How to choose font type</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"3\"><b>@</b></font> and an <b>ID</b> or <b>Font</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Font</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Preview</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><center><b>HERCULANUM<font color=\"rgb(32,96,200)\"> ¹</font></b></center></td>\n       <td style=\"color:white\"><font face=\"HERCULANUM\" size=\"3\"><center>blah</center></font></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><center><b>Dialog</b></center></td>\n       <td style=\"color:white\"><font face=\"Dialog\" size=\"3\"><center>blah</center></font></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><center><b>Comic Sans MS</b></center></td>\n       <td style=\"color:white\"><font face=\"Comic Sans MS\" size=\"3\"><center>blah</center></font></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200)\">@2</font></b></li>\n   <li><b><font color=\"rgb(32,96,200)\">@Dialog</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\">@Dialog</b></font></li>\n</ol>\n\n<font color=\"rgb(32,96,200)\"><b>[1]</b></font>. <b>is default</b><br>\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(sumOf, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(copyright))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(google)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hacker)
                        .addGap(115, 115, 115)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dictInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(themeInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fontInfo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dictInfo)
                    .addComponent(themeInfo)
                    .addComponent(google)
                    .addComponent(hacker)
                    .addComponent(fontInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sumOf, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyright))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
	setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Midnight theme.
     */
    private void midnight() {
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
    private void dark() {
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
    private void light() {
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
    
    /**
     * Set the font.
     * @param fontType the actual font type
     */
    private void setFont(String fontType) {
        search.setFont(new Font(fontType, 0, 13));
        table.setFont(new Font(fontType, 0, 12));
        status.setFont(new Font(fontType, 0, 13));
    }
    
    /**
     * Launch default browser with the specified url.
     * @param url target url
     */
    private void getWWW(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * time delay for help
     * @return {@code MouseListener}
     */
    private MouseListener getMouseListener() {
        return new MouseAdapter() {
            final int defaultDismissTimeout = ToolTipManager.sharedInstance()
                    .getDismissDelay();

            final int dismissDelayMinutes = (int) TimeUnit.SECONDS.toMillis(10);

            @Override
            public void mouseEntered(MouseEvent e) {
                ToolTipManager.sharedInstance()
                              .setDismissDelay(dismissDelayMinutes);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ToolTipManager.sharedInstance()
                              .setDismissDelay(defaultDismissTimeout);
            }
        };
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Mac OS X / Windows or Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc="Look and feel setting">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        String laf = "Nimbus";
        String os = System.getProperty("os.name");
        
        if (os.startsWith("Win")) laf = "Windows"; 
        if (os.equals("Mac OS X"))laf = "Mac OS X";
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (laf.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 
                    JOptionPane.PLAIN_MESSAGE);
        }//</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            try {
                new Dictionary().setVisible(true);
            } catch (IOException | 
                     IllegalAccessException | 
                     IllegalArgumentException | 
                     InvocationTargetException | 
                     NoSuchMethodException ex) {
                
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel copyright;
    private javax.swing.JLabel dictInfo;
    private javax.swing.JLabel fontInfo;
    private javax.swing.JLabel google;
    private javax.swing.JLabel hacker;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextField search;
    private javax.swing.JTextField status;
    private javax.swing.JLabel sumOf;
    private javax.swing.JTable table;
    private javax.swing.JLabel themeInfo;
    // End of variables declaration//GEN-END:variables
}