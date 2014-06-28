/*
 * CompPair.java
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



package org.tweetsmining.engines.graphsminer.gmatrix.utils;


/**
 * It defines a pair where the elements are comparable
 *
 * @author gyankos
 * @param <A>
 * @param <B>
 */
public class CompPair<A extends Comparable<A>, B extends Comparable<B>> extends NonCompPair<A, B> implements Comparable<CompPair<A, B>> {

    /**
     *
     * @param first
     * @param second
     */
    public CompPair(A first, B second) {
        super(first, second);
    }

    @Override
    /**
     * this = <a,b>
     * o = <c,d>
     *
     * if (compare(a,c)==0) then compare(b,d) else compare(a,c)
     *
     */
    public int compareTo(CompPair<A, B> o) {
        if (o==null) return 1;
        int comp_first = this.getFirst().compareTo(o.getFirst());
        if (comp_first == 0) {
            return this.getSecond().compareTo(o.getSecond());
        }
        return comp_first;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompPair)) {
            return false;
        }
        CompPair<?, ?> p = (CompPair<?, ?>) o;
        
        return p.getFirst().equals(getFirst()) && p.getSecond().equals(getSecond());
    
    }
    


    /**
     *
     * @param ps
     * @return
     */
    public static String toString(CompPair<String, String> ps) {
        return ps.getFirst() + "_" + ps.getSecond();
    }

    /**
     *
     * @param str
     * @return
     */
    public static CompPair<String, String> PairString(String str) {
        int pos = str.indexOf("_");
        if (pos < 0) {
            return null;
        }
        return new CompPair<String, String>(str.substring(0, pos), str.substring(pos + 1));
    }

}
