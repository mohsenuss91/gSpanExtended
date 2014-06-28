/*
 * SimpleDFSCAlgorithm.java
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

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.engines.graphsminer.graphs.AbstractCodeGenerators;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;

/**
 *
 * @author gyankos
 */
public class SimpleDFSCAlgorithm extends AbstractCodeGenerators<GSCode> {
    
    @Override
    public NonCompPair<GSCode, Entity> getADFSCode(MultiLayerGraph ml) {
        SimpleDFSCodeGenerator e = new SimpleDFSCodeGenerator();
        DFS<GSCode> d = new DFS<>(ml, e);
        GSCode dfsc = d.start();
        if (e.getOutput() == null || (e.getOutput().isEmpty() && (!dfsc.isEmpty()))) {
            throw new RuntimeException("Java SuX");
        }
        Entity firstNode;
        if (e.getFirst() == null) {
            firstNode = null;
        } else {
            firstNode = e.getFirst().getSource();
        }
        return new NonCompPair<>(dfsc, firstNode);
    }
    
}
