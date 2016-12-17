/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import static java.lang.Math.pow;
import java.util.ArrayList;
import javafx.scene.Group;

/**
 *
 * @author Sheriff
 */
public class GraphEdges {
    //(ArrayList<ArrayList <Integer>> outer, ArrayList<ArrayList <Integer>> reachs, Klaster k, Drawer drawer, Group visualGroup)
    private ArrayList<ArrayList<Integer>> outer;
    private Klaster k;
    //private Drawer drawer;
    private Group visualGroup;
    public long timeSpent;
    public static ArrayList<ArrayList<Integer>> copyArray(ArrayList<ArrayList<Integer>> a)
    {
        int size = a.size();
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        for (int is= 0; is<size;is++)
        {
            ArrayList <Integer> inner = new ArrayList<Integer>();
            for (int js = 0; js<size;js++)
                inner.add(a.get(is).get(js));
            ret.add(inner);
        }
        return ret;
    }
    public static ArrayList<Integer> copyMask(ArrayList<Integer> a)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i=0; i<a.size(); i++)
            ret.add(a.get(i));
        return ret;
    }
    GraphEdges(ArrayList<ArrayList<Integer>> outer , Klaster k, /*Drawer drawer, */ Group visualGroup)
    {
        this.outer = copyArray(outer);
        this.k = k;
        //this.drawer = drawer;
        this.visualGroup = visualGroup;
    }
    public static ArrayList<Integer> toMask (int a)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        switch (a)
        {
            case 0:
                ret.add(0);
                ret.add(0);
                ret.add(0);
                ret.add(0);
                return ret;
            case 1:
                ret.add(0);
                ret.add(0);
                ret.add(0);
                ret.add(1);
                return ret;
            case 2:
                ret.add(0);
                ret.add(0);
                ret.add(1);
                ret.add(0);
                return ret;
            case 3:
                ret.add(0);
                ret.add(0);
                ret.add(1);
                ret.add(1);
                return ret;
            case 4:
                ret.add(0);
                ret.add(1);
                ret.add(0);
                ret.add(0);
                return ret;
            case 5:
                ret.add(0);
                ret.add(1);
                ret.add(0);
                ret.add(1);
                return ret;
            case 6:
                ret.add(0);
                ret.add(1);
                ret.add(1);
                ret.add(0);
                return ret;
            case 7:
                ret.add(0);
                ret.add(1);
                ret.add(1);
                ret.add(1);
                return ret;
            case 8:
                ret.add(1);
                ret.add(0);
                ret.add(0);
                ret.add(0);
                return ret;
            case 9:
                ret.add(1);
                ret.add(0);
                ret.add(0);
                ret.add(1);
                return ret;
            case 10:
                ret.add(1);
                ret.add(0);
                ret.add(1);
                ret.add(0);
                return ret;
            case 11:
                ret.add(1);
                ret.add(0);
                ret.add(1);
                ret.add(1);
                return ret;
            case 12:
                ret.add(1);
                ret.add(1);
                ret.add(0);
                ret.add(0);
                return ret;
            case 13:
                ret.add(1);
                ret.add(1);
                ret.add(0);
                ret.add(1);
                return ret;
            case 14:
                ret.add(1);
                ret.add(1);
                ret.add(1);
                ret.add(0);
                return ret;
            case 15:
                ret.add(1);
                ret.add(1);
                ret.add(1);
                ret.add(1);
                return ret;
            default:
                ret.add(0);
                ret.add(0);
                ret.add(0);
                ret.add(0);
                return ret;
        }
    };
    public static int toInt (ArrayList<Integer> a)
    {
        int ret = 0;
        for (int i=0; i<a.size(); i++)
            ret+=a.get(i)*pow(2,4-i-1);
        return ret;
    };
   
    public boolean pathUp(ArrayList<ArrayList<Integer>> test)
        {
            int size = test.size();
            for (int i=0; i<size; i++)
                for (int j=0;j<size; j++)
                    if (test.get(i).get(j)!=0)
                        return true;
            return false;
        };
    public double reach(ArrayList<ArrayList <Integer>> outer) {
                int size = outer.size();
                double max = size*size*size*(size-1);
                double kIndex = 0;
                ArrayList<ArrayList <Integer>> reachs = new ArrayList<ArrayList <Integer>>();
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        for (int p = 0; p < size; p++)
                            for (int u = 0; u < size; u++)
                            {
                                if (j!=u)
                                {
                                    reachs = k.path(i, j, p, u, outer);
                                    if (pathUp(reachs))
                                        kIndex++;
                                }
                                k.reset();
                            }
                kIndex = kIndex/max;
                kIndex = Math.round(kIndex * 1000.0) / 1000.0;
                return kIndex;
            }
    public Path bestPath(ArrayList<ArrayList <Integer>> outer)
    {
        long startTime = System.currentTimeMillis();
        Path path = new Path();
        ArrayList<Integer> mask = new ArrayList<Integer>();
        ArrayList<ArrayList <Integer>> tester = new ArrayList<ArrayList <Integer>>();
        tester = copyArray(outer);
        int size = outer.size();
        double currentBest = reach(outer);
        path.k = currentBest;
        double currentBestTemp = 0;
        int holder = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                mask = toMask(outer.get(i).get(j));
                    for (int k = 0; k < mask.size(); k++)
                        if (mask.get(k)==0)
                        {
                            if (!((i==0)&&(k==2)))
                                if (!((j==size-1)&&(k==3)))
                                    if (!((i==size-1)&&(k==0)))
                                        if (!((j==0)&&(k==1)))
                                        {
                                            mask.set(k,1);
                                            holder = tester.get(i).get(j);
                                            tester.get(i).set(j,toInt(mask));
                                            currentBestTemp = reach(tester);
                                            if (currentBestTemp > currentBest)
                                            {
                                                currentBest = currentBestTemp;
                                                path.setZeros();
                                                path.bitMask.set(k, 1);
                                                path.circle.setAll(i, j);
                                                path.k = currentBest;
                                                path.code = toMask(tester.get(i).get(j));
                                            }
                                            tester.get(i).set(j,holder);
                                            mask.set(k,0);
                                        }
                        }           
            }
        timeSpent = System.currentTimeMillis() - startTime;
        return path;
    }
}
