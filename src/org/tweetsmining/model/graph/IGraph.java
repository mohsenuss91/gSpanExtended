/*
 * IGraph.java
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


/**
 *
 * @author gyankos
 */
public interface IGraph<T> {
    
    public String getName();
    public IGraph<T> rename(String new_name);
    public void setVertex(String name, T val);
    public T addVertex(String name);
    public void addVertex(String name, T val);
    public T getVertex(String name);
    void removeVertex(String name);
    public boolean addEdge(String left, String right, double value);
    public boolean addEdge(String left, String right);
    public void removeEdge(String left, String right);
    public double getEdge(String left, String right);
    public void clear();
    
}
