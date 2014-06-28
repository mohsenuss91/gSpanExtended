/*
 * SimpleRightmostExpansion.java
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
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

/**
 * This class returns the edges over which we shall later on implement the
 * extensions
 *
 * @author gyankos
 */
public class SimpleRightmostExpansion extends AbstractDBVisit<List<Entity>> {

    public SimpleRightmostExpansion() {
    }
    List<Entity> q = new LinkedList<>();

    @Override
    public Collection<ERTriple> getEdgeVisitOrder(SortedSet<ERTriple> cert) {
        LinkedList<ERTriple> er = new LinkedList<>();
        if (!cert.isEmpty()) {
            er.add(cert.last());
        }
        return er;
    }

    @Override
    public void visitVertex(Entity v) {
    }

    @Override
    public void visitEdge(Entity src, Relation l, Entity dst) {
        if ((q.isEmpty()) || (!q.get(q.size() - 1).equals(src))) {
            q.add(src);
        }

        q.add(dst);
    }

    @Override
    public List<Entity> getOutput() {
        return q;
    }
}
