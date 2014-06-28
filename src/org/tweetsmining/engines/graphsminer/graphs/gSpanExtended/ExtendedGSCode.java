/*
 * ExtendedGSCode.java
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.Collections3;
import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.engines.graphsminer.graphs.DFS.ICode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.GSCode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleDFSCodeGenerator;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;

import com.google.common.base.Predicate;

/**
 * Defines the extended version of the GSCode
 * @author gyankos
 */
public class ExtendedGSCode extends NonCompPair<GSCode,Set<GSCode>> implements ICode, Comparable<ExtendedGSCode>{


    public ExtendedGSCode(GSCode first, Set<GSCode> second) {
        super((first==null ? new GSCode() : first), (second==null ? new HashSet<GSCode>() : second));
    }
    
    public GSCode toList() {
        GSCode toret = new GSCode(getFirst());
        Set<GSCode> s= getSecond();
        if (s==null)
            return toret;
        for (GSCode x:s) {
            toret.addAll(x);
        }
        return toret;
    }
    
    public static ExtendedGSCode getExtendedGSCode(MultiLayerGraph ml) {
        SimpleDFSCodeGenerator e = new SimpleDFSCodeGenerator();
        DFS<GSCode> d = new DFS<>(ml,e);
        GSCode dfsc = d.start();
        if (d.hasVisitedAllVertices()) {
            //Classic implementation
            return new ExtendedGSCode(dfsc,new HashSet<GSCode>());
        }
        return null;
    }
    
    public static boolean preceq(Set<GSCode> left, final Set<GSCode> right) {
        return Collections3.<GSCode>forall(left, new Predicate<GSCode>() {
            @Override
            public boolean apply(final GSCode inputl) {
                return Collections3.<GSCode>exists(right, new Predicate<GSCode>() {
                    @Override
                    public boolean apply(GSCode inputr) {
                        return (inputl.compareTo(inputr)<=0);
                    }
                });
            }
        });
    }
    
    public static boolean eq(Set<GSCode> left, final Set<GSCode> right) {
        return (preceq(left,right) && preceq(right,left));
    }

    @Override
    public int compareTo(ExtendedGSCode o) {
        if (o==null)
            return 1;
        int tocomp;
        
        if (getFirst()!=null && o.getFirst()!=null) {
            tocomp = getFirst().compareTo(o.getFirst());
            if (tocomp!=0)
                return tocomp;
        } else
            tocomp = 0;
        
        //here tocomp is 0
        
        if (preceq(getSecond(),o.getSecond())) {
            if (preceq(o.getSecond(),getSecond()))
                return 0;
            else 
                return -1;
        } else
            return 1;
        
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o==null)
            return false;
        if (!(o instanceof ExtendedGSCode))
            return false;
        ExtendedGSCode right = (ExtendedGSCode)o;
        return (getFirst().equals(right.getFirst()) && getSecond().equals(right.getSecond()));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67*hash + Objects.hashCode(getFirst());
        hash = 67*hash + Objects.hashCode(getSecond());
        return hash;
    }

    @Override
    public int size() {
        int s = getFirst().size();
        for (GSCode x:getSecond()) {
            s +=  x.size();
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        return (getFirst().isEmpty() && getSecond().isEmpty());
    }

    @Override
    public boolean contains(Object o) {
        if (o==null)
            return true;
        if (!(o instanceof ExtendedGSCode))
            return false;
        ExtendedGSCode right = (ExtendedGSCode)o;
        return (ExtendedSubgraphOf.getInstance().subgraphOf(this, right));
    }

    @Override
    public Iterator<ERTriple> iterator() {
        return toList().iterator();
    }

    @Override
    public Object[] toArray() {
        return toList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return toList().toArray(a);
    }

    @Override
    public boolean add(ERTriple e) {
        GSCode first = getFirst();
        //the rough way...
        if (first==null) {
            first = new GSCode();
        }
        boolean toret = first.add(e);
        setFirst(first);
        return toret;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends ERTriple> c) {
        boolean toret = true;
        for (ERTriple x:c) {
            toret = toret && add(x);
        }
        return toret;
    } 

    @Override
    public boolean addAll(int index, Collection<? extends ERTriple> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public void clear() {
        setFirst(new GSCode());
        setSecond(new HashSet<GSCode>());
    }

    @Override
    public ERTriple get(int index) {
        int i = 0;
        for (ERTriple x:getFirst()) {
            if (i==index)
                return x;
            i++;
        }
        for (GSCode x:getSecond()) {
            for (ERTriple y:x) {
                if (i==index)
                    return y;
                i++;
            }
        }
        return null;
    }

    @Override
    public ERTriple set(int index, ERTriple element) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public void add(int index, ERTriple element) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public ERTriple remove(int index) {
        throw new UnsupportedOperationException("Cannot arbitrarly change an ExtendedGSCode.");
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for (ERTriple x:getFirst()) {
            if (x.equals(o))
                return i;
            i++;
        }
        for (GSCode x:getSecond()) {
            for (ERTriple y:x) {
                if (y.equals(o))
                    return i;
                i++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    @Override
    public ListIterator<ERTriple> listIterator() {
        return toList().listIterator();
    }

    @Override
    public ListIterator<ERTriple> listIterator(int index) {
        return toList().listIterator(index);
    }

    @Override
    public List<ERTriple> subList(int fromIndex, int toIndex) {
        return toList().subList(fromIndex, toIndex);
    }
}
