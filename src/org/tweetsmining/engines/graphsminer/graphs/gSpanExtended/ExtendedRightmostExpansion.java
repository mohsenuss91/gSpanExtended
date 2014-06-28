/*
 * ExtendedRightmostExpansion.java
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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 *
 * @author gyankos
 */
public class ExtendedRightmostExpansion extends AbstractDBVisit<List<Entity>> {

    private Set<Entity> toAvoid;
    List<Entity> q = new LinkedList<>();

    public ExtendedRightmostExpansion(Set<Entity> visited) {
        super();
        toAvoid = visited;
    }

    /**
     * Get the rightmost path from the not-yet visited edges
     *
     * @param cert
     * @return
     */
    @Override
    public Collection<ERTriple> getEdgeVisitOrder(SortedSet<ERTriple> cert) {
        SortedSet<ERTriple> edges = new TreeSet<>(Collections2.filter(cert, new Predicate<ERTriple>() {
            @Override
            public boolean apply(ERTriple input) {
                return (!(toAvoid.contains(input.getSource())));
            }
        }));
        LinkedList<ERTriple> er = new LinkedList<>();
        if (!edges.isEmpty()) {
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
