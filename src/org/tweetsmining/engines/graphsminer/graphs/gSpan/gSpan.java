/*
 * gSpan.java
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

import org.tweetsmining.engines.graphsminer.graphs.Abstract_gSpan;
import org.tweetsmining.engines.graphsminer.graphs.IgSpanGraphs;
import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;

/**
 *
 * @author gyankos
 */
public class gSpan extends Abstract_gSpan<GSCode> {

    public static Collection<IgSpanGraphs<GSCode>> CollectionOf_gSpanGraphToAbstract(Collection<gSpanGraph> tmp) {
        Collection<IgSpanGraphs<GSCode>> tt = new LinkedList<>();
        for (gSpanGraph x : tmp) {
            tt.add(x);
        }
        return tt;
    }

    public gSpan() {
        super(SimpleSubgraphOf.getInstance());
    }

    public static List<Entity> getCommonEntities(IgSpanGraphs<GSCode> mlg) {
        AbstractDBVisit<List<Entity>> adbv_rightmost = new SimpleRightmostExpansion();
        DFS<List<Entity>> d = new DFS(mlg.getRepresentation(), adbv_rightmost);
        return d.start();
    }
    
    @Override

    public List<Entity> getEntities(final IgSpanGraphs<GSCode> mlg) {
        return getCommonEntities(mlg);
    }

    @Override
    public IgSpanGraphs<GSCode> createCodeGraph(MultiLayerGraph m) {
        return new gSpanGraph(m);
    }

}
