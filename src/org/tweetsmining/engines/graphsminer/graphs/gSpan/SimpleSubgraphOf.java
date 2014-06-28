/*
 * SimpleSubgraphOf.java
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
import java.util.Stack;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.Collections3;
import org.tweetsmining.engines.graphsminer.gmatrix.utils.gSpanFactory;
import org.tweetsmining.engines.graphsminer.graphs.AbstractSubgraphOf;
import org.tweetsmining.engines.graphsminer.graphs.SubgraphE;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;

/**
 *
 * @author gyankos
 */
public class SimpleSubgraphOf extends AbstractSubgraphOf<GSCode>{

    private final static SimpleSubgraphOf self = new SimpleSubgraphOf();
    private SimpleSubgraphOf() {
    }
    
    public static SimpleSubgraphOf getInstance() {
        return self;
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
    public  boolean subgraphOf(GSCode cmpSub, GSCode cmpSup) {
        
        //New compact version
        return new SubgraphE<ERTriple>().compare(cmpSub, cmpSup);
        
        /*if (cmpSub.size()>cmpSup.size())
            return false;
        
        /////////////////////////////////////////////
        // RECHECK
        TreeSet<Entity> vertices_l = new TreeSet<>();
        for (ERTriple x:cmpSub) {
            vertices_l.add(x.getSource());
            vertices_l.add(x.getDestination());
        }
        TreeSet<Entity> vertices_r = new TreeSet<>();
        for (ERTriple x:cmpSup) {
            vertices_r.add(x.getSource());
            vertices_r.add(x.getDestination());
        }
        Collection<Entity> intersect = Collections3.intersect(vertices_l, vertices_r);
        if (intersect.isEmpty())
            return false;
        //////////////////////////////////////////////////////////
        
        if (!vertices_r.contains(vertices_l.first()))
            return false;
        
    
        
        ERTriple startFrom = null;
        int index = 0;
        int subIndex =0;
        
        
        for (ERTriple x:cmpSub) {
            index = cmpSup.indexOf(x);
            if (index!=-1) {
                startFrom = x;
                break;
            }
            subIndex++;
        }
        if (startFrom == null)
            return false;
        ListIterator<ERTriple> supIt = cmpSup.listIterator(index);
        ListIterator<ERTriple> subIt = cmpSub.listIterator(subIndex);
        while (subIt.hasNext()) {
            if (!supIt.hasNext())
                return false;
            if (!subIt.next().equals(supIt.next()))
                return false;
        }
        return true;*/
        
        
        
    }
    
    @Override
    public boolean subgraphOf(MultiLayerGraph sub, MultiLayerGraph sup) {
        
        if (sub==null)
            return true;
        else if (sup==null)
            return false;
        
        GSCode cmpSub = (GSCode)gSpanFactory.getCode(false, sub);
        if (cmpSub.isEmpty())
            return true;
        
        GSCode cmpSup = (GSCode)gSpanFactory.getCode(false, sup);
        if (cmpSub.size()>cmpSup.size())
            return false;
        
        Collection<Entity> intersect = Collections3.intersect(sub.getVertices(), sup.getVertices());
        if (intersect.isEmpty())
            return false;
        
        return new SimpleSubgraphOf().subgraphOf(cmpSub, cmpSup);
        
    }
    
    /*@Deprecated
    public static boolean old_subgraphOf(MultiLayerGraph sub, MultiLayerGraph sup) {
        
        if (sub==null || (sub==null && sup==null))
            return true;
        else if (sup==null)
            return false;
        
        final LinkedList<Entity> lp = new LinkedList<>(sup.getVertices());
        //Java SuX: this simple command (l.retainAll(lp);) will remove the elements from sub :@ 
        //Since that is absurd, I prefer to adopt Guava's filtering
        Collection<Entity> common_vertices = Collections2.filter(sub.getVertices(),new Predicate<Entity>() {
            @Override
            public boolean apply(Entity input) {
                return lp.contains(input);
            }
        });
        
        if (common_vertices.isEmpty())
            return false;
        
        ////////////////////////////////////////////////////////////////////////
        Entity start = null;
        for (Entity x:common_vertices) {
                if (start == null)
                    start = x;
                else if (x.compareTo(start)<0)
                    start = x;
        }
        ////////////////////////////////////////////////////////////////////////
        
        
        Stack<ERTriple> s = new Stack<>();
        s.push(new ERTriple(null,null,start));
        SortedSet<Entity> visited = new TreeSet<>();
        
        while(!s.isEmpty()) {
            ERTriple tmp = s.pop();
            //System.out.println(tmp);
            Entity e = tmp.getDestination();
            if (!visited.contains(e)) {
                visited.add(e);
                
                LinkedList<ERTriple> subs = new LinkedList<>(AbstractModelQuery.query(sub, e, null, null));
                LinkedList<ERTriple> sups = new LinkedList<>(AbstractModelQuery.query(sup, e, null, null));
                if (subs.size()==0 || sups.size()==0)
                    continue;
                
                LinkedList<ERTriple> intersection = new LinkedList<>();
                for (ERTriple x:subs) {
                    if (sups.contains(x))
                        intersection.add(x);
                }
                if (intersection.size()!=subs.size())
                    return false;
                s.addAll(intersection);
            }
            
        }
                
        
        return (true);
    }
    */
    
}
