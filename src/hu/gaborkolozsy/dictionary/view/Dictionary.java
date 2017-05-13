/*
 * Copyright © 2016, Gábor Kolozsy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.gaborkolozsy.dictionary.view;

import hu.gaborkolozsy.dictionary.controller.DictionaryService;
import hu.gaborkolozsy.dictionary.controller.SearchService;
import hu.gaborkolozsy.dictionary.model.Config;
import hu.gaborkolozsy.dictionary.model.interfaces.Service;
import hu.gaborkolozsy.dictionary.model.interfaces.impl.FileServiceImpl;
import hu.gaborkolozsy.dictionary.model.interfaces.impl.FontServiceImpl;
import hu.gaborkolozsy.dictionary.model.interfaces.impl.ThemeServiceImpl;
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
 * (editing on Mac OS X and looks better on it)
 * 
 * @author Gabor Kolozsy (gabor.kolozsy.development@gmail.com)
 * @version 0.1.1
 * 
 * @see DictionaryService
 * @see SearchService
 * @see Config
 * @see Service
 * @see FileServiceImpl
 * @see FontServiceImpl
 * @see ThemeServiceImpl
 * @see Color
 * @see Desktop
 * @see EventQueue
 * @see Font
 * @see ActionEvent
 * @see KeyEvent
 * @see KeyListener
 * @see MouseAdapter
 * @see MouseEvent
 * @see MouseListener
 * @see IOException
 * @see InvocationTargetException
 * @see Method
 * @see URI
 * @see URISyntaxException
 * @see List
 * @see TimeUnit
 * @see BorderFactory
 * @see JFrame
 * @see JLabel
 * @see JOptionPane
 * @see ToolTipManager
 * @see UIManager
 * @see UnsupportedLookAndFeelException
 * @see DefaultTableCellRenderer
 * @see DefaultTableModel
 */
public class Dictionary extends JFrame {

    /** 
     * Username. 
     */
    private final String name = System.getProperty("user.name");
    
    /** 
     * {@code Config} object. 
     */
    private static final Config config = new Config();
    
    /** 
     * {@code FileServiceImpl} object. 
     */
    private final FileServiceImpl file = new FileServiceImpl(config);
    
    /** 
     * {@code ThemeServiceImpl} object. 
     */
    private final Service theme = new ThemeServiceImpl(config);
    
    /** 
     * {@code FontServiceImpl} object. 
     */
    private final Service font = new FontServiceImpl(config);
    
    /** 
     * {@code Reader} object. 
     */
    private static final DictionaryService dictionaryService = new DictionaryService();
    
    /** 
     * {@code Search} object. 
     */
    private final SearchService searchService = new SearchService();
    
    /** 
     * Default table model. 
     */
    private static DefaultTableModel tableM;
    
    /** 
     * The dictionary's name. 
     */
    private static String fileName;
    
    /** 
     * Word to translation. 
     */
    private static String from;
    
    /** 
     * Meaning. 
     */
    private static String to;
    
    /** 
     * Colors. 
     */
    private static final Color BLACK = Color.black;
    private static final Color WHITE = Color.white;
    private static final Color BLUE = new Color(32, 96, 200);
    private static final Color LIGHT = new Color(255, 249, 236);
    private static final Color LIGHT_GRAY = Color.lightGray;
    
