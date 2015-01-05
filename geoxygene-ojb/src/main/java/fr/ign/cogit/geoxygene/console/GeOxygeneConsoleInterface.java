/*
 * This file is part of the GeOxygene project source files.
 * 
 * GeOxygene aims at providing an open framework which implements OGC/ISO
 * specifications for the development and deployment of geographic (GIS)
 * applications. It is a open source contribution of the COGIT laboratory at the
 * Institut Géographique National (the French National Mapping Agency).
 * 
 * See: http://oxygene-project.sourceforge.net
 * 
 * Copyright (C) 2005 Institut Géographique National
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library (see file LICENSE if present); if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */

package fr.ign.cogit.geoxygene.console;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Thierry Badard & Arnaud Braun & Eric Grosso
 * @version 1.1
 * 
 */

class GeOxygeneConsoleInterface extends JFrame {

  /**
   * serial uid.
   */
  private static final long serialVersionUID = 1L;

  private static String ojb = "OJB";
  private static String castor = "Castor";
  private static String hibernate = "Hibernate";

  private static String sqlToJavaText = "SQL to Java";
  private static String javaToSqlText = "Java to SQL";
  private static String manageDataText = "Manage Data";
  private static String datasetText = "Create DataSet";
  private static String viewDataText = "View data";
  private static String importDatatext = "Import data";
  private static String exportDatatext = "Export data";
  private static String quitText = "QUIT";

  /**
   * Constructeur.
   * @param titre de la console
   */
  protected GeOxygeneConsoleInterface(String titre) {
    super(titre);
    this.InterfaceInit();
  }

  /**
   * Initialisation
   */
  private void InterfaceInit() {

    System.out.println("Bonjour");

    // Init GUI
    this.getContentPane().setLayout(new GridLayout(9, 1));

    // A COMMENTER
    final JPanel mappingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    final JComboBox mappingComboBox = new JComboBox(new String[] { GeOxygeneConsoleInterface.hibernate, GeOxygeneConsoleInterface.ojb,
        GeOxygeneConsoleInterface.castor });
    mappingPanel.add(mappingComboBox);

    this.getContentPane().add(mappingPanel);

    /**
     * Action SQLtoJava
     */
    JButton sqlToJavaButton = new JButton(GeOxygeneConsoleInterface.sqlToJavaText);
    this.getContentPane().add(sqlToJavaButton);
    sqlToJavaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String mappingTool = (String) mappingComboBox.getSelectedItem();
        SqlToJava.action(GeOxygeneConsoleInterface.this.getMappingTool(mappingTool));
      }
    });

    JButton javaToSqlButton = new JButton(GeOxygeneConsoleInterface.javaToSqlText);
    this.getContentPane().add(javaToSqlButton);
    javaToSqlButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JavaToSql.action();
      }
    });

    JButton manageDataButton = new JButton(GeOxygeneConsoleInterface.manageDataText);
    this.getContentPane().add(manageDataButton);
    manageDataButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ManageData.action();
      }
    });

    JButton datasetButton = new JButton(GeOxygeneConsoleInterface.datasetText);
    this.getContentPane().add(datasetButton);
    datasetButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("marche pas !!");
      }
    });

    JButton importDataButton = new JButton(GeOxygeneConsoleInterface.importDatatext);
    this.getContentPane().add(importDataButton);
    importDataButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("marche pas !!");
      }
    });

    final ExportData exportData = new ExportData();
    JButton exportDataButton = new JButton(GeOxygeneConsoleInterface.exportDatatext);
    this.getContentPane().add(exportDataButton);
    exportDataButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        exportData.action();
      }
    });

    JButton viewDataButton = new JButton(GeOxygeneConsoleInterface.viewDataText);
    this.getContentPane().add(viewDataButton);
    viewDataButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ViewData.action();
      }
    });

    JButton quitButton = new JButton(GeOxygeneConsoleInterface.quitText);
    this.getContentPane().add(quitButton);
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GeOxygeneConsoleInterface.this.dispose();
        System.exit(0);
      }
    });

  }

  /**
   * Return mapping tool.
   * @param mappingToolTxt
   * @return mapping tool code
   */
  private int getMappingTool(String mappingToolTxt) {
    if (mappingToolTxt.equals(GeOxygeneConsoleInterface.ojb)) {
      return MappingTool.MAPPING_OJB;
    } else if (mappingToolTxt.equals(GeOxygeneConsoleInterface.castor)) {
      return MappingTool.MAPPING_CASTOR;
    } else if (mappingToolTxt.equals(GeOxygeneConsoleInterface.hibernate)) {
      return MappingTool.MAPPING_HIBERNATE;
    } else {
      return 0;
    }
  }

}
