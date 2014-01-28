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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * This class defines a Listener for the popup menu dedicated to the theme
 * buttons of the (Geo)Object Viewer.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

class PopupListener extends MouseAdapter {
  public JPopupMenu themepopup;

  public PopupListener(JPopupMenu tp) {
    this.themepopup = tp;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.maybeShowPopup(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.maybeShowPopup(e);
  }

  private void maybeShowPopup(MouseEvent e) {
    if (e.isPopupTrigger()) {
      this.themepopup.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}
