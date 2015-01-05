/*
 * This file is part of the GeOxygene project source files. GeOxygene aims at
 * providing an open framework which implements OGC/ISO specifications for the
 * development and deployment of geographic (GIS) applications. It is a open
 * source contribution of the COGIT laboratory at the Institut Géographique
 * National (the French National Mapping Agency). See:
 * http://oxygene-project.sourceforge.net Copyright (C) 2005 Institut
 * Géographique National This library is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License,
 * or any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library (see file
 * LICENSE if present); if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package fr.ign.cogit.geoxygene.datatools.ojb;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

/**
 * Conversion des geometries d'un SGBD (Oracle ou Postgis) dans le format
 * GeOxygene, et reciproquement. Pour fonctionner, utiliser absolument la classe
 * "GeOxygeneStatementManager" ( à configurer dans OJB.properties). Permet
 * d'utiliser le même convertisseur pour Oracle et Postgis, et ainsi les mêmes
 * fichiers de mapping.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.1
 */
public class GeomGeOxygene2Dbms implements FieldConversion {
  static Logger logger = Logger.getLogger(GeomGeOxygene2Dbms.class.getName());

  /**
     * 
     */
  private static final long serialVersionUID = 1L;
  private static final String POSTGRES_GEOM_CLASS_NAME = "org.postgis.PGgeometry"; //$NON-NLS-1$
  private static final String ORACLE_GEOM_CLASS_NAME = "oracle.sql.STRUCT"; //$NON-NLS-1$

  private final String GeomGeOxygene2Oracle_CLASS_NAME = "fr.ign.cogit.geoxygene.datatools.oracle" + //$NON-NLS-1$
      ".GeomGeOxygene2Oracle"; //$NON-NLS-1$
  private final String GeomGeOxygene2Postgis_CLASS_NAME = "fr.ign.cogit.geoxygene.datatools.postgis" + //$NON-NLS-1$
      ".GeomGeOxygene2Postgis"; //$NON-NLS-1$

  private Method geomOracle2GeOxygeneMethod;
  private Method geomPostgis2GeOxygeneMethod;

  // Le constructeur initialise les méthodes à appeler
  public GeomGeOxygene2Dbms() {
    // ORACLE
    try {
      Class<?> geomGeOxygene2OracleClass = Class
          .forName(this.GeomGeOxygene2Oracle_CLASS_NAME);
      try {
        this.geomOracle2GeOxygeneMethod = geomGeOxygene2OracleClass.getMethod(
            "sqlToJava", //$NON-NLS-1$
            new Class[] { Object.class });
      } catch (NoSuchMethodException nosuch1) {
        nosuch1.printStackTrace();
        System.exit(0);
      }
    } catch (ClassNotFoundException notfound1) {
      // On ne dit rien : Oracle n'a pas été compilé !
    }

    // POSTGIS
    try {
      Class<?> geomGeOxygene2PostgisClass = Class
          .forName(this.GeomGeOxygene2Postgis_CLASS_NAME);
      try {
        this.geomPostgis2GeOxygeneMethod = geomGeOxygene2PostgisClass
            .getMethod("sqlToJava", //$NON-NLS-1$
                new Class[] { Object.class });
      } catch (NoSuchMethodException nosuch2) {
        GeomGeOxygene2Dbms.logger.fatal("sqlToJava does not exist in " //$NON-NLS-1$
            + this.GeomGeOxygene2Postgis_CLASS_NAME);
        nosuch2.printStackTrace();
        System.exit(0);
      }
    } catch (ClassNotFoundException notfound2) {
      GeomGeOxygene2Dbms.logger.fatal("Unhandled platform"); //$NON-NLS-1$
      GeomGeOxygene2Dbms.logger.fatal(notfound2.getMessage());
      System.exit(0);
    }
  }

  @Override
  public Object sqlToJava(Object geom) {
    if (geom == null) {
      return null;
    }
    // ORACLE
    if (geom.getClass().getName()
        .compareTo(GeomGeOxygene2Dbms.ORACLE_GEOM_CLASS_NAME) == 0) {
      try {
        return this.geomOracle2GeOxygeneMethod.invoke(null,
            new Object[] { geom });
      } catch (Exception e) {
        GeomGeOxygene2Dbms.logger.error("Returning null geometry"); //$NON-NLS-1$
        GeomGeOxygene2Dbms.logger.error(e.getMessage());
        e.printStackTrace();
        return null;
      }
    }
    if (geom.getClass().getName()
        .compareTo(GeomGeOxygene2Dbms.POSTGRES_GEOM_CLASS_NAME) == 0) {
      try {
        return this.geomPostgis2GeOxygeneMethod.invoke(null,
            new Object[] { geom });
      } catch (Exception e) {
        GeomGeOxygene2Dbms.logger.error("Returning null geometry"); //$NON-NLS-1$
        GeomGeOxygene2Dbms.logger.error(e.getMessage());
        e.printStackTrace();
        return null;
      }
    }
    GeomGeOxygene2Dbms.logger.error("Returning null: unhandled platform"); //$NON-NLS-1$
    return null;
  }

  // Les méthodes relatives à Oracle ou Postgis sont appelée directement
  // dans "GeOxygeneStatementManager"
  @Override
  public Object javaToSql(Object geom) {
    GeomGeOxygene2Dbms.logger.error("Should not be reached: returning null"); //$NON-NLS-1$
    return null;
  }
}
