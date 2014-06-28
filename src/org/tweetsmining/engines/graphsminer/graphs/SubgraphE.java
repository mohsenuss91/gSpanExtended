/*
 * SubgraphE.java
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

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gyankos
 */
public class SubgraphE<E> {
    
    public boolean compare(List<E> sub, List<E> sup) {
        if (sub == null)
            return true;
        else if (sup == null)
            return false;
        while (!sub.isEmpty()) {
            while ((!sup.isEmpty()) && (!sub.get(0).equals(sup.get(0)))){
                if (sup.size()>1)
                    sup = sup.subList(1, sup.size());
                else 
                    sup = new LinkedList<>();
            }
            if ((sup.isEmpty()) && (!sub.isEmpty()))
                return false;
            //System.out.println(subI.hasNext()+" "+supI.hasNext());
            while ((!sub.isEmpty()) && (!sup.isEmpty()) && sub.get(0).equals(sup.get(0))){
                if (sub.size()>1)
                    sub = sub.subList(1, sub.size());
                else 
                    sub = new LinkedList<>();
                
                if (sup.size()>1)
                    sup = sup.subList(1, sup.size());
                else 
                    sup = new LinkedList<>();
            }
        }
        return true; // sub == empty is a subgraph
    }
    
}
