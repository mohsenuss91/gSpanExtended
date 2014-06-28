/*
 * GSCode.java
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.tweetsmining.engines.graphsminer.graphs.DFS.ICode;
import org.tweetsmining.model.graph.database.ERTriple;

/**
 *
 * @author gyankos
 */
public class GSCode implements ICode, Comparable<GSCode> {

    List<ERTriple> tmp;

    public GSCode() {
        tmp = new LinkedList<>();
    }
    
    public GSCode(ERTriple... e) {
        tmp = new LinkedList<>(Arrays.asList(e));
    }

    public GSCode(Collection<ERTriple> e) {
        if (e==null)
            tmp = new LinkedList<>();
        else
            tmp = new LinkedList<>(e);
    }

    @Override
    public int compareTo(GSCode o) {
        if (o == null) {
            return 1;
        }
        if (size() != o.size()) {
            if (size() < o.size()) {
                return -1;
            } else {
                return 1;
            }
        }
        Iterator<ERTriple> ti = iterator();
        Iterator<ERTriple> oi = o.iterator();
        while (ti.hasNext() && oi.hasNext()) {
            int result;
            if ((result = (ti.next().compareTo(oi.next()))) != 0) {
                return result;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof GSCode)) {
            return false;
        }
        GSCode right = (GSCode) o;

        if (size() != right.size()) {
            return false;
        }

        Iterator<ERTriple> ti = iterator();
        Iterator<ERTriple> oi = right.iterator();
        while (ti.hasNext() && oi.hasNext()) {
            if (!ti.next().equals(oi.next())) {
                return false;
            }
        }
        return true;
    }
    //Class as a simple renaming

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.tmp);
        return hash;
    }

    @Override
    public int size() {
        return tmp.size();
    }

    @Override
    public boolean isEmpty() {
        return tmp.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return tmp.contains(o);
    }

    @Override
    public Iterator<ERTriple> iterator() {
        return tmp.iterator();
    }

    @Override
    public Object[] toArray() {
        return tmp.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return tmp.toArray(a);
    }

    @Override
    public boolean add(ERTriple e) {
        return tmp.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return tmp.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return tmp.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ERTriple> c) {
        return tmp.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public void clear() {
        tmp.clear();
    }

    @Override
    public boolean addAll(int index, Collection<? extends ERTriple> c) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public ERTriple get(int index) {
        return tmp.get(index);
    }

    @Override
    public ERTriple set(int index, ERTriple element) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public void add(int index, ERTriple element) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public ERTriple remove(int index) {
        throw new UnsupportedOperationException("Cannot arbitrarly change a GSCode.");
    }

    @Override
    public int indexOf(Object o) {
        return tmp.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return tmp.lastIndexOf(o);
    }

    @Override
    public ListIterator<ERTriple> listIterator() {
        return tmp.listIterator();
    }

    @Override
    public ListIterator<ERTriple> listIterator(int index) {
        return tmp.listIterator(index);
    }

    @Override
    public List<ERTriple> subList(int fromIndex, int toIndex) {
        return tmp.subList(fromIndex, toIndex);
    }
    
    @Override
    public String toString() {
        String s = "";
        for (ERTriple x: tmp) {
            s = s + "<" +x.toString() + ">";
        } 
        return s;
    }

}
