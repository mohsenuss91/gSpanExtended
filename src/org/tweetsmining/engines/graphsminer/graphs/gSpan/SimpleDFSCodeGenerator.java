/*
 * SimpleDFSCodeGenerator.java
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
import java.util.SortedSet;

import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

/**
 * DFS Algorithm to generate the DFSCode for the gSpan Algorithm
 *
 * @author gyankos
 */
public class SimpleDFSCodeGenerator extends AbstractDBVisit<GSCode> {

    
    
    public SimpleDFSCodeGenerator() {
        super();
    }
    private GSCode dfsCode = new GSCode();
    private ERTriple first = null;

    @Override
    public void visitVertex(Entity v) {
        //Void
    }

    @Override
    public void visitEdge(Entity src, Relation l, Entity dst) {
        ERTriple toadd = new ERTriple(src, l, dst);
        if (dfsCode.isEmpty()) {
            first = toadd;
        }
        dfsCode.add(toadd);
    }

    @Override
    public GSCode getOutput() {
        return dfsCode;
    }

    @Override
    public Collection<ERTriple> getEdgeVisitOrder(SortedSet<ERTriple> cert) {
        return cert;
    }

    public ERTriple getFirst() {
        return first;
    }
}
