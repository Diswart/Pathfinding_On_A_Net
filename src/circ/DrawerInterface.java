/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *
 * @author Sheriff
 */
public interface DrawerInterface {
     public void setSpacing(double space);
     public void setRadius(double rad);
     public void setDefault();
     public double getRadius();
     public Point getStart();
     public Point getEnd();
     public void draw(ArrayList<ArrayList<Integer>> outer,  Group group);//drawing circle and its connections
     public void drawPath(Map<Integer,Point> sequence, Group group);
     public void dropPath(Group group);
     public void arrow(double startX, double startY, double endX, double endY,  Group group, Color col);
     public void addEdge(Path path, Group group);
}
