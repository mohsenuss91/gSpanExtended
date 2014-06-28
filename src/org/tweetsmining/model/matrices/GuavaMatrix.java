/*
 * GuavaMatrix.java
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

/**
 *
 * @author gyankos
 */
public class GuavaMatrix implements IMatrix, Serializable {

	private static final long serialVersionUID = 1942775687702205962L;
	private final Table<Integer,Integer,Double> p;
    
    public GuavaMatrix() {
        p =  HashBasedTable.create();
    }

    public GuavaMatrix(IMatrix cpy) {
        p =  HashBasedTable.create();
        for (Cell<Integer, Integer, Double> db:cpy.getValueRange()) {
            p.put(db.getRowKey(), db.getColumnKey(), db.getValue());//set
        }
    }
    
    @Override
    public int nCols() {
        return p.columnKeySet().size();
    }

    @Override
    public int nRows() {
        return p.rowKeySet().size();
    }

    @Override
    public double get(int i, int j) {
    	if (p.contains(i, j))
    		return p.get(i, j);
    	else
    		return 0;
    }

    @Override
    public double get(NonCompPair<Integer, Integer> x) {
        return get(x.getFirst(),x.getSecond());
    }

    @Override
    public void incr(NonCompPair<Integer, Integer> x, double val) {
        incr(x.getFirst(),x.getSecond(),val);
    }

    @Override
    public void rem(int i, int j) {
        p.remove(i, j);
    }

    @Override
    public void incr(int i, int j, double val) {        
        double tmp = val;
        if (p.contains(i,j))
            tmp = p.get(i,j)+val;
        p.put(i,j, val);
    }

    @Override
    public void set(int i, int j, double val) {
        p.put(i,j,val);
    }

    @Override
    public void set(NonCompPair<Integer, Integer> x, double val) {
        set(x.getFirst(),x.getSecond(),val);
    }
    
    public void set(Table.Cell<Integer,Integer,Double> db) {
        p.put(db.getRowKey(), db.getColumnKey(), db.getValue());
    }

    @Override
    public Set<Table.Cell<Integer, Integer, Double>> getValueRange() {
        return p.cellSet();
    }

    @Override
    public void removeRow(int i) {
        for (Integer j : p.row(i).keySet()) {
            p.remove(i, j);
        }
    }

    @Override
    public void removeCol(int j) {
        for (Integer i: p.column(j).keySet()) {
            p.remove(i, j);
        }
    }

    @Override
    public void removeEnt(int elem) {
        removeRow(elem);
        removeCol(elem);
    }

    @Override
    public void sum(IMatrix right) {
        for (Table.Cell<Integer, Integer, Double> x:right.getValueRange()) {
            incr(x.getRowKey(),x.getColumnKey(), x.getValue());
        }
    }

    @Override
    public void diff(IMatrix right) {
        for (Table.Cell<Integer, Integer, Double> x:right.getValueRange()) {
            incr(x.getRowKey(),x.getColumnKey(), -x.getValue());
        }
    }
    
    @Override
    public Set<Integer> getInSet(int ent) {
        return p.column(ent).keySet();
    }
    
    @Override
    public Set<Integer> getOutSet(int ent) {
        return p.row(ent).keySet();
    }


    
}
