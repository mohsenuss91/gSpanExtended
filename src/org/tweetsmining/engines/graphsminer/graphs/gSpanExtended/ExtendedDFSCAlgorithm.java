/*
 * ExtendedDFSCAlgorithm.java
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

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.engines.graphsminer.graphs.AbstractCodeGenerators;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.GSCode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleDFSCodeGenerator;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;

/**
 * This algorithm returns the Extended DFSCode ~ Used in ExtendedgSpanGraph
 * @author gyankos
 */
public class ExtendedDFSCAlgorithm extends AbstractCodeGenerators<ExtendedGSCode> {
    
    @Override
    public NonCompPair<ExtendedGSCode,Entity> getADFSCode(MultiLayerGraph ml) {
        
        SimpleDFSCodeGenerator e = new SimpleDFSCodeGenerator();
        
        //First Part of the triple
        DFS<GSCode> d = new DFS<>(ml,e);
        Entity start = d.getCurrentPos();
        GSCode first = d.start();
        
        if (d.hasVisitedAllVertices()) {
            //Classic implementation
            return new NonCompPair<>(new ExtendedGSCode(first,new HashSet<GSCode>()),start);
        }
        
        //Visited Vertices
        SortedSet<Entity> visited = d.getVisitedVertices();
        //Vertices to visit
        TreeSet<Entity> unvisited = new TreeSet<>(ml.getVertices());
        unvisited.removeAll(visited);
        
        
        //DFSCode extensions
        TreeSet<GSCode> second = new TreeSet<>(); 
        while (!unvisited.isEmpty()) {
            Entity startVisit = unvisited.first();
            
            ExtendedDFSCodeGenerator algorithm = new ExtendedDFSCodeGenerator(visited);
            DFS<GSCode> next_step = new DFS<>(ml,algorithm,startVisit);
            second.add(next_step.start());
            SortedSet<Entity> loc = next_step.getVisitedVertices();
            visited.addAll(loc);
            unvisited.removeAll(loc);
        }
        
        
        return new NonCompPair<>(new ExtendedGSCode(first,second),start);
    }
    
}
