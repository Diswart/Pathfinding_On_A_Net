/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import java.util.ArrayList;


public class Randomizer {
    static public ArrayList<ArrayList<Integer>> randomize(ArrayList<ArrayList<Integer>> outer, int size, double p)
    {
        double nn;
        int num=0;
        for (int i=0; i<size; i++)
            {
                ArrayList<Integer> inner = new ArrayList<Integer>();
                for (int j=0; j<size;j++)
                {
                  inner.add(0);
                }
                outer.add(inner);
            }
        for (int i = 0; i<size;i++) 
        {
            for (int j = 0; j<size ;j++) 
            { 
                outer.get(i).set(j,0); 
                for (int x = 3; x >= 0; x--) { 
                    nn = 0.01 * ((int) (Math.random()*101)); 
                    if (0<=nn && nn<=p) num = 1; 
                    if (nn<=1 && nn>p) num = 0; 
                    if (nn==p && nn==0.0) num = 0; 
                    if (nn==p && nn==1.0) num =1; 
                    outer.get(i).set(j,(int)(outer.get(i).get(j)+ num*Math.pow(2, x))); 
                } 
            }
        }
        return outer;
    }
}