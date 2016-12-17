/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import java.util.ArrayList;

public class Klaster {
    private ArrayList<ArrayList <Integer>> A; // основная матрица
    private ArrayList<ArrayList <Integer>> masf; // текущая матрица
    private ArrayList<ArrayList <Integer>> msf; // кротчайший путь
    public long timeSpent;

    public Klaster() {
        A=new ArrayList<ArrayList <Integer>>();
        masf=new ArrayList<ArrayList <Integer>>();
        msf=new ArrayList<ArrayList <Integer>>();
    }

    public void reset()
    {
        A=new ArrayList<ArrayList <Integer>>();
        masf=new ArrayList<ArrayList <Integer>>();
        msf=new ArrayList<ArrayList <Integer>>();
    }
    
    // замена на 1, 2, 4, 8 (направления)
    private void replacement(int i, int j)
    {
        for(int s=masf.get(i).get(j);s>1;s--)
        {
            int mask = 0x1;
            int maska;
            boolean flag = true;
            if (j != A.size()-1){
                maska = A.get(i).get(j+1) & (mask<<2);
                if ((masf.get(i).get(j+1)+1==s)&&(maska == 4)){
                    msf.get(i).set(j+1, 4);
                    j=j+1;
                    flag = false;
                }
            }
            if ((flag)&&(j != 0)){ 
                maska = A.get(i).get(j - 1)&(mask);
                if ((masf.get(i).get(j-1)+1==s)&&(maska == 1)){
                    msf.get(i).set(j-1, 1);
                    j=j-1;
                    flag = false;
                }
            }
            if ((flag)&&(i != 0)){
                maska = A.get(i-1).get(j) &(mask<<3); 
                if ((masf.get(i-1).get(j)+1==s)&&(maska == 8)){
                    msf.get(i-1).set(j, 8);
                    i=i-1;
                    flag = false;
                }
            }
            if ((flag)&&(i != A.size()-1)){
                maska = A.get(i+1).get(j) &(mask<<1);
                if ((masf.get(i+1).get(j)+1==s)&&(maska == 2)){
                    msf.get(i+1).set(j, 2);
                    i=i+1;
                    flag = false;
                }
            }
        }
    }

    // запоминаем путь, если нашли конец пути
    private boolean filling(int i, int j, int ik, int jk)
    {
        if ((i==ik)&&(j==jk)){
            msf.get(i).set(j, 3);
            replacement(i,j);
            return true;
        }
        return false;
    }

    // ищем ходыиз точки. Если конец нашли, то возвращаем true
    private boolean inspection(int i, int j, int ik, int jk)
    {
        int mask = 0x1;
        int maska = A.get(i).get(j) & (mask);
        if ((j != A.size()-1) && (maska == 1))
            if((masf.get(i).get(j+1)>masf.get(i).get(j)+1) || (masf.get(i).get(j+1)==0))
            {
                masf.get(i).set(j+1, masf.get(i).get(j)+1);
                if(filling(i, j + 1, ik, jk))
                    return true;
            }
        maska = A.get(i).get(j)&(mask<<2);
        if ((j != 0) && (maska == 4))
            if((masf.get(i).get(j-1)>masf.get(i).get(j)+1) || (masf.get(i).get(j-1)==0))
            {
                masf.get(i).set(j-1, masf.get(i).get(j)+1);
                if(filling(i, j - 1, ik, jk))
                    return true;
            }
        maska = A.get(i).get(j) &(mask<<1);
        if ((i != 0) && (maska == 2))
            if((masf.get(i-1).get(j)>masf.get(i).get(j)+1) || (masf.get(i-1).get(j)==0))
            {
                masf.get(i-1).set(j, masf.get(i).get(j)+1);
                if(filling(i - 1, j, ik, jk))
                    return true;
            }
        maska = A.get(i).get(j) &(mask<<3);
        if ((i != A.size()-1) && (maska == 8))
            if((masf.get(i+1).get(j)>masf.get(i).get(j)+1) || (masf.get(i+1).get(j)==0))
            {
                masf.get(i+1).set(j, masf.get(i).get(j)+1);
                if(filling(i + 1, j, ik, jk))
                    return true;
            }
        return false;
    }
    
    // нахождение кратчайшего пути
    private void queue(int iStart, int jStart, int iFinish, int jFinish) 
    {
       boolean flag = false;
       masf.get(iStart).set(jStart, 1);
       int iStDistance = iStart, iFinDistance = iStart;
       int jStDistance = jStart, jFinDistance = jStart;
       while (!flag)
       {
            for(int i = iStDistance; i<=iFinDistance; i++){
                for(int j = jStDistance; j<=jFinDistance; j++){
                    if(masf.get(i).get(j)!=0)
                        flag = inspection(i,j,iFinish,jFinish);
                    if(flag) break;
                }
                if(flag) break;
            }
            int border = 4;
            if(iStDistance != 0){
               border--;
               iStDistance--;
            }
            if(iFinDistance != A.size()-1){
               border--;
               iFinDistance++;
            }
            if(jStDistance != 0){
               border--;
               jStDistance--;
            }
            if(jFinDistance != A.size()-1){
               border--;
               jFinDistance++;
            }
            if(border==4) flag = true;
       }
    }

    // обнуление данных и вызов функции queue, споследующим выводом пути
    public ArrayList<ArrayList<Integer>> path(int i1, int j1, int i2, int j2, ArrayList<ArrayList <Integer>> mas){
        long startTime = System.currentTimeMillis();
        ArrayList<ArrayList<Integer>> a=(ArrayList)mas.clone();
        for (int is= 0; is<mas.size();is++)
        {
            ArrayList <Integer> inner = new ArrayList<Integer>();
            for (int js = 0; js<mas.size();js++)
                inner.add(0);
            msf.add(inner);
        }
        for (int is= 0; is<mas.size();is++)
        {
            ArrayList <Integer> inner = new ArrayList<Integer>();
            for (int js = 0; js<mas.size();js++)
                inner.add(0);
            masf.add(inner);
        }
        for (int is= 0; is<mas.size();is++)
        {
            ArrayList <Integer> inner = new ArrayList<Integer>();
            for (int js = 0; js<mas.size();js++)
                inner.add(mas.get(is).get(js));
            A.add(inner);
        }
        queue(i1, j1, i2, j2);
        
        timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
        return msf;
    }
    
    // запись матрицы в объект
    public void setter(ArrayList<ArrayList <Integer>> mas, ArrayList<ArrayList <Integer>> masf, ArrayList<ArrayList <Integer>> msf){
        A=mas;
        this.masf=masf;
        this.msf=msf;
    }
}