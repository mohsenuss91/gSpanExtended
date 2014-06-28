/*
 * Abstract_gSpan.java
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;

import org.tweetsmining.engines.graphsminer.graphs.DFS.AbstractDBVisit;
import org.tweetsmining.engines.graphsminer.graphs.DFS.DFS;
import org.tweetsmining.engines.graphsminer.graphs.DFS.ICode;
import org.tweetsmining.engines.graphsminer.graphs.gSpan.SimpleRightmostExpansion;
import org.tweetsmining.model.graph.MultiLayerGraph;
import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 *
 * @author gyankos
 * @param <CodeType>
 */
public abstract class Abstract_gSpan<CodeType extends ICode> {
    
    //private SerializableList<IgSpanGraphs<CodeType>> database = null;
	private List<MultiLayerGraph> new_database = null;
    private AbstractSubgraphOf<CodeType> subgraphIsomorphismAlgorithm;
    
    /**
     * Filters the triples that could be added for rightmost expansion
     * @param mlg           
     * @param ert
     * @param right_path
     * @return 
     */
    public Collection<ERTriple> filterTriplesForExpansion(final IgSpanGraphs<CodeType> mlg, Collection<ERTriple> ert, final List<Entity> right_path) {
        return Collections2.filter(ert, new Predicate<ERTriple>() {
            @Override
            public boolean apply(ERTriple input) {
                //Keep the rightmost path (don't break it) 
                if (input == null) {
                    return false;
                }
                //Remove errors ~ belts and suspenders
                if (input.hasNull())
                    return false;

                if (mlg.getRepresentation() != null) {
                    //get the graph
                    MultiLayerGraph tmp = mlg.getRepresentation();
                    //for all the outgoing vertices from input.source();
                    for (Entity x : tmp.getOutSet(input.getSource(), null)) {
                        //remove the triple if this could break the integrity of the rightmost path
                        if (input.getDestination().compareTo(x) > 0) {
                            return false;
                        }
                    }
                }

                return right_path.contains(input.getSource());
            }
        });
    }

    /**
     * Forcing the implementation of an algorithm
     * @param subIso        Subgraph Algorithm Implementation
     */
    protected Abstract_gSpan(AbstractSubgraphOf<CodeType> subIso) {
        subgraphIsomorphismAlgorithm = subIso;
    }
    
    public abstract IgSpanGraphs<CodeType> createCodeGraph(MultiLayerGraph m);
    
    
    /*public Collection<IgSpanGraphs<CodeType>> getDatabase() {
        return database;
    }*/
    
    public void setDatabase(List<MultiLayerGraph> mlgdb) {
        new_database = mlgdb;
    }
    
    public double support(MultiLayerGraph g) {
        double n = 0;
        for (MultiLayerGraph x : new_database) {
            if (subgraphIsomorphismAlgorithm.subgraphOf(g, x)) {
                n++;
            }
        }
        n = n / ((double) new_database.size());
        return n;
    }
    
    public Collection<ERTriple> getMostFrequentEdges(double thereshold) {

        HashMap<ERTriple, Double> count = new HashMap<>();
        if (thereshold <= 0) {
            thereshold = Double.MIN_VALUE;
        }
        final double tmpt = thereshold;

        int i = 1;
        Iterator<MultiLayerGraph> mlgi = new_database.iterator();
        //Initializing count, that is counting the occurrence of the edges inside the database
        while (mlgi.hasNext()) {
        	MultiLayerGraph x = mlgi.next();
        	System.out.print(i+ ", ");
        	i++;
        	if (x==null)
        		System.out.println("ERROR");
        	IgSpanGraphs<CodeType> old = createCodeGraph(x);
            for (ERTriple y : old.getAllEdges()) {
                if (!count.containsKey(y)) {
                    /*
                     //TEST
                     for (ERTriple z:count.keySet())
                     if (z.equals(y)) 
                     throw new RuntimeException("ERROR: equal but not incremented");
                     */
                    count.put(y, (double) 1);
                } else {
                    count.put(y, count.get(y) + 1);
                }
            }
        }

        //Normalizing factor
        final double size = new_database.size();

        //Filters the entries by their frequency
        Collection<Map.Entry<ERTriple, Double>> frequentOnes = Collections2.filter(count.entrySet(), new Predicate<Map.Entry<ERTriple, Double>>() {
            @Override
            public boolean apply(Map.Entry<ERTriple, Double> t) {
                return ((t.getValue() / size) >= tmpt);
            }
        });

        //Given the entry, returns only the key, that is the triple  ~~~ Vonlenska/Hopelandic
        return Collections2.transform(frequentOnes, new Function<Map.Entry<ERTriple, Double>, ERTriple>() {
            @Override
            public ERTriple apply(Map.Entry<ERTriple, Double> f) {
                return f.getKey();
            }
        });

    }
        
    public static MultiLayerGraph toMLG(ERTriple input) {
        LinkedList<ERTriple> e = new LinkedList<>();
        e.add(input);
        return MultiLayerGraph.create(e);
    }

    
    public abstract List<Entity> getEntities(final IgSpanGraphs<CodeType> mlg);
    