    /**
     * Creates new form Dictionarys.
     * 
     * @throws IOException by I/O error
     * @throws IllegalAccessException if an application tries to reflectively 
     * create an instance ..
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
        
        tableM = (DefaultTableModel) table.getModel();
        table.getTableHeader().setForeground(BLUE);
        table.getTableHeader().setFont(new Font("HERCULANUM", 1, 12));
        
        // set table header identifier in center
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) 
                table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        fileName = file.get();
        dictionaryService.readFile(fileName);
        
        // set table header
        table.getColumnModel().getColumn(0)
                .setHeaderValue(file.split(fileName, 0));
        table.getColumnModel().getColumn(1)
                .setHeaderValue(file.split(fileName, 1));
        table.getTableHeader().resizeAndRepaint();
        
        Method method = (Method) theme.get();
        method.invoke(this); // set theme
        setFont((String) font.get());
        
        this.sumOf.setText(String.valueOf(dictionaryService.getSize()));
        this.status.setText("dictionary read in");
        
        // search ActionListener
        this.search.addActionListener((ActionEvent e) -> {
            String text = search.getText();
            String[] result;
            try {
                if (text.endsWith("#1") ||
                    text.endsWith("#2") ||
                    text.endsWith("#3") ||
                    text.endsWith("#4") ||
                    text.endsWith("#5") ||
                    text.endsWith("#6")) {
			
                    result = text.split("(#\\w+#|##|#)");
                    file.set(result[1]);
                    fileName = file.choose(result[1]);
                    dictionaryService.readFile(fileName);

                    // set table header
                    table.getColumnModel().getColumn(0)
                            .setHeaderValue(file.split(fileName, 0));
                    table.getColumnModel().getColumn(1)
                            .setHeaderValue(file.split(fileName, 1));
                    table.getTableHeader().resizeAndRepaint();

                    sumOf.setText(String.valueOf(dictionaryService.getSize()));
                    setSearchField();
                } else { 
		    if (text.endsWith(":1") || 
		        text.endsWith(":2") ||
		        text.endsWith(":3")) {
			    
			result = text.split("(:\\w+:|::|:)");
			theme.set(result[1]);
			Method meth = (Method) theme.choose(result[1]);
			meth.invoke(this); // set theme
			setSearchField();
		    } else { 
			if (text.endsWith("@1") ||
			    text.endsWith("@2") ||
		            text.endsWith("@3")) {

			    result = text.split("(@\\w+@|@@|@)");
			    font.set(result[1]);
			    setFont((String) font.choose(result[1]));
			    setSearchField();
			}
		    }
		}
            } catch (IOException | NoSuchMethodException | 
                     IllegalArgumentException | InvocationTargetException | 
                     IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        
        // search KeyListeners
        this.search.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = search.getText();
                if (text.equals("")) return;
                
                for (int i = tableM.getRowCount() - 1; i >= 0 ; i--) {
                    removeUnnecessaryRowsAndValues(i);
                }
                
                String[] keys = dictionaryService.getKeyArray();
                List<String> hits = searchService.getHits(keys, text);
                for (int i = 0; i < hits.size(); i++) {
                    String hit = dictionaryService.getValue(hits.get(i));
                    if ((!hit.contains("~") && !hit.contains("/")) ||
                          hit.contains("/")) {
                        from = hits.get(i);
                        to = hit;
                    } 
                    
                    // if contains '~' but '/' no, than cut and join to from
                    if (hit.contains("~") && !hit.contains("/")) {
                        String[] meanings = hit.split(":");
                        from = hits.get(i) + meanings[0].substring(1);
                        setTo(meanings);
                    }
                    printHits(i);
                }
                
                // set status text field
                int max = searchService.getMaxHits(keys, text);
                String words = " words";
                if (max > 10) {
                    status.setText("first 10 / " + max + words);
                } else {
                    if (max <= 1) words = " word";
                    status.setText(max + words);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
        });
        
        // table MouseListener
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                int row = table.getSelectedRow();
                if (row != -1) { 
                    Object valueFrom = tableM.getValueAt(row, 0);
                    Object valueTo = tableM.getValueAt(row, 1);
                    if ((String) valueTo != null) {
                        if (((String) valueTo).contains("/")) {
                            String[] hits = ((String) valueTo).split("/");
                            int numberOfHits = hits.length;
                            for (int i = 0; i < numberOfHits; i++) {
                                if (!hits[i].contains("~")) {
                                    from = valueFrom + "";
                                    to = hits[i];
                                } else {
                                    // cut by ~ and paste to from
                                    String[] meanings = hits[i].split(":");
                                    if (meanings[0].startsWith("~")) {
                                        from = valueFrom + meanings[0].substring(1);
                                        setTo(meanings);
                                    } else {
                                        String[] pre = meanings[0].split("~");
                                        from = pre[0] + valueFrom + pre[1];
                                        setTo(meanings);
                                    }
                                }
                                printHits(i);
                            }
                            for (int i = numberOfHits; i < tableM.getRowCount(); i++) {
                                removeUnnecessaryRowsAndValues(i);
                            }
                            status.setText(numberOfHits + " words");
                        }
                        search.requestFocus();
                    }
                }
            }
        });
        
        // google
        this.google.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWWW("http://www.google.hu/");
            }
        });
        
        // hackerrank
        this.google.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getWWW("http://www.hackerrank.com/");
            }
        });
        
        // time delay
        this.dictInfo.addMouseListener(getMouseListener());
        this.themeInfo.addMouseListener(getMouseListener());
        this.fontInfo.addMouseListener(getMouseListener());
        
        // email
        this.copyright.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:gabor.kolozsy.development@gmail.com"
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
        fontInfo = new javax.swing.JLabel();
        google1 = new javax.swing.JLabel();

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
        copyright.setToolTipText("<html>Copyright © 2016 Gabor Kolozsy<br>\n<a href=\"http ://blank\">gabor.kolozsy.development@gmail.com</a>");

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
        dictInfo.setToolTipText("<html><h4><a href=http://balnk>How to choose dictionary</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"3\"><b>#</b></font> and an <b>ID</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Dictionary</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><center><b>English-Hungarian<font color=\"rgb(32,96,200)\"></font></b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><center><b>Hungarian-English</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><center><b>German-Hungarian</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>4</b></center></td>\n       <td style=\"color:white\"><center><b>Hungarian-German</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>5</b></center></td>\n       <td style=\"color:white\"><center><b>English-German</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>6</b></center></td>\n       <td style=\"color:white\"><center><b>German-English</b></center></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200) \">#1</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\">#1</b></font></li>\n</ol>\n\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        themeInfo.setBackground(new java.awt.Color(0, 0, 0));
        themeInfo.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        themeInfo.setForeground(new java.awt.Color(32, 96, 200));
        themeInfo.setText("T");
        themeInfo.setToolTipText("<html><h4><a href=http://blank>How to choose theme</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"4\"><b>:</b></font> and an <b>ID</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Theme</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><center><b>Midnight<font color=\"rgb(32,96,200)\"></font></b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><center><b>Dark</b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><center><b>Light</b></center></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200) \"><font size=\"4\">:</font>1</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\"><font size=\"4\">:</font>1</b></font></li>\n</ol>\n\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        google.setBackground(new java.awt.Color(0, 0, 0));
        google.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        google.setForeground(new java.awt.Color(32, 96, 200));
        google.setText("G");
        google.setToolTipText("<html><a href=http://>google.hu</a></html>");

        fontInfo.setBackground(new java.awt.Color(0, 0, 0));
        fontInfo.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        fontInfo.setForeground(new java.awt.Color(32, 96, 200));
        fontInfo.setText("F");
        fontInfo.setToolTipText("<html><h4><a href=http://blank>How to choose font type</a></h4>\n\ntype <font color=\"rgb(32,96,200)\" size=\"3\"><b>@</b></font> and an <b>ID</b>\n   \n<blockquote>\n  <table border=1 style=\"background-color: black\">\n   <tr>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">ID</font></th>\n       <th style=\"color:rgb(32,96,200)\"><font color=\"rgb(32,96,200)\" face=\"HERCULANUM\" size=\"4\">Font</font></th>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>1</b></center></td>\n       <td style=\"color:white\"><font face=\"HERCULANUM\" size=\"3\" color=\"white\"><center>HERCULANUM</font></b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>2</b></center></td>\n       <td style=\"color:white\"><font face=\"Dialog\" size=\"3\" color=\"white\"><center>Dialog</font></b></center></td>\n   </tr>\n   <tr>\n       <td style=\"color:white\"><center><b>3</b></center></td>\n       <td style=\"color:white\"><font face=\"Comic Sans MS\" size=\"3\" color=\"white\"><center>Comic Sans MS</font></b></center></td>\n   </tr>\n  </table>\n</blockquote>\n\nand press <font color=\"rgb(32,96,200)\" size=\"3\"><b><code>Enter</code></b></font><br><br>\n\n<font size=\"3\"><b>For example:</b></font>\n\n<ol>\n   <li><b><font color=\"rgb(32,96,200)\">@2</font></b></li>\n   <li><b><font color=\"red\">blah</font><font color=\"rgb(32,96,200)\">@2</b></font></li>\n</ol>\n\n\nand enjoy ... <font color=\"rgb(32,96,200)\" size=\"6\"><b>☮</b></font></html>");

        google1.setBackground(new java.awt.Color(0, 0, 0));
        google1.setFont(new java.awt.Font("Herculanum", 0, 18)); // NOI18N
        google1.setForeground(new java.awt.Color(32, 96, 200));
        google1.setText("H");
        google1.setToolTipText("<html><a href=http://>hackerrank.com</a></html>");

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
                        .addComponent(google1)
                        .addGap(117, 117, 117)
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
                    .addComponent(fontInfo)
                    .addComponent(google1))
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
     * Return the meaning.
     * @param meanings the meanings array
     * @return the meaning
     */
    private String setTo(String[] meanings) {
        to = meanings[1];
        if (meanings[1].startsWith(" ")) {
            to = meanings[1].substring(1);
        }
        return to;
    }
    
