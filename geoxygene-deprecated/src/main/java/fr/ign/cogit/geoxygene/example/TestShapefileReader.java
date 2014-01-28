package fr.ign.cogit.geoxygene.example;

import java.io.File;

import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IPopulation;
import fr.ign.cogit.geoxygene.util.conversion.ShapefileReader;
import fr.ign.cogit.geoxygene.util.loader.gui.GUIShapefileChoice;
import fr.ign.cogit.geoxygene.util.viewer.ObjectViewer;

public class TestShapefileReader {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ObjectViewer vwr = new ObjectViewer();
    GUIShapefileChoice chooser = new GUIShapefileChoice(true);
    File[] files = chooser.getSelectedFiles();
    chooser.dispose();
    if (files != null) {
      for (File file : files) {
        System.out.println(file.getAbsolutePath());
        IPopulation<IFeature> pop = ShapefileReader
            .read(file.getAbsolutePath());
        vwr.addFeatureCollection(pop,
            file.getName().substring(0, file.getName().lastIndexOf(".")));
      }
    }
  }

}
