/*
 * MatrixFactory.java
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

import org.tweetsmining.model.matrices.GuavaMatrix;
import org.tweetsmining.model.matrices.IMatrix;

import Jama.Matrix;

import com.google.common.collect.Table.Cell;

/**
 *
 * @author gyankos
 */
public class MatrixFactory {
    
    public static GuavaMatrix toGMatrix(Matrix m) {
        GuavaMatrix toret = new GuavaMatrix();
        for (int i=0; i<m.getRowDimension(); i++)
            for (int j=0; j<m.getColumnDimension(); j++)
                toret.set(i, j, m.get(i, j));
        return toret;
    }

    public static Matrix toMatrix(IMatrix m) {
        int size = Math.max(m.nRows(), m.nCols());
        Matrix toret = new Matrix(size,size);
        try {
            for (Cell<Integer, Integer, Double> x: m.getValueRange()) {
                toret.set(x.getRowKey(), x.getColumnKey(), x.getValue());
            }
            return toret;
        } catch (Throwable ex) {
            return toret;
        }
    }
    
    public static double[] toColumn(IMatrix m) {
        if (m.nCols()==1) {
            double toret [] = new double[m.nRows()];
            for (Cell<Integer, Integer, Double> x : m.getValueRange()) {
                toret[x.getRowKey()] = x.getValue();
            }
            return toret;
        } else
            return new double[0];
    }
    
}
