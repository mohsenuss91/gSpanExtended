/*
 * PerRowPair.java
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


package org.tweetsmining.engines.graphsminer.gmatrix.utils;


/**
 *
 * @author gyankos
 */
public class PerRowPair implements Comparable<PerRowPair> {

    private int i;
    private int j;
    private int dove;
    
    public PerRowPair(int i, int j) {
        this.i = i;
        this.j = j;
        this.dove = (i+((i+j)*(i+j+1))/2);
    }
    
    public int getDovetailingCode() {
        return dove;
    }
    
    @Override
    public String toString() {
        return "<"+Integer.toString(i)+","+Integer.toString(j)+">";
    }
    
    public int getRow() {
        return i;
    }
    public int getCol() {
        return j;
    }
    
    @Override
    public int compareTo(PerRowPair right) {
            if (i<right.getRow())
                return -1;
            else if (i>right.getRow())
                return 1;
            else  {
                if (j<right.getCol()) 
                    return -1;
                else if (j>right.getCol())
                    return 1;
                else
                    return 0;
            }
    }
    
}
