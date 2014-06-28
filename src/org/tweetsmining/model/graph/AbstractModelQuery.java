/*
 * AbstractModelQuery.java
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



package org.tweetsmining.model.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;

import com.google.common.collect.Table.Cell;

/**
 * Provides an interface to query the model contained in a MultiLayeredGraph
 * @author gyankos
 */
public class AbstractModelQuery {
    
    
     public static SortedSet<ERTriple> query(MultiLayerGraph mlg, ERTriple t) {
         if (t==null)
             return query(mlg,null,null,null);
         else
            return query(mlg, t.getSource(), t.getRelation(), t.getDestination());
     }
    
    public static SortedSet<ERTriple> query(MultiLayerGraph mlg, Entity subject, Relation predicate, Entity object) {
         	
        Collection<Relation> rels;
        if (predicate == null)
        	rels = mlg.getRelations();
        else {
        	rels = new HashSet<>();
        	rels.add(predicate);
        }
    	
        SortedSet<ERTriple> sert = new TreeSet<>();
        
    	if (subject != null && object != null) {
        	for (Relation rel : rels) {
        		if (rel.get(subject.getIndex(), object.getIndex())!=0)
        			sert.add(new ERTriple(subject, rel, object));
        	}
        } 
        	
        if (subject != null) 
        	return mlg.getInTripleSet(object, predicate);
        if (object != null)
        	return mlg.getOutTripleSet(subject, predicate);
        
        
        for (Relation rel: rels) {
        	for (Cell<Integer, Integer, Double> x: rel.getValueRange()) {
        		sert.add(new ERTriple(mlg.getVertex(x.getRowKey()), rel, mlg.getVertex(x.getColumnKey())));
        	}
        }
        
        
        return sert;
        
    }
    
}
