/*
 * PropertyGraph.java
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


package org.tweetsmining.model.graph;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tweetsmining.engines.graphsminer.gmatrix.utils.NonCompPair;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.matrices.GuavaMatrix;
import org.tweetsmining.model.matrices.IMatrix;

import com.google.common.collect.Table.Cell;

/**
 * This class provides over a view of given relations. See IMatrix and IGraph
 * for comments
 *
 * @author gyankos
 */
public class PropertyGraph implements IAdjacencyGraph<Entity> {

    private String layer;
    private MultiLayerGraph uber;

    public PropertyGraph(String layer, MultiLayerGraph uber) {
        this.layer = layer;
        this.uber = uber;
    }

    public PropertyGraph(String layer, MultiLayerGraph uber, IMatrix cpy) {
        this(layer, uber);
        try {
            for (Cell<Integer, Integer, Double> x : cpy.getValueRange()) {
                set(x.getRowKey(),x.getColumnKey(), cpy.get(x.getRowKey(),x.getColumnKey()));
                
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void clear() {
        for (Integer x:uber.getVerticesId()) {
            removeEnt(x);
        }
    }

    @Override
    public double get(int i, int j) {
        return uber.getRelation(layer).get(i, j);
    }

    @Override
    public double get(NonCompPair<Integer,Integer> x) {
        return uber.getRelation(layer).get(x);
    }

    public void removeEdge(Entity src, Entity dst) {
        if (src != null && dst != null) {
            uber.remTriple(src, uber.getRelation(layer), dst);
        }
    }

    @Override
    public void rem(int i, int j) {
        Entity src = uber.getVertex(i);
        Entity dst = uber.getVertex(j);
        if (src == null || dst == null) {
            return;
        }
        removeEdge(src, dst);
    }

    @Override
    public void incr(NonCompPair<Integer,Integer> x, double val) {
        if (get(x) != 0) {
            uber.getRelation(layer).incr(x, val);
        } else {
            set(x, val);
        }
    }

    @Override
    public void incr(int i, int j, double val)  {
        if (get(i, j) != 0) {
            uber.getRelation(layer).incr(i, j, val);
        } else {
            set(i, j, val);
        }
    }

    public boolean set(Entity src, Entity dst, double val) {
        if (src != null && dst != null && val != 0) {
            try {
                uber.addTriple(src, uber.getRelation(layer), dst, val);
                return true;
            } catch (Throwable ex) {
                Logger.getLogger(PropertyGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public void set(int i, int j, double val) {
        if (val == 0) {
            return;
        }
        Entity src = uber.getVertex(i);
        Entity dst = uber.getVertex(j);
        if (src == null || dst == null) {
            return;
        }
        set(src, dst, val);
    }


    @Override
    public Set<Cell<Integer, Integer, Double>> getValueRange() {
        return uber.getRelation(layer).getValueRange();
    }

    public void removeRow(Entity e) {
        if (e == null) {
            return;
        }
        Collection<Entity> out = uber.getOutSet(e, uber.getRelation(layer));
        for (Entity x : out) {
            removeEdge(e, x);
        }
    }

    @Override
    public void removeRow(int i) {
        Entity src = uber.getVertex(i);
        if (src == null) {
            return;
        }
        removeRow(src);
    }

    public void removeCol(Entity e) {
        if (e == null) {
            return;
        }
        Collection<Entity> ins = uber.getInSet(e, uber.getRelation(layer));
        for (Entity x : ins) {
            removeEdge(x, e);
        }
    }

    @Override
    public void removeCol(int j) {
        Entity src = uber.getVertex(j);
        if (src == null) {
            return;
        }
        removeCol(src);
    }

    @Override
    public void removeEnt(int elem) {
        removeRow(elem);
        removeCol(elem);
    }

    @Override
    public String getName() {
        return layer;
    }

    @Override
    public IGraph rename(String new_fullPropURI) {
        if (uber.getLayer(new_fullPropURI) != null) {
            return null;
        } else {
            return uber.newLayer(new_fullPropURI, this);
        }
    }

    @Override
    public Entity addVertex(String name) {
        return uber.addVertex(name);
    }

    @Override
    public void removeVertex(String name) {
        Entity src = uber.getVertex(name);
        if (src != null) {
            removeEnt(src.getIndex());
        }
    }

    @Override
    public boolean addEdge(String left, String right, double value) {
        Entity src = uber.addVertex(left);
        Entity dst = uber.addVertex(right);
        if (src == null || dst == null) {
            return false;
        }
        return set(src, dst, value);
    }

    @Override
    public void removeEdge(String left, String right) {
        Entity src = uber.getVertex(left);
        Entity dst = uber.getVertex(right);
        if (src == null || dst == null) {
            return;
        }
        removeEdge(src, dst);
    }

    @Override
    public double getEdge(String left, String right) {
        Entity src = uber.getVertex(left);
        Entity dst = uber.getVertex(right);
        if (src == null || dst == null) {
            return 0;
        }
        return get(src.getIndex(), dst.getIndex());
    }

    @Override
    @Deprecated
    public void setVertex(String name, Entity val) {
        throw new UnsupportedOperationException("Deprecated Method.");
    }

    @Override
    @Deprecated
    public void addVertex(String name, Entity val) {
        throw new UnsupportedOperationException("Deprecated Method.");
    }

    @Override
    public Entity getVertex(String name) {
        return uber.getVertex(name);
    }

    @Override
    public void sum(IMatrix right) {
        try {
            Set<Cell<Integer, Integer, Double>> val = this.getValueRange();
            val.addAll(right.getValueRange());
            for (Cell<Integer, Integer, Double> x : val) {
            	this.incr(x.getRowKey(),x.getColumnKey(), right.get(x.getRowKey(),x.getColumnKey()));
            }
        } catch (Throwable ex) {
            Logger.getLogger(GuavaMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public void diff(IMatrix right) {
        try {
            Set<Cell<Integer, Integer, Double>> val = this.getValueRange();
            val.addAll(right.getValueRange());
            for (Cell<Integer, Integer, Double> x : val) {
                this.incr(x.getRowKey(),x.getColumnKey(), -right.get(x.getRowKey(),x.getColumnKey()));
            }
        } catch (Throwable ex) {
            Logger.getLogger(GuavaMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int nCols() {
        return uber.getNVertices();
    }

    @Override
    public int nRows() {
        return uber.getNVertices();
    }

    @Override
    public boolean addEdge(String left, String right) {
        return addEdge(left, right, 1);
    }

	@Override
	public void set(NonCompPair<Integer, Integer> x, double val) {
		set(x.getFirst(),x.getSecond(),val);
	}

	@Override
	public Set<Integer> getInSet(int ent) {
		return uber.getRelation(layer).getInSet(ent);
	}

	@Override
	public Set<Integer> getOutSet(int ent) {
		return uber.getRelation(layer).getOutSet(ent);
	}



}
