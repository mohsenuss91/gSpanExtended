/*
 * DFS.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import org.tweetsmining.model.graph.AbstractModelQuery;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;

/**
 * Implementation of the Depth First Search Algorithm for Graph Visit
 * @author gyankos
 * @param <T>
 */
public class DFS<T> implements  IVisit<T> {
    
    private Entity start ;
    private AbstractDBVisit<T> algorithm;
    private MultiLayerGraph mlg;
    private int vs;
    private boolean v[] = null;
    private Stack<ERTriple> s = null;
    private boolean has_finished = true;
    
    /**
     * Initializes DFS Algorithm
     * @param g Graph where to perform DSF
     * @param e DSF algorithmical's extension
     */
    public DFS(MultiLayerGraph g, AbstractDBVisit<T> e) {
        vs = -1;
        Collection<Entity> l = g.getVertices();
        
        if (l == null || e == null) {
            start = null;
            vs = 0;
        } else if (l.isEmpty()) {
            start = null;
            vs = 0;
        } else {
            start = e.sortAscendingVertices(new ArrayList<>(g.getVertices())).get(0);
            for (Entity x:l) {
                if (x.getIndex()>vs)
                    vs = x.getIndex();
            }
        }
        this.algorithm = e;
        this.mlg = g;
    }
    
    /**
     * Initializes DFS Algorithm
     * @param g         Graph where to perform DSF
     * @param e         DSF algorithmical's extension
     * @param startStr  Where to start the computation
     */
    public DFS(MultiLayerGraph g, AbstractDBVisit<T> e,String startStr) {
        this(g,e);
        if (start!=null) {
            start = g.getVertex(startStr);
        }
    }
    
    /**
     * Initializes DFS Algorithm
     * @param g         Graph where to perform DSF
     * @param e         DSF algorithmical's extension
     * @param estart    Where to start the computation
     */
    public DFS(MultiLayerGraph g, AbstractDBVisit<T> e,Entity estart) {
        this(g,e);
        if (start!=null) {
            start = estart;
        }
    }
    
    @Override
    public Entity getCurrentPos() {
        return start;
    }
    
    @Override
    public SortedSet<Entity> getVisitedVertices() {
        if (has_finished) {
            SortedSet<Entity> toret = new TreeSet<>();
            for (int i=0; i<v.length; i++)
                if (v[i]) toret.add(mlg.getVertex(i));
            return toret;
        }
        return null;
    }
    
    @Override
    public int nVertices() {
        return mlg.getNVertices();
    }
 
    @Override
    public boolean hasVisitedAllVertices() {
        Set<Entity> lll = getVisitedVertices();
        if (lll==null)
            return false;
        else
            return (lll.size()==nVertices());
    }
    
    private static ERTriple pop(Stack<ERTriple> s) {
        if (s==null)
            return null;
        else if (s.empty()) 
            return null;
        else
            return s.pop();
    }
    
    @Override
    public boolean hasNext() {
        if (start==null) {
            has_finished = true;
            return false;
        }
        if (v == null) {
            has_finished = false;
            v = new boolean[vs+1];
            for (int i=0; i<=vs; i++) {
               v[i] = false;
            }
            s = new Stack<>();
            s.push(new ERTriple(null,null,start));
        }
        ERTriple tmp = null;
        if ((tmp = pop(s))!=null) {
              start = tmp.getDestination();
              if (tmp.getRelation()!=null) {
                  algorithm.visitEdge(tmp.getSource(),tmp.getRelation(),tmp.getDestination());
              }
              if (!v[start.getIndex()]) {
                  
                  algorithm.visitVertex(start);
                  v[start.getIndex()] = true;
                  
                  
                  s.addAll(algorithm.getEdgeVisitOrder(AbstractModelQuery.query(mlg, start, null, null)));
               
              }
              return true;
        } else {
            has_finished = true;
            return false;
        }
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public T start() {
        
        while (hasNext());
        return algorithm.getOutput();
        
    }
    
}
