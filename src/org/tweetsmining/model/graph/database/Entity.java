/*
 * Entity.java
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

import java.io.Serializable;
import java.util.Objects;

/**
 * Commodity class for the Jena Resources (subject/object)
 * @author gyankos
 */
public class Entity implements Comparable<Entity>, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2079110959550063321L;
	private final String fullURI;
    private final int val;

    
    /**
     * Crea un'entità all'interno del modello
     * @param fullURI   Percorso di riferimento 
     * @param pos       Numero dell'elemento
     */
    public Entity(String fullURI, int pos) {
        this.fullURI = fullURI;
        this.val = pos;
    }
    
    
    public String getFullUri() {
        return fullURI;
    }
    
    public int getIndex() {
        return val;
    }
    
    @Override
    public String toString() {
        return getFullUri();
    }

    @Override
    public int compareTo(Entity o) {
        return toString().compareTo(o.toString());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity))
            return false;
        Entity right = (Entity)obj;
        return (compareTo(right)==0);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.fullURI);
        return hash;
    }


}
