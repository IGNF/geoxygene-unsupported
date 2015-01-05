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

package fr.ign.cogit.geoxygene.example.relations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.ign.cogit.geoxygene.datatools.Geodatabase;
import fr.ign.cogit.geoxygene.datatools.ojb.GeodatabaseOjbFactory;

// A FINIR 1-N marche pas : A VOIR SI FIXE PAR OJB v1.0
// tester le chargement a ce moment

/**
 * Test des relations MONODIRECTIONNELLES entre les classes AAA et BBB. Ce test
 * teste aussi la persistance. C'est le meme que
 * "TestRelationsMonoNonPersistantes", avec en plus les fonctions d'ecriture
 * dans le SGBD. On peut controler a tout moment l'etat de la base en activant
 * des commit et en quittant l'application. Pensez a activer le fichier de
 * mapping "repository_AAA_BBB.xml" . La base s'initialise avec le script
 * "init_relations_AAA_BBB.sql" .
 * 
 * @author Thierry Badard, Arnaud Braun & Sébastien Mustière
 * @version 1.0
 * 
 */

public class TestRelationsMonoPersistantes {

  private static Geodatabase db = GeodatabaseOjbFactory.newInstance();

  public static void main(String args[]) {
    System.out.println("DEBUT DES TESTS");
    TestRelationsMonoPersistantes.test_mono11();
    TestRelationsMonoPersistantes.test_mono1N();
    TestRelationsMonoPersistantes.test_monoNN();
    TestRelationsMonoPersistantes.charge();
  }

  /** Teste la relation 1-1 mono-directionnelle */
  public static void test_mono11() {

    TestRelationsMonoPersistantes.db.begin();

    System.out.println("Creation des objets AAA, BBB");
    AAA a1 = new AAA();
    a1.setNom("a1");
    AAA a2 = new AAA();
    a2.setNom("a2");
    AAA a3 = new AAA();
    a3.setNom("a3");
    BBB b1 = new BBB();
    b1.setNom("b1");
    BBB b2 = new BBB();
    b2.setNom("b2");
    BBB b3 = new BBB();
    b3.setNom("b3");

    TestRelationsMonoPersistantes.db.makePersistent(a1);
    TestRelationsMonoPersistantes.db.makePersistent(a2);
    TestRelationsMonoPersistantes.db.makePersistent(a3);
    TestRelationsMonoPersistantes.db.makePersistent(b1);
    TestRelationsMonoPersistantes.db.makePersistent(b2);
    TestRelationsMonoPersistantes.db.makePersistent(b3);

    System.out.println("");
    System.out.println("TEST RELATION 1-1 MONODIRECTIONNELLE");
    System.out.println("objet BBB en relation avec a1 (null) : "
        + a1.getObjetBBB_mono11());
    System.out.println("objet BBB en relation avec a2 (null) : "
        + a2.getObjetBBB_mono11());
    System.out.println("--");
    System.out.println("instanciation sur a1 de a1 R b1 ");
    a1.setObjetBBB_mono11(b1);
    System.out.println("objet BBB en relation avec a1 (b1) : "
        + a1.getObjetBBB_mono11().getNom());
    System.out.println("objet BBB en relation avec a2 (null) : "
        + a2.getObjetBBB_mono11());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("--");
    System.out.println("instanciation sur a1 de a1 R b2 ");
    a1.setObjetBBB_mono11(b2);
    System.out.println("objet BBB en relation avec a1 (b2) : "
        + a1.getObjetBBB_mono11().getNom());
    System.out.println("objet BBB en relation avec a2 (null) : "
        + a2.getObjetBBB_mono11());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("--");
    System.out.println("instanciation sur a1 de a1 R b2 (2eme fois)");
    a1.setObjetBBB_mono11(b2);
    System.out.println("objet BBB en relation avec a1 (b2) : "
        + a1.getObjetBBB_mono11().getNom());
    System.out.println("objet BBB en relation avec a2 (null) : "
        + a2.getObjetBBB_mono11());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    /*
     * System.out.println("--");
     * System.out.println("vidage des relations sur b1");
     * a1.setObjetBBB_mono11(null);
     * System.out.println("objet BBB en relation avec a1 (null) : "
     * +a1.getObjetBBB_mono11());
     * System.out.println("objet BBB en relation avec a2 (null) : "
     * +a2.getObjetBBB_mono11());
     */
    /* decommenter si on veut consulter l'etat de la base ici */
    TestRelationsMonoPersistantes.db.commit();

  }

