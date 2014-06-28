/*
 * gSpanGraph.java
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



package org.tweetsmining.engines.graphsminer.graphs.gSpan;

import java.util.Collection;
import java.util.Objects;
import java.util.SortedSet;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.gSpanFactory;
import org.tweetsmining.engines.graphsminer.graphs.IgSpanGraphs;
import org.tweetsmining.model.graph.AbstractModelQuery;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;

/**
 * Creates a graph that compares two graphs with their basic DFSCode
 * @author gyankos
 */
public class gSpanGraph implements IgSpanGraphs<GSCode> {
    
    private final MultiLayerGraph ml;
    private GSCode dfsc;
    private Entity firstNode;
    
    public gSpanGraph(final MultiLayerGraph mlg) {
        this.ml = mlg;
        this.dfsc = (GSCode)gSpanFactory.getCode(false, ml);
        this.firstNode = gSpanFactory.getStartNode(ml);
    }
    
    public static gSpanGraph copy(gSpanGraph cpy) {
        return new gSpanGraph(MultiLayerGraph.create(cpy.getAllEdges()));
    }
    public static gSpanGraph copy(Collection<ERTriple> x) {
        return new gSpanGraph(MultiLayerGraph.create(x));
    }
    
    @Override
    public Entity getFirstNode() {
        return firstNode;
    }
    
    @Override
    public SortedSet<ERTriple> getAllEdges() {
        return AbstractModelQuery.query(ml, null);
    }
    
    public GSCode getSimpleDFSCode() {
        return this.dfsc;
    }
    
    @Override
    public MultiLayerGraph getRepresentation() {
        return ml;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof gSpanGraph))
            return false;
        gSpanGraph r = (gSpanGraph)o;
        
        return (getACode().equals(r.getACode()));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.dfsc); //Get equality hash only on dfs code
        return hash;
    }

    @Override
    public GSCode getACode() {
        return getSimpleDFSCode();
    }

    

    
}
