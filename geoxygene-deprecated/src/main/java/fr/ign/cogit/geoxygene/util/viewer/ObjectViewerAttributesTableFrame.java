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

package fr.ign.cogit.geoxygene.util.viewer;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import uk.ac.leeds.ccg.dbffile.Dbf;
import uk.ac.leeds.ccg.geotools.DataSource;
import uk.ac.leeds.ccg.geotools.ShapefileReader;
import uk.ac.leeds.ccg.geotools.Theme;

/**
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

class ObjectViewerAttributesTableFrame extends JFrame {

  /**
	 * 
	 */
  private static final long serialVersionUID = 890219238828518490L;

  private static final String FRAME_TITLE = "GeOxygene Theme Values - ";

  private Theme theme;
  private int nbFields;
  private int nbRecords;
  private Vector<StringBuffer> columnNames;
  private Vector<Vector<String>> rowData;
  private String title;
  private DataSource dataSource;

  public ObjectViewerAttributesTableFrame(Theme t, String dataSourceType,
      DataSource source) {
    super();

    this.theme = t;
    this.dataSource = source;

    if (dataSourceType.equals(Utils.SHAPEFILE)) {
      this.shapefile();
    }

    else if (dataSourceType.equals(Utils.GEOXYGENE)) {
      this.geoxygene();
    }

    else {
      System.out
          .println(" ## SHOW ATTRIBUTES : NOT DEFINED FOR THIS KIND OF DATASOURCE ");
    }

    // Title
    this.setTitle(ObjectViewerAttributesTableFrame.FRAME_TITLE + this.title);

    // Layout
    this.getContentPane().setLayout(new BorderLayout());

    // Create/setup table
    JTable table = new JTable(this.rowData, this.columnNames);

    // Place table in JScrollPane
    JScrollPane scrollPane = new JScrollPane(table);

    // Add to Screen
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);

  }

  /**
   * Fill vectors of attributes for a shapefile by reading directly dbf file (we
   * do not load it into memory)
   */
  public void shapefile() {

    String themeName = this.theme.getName();
    this.title = themeName.substring(themeName.lastIndexOf("/") + 1); //$NON-NLS-1$

    try {
      // Transformer URL en File pour eviter le chargement en memoire du fichier
      // Dbf
      final Dbf themeDbf = ((ShapefileReader) this.dataSource).dbf;

      this.nbFields = themeDbf.getNumFields();
      this.nbRecords = themeDbf.getLastRec();
      StringBuffer fieldName;
      Vector<String> row;
      this.columnNames = new Vector<StringBuffer>();
      this.rowData = new Vector<Vector<String>>();

      for (int i = 0; i < this.nbFields; i++) {
        fieldName = themeDbf.getFieldName(i);
        this.columnNames.add(fieldName);
      }

      for (int j = 0; j < this.nbRecords; j++) {
        row = new Vector<String>();
        for (int i = 0; i < this.nbFields; i++) {
          if (themeDbf.getFieldType(i) == 'N') {
            row.add((themeDbf.getFloatCol(i, j, j + 1))[0].toString());
          } else {
            row.add((themeDbf.getStringCol(i, j, j + 1))[0].toString());
          }
        }
        this.rowData.add(row);
      }

    } catch (Exception e) {
      System.err.println("Can not open the file: " + e);
    }

  }

  /** Fill vectors of attributes for GeOxygene data */
  @SuppressWarnings("unchecked")
  public void geoxygene() {
    this.title = this.theme.getName();
    GeOxygeneReader geOxyRd = (GeOxygeneReader) this.dataSource;
    Object[] result = geOxyRd.readData();
    this.columnNames = (Vector<StringBuffer>) result[0];
    this.rowData = (Vector<Vector<String>>) result[1];

  }

}
