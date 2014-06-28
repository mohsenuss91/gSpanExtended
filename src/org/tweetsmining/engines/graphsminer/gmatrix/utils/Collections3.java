/*
 * Collections3.java
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

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 *
 * @author gyankos
 */
public class Collections3 {
    
    public static <E extends Object> boolean exists(Collection<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered.isEmpty())
            return false;
        return (Collections2.<E>filter(unfiltered, predicate).size()>0);
    }
    
    
    public static <E extends Object> boolean forall(Collection<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered.isEmpty())
            return true;
        int fall_size = Collections2.<E>filter(unfiltered, predicate).size();
        return (fall_size==unfiltered.size());
    }
    
    
    public static <E extends Object> Collection<E> intersect(Collection<E> left,final Collection<E> right) {
        
        return Collections2.<E>filter(left, new Predicate<E>() {
            @Override
            public boolean apply(E input) {
                return right.contains(input);
            }
        });
    }
    
    
}
