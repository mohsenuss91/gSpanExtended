/*
 * AbstractSubgraphOf.java
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



package org.tweetsmining.engines.graphsminer.graphs;

import org.tweetsmining.model.graph.MultiLayerGraph;



/**
 * Defines the classes that perform the subgraph isomorphism ~ used in support functions
 * @author gyankos
 */
public abstract class AbstractSubgraphOf<T> {
    
    public abstract boolean subgraphOf(T cmpSub, T cmpSup);
    public abstract boolean subgraphOf(MultiLayerGraph sub, MultiLayerGraph sup);
    
}
