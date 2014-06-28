/*
 * IVisit.java
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



package org.tweetsmining.engines.graphsminer.graphs.DFS;

import java.util.SortedSet;

import org.tweetsmining.model.graph.database.Entity;

/**
 * Generic interface for a visit algorithm
 * @author gyankos
 */
public interface  IVisit<T> {
    
    public SortedSet<Entity> getVisitedVertices();
    
    public int nVertices();
 
    public boolean hasVisitedAllVertices();
    
    public abstract boolean hasNext();
    
    /**
     * The current position of the visit
     * @return  If the DFS has not started yet, it returns the vertex from which
     *          the computation will be started, otherwise it will return the
     *          vertex that is currently visited.
     */
    public Entity getCurrentPos();
    
    /**
     * Starts the visit of the algorithm, and returns an 
     * @return 
     */
    public T start();
    
}
