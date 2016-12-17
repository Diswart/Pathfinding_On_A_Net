/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

/**
 *
 * @author Sheriff
 */
public class Point {
    private int i;
    private int j;
    public Point(int i, int j)
    {
        this.i=i;//for "i" index
        this.j=j;//for "j" index
    }
    public Point()
    {
        i=-1;
        j=-1;
    }
    public int getI()
    {
        return i;
    }
    public int getJ()
    {
        return j;
    }
    public void setI(int i)
    {
        this.i=i;
    }
    public void setJ(int j)
    {
        this.j=j;
    }
    public void setAll(int i, int j)
    {
        this.i=i;
        this.j=j;
    }
    public void reset()
    {
        i=-1;
        j=-1;
    }
}
