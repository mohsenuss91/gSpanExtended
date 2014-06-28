/*
 * IMatrix.java
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



package org.tweetsmining.model.matrices;


import java.util.Set;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;

import com.google.common.collect.Table;
/**
 *
 * @author gyankos
 */
public interface IMatrix {
    
   public int nCols();
   public int nRows();
    
   public double get(int i, int j);
   /**
    * Returns the cell's value
    * @param x cell coordinates
    * @return 
    */
   public double get(NonCompPair<Integer,Integer> x);
   
   /**
    * Adds val to the x cell value
    * @param x      Cell coordinates
    * @param val    Value
    * @ 
    */
   public void incr(NonCompPair<Integer,Integer> x, double val) ;
   
   /**
    * Removes the cell (i,j)s
    * @param i  Row
    * @param j  Column
    */
   public void rem(int i, int j);
   
   /**
    * Add val to the cell (i,j)
    * @param i  row
    * @param j  Column
    * @param val    Value
    * @ 
    */
   public void incr(int i, int j, double val) ;
   
   
   public void set(int i, int j,double val) ;
   
   public void set(NonCompPair<Integer,Integer> x,double val) ;
   
   /**
    * Removes cells that have non-empty values
    * @return
    * @ 
    */
   public Set<Table.Cell<Integer,Integer,Double>> getValueRange() ;
   
   /**
    * Removes the whole row i
    * @param i 
    */
   public void removeRow(int i);
   
   /**
    * Removes the whole column j
    * @param j 
    */
   public void removeCol(int j);
   
   /**
    * Removes both row and columns with the same number
    * @param elem 
    */
   public void removeEnt(int elem);
   
   public void sum(IMatrix right);
   public void diff(IMatrix right);
   
   public Set<Integer> getInSet(int ent);
   
   public Set<Integer> getOutSet(int ent);
    
}