        /**
     * Given a base graph and a set of possible edge extensions, it returns the
     * set of all the possible rightmost-extensions of such graph
     *
     * @param mlg basic graph
     * @param ert List of possible edge-right extensions
     * @return
     */
    public Collection<IgSpanGraphs<CodeType>> getRightmostExpansions(final IgSpanGraphs<CodeType> mlg, Collection<ERTriple> ert) {

        Collection<IgSpanGraphs<CodeType>> toret;
        if (ert.isEmpty()) {
            return new LinkedList<>();
        }

        ///////////////////////////////////////////////////////////////////////
        //Returns the sequence of the entities encountered on the rightmostpath
        AbstractDBVisit<List<Entity>> adbv_rightmost = new SimpleRightmostExpansion();
        DFS<List<Entity>> d = new DFS<List<Entity>>(mlg.getRepresentation(), adbv_rightmost);
        final List<Entity> right_path = d.start();
        ///////////////////////////////////////////////////////////////////////

        //Returns the possible rightmost extensions
        ert = filterTriplesForExpansion(mlg, ert, right_path);
        
        //Add the rightmost extension to the graph itself
        toret = Collections2.transform(ert, new Function<ERTriple, IgSpanGraphs<CodeType>>() {
            @Override
            public IgSpanGraphs<CodeType> apply(ERTriple input) {
                CodeType ls = mlg.getACode();
                ls.add(input);
                return createCodeGraph(MultiLayerGraph.create(ls));
            }
        });

        return toret;
    }
    
    public void gSpan(double edge_t, int depth, List<MultiLayerGraph> returned) {
        gSpan(edge_t, depth,Double.MIN_VALUE,returned);
    }
    
    public void gSpan(double edge_threshold, int depth_search, double support_thereshold, List<MultiLayerGraph> returned) {
        //Candidates for rightmost expansion
    	System.out.println("getting frequent edges... ");
        LinkedList<ERTriple> N = new LinkedList<>(getMostFrequentEdges(edge_threshold));
        System.out.print("Done.");
        
        //HashSet<SortedSet<ERTriple>> S = new HashSet<>();
        
        for (ERTriple n : N) {
        	System.out.println("Triple ~ " + n.toString());
            int depth;
            returned.add(toMLG(n)); //adds first element
            LinkedList<IgSpanGraphs<CodeType>> gsgs = new LinkedList<>();
            Stack<Integer> layer = new Stack<>();
            gsgs.push(createCodeGraph(toMLG(n)));
            layer.push(0);
            while (!gsgs.isEmpty()) {
                final IgSpanGraphs<CodeType> candidate = gsgs.get(0); //double-checking staticity of the result
                gsgs.remove();
                depth = layer.pop();
                if ((depth < depth_search)
                        && (support(candidate.getRepresentation()) >= support_thereshold)
                        ) {

                    List<IgSpanGraphs<CodeType>> gsl = new LinkedList<>(getRightmostExpansions(candidate, N));
                    
                    SortedSet<ERTriple> tmpEdges = candidate.getAllEdges();
                    if (!tmpEdges.isEmpty())
                        returned.add(MultiLayerGraph.create(tmpEdges));
                    int hasNull = 0;
                    
                    for (IgSpanGraphs<CodeType> kt : gsl) {
                        if (kt!=null)
                            gsgs.push(kt);
                        else
                            hasNull += 1;
                    }

                    for (int j = 0; j < gsl.size()-hasNull; j++) {
                        layer.push(depth + 1);
                    }
                }

            }
        }

        /*LinkedList<IgSpanGraphs<CodeType>> toret = new LinkedList<>(Collections2.transform(N, new Function<ERTriple,IgSpanGraphs<CodeType>>() {
            @Override
            public IgSpanGraphs<CodeType> apply(ERTriple n) {
                return createCodeGraph(toMLG(n));
            }
        }));*/
        /*toret.addAll(new LinkedList<>(Collections2.transform(S, new Function<SortedSet<ERTriple>, IgSpanGraphs<CodeType>>() {
            @Override
            public IgSpanGraphs<CodeType> apply(SortedSet<ERTriple> input) {
                return createCodeGraph(MultiLayerGraph.create(input)); 
            }
        })));*/
        //return toret;
    }
    
    public Collection<IgSpanGraphs<CodeType>> createCodeGraph(Collection<MultiLayerGraph> m) {
        return Collections2.transform(m, new Function<MultiLayerGraph,IgSpanGraphs<CodeType>>() {
            @Override
            public IgSpanGraphs<CodeType> apply(MultiLayerGraph input) {
                return createCodeGraph(input);
            }
        });
    }
    
    public Collection<MultiLayerGraph> getRepresentation(Collection<IgSpanGraphs<CodeType>> cl) {
        return Collections2.transform(cl, new Function<IgSpanGraphs<CodeType>, MultiLayerGraph>() {
            @Override
            public MultiLayerGraph apply(IgSpanGraphs<CodeType> input) {
                return input.getRepresentation();
            }
        });
    }


    
}
