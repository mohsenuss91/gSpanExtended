/*
 * ExtendedgSpan.java
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

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tweetsmining.engines.graphsminer.graphs.Abstract_gSpan;
import org.tweetsmining.engines.graphsminer.graphs.IgSpanGraphs;
import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleRightmostExpansion;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * Extended implementation of the gSpan algorithm
 * @author gyankos
 */
public class ExtendedgSpan extends Abstract_gSpan<ExtendedGSCode> {

    public static Collection<IgSpanGraphs<ExtendedGSCode>> CollectionOf_gSpanGraphToAbstract(Collection<ExtendedgSpanGraph> tmp) {
        return Collections2.transform(tmp, new Function<ExtendedgSpanGraph,IgSpanGraphs<ExtendedGSCode>>() {
            @Override
            public IgSpanGraphs<ExtendedGSCode> apply(ExtendedgSpanGraph input) {
                return input;
            }
        });
    }

    public ExtendedgSpan() {
        super(ExtendedSubgraphOf.getInstance());
    }
    

    @Override
    public IgSpanGraphs<ExtendedGSCode> createCodeGraph(MultiLayerGraph m) {
        return new ExtendedgSpanGraph(m);
    }

    @Override
    public List<Entity> getEntities(IgSpanGraphs<ExtendedGSCode> mlg) {
        //
        AbstractDBVisit<List<Entity>> adbv_rightmost = new SimpleRightmostExpansion();
        DFS<List<Entity>> d = new DFS(mlg.getRepresentation(), adbv_rightmost);
        List<Entity> ent =  d.start();
        
        if (d.hasVisitedAllVertices()) {
            return ent;
        }
        
        //Visited Vertices
        SortedSet<Entity> visited = d.getVisitedVertices();
        //Vertices to visit
        TreeSet<Entity> unvisited = new TreeSet<>(mlg.getRepresentation().getVertices());
        unvisited.removeAll(visited);
        
        
        //DFSCode extensions
        while (!unvisited.isEmpty()) {
            Entity startVisit = unvisited.first();
            ExtendedRightmostExpansion algorithm = new ExtendedRightmostExpansion(visited);
            DFS<List<Entity>> next_step = new DFS<>(mlg.getRepresentation(),algorithm,startVisit);
            ent.addAll(next_step.start());
            SortedSet<Entity> loc = next_step.getVisitedVertices();
            visited.addAll(loc);
            unvisited.removeAll(loc);
        }
        
        return ent;
    
    }

   

    
}