    /**
     * Print the hits.
     * @param i iteration
     */
    private void printHits(int i) {
        if (i >= tableM.getRowCount()) {
            tableM.addRow(new Object[] {from, to });
        } else {
            tableM.setValueAt(from, i, 0);
            tableM.setValueAt(to, i, 1);
        }
    }
    
    /**
     * Remove unnecessary rows and values from table.
     * @param i iteration
     */
    private void removeUnnecessaryRowsAndValues(int i) {
        if (i < 10) {
            tableM.setValueAt("", i, 0);
            tableM.setValueAt("", i, 1);
        } else {
            tableM.removeRow(i);
        }
    }
    
    /**
     * Midnight theme.
     */
    private void midnight() {
        search.setBorder(BorderFactory.createLineBorder(BLACK));
        search.setBackground(BLACK);
        search.setForeground(WHITE);
        jPanel.setBackground(BLACK);
        jScrollPane.setBorder(BorderFactory.createLineBorder(BLACK));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(BLACK));
        table.getTableHeader().setBackground(BLACK);
        table.setBackground(BLACK);
        table.setForeground(WHITE);
        table.setGridColor(BLACK);
        table.setSelectionBackground(BLACK);
        table.setSelectionForeground(WHITE);
        status.setBorder(BorderFactory.createLineBorder(BLACK));
        status.setBackground(BLACK);
        status.setForeground(WHITE);
    }
    
    /**
     * Dark theme.
     */
    private void dark() {
        search.setBorder(BorderFactory.createLineBorder(BLUE));
        search.setBackground(BLACK);
        search.setForeground(WHITE);
        jPanel.setBackground(BLACK);
        jScrollPane.setBorder(BorderFactory.createLineBorder(BLACK));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(BLUE));
        table.getTableHeader().setBackground(BLACK);
        table.setBackground(BLACK);
        table.setForeground(WHITE);
        table.setGridColor(new Color(25, 25, 25));
        table.setSelectionBackground(BLACK);
        table.setSelectionForeground(WHITE);
        status.setBorder(BorderFactory.createLineBorder(BLUE));
        status.setBackground(BLACK);
        status.setForeground(WHITE);
    }
    
    /**
     * Light theme.
     */
    private void light() {
        search.setBorder(BorderFactory.createLineBorder(LIGHT));
        search.setBackground(LIGHT);
        search.setForeground(BLACK);
        jPanel.setBackground(LIGHT);
        jScrollPane.setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));
        table.getTableHeader().setBackground(LIGHT);
        table.setBackground(LIGHT);
        table.setForeground(BLACK);
        table.setGridColor(LIGHT_GRAY);
        table.setSelectionBackground(LIGHT);
        table.setSelectionForeground(BLACK);
        status.setBorder(BorderFactory.createLineBorder(LIGHT));
        status.setBackground(LIGHT);
        status.setForeground(BLACK);
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
     * Set search text field.
     * Focus first time pass and then take back.
     */
    private void setSearchField() {
        this.sumOf.requestFocus();
        this.search.requestFocus();
        this.search.setText("type something..");
        if (System.getProperty("os.name").startsWith("Win")) {
            this.search.setText("");
        }
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

            final int dismissDelayMinutes = (int) TimeUnit.SECONDS.toMillis(15);

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
                System.exit(0);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel copyright;
    private javax.swing.JLabel dictInfo;
    private javax.swing.JLabel fontInfo;
    private javax.swing.JLabel google;
    private javax.swing.JLabel google1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextField search;
    private javax.swing.JTextField status;
    private javax.swing.JLabel sumOf;
    private javax.swing.JTable table;
    private javax.swing.JLabel themeInfo;
    // End of variables declaration//GEN-END:variables
}
