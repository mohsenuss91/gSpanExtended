/*
 * ExtendedDFSCodeGenerator.java
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
import java.util.Set;
import java.util.SortedSet;

import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.GSCode;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * Implements the visit of the other bits of the graph. ~ Algorithmic Step
 * @author gyankos
 */
public class ExtendedDFSCodeGenerator extends AbstractDBVisit<GSCode> {

    private Set<Entity> toAvoid;
    private GSCode next;
    
    public ExtendedDFSCodeGenerator(Set<Entity> visited) {
        super();
        toAvoid = visited;
        next = new GSCode();
    }
    
    /**
     * Removes the edges that start from the vertices that have been already visited
     * @param cert  List of possible incoming edges
     * @return      filtered edges
     */
    @Override
    public Collection<ERTriple> getEdgeVisitOrder(SortedSet<ERTriple> cert) {
        return Collections2.filter(cert, new Predicate<ERTriple>() {
            @Override
            public boolean apply(ERTriple input) {
                return (!(toAvoid.contains(input.getSource())));
            }
        });
    }

    @Override
    public void visitVertex(Entity v) {
        
    }

    @Override
    public void visitEdge(Entity src, Relation l, Entity dst) {
        next.add(new ERTriple(src, l, dst));//forms the extension
    }

    @Override
    public GSCode getOutput() {
        return next;//returns the extension
    }
    
}