  /** Teste la relation 1-n mono-directionnelle */
  public static void test_mono1N() {

    TestRelationsMonoPersistantes.db.begin();

    System.out.println("Creation des objets AAA, BBB");
    AAA a1 = new AAA();
    a1.setNom("a1");
    AAA a2 = new AAA();
    a2.setNom("a2");
    AAA a3 = new AAA();
    a3.setNom("a3");
    BBB b1 = new BBB();
    b1.setNom("b1");
    BBB b2 = new BBB();
    b2.setNom("b2");
    BBB b3 = new BBB();
    b3.setNom("b3");
    List<Object> L;

    TestRelationsMonoPersistantes.db.makePersistent(a1);
    TestRelationsMonoPersistantes.db.makePersistent(a2);
    TestRelationsMonoPersistantes.db.makePersistent(a3);
    TestRelationsMonoPersistantes.db.makePersistent(b1);
    TestRelationsMonoPersistantes.db.makePersistent(b2);
    TestRelationsMonoPersistantes.db.makePersistent(b3);

    System.out.println("");
    System.out.println("TEST RELATION 1-N MONODIRECTIONNELLE");
    System.out.println("objet BBB en relation avec a1 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a2 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_mono1N());

    System.out.println("--");
    System.out.println("ajout sur a1, de b1 aux objets en relation avec a1");
    System.out
        .println("set sur a2, de liste 'b2, b3' comme objets en relation avec a2");
    a1.addObjetBBB_mono1N(b1);
    L = new ArrayList<Object>();
    L.add(b2);
    L.add(b3);
    a2.setListe_objetsBBB_mono1N(L);
    System.out.println("objet BBB en relation avec a1 (b1) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a2 (b2, b3) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_mono1N());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("--");
    System.out.println("vidage sur a1 des objets en relation");
    a1.emptyListe_objetsBBB_mono1N();
    System.out.println("objet BBB en relation avec a1 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a2 (b2,b3) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_mono1N());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("--");
    System.out
        .println("set sur a2, de liste 'b1, b3' comme objets en relation avec a2");
    L = new ArrayList<Object>();
    L.add(b1);
    L.add(b3);
    a2.setListe_objetsBBB_mono1N(L);
    System.out.println("objet BBB en relation avec a1 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a2 (b1, b3) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_mono1N());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("--");
    System.out
        .println("ajout sur a2, de liste b2 comme objets en relation avec a2");
    a2.addObjetBBB_mono1N(b2);
    System.out.println("objet BBB en relation avec a1 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a2 (b1, b3, b2) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_mono1N());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_mono1N());
    /* decommenter si on veut consulter l'etat de la base ici */
    TestRelationsMonoPersistantes.db.commit();

  }

  /** Teste la relation n-n mono-directionnelle */
  public static void test_monoNN() {

    TestRelationsMonoPersistantes.db.begin();

    System.out.println("Creation des objets AAA, BBB");
    AAA a1 = new AAA();
    a1.setNom("a1");
    AAA a2 = new AAA();
    a2.setNom("a2");
    AAA a3 = new AAA();
    a3.setNom("a3");
    BBB b1 = new BBB();
    b1.setNom("b1");
    BBB b2 = new BBB();
    b2.setNom("b2");
    BBB b3 = new BBB();
    b3.setNom("b3");
    List<Object> L;

    TestRelationsMonoPersistantes.db.makePersistent(a1);
    TestRelationsMonoPersistantes.db.makePersistent(a2);
    TestRelationsMonoPersistantes.db.makePersistent(a3);
    TestRelationsMonoPersistantes.db.makePersistent(b1);
    TestRelationsMonoPersistantes.db.makePersistent(b2);
    TestRelationsMonoPersistantes.db.makePersistent(b3);

    System.out.println("TEST RELATION N-M MONODIRECTIONNELLE");
    System.out
        .println("set sur a1, de liste b1 b2 comme objets en relation avec a2");
    System.out.println("add sur a2, de b2 comme objets en relation avec a2");
    System.out.println("add sur a3, de b3 comme objets en relation avec a3");
    L = new ArrayList<Object>();
    L.add(b1);
    L.add(b2);
    a1.setListe_objetsBBB_monoNM(L);
    a2.addObjetBBB_monoNM(b2);
    a3.addObjetBBB_monoNM(b3);
    System.out.println("objet BBB en relation avec a1 (b1, b2) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a2 (b2) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a3 (b3) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_monoNM());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("");
    System.out.println("--");
    System.out
        .println("set sur a1, de liste b1 b2 comme objets en relation avec a2");
    System.out.println("add sur a2, de b2 comme objets en relation avec a2");
    System.out.println("add sur a3, de b3 comme objets en relation avec a3");
    L = new ArrayList<Object>();
    L.add(b1);
    L.add(b2);
    a1.setListe_objetsBBB_monoNM(L);
    a2.addObjetBBB_monoNM(b2);
    a3.addObjetBBB_monoNM(b3);
    System.out.println("objet BBB en relation avec a1 (b1, b2) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a2 (b2, b2) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a3 (b3, b3) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_monoNM());
    /* decommenter si on veut consulter l'etat de la base ici */
    // db.commit();
    // System.exit(0);

    System.out.println("");
    System.out.println("-- ");
    System.out.println("remove sur b1 de a1");
    System.out.println("remove sur b2 de a2");
    System.out.println("vidage de a3");
    L = new ArrayList<Object>();
    L.add(b1);
    L.add(b2);
    a3.emptyListe_objetsBBB_monoNM();
    a1.removeObjetBBB_monoNM(b1);
    a2.removeObjetBBB_monoNM(b2);
    System.out.println("objet BBB en relation avec a1 (b2) : ");
    TestRelationsMonoPersistantes.affiche(a1.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a2 (b2) : ");
    TestRelationsMonoPersistantes.affiche(a2.getListe_objetsBBB_monoNM());
    System.out.println("objet BBB en relation avec a3 (vide) : ");
    TestRelationsMonoPersistantes.affiche(a3.getListe_objetsBBB_monoNM());
    /* decommenter si on veut consulter l'etat de la base ici */
    TestRelationsMonoPersistantes.db.commit();

  }

  /** Chargement des objets */
  public static void charge() {
    TestRelationsMonoPersistantes.db.begin();
    List<AAA> allAAA = TestRelationsMonoPersistantes.db.loadAll(AAA.class);
    System.out.println("-- AAA ---");
    Iterator<AAA> itA = allAAA.iterator();
    while (itA.hasNext()) {
      AAA a = itA.next();
      System.out.println("##  objet - id = " + a.getId() + " - nom = "
          + a.getNom());
      System.out.print("        lien 1-1 : ");
      if (a.getObjetBBB_mono11() != null) {
        System.out.print("id = " + a.getObjetBBB_mono11().getId() + " - nom = "
            + a.getObjetBBB_mono11().getNom());
      }
      System.out.println();
      System.out.println("        lien 1-n : ");
      TestRelationsMonoPersistantes.affiche(a.getListe_objetsBBB_mono1N());
      System.out.println("        lien n-m : ");
      TestRelationsMonoPersistantes.affiche(a.getListe_objetsBBB_monoNM());
    }
  }

  public static void affiche(List<Object> L) {
    Iterator<Object> it = L.iterator();
    while (it.hasNext()) {
      ClasseMere o = (ClasseMere) it.next();
      if (o == null) {
        System.out.println("   - null");
      } else {
        System.out.println("   - " + o.getNom());
      }
    }
  }
}
