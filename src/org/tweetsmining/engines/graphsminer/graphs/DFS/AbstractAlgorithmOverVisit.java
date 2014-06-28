/*
 * AbstractAlgorithmOverVisit.java
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

import java.util.Collections;
import java.util.List;

/**
 * Generic abstraction of the oprations that coul be performed over a search algorithm
 * 
 * @author gyankos
 * @param <Vertex>
 * @param <LayerName>
 * @param <Output>
 */
public abstract class AbstractAlgorithmOverVisit<Vertex extends Comparable<Vertex>,LayerName extends Comparable<LayerName>,Output> {
    
    /**
     * Perform a visit over the vertex v
     * @param v 
     */
    public abstract void visitVertex(Vertex v);
    
    /**
     * Sorts the vertices in ascending order
     * 
     * @param els
     * @return 
     */
    public List<Vertex> sortAscendingVertices(List<Vertex> els) {
       Collections.sort(els);
       return els;
    }
    
    /**
     * Visiting the current edge
     * @param src   Source Vertex
     * @param l     Link
     * @param dst   Destination Vertex
     */
    public abstract void visitEdge(Vertex src, LayerName l, Vertex dst);
    
    /**
     * Sorte the edges in ascending order
     * @param els
     * @return 
     */
    public List<LayerName> sortAscendingEdges(List<LayerName> els) {
       Collections.sort(els);
       return els;
    }
    
    /**
     * Returns the output at the end of the visit algorithm
     * @return 
     */
    public abstract Output getOutput();
    
}
