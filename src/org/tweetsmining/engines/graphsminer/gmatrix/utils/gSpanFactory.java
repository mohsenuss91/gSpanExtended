/*
 * gSpanFactory.java
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import org.tweetsmining.engines.graphsminer.graphs.AbstractCodeGenerators;
import org.tweetsmining.engines.graphsminer.graphs.AbstractSubgraphOf;
import org.tweetsmining.engines.graphsminer.graphs.DFS.ICode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleDFSCAlgorithm;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleSubgraphOf;
import org.tweetsmining.engines.graphsminer.graphs.gSpanExtended.ExtendedDFSCAlgorithm;
import org.tweetsmining.engines.graphsminer.graphs.gSpanExtended.ExtendedSubgraphOf;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;


/**
 *
 * @author gyankos
 */
public class gSpanFactory {
    
    /**
     * Creates an instance of the gSpan algorithm for detecting the most frequent subgraphs
     * @param isMultiSource  State if many starting sources have to be explored or not
     * @param db             Graph Database
     * @return 
     */
    /*public static Abstract_gSpan create(boolean isMultiSource, Collection<MultiLayerGraph> db) {
        Abstract_gSpan algorithm;
        if (isMultiSource)
            algorithm = new ExtendedgSpan();
        else 
            algorithm = new gSpan();
        algorithm.setDatabase(db);
        return algorithm;
    }*/
    
    /**
     * Returns if sub is a subgraph of sup
     * @param isMultiSource  State if many starting sources have to be explored or not
     * @param sub            Subgraph to be checked
     * @param sup            Super-Graph
     * @return 
     */
    public static boolean subgraphOf(boolean isMultiSource, MultiLayerGraph sub, MultiLayerGraph sup) {
        AbstractSubgraphOf algorithm;
        if (isMultiSource)
            algorithm = ExtendedSubgraphOf.getInstance();
        else 
            algorithm = SimpleSubgraphOf.getInstance();
        return algorithm.subgraphOf(sub, sup);
    }
    
    /**
     * Returns the DFSCode of a graph
     * @param isMultiSource  State if many starting sources have to be explored 
     *                       or not (and hence, if we have to provide the
     *                       extended version or not)
     * @param mlg            Graph 
     * @return 
     */
    public static ICode getCode(boolean isMultiSource, MultiLayerGraph mlg) {
        AbstractCodeGenerators algorithm;
        if (isMultiSource)
            algorithm = new ExtendedDFSCAlgorithm();
        else
            algorithm = new SimpleDFSCAlgorithm();
        return (ICode)algorithm.getADFSCode(mlg).getFirst();
    }
    
    /**
     * Returns the node from which the DFS visit will be started
     * @param mlg
     * @return 
     */
    public static Entity getStartNode(MultiLayerGraph mlg) {
        return new SimpleDFSCAlgorithm().getADFSCode(mlg).getSecond();
    }
    
}
