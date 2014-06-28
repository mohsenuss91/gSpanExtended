/*
 * ExtendedSubgraphOf.java
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

import java.util.Set;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.Collections3;
import org.tweetsmining.engines.graphsminer.graphs.AbstractSubgraphOf;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.GSCode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleSubgraphOf;
import org.tweetsmining.model.graph.MultiLayerGraph;

import com.google.common.base.Predicate;

/**
 *
 * @author gyankos
 */
public class ExtendedSubgraphOf extends AbstractSubgraphOf<ExtendedGSCode> {

    private final static ExtendedSubgraphOf self = new ExtendedSubgraphOf();
    private ExtendedSubgraphOf() { }
    
    public static ExtendedSubgraphOf getInstance() {
        return self;
    }
    
    @Override
    public boolean subgraphOf(ExtendedGSCode cmpSub, ExtendedGSCode cmpSup) {
        
        SimpleSubgraphOf sso = SimpleSubgraphOf.getInstance();
        if (!sso.subgraphOf(cmpSub.getFirst(), cmpSup.getFirst()))
            return false;
        
        final Set<GSCode> csbS = cmpSub.getSecond();
        final Set<GSCode> cspS = cmpSup.getSecond();
        
        if (csbS.size()>cspS.size())
            return false;
        
        //Could it be optimized in O(n)?
        return Collections3.<GSCode>forall(csbS, new Predicate<GSCode>() {
            @Override
            public boolean apply(final GSCode x) {
                boolean toret = Collections3.<GSCode>exists(cspS, new Predicate<GSCode>() {
                    @Override
                    public boolean apply(GSCode y) {
                        boolean toret = SimpleSubgraphOf.getInstance().subgraphOf(x,y);
                        //System.out.println(x+" "+y+" ~~~~~> "+toret);
                        return toret;
                    }
                });
                //System.out.println("Final:> "+toret);
                return toret;
            }
        });
        
    }

    @Override
    public boolean subgraphOf(MultiLayerGraph sub, MultiLayerGraph sup) {
        return subgraphOf(new ExtendedgSpanGraph(sub).getACode(),new ExtendedgSpanGraph(sup).getACode());
    }
    
}
