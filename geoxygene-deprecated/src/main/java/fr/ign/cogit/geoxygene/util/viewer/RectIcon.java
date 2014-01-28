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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

class RectIcon implements Icon {
  Color color;

  public RectIcon(Color c) {
    this.color = c;
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.setColor(this.color);
    g.fillRect(x, y, this.getIconWidth(), this.getIconHeight());
  }

  @Override
  public int getIconWidth() {
    return 40;
  }

  @Override
  public int getIconHeight() {
    return 10;
  }

  public Color getColor() {
    return this.color;
  }

}
