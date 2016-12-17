/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import java.util.ArrayList;

/**
 *
 * @author Sheriff
 */
public class Path {
    ArrayList<Integer> bitMask;
    ArrayList<Integer> code;
    Point circle;
    double k;
    Path()
    {
        bitMask = new ArrayList<Integer>();
        code = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            bitMask.add(0);
        circle = new Point();
        k = 0;
    }
    void setZeros()
    {
        bitMask.clear();
        for (int i = 0; i < 4; i++)
            bitMask.add(0);
    }
}

