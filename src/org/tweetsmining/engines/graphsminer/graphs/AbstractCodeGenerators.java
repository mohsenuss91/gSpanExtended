/*
 * AbstractCodeGenerators.java
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

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.Entity;

/**
 * Interface used by the classes that generate DFS codes
 * @author gyankos
 */
public abstract class AbstractCodeGenerators<T> {
    
    /**
     * Generate a DFS code for the given MultiLayerGraph
     * @param ml
     * @return 
     */
    public abstract NonCompPair<T, Entity> getADFSCode(MultiLayerGraph ml);
    
}
