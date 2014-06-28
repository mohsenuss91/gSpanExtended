/*
 * MarkovClustering.java
 * This file is part of gSpanExtended
 *
 * Copyright (C) 2014 - Giacomo Bergami
 *
 * gSpanExtended is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * gSpanExtended is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with gSpanExtended. If not, see <http://www.gnu.org/licenses/>.
 */



package org.tweetsmining.engines.graphsminer.matrices;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.Collections3;
import org.tweetsmining.model.matrices.IMatrix;

import com.google.common.collect.Table.Cell;

/**
 *
 * @author Mattia
 */
public class MarkovClustering extends MatrixOp {

    /**
     * Matrix inflation approximated as a matrix product
     * @param m     matrix  
     * @param inf   Inflation product
     * @return 
     */
    public static IMatrix mclInflate (IMatrix m, int inf){
        int i;
        IMatrix tmp = m;
        for(i=1;i<=inf;i++){
            tmp = prod(tmp, m);
        }
        return tmp;
    }

    /**
     * Matrix normalization
     * @param m
     * @return 
     */
    public static IMatrix mclNorm (IMatrix m) {
        int columSum = 0;
        for (Cell<Integer, Integer, Double> x : m.getValueRange()) {
            columSum += x.getValue();
        }
        return transpose(div(transpose(m),columSum));
    }

    /**
     * Performs the matrix clustering
     * @param m         Matrix 
     * @param inf       Inflate parameter
     * @param iter      Iteration parameter
     * @return          The clustered matrix
     */
    public static IMatrix mcl(IMatrix m,int inf, int iter) {
        int i;
        IMatrix oldm;
        IMatrix mNorm;
        for(i=1;i<=iter;i++){
            oldm = m;
            
            mNorm = mclNorm(m);
            m = prod(mNorm,mNorm);
            m = mclInflate(m, inf);
            m = mclNorm(m);
            
            //obtaining the common valus
            Collection<Cell<Integer, Integer, Double>> oldmVals = oldm.getValueRange();
            Collection<Cell<Integer, Integer, Double>> mcalcVals = m.getValueRange();
            int row = Math.max(m.nCols(), oldm.nCols());
            int col = Math.max(m.nRows(), oldm.nRows());
            
            
            if (oldmVals.size()==mcalcVals.size() && oldmVals.size()==(row*col))
            {
                int count = 0;
                for (Cell<Integer, Integer, Double> x : Collections3.intersect(oldmVals, mcalcVals)) {
                    if (oldm.get(x.getRowKey(),x.getColumnKey())==m.get(x.getRowKey(),x.getColumnKey()))
                        count += 1;
                }
                //break through ~ stationary matrix reached
                if (count == row*col)
                    return m;
            }
            
            //if()
        }
        return m;
    }


    /**
     * Given a matrix over which we have performed MCL, we want to return the set of clusters
     * @param m
     * @return 
     */
    public static Collection<Set<Integer>> collectMCLFeatures (IMatrix m){
        //Maps each cluster on the i-th row into a list of elements 
        HashMap<Integer,Set<Integer>> hm = new HashMap<>();
        Set<Set<Integer>> toret = new HashSet<Set<Integer>>();
        
        //Obtaining the clusters only for the non-empty rows
        for (Cell<Integer, Integer, Double> ij : m.getValueRange()) {
        	//System.out.println(ij.getKey(0)+"~"+ij.getKey(1));
            if (!hm.containsKey((Integer)ij.getRowKey()))
                hm.put((Integer)ij.getRowKey(), new HashSet<Integer>());
            Set<Integer> li = hm.get((Integer)ij.getRowKey());
            li.add((Integer)ij.getColumnKey());
            hm.put((Integer)ij.getRowKey(),li);
        }
        for (Integer i : hm.keySet()) {
        	Set<Integer> item = hm.get(i);
        	if (!toret.contains(item))
        		toret.add(item);
        }
        
        hm.clear();

        //Obtaining the clusters only
        return toret;
    }
}
