/*
 * AbstractDBVisit.java
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

import java.util.Collection;
import java.util.SortedSet;

import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

/**
 * Concretization of the Visit algorithm for RDF-Model Graphs
 * @author gyankos
 */
public abstract class AbstractDBVisit<T> extends AbstractAlgorithmOverVisit<Entity, Relation, T> {

    /**
     * This function is called when we want to choose the next edge to visit
     * in order to reach another vertex. In order to do so, we could manipulate
     * the input list in order to filter the edges to visit and to choose which
     * one to visit first. 
     * @param cert  The edges that could be visited ordered in lexicographic order
     * @return 
     */
    public abstract Collection<ERTriple> getEdgeVisitOrder(SortedSet<ERTriple> cert);
    
}
