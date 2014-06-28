/*
 * MatrixOp.java
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

import java.util.HashSet;
import java.util.Set;

import org.tweetsmining.model.matrices.GuavaMatrix;
import org.tweetsmining.model.matrices.IMatrix;

import com.google.common.collect.Table.Cell;

/**
 *
 * @author gyankos
 */
public abstract class MatrixOp {
  
	
	/*
        public static int getRow(MultiKey x) {
        if (x==null)
            return -1;
        else
            return (Integer)x.getKey(0);
    }
    
    public static int getCol(MultiKey x) {
        if (x==null)
            return -1;
        else
            return (Integer)x.getKey(1);
    }
       */
    
    
   /**
    * Matrix sum
    * @param left
    * @param right
    * @return
    */
   public static IMatrix sum(IMatrix left, IMatrix right) {
       Set<Cell<Integer, Integer, Double>> iter = new HashSet<>();
       iter.addAll(right.getValueRange());
       iter.addAll(left.getValueRange());
       IMatrix g = new GuavaMatrix();
       for (Cell<Integer, Integer, Double> x : iter) {
           g.set(x.getRowKey(),x.getColumnKey(),left.get(x.getRowKey(),x.getColumnKey())+right.get(x.getRowKey(),x.getColumnKey()));
       }
       return g;
   }
   
   /**
    * MAtrix difference
    * @param left
    * @param right
    * @return
    */
   public static IMatrix diff(IMatrix left, IMatrix right) {
	   Set<Cell<Integer, Integer, Double>> iter = left.getValueRange();
       iter.addAll(right.getValueRange());
       IMatrix g = new GuavaMatrix();
       for (Cell<Integer, Integer, Double> x : iter) {
           g.set(x.getRowKey(),x.getColumnKey(),left.get(x.getRowKey(),x.getColumnKey())-right.get(x.getRowKey(),x.getColumnKey()));
       }
       return g;
   }
   
   static void updateProgress(double progressPercentage) {
	    final int width = 50; // progress bar width in chars

	    System.out.print("\r[");
	    int i = 0;
	    for (; i <= (int)(progressPercentage*width); i++) {
	      System.out.print(".");
	    }
	    for (; i < width; i++) {
	      System.out.print(" ");
	    }
	    System.out.print("]");
	  }

   
   /**
    * Matrix product
    * @param left
    * @param right
    * @return 
    */
   public static IMatrix prod(IMatrix left, IMatrix right) {
       IMatrix g = new GuavaMatrix();
       double size = (left.getValueRange().size()*right.getValueRange().size());
       System.out.println(size+" ~~~ ");
       double i = 0;
       for (Cell<Integer, Integer, Double> l : left.getValueRange()) {
           int li = (Integer)l.getColumnKey();
           for (Cell<Integer, Integer, Double> r : right.getValueRange()) {
               int ri = (Integer)r.getRowKey();
               if (li==ri) {
                   double lv = (Double)left.get(l.getRowKey(),l.getColumnKey());
                   double rv = (Double)right.get(r.getRowKey(),r.getColumnKey());
                   //System.out.println(lv+" "+rv+" "+lv*rv);
                   g.incr((Integer)l.getRowKey(),(Integer)r.getColumnKey(),lv*rv);
               }
               i++;
               //updateProgress((double) i / (double) size);
           }
       }
       System.out.println("Done!");
       return g;
   }
   
   
   /**
    * Matrix divide
    * @param left
    * @param r
    * @return
    */
   public static IMatrix div(IMatrix left, double r) {
       IMatrix g = new GuavaMatrix();
       for (Cell<Integer, Integer, Double> l : left.getValueRange()) {
           int li = l.getRowKey();
           int ri = l.getColumnKey();
           g.incr(li,ri,(left.get(li,ri)/r));
       }
       return g;
   }
   
     /**
    * Matrix divide
    * @param left
    * @param right
    * @return
    */
   public static IMatrix div(IMatrix left, IMatrix right) {
       IMatrix g = new GuavaMatrix();
       for (Cell<Integer, Integer, Double> l : left.getValueRange()) {
           for (Cell<Integer, Integer, Double> r : right.getValueRange()) {
               int li = l.getColumnKey();
               int ri = l.getRowKey();
               if (li==ri) {
                   double lv = (Double)left.get(li,ri);
                   double rv = (Double)right.get(r.getRowKey(),r.getColumnKey());
                   //System.out.println(lv+" "+rv+" "+lv*rv);
                   g.incr((Integer)l.getRowKey(),(Integer)r.getColumnKey(),lv/rv);
               }
           }

       }
       return g;
   }
   

   /**
    * Matrix transpose
    * @param m
    * @return
    */
   public static IMatrix transpose(IMatrix m)  {
       IMatrix g = new GuavaMatrix();
       for (Cell<Integer, Integer, Double> k: m.getValueRange()) {
               int li = k.getRowKey();
               int ri = k.getColumnKey();
               g.set(ri, li, m.get(li,ri));
       }
       return g;
   }
   
   public static double[] stationaryDistribution(IMatrix m) {
	   int times = 6;
	   IMatrix tmp = m;
	   for (int i = 0; i<times; i++)
		   tmp = MatrixOp.prod(tmp, m);
	   return rowSums(tmp);
       /* Matrix tmp = MatrixFactory.toMatrix(m);
       int N = tmp.getColumnDimension();
       Matrix B = tmp.minus(Matrix.identity(N, N));
       for (int j = 0; j < N; j++)
           B.set(0, j, 1.0);
       Matrix b = new Matrix(N, 1);
       b.set(0, 0, 1.0);
       return MatrixFactory.toGMatrix(B.solve(b));
       */
   }
   
   public static IMatrix diagonal(double... d) {
       IMatrix tmp = new GuavaMatrix();
       for (int i=0; i<d.length; i++)
           tmp.set(i,i, d[i]);
       return tmp;
   }
   
   public static IMatrix diagonal(double val, int size) {
       IMatrix tmp = new GuavaMatrix();
       for (int i=0; i<size; i++)
           tmp.set(i,i, val);
       return tmp;
   }
   
   public static double[] rowSums(IMatrix m) {
       int size = Math.max(m.nCols(), m.nRows());
       double toret[] = new double[size];
       for (int i=0; i<size; i++)
           toret[i]=0;
       for (int i=0; i<size; i++)
           for (int j=0; j<size; j++)
               toret[i] += m.get(i,j);
       return toret;
   }
   
   public static IMatrix regularizedLaplacianMatrix(IMatrix m) {
       IMatrix tmp = MatrixOp.prod(m, MatrixOp.transpose(m));
       int size = tmp.nCols();
       IMatrix i = diagonal(1,size);
       IMatrix d = diagonal(rowSums(tmp));
       IMatrix laplacian = new GuavaMatrix();
       for (int ii = 0; ii<size; ii++)
           for (int ji = 0; ji<size; ji++)
               laplacian.set(ii, ji, (d.get(ii, ji)-tmp.get(ii, ji))/ Math.sqrt(d.get(ii, ii)*d.get(ji, ji)));
       return laplacian;
   }
    
}
