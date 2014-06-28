/*
 * Relation.java
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


package org.tweetsmining.model.graph.database;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.tweetsmining.model.matrices.GuavaMatrix;

import com.google.common.collect.Table.Cell;

/**
 * The general instance of a Jena Property is seen as a Matrix between the entities
 * @author gyankos
 */
public class Relation extends GuavaMatrix implements Comparable<Relation> {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3191467246783941144L;
	private String lay;
 // private Property prop;
   
    public Relation(String layer/*, Property prop*/) {
        super();
        this.lay = layer;
        //this.prop = prop;
    }

    
    public String getName() {
        return lay;
    }
    
    
    /**
     * Returns the Jena Property
     * @return 
     */
    /*public Property getProperty() {
        return prop;
    }*/

    @Override
    public int compareTo(Relation o) {
        return this.getName().compareTo(o.getName());
    }
    
    @Override
    public String toString() {
        return lay;
    }
    
}
