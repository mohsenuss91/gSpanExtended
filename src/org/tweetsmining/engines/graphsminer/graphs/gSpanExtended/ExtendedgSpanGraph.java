/*
 * ExtendedgSpanGraph.java
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



package org.tweetsmining.engines.graphsminer.graphs.gSpanExtended;

import java.util.Objects;
import java.util.SortedSet;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.engines.graphsminer.graphs.IgSpanGraphs;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.gSpanGraph;
import org.tweetsmining.model.graph.AbstractModelQuery;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;

/**
 * Creates a graph that compares two graphs with their basic DFSCode
 * @author gyankos
 */
public class ExtendedgSpanGraph implements IgSpanGraphs<ExtendedGSCode> {
    
    
    private final MultiLayerGraph ml;
    private ExtendedGSCode dfsc;
    private Entity firstNode;
    
    public ExtendedgSpanGraph(final MultiLayerGraph mlg) {
        this.ml = mlg;
        NonCompPair<ExtendedGSCode, Entity> cp = new ExtendedDFSCAlgorithm().getADFSCode(ml);
        this.dfsc = cp.getFirst();
        this.firstNode = cp.getSecond();
    }
    
    @Override
    public Entity getFirstNode() {
        return firstNode;
    }
    
    @Override
    public SortedSet<ERTriple> getAllEdges() {
        return AbstractModelQuery.query(ml, null);
    }
    
    @Override
    public MultiLayerGraph getRepresentation() {
        return ml;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof gSpanGraph))
            return false;
        
        ExtendedgSpanGraph right = (ExtendedgSpanGraph)o;
        
        return (getACode().equals(right.getACode()));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.dfsc); //Get equality hash only on dfs code
        return hash;
    }

    @Override
    public ExtendedGSCode getACode() {
        return dfsc;
    }

 
}
