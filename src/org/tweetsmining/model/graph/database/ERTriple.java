/*
 * ERTriple.java
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



package org.tweetsmining.model.graph.database;

import java.util.Objects;

/**
 * Maps a triple (Entity,Relation,Entity) to a type
 * @author gyankos
 */
public class ERTriple implements Comparable<ERTriple> {
    
    private Entity src;
    private Relation link;
    private Entity dst;
    private String hashkey;
    
    public static ERTriple easyMakeTriple(String source, String relation, String dest)
    {
        Entity src= new Entity(source, 0);
        Entity dst = new Entity(dest,0 );
        Relation r = new Relation(relation);
        return new ERTriple(src,r,dst);
       
    }
    public boolean hasNull() {
        return (this.src == null || this.link== null|| this.dst==null);
    }
    public ERTriple(Entity source, Relation r, Entity dest) {
        this.src = source;
        this.link = r;
        this.dst = dest;
        String s  = (this.src==null? " " : this.src.toString());
        String l = (this.link==null? " " : this.link.toString());
        String d = (this.dst==null? " " : this.dst.toString());
        hashkey =  s+" "+l+" "+d;
    }
    
    public Entity getSource() { return src; }
    public Relation getRelation() { return link; }
    public Entity getDestination() { return dst; }
    
    
    @Override
    /**
     * this = <a,b>
     * o = <c,d>
     *
     * if (compare(a,c)==0) then compare(b,d) else compare(a,c)
     *
     */
    public int compareTo(ERTriple o) {
        if (o==null) return 1;
        int comp_first = this.getSource().compareTo(o.getSource());
        if (comp_first == 0) {
            comp_first =  this.getRelation().compareTo(o.getRelation());
            if (comp_first == 0) {
                return this.getDestination().compareTo(o.getDestination());
            }
        }
        return comp_first;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o==null)
            return false;
        if (!(o instanceof ERTriple)) {
            return false;
        }
        ERTriple p = (ERTriple) o;
        
        return hashkey.equals(p.toString());
    }
    
    @Override
    public String toString() {
        return hashkey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.hashkey);
        return hash;
    }
    
}
