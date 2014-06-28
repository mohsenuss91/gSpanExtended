/*
 * MultiLayerGraph.java
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tweetsmining.model.graph.database.ERTriple;
import org.tweetsmining.model.graph.database.Entity;
import org.tweetsmining.model.graph.database.Relation;
import org.tweetsmining.model.matrices.IMatrix;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Table.Cell;

/**
 * This Class gives the abstraction of the OWL ABox with different layers 
 * (IGraph-s) where each layer is formed by graphs composed by edges of
 * the same kind of Relation
 * 
 * @author gyankos
 */
public class MultiLayerGraph  implements IGraph<Entity> {
    
	private static final long serialVersionUID = -8381179161274281628L;
	private Map<Integer,Entity> elems = new HashMap<>();
    private Map<String,Relation> layers = new HashMap<>();
    //private Model mod;
    //public static final String CONVERSION_FORMAT = "Turtle";
    
    
    public ERTriple toTriple(int i, String rel, int j) {
    	return new ERTriple(elems.get(i), layers.get(rel), elems.get(j));
    }
    
    /**
     * Defines the overall Ontology model
     */
    public MultiLayerGraph() {
        /*this.mod = createModel();
        recheck();*/
    }
    
    

    
    @Override
    public String toString() {
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	mod.write(baos,CONVERSION_FORMAT);
    	return baos.toString();*/
    	return "";
    }
    
    
    
    /**
     * Checks the consistency of the hashmaps
     */
    private void recheck() {
        /*for (Statement x:mod.listStatements().toSet()) {
            Entity subject = addVertex(x.getSubject().getURI()); //Returns existant Entity, or creates a new one
            Entity object = addVertex(((Resource)x.getObject()).getURI());
            String uri = x.getPredicate().getURI();
            Relation predicate = newLinkType(uri);
            if (predicate.getProperty().getURI().equals("http://my.new/full#wifeOf")) {
                if (true) {
                    
                }
            }
            //System.out.println(predicate.getProperty().getURI());
            
            try {
                if (getTriple(subject,predicate,object)==0) 
                     predicate.set(subject.getIndex(), object.getIndex(),1);
                //System.out.println(predicate.get(subject.getIndex(), object.getIndex()));
            } catch (Throwable ex) {
                Logger.getLogger(AbstractMultiLayerGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
    
    /**
     * Returns the Jena model
     * @return .
     */
    /*public Model getModel() {
        return mod;
    }*/
    
    /**
     * Unites the current model to an other external one
     * @param model 
     */
    /*public void union(Model model) {
        if (model==null)
            return;
        this.mod.add(model.listStatements());
        recheck();
    }*/
    
    /**
     * Recreates the overrall model, by changing 
     * @param model 
     */
    /*public void update(Model model) {
        //Tabula Rasa
        elems.clear();
        layers.clear();
        this.mod = model;
        recheck();
    }*/
    
    @Override
    public void clear() {
        for (String x:layers.keySet()) {
            getLayer(x).clear();
        }
    }
    
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/
    
    /**
     * Returns a new Entity with the given URI
     * @param fullUri
     * @return 
     */
    @Override
    public Entity addVertex(String fullUri) {
        if (fullUri==null)
            return null;
        /*try {
			setNamespaceFromURI(fullUri);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}*/
        Entity elem = getVertex(fullUri);
        if (elem!=null) 
            return elem;
        int size = elems.size();
        elem = new Entity(fullUri,size);
        elems.put(size, elem);
        return elem;
    }
    
    /**
     * Returns the Entity defined by the assigned URI
     * @param fullUri  URI that defines the Entity
     * @return 
     */
    @Override
    public Entity getVertex(String fullUri) {
        if (fullUri==null)
            return null;
        //TODO: more efficient version
        for (Entity v : elems.values()) {
            if (v.toString().equals(fullUri))
                return v;
        }
        return null;
    }
    
    public int getNVertices() {
        return elems.size();
    }
    
    
    /**
     * Returns the Entity defined by the assigned index position
     * @param pos   Entity's index position
     * @return 
     */
    public Entity getVertex(int pos) {
        if (elems.containsKey(pos)) {
            return elems.get(pos);
        } else 
            return null;
    }
    
     /**
     * Remove the given Vertex from all the layers, and hence it removes all
     * the incoming edges and all the outgoing edges from and to it.
     * 
     * @param name Vertex Full URI
     */
    @Override
    public void removeVertex(String name) {
        if (name==null)
            return;
        Entity e = getVertex(name);
        if (e==null)
            return;
        for (String x:layers.keySet()) { 
            getLayer(x).removeVertex(name);
        }
        elems.remove(e.getIndex());
    }
    
    /**
     * Returns the set of all the vertices
     * @return 
     */
    public Set<Integer> getVerticesId() {
        return elems.keySet();
    }
    
    public Collection<Entity> getVertices() {
        return elems.values();
    }
    
    public List<String> getVerticesURI() {
        LinkedList<String> toret = new LinkedList<>();
        for (Entity x:getVertices()) {
            toret.add(x.getFullUri());
        }
        return toret;
    }
    
        
    /**
     * Returns the set of the Entities that are reachable from subject
     * @param subject       Triple's subject (source)
     * @param predicate     Triple's predicate (link type/Layers)
     * @return 
     */
    public Collection<Entity> getOutSet(Entity subject, Relation predicate) {      
        if (subject == null)
            return new HashSet<>(); //ERROR
        
        HashSet<Integer> taonta = new HashSet<>();
        Collection<Relation> rl;
        if (predicate ==null)
            rl = getRelations();
        else {
            rl = new LinkedList<>();
            rl.add(predicate);
        }
        
        for (Relation pred:rl) {
        	taonta.addAll(pred.getOutSet(subject.getIndex()));
        	
        }

        return Collections2.transform(taonta,  new Function<Integer, Entity>() {
			@Override
			public Entity apply(Integer arg0) {
				return elems.get(arg0);
			}
		});
    }
    
    /**
     * Returns the set of the Entities that reach the Object
     * @param object        Triple's object (destination)
     * @param predicate     Triple's predicate (link type/Layers)
     * @return 
     */
    public Collection<Entity> getInSet(Entity object, Relation predicate) {
        if (object == null)
            return new HashSet<>(); //ERROR
        
        HashSet<Integer> taonta = new HashSet<>();
        Collection<Relation> rl;
        if (predicate ==null)
            rl = getRelations();
        else {
            rl = new LinkedList<>();
            rl.add(predicate);
        }
        
        for (Relation pred:rl) {
        	taonta.addAll(pred.getInSet(object.getIndex()));
        	
        }

        return Collections2.transform(taonta,  new Function<Integer, Entity>() {
			@Override
			public Entity apply(Integer arg0) {
				return elems.get(arg0);
			}
		});
    }
    
    /**
     * Returns the set of the Entities that reach the Object
     * @param object        Triple's object (destination)
     * @param predicate     Triple's predicate (link type/Layers)
     * @return 
     */
    public SortedSet<ERTriple> getInTripleSet(Entity object, Relation predicate) {
        if (object == null)
            return new TreeSet<>(); //ERROR
        
        SortedSet<ERTriple> taonta = new TreeSet<>();
        Collection<Relation> rl;
        if (predicate ==null)
            rl = getRelations();
        else {
            rl = new LinkedList<>();
            rl.add(predicate);
        }
        
        for (Relation pred:rl) {
        	for (Integer x:pred.getInSet(object.getIndex())) {
        		taonta.add(new ERTriple(elems.get(x), pred, object));
        	}
        }
        return taonta;

    }
    
    public SortedSet<ERTriple> getOutTripleSet(Entity object, Relation predicate) {
        if (object == null)
            return new TreeSet<>(); //ERROR
        
        SortedSet<ERTriple> taonta = new TreeSet<>();
        Collection<Relation> rl;
        if (predicate ==null)
            rl = getRelations();
        else {
            rl = new LinkedList<>();
            rl.add(predicate);
        }
        
        for (Relation pred:rl) {
        	for (Integer x:pred.getOutSet(object.getIndex())) {
        		taonta.add(new ERTriple(object, pred,  elems.get(x)));
        	}
        }
        return taonta;

    }
    
    /*private void setNamespaceFromURI(String fullUri) throws MalformedURLException {
    	URL u = new URL(fullUri);
		mod.setNsPrefix( u.getHost().replaceAll("\\.", ""), fullUri.replaceAll(u.getRef(), "") );
    }*/
    
    
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/
    
    /**
     * Creates a new kind Relation, and hence a new layer
     * @param fullUri   New Properties' URI
     * @return 
     */
    public  Relation newLinkType(String fullUri) {
        Relation rela;
        if (fullUri==null)
            return null;
        if (!layers.containsKey(fullUri)) {
            //Property prop;
           /* try {
            	setNamespaceFromURI(fullUri);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}*/
            //prop = mod.createProperty(fullUri);
            rela = new Relation(fullUri/*,prop*/);
            layers.put(fullUri, rela);
        } else
            rela = layers.get(fullUri);
        return rela;
    }
    
    /**
     * Creates a new Layer by defining the URI of the new Relations that will lay on it
     * @param fullUri   Properties' URI that will be used on the new Layer
     * @return 
     */
    public IAdjacencyGraph<Entity> newLayer(String fullUri) {
        if (fullUri==null)
            return null;
        IAdjacencyGraph<Entity> g = getLayer(fullUri);
        if (g == null){
            newLinkType(fullUri);
            g = getLayer(fullUri);
        }
        return g;
    }
    
    /**
     * Creates a new Layer
     * @param fullUri   Properties' URI that will be used on the new Layer
     * @param val       Matrix of previous assertions
     * @return b
     */
    public IAdjacencyGraph<Entity> newLayer(String fullUri, IMatrix val) {
        IAdjacencyGraph<Entity> g;
        if (val==null) {
            if (fullUri == null)
                g = null;
            else 
                g = newLayer(fullUri);
        } else {
            g = getLayer(fullUri);
            if (g == null){
                newLinkType(fullUri); //Creates a new value
                PropertyGraph pg = new PropertyGraph(fullUri,this,val);
                g= (IAdjacencyGraph<Entity>)pg;
            }
        }
        return g;
    }
    
    /**
     * Provides the Relation from its full URI
     * @param name
     * @return 
     */
    public Relation getRelation(String name) {
        if (name == null)
            return null;
        if (layers.containsKey(name))
            return layers.get(name);
        else {
            //Empty Relation
            //Property p = ResourceFactory.createProperty(name);
            return new Relation(name/*p*/);
        }
    }
    
    
    
    /**
     * Returns the graph that represents the layer with the given name
     * @param name
     * @return 
     */
    public PropertyGraph getPropertyGraph(String name) {
        if (name==null)
            return null;
        return new PropertyGraph(name,this);
    }
    
    public PropertyGraph getPropertyGraph(Relation rel) {
         return getPropertyGraph(rel.getName());
    }
    
    public IAdjacencyGraph<Entity> getLayer(String name) {
        return getPropertyGraph(name);
    }
    
    public IMatrix getMatrix(String name) {
        return getPropertyGraph(name);
    }
    
    public IAdjacencyGraph<Entity> getLayer(Relation rel) {
        return getLayer(rel.getName());
    }
    
    public IMatrix getMatrix(Relation rel) {
        return getMatrix(rel.getName());
    }
    
    
    
    /**
     * Returns all the Layers' URIs
     * @return 
     */
    public Set<String> getLayerURIs() {
        return layers.keySet();
    }
    
    public Collection<Relation> getRelations() {
        return layers.values();
    }
    
    public Set<IAdjacencyGraph<Entity>> getLayers() {
        Set<IAdjacencyGraph<Entity>> lls = new HashSet<>();
        for (Relation x:layers.values()) {
            lls.add(new PropertyGraph(x.getName(), this));
        }
        return lls;
    }
    
    
    
        /**
     * Remove all the edges in all the layers from source to destination
     * @param left      Subject's URI (source)
     * @param right     Object's URI  (destination)
     */
    @Override
    public void removeEdge(String left, String right) {
        if (left==null || right == null)
            return;
        for (String x:layers.keySet()) { 
            getLayer(x).removeEdge(left, right);
        }
    }

    /**
     * Returns the mean of the weight of all the arcs from source to destination
     * @param left      Subject's URI (source)
     * @param right     Object's URI  (destination)
     * @return 
     */
    @Override
    public double getEdge(String left, String right) {
        if (left==null || right == null)
            return 0;
        double sz = layers.size();
        if (sz==0)
            return 0;
        double counting = 0;
        
        Entity el = getVertex(left);
        Entity er = getVertex(right);
        if (el==null || er== null)
            return 0;
        
        for (String x:layers.keySet()) {
            counting += layers.get(x).get(el.getIndex(), er.getIndex());
        }
        return (counting / sz);
    }
    
    
    public double getEdge(Entity src, Entity dst) {
        return getEdge(src.getFullUri(),dst.getFullUri());
    }
     
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/
    
    
    /**
     * Creates a new OWL relation
     * @param subject       Triple's subject (source)
     * @param predicate     Triple's predicate (link type/Layers)
     * @param object        Triple's object (destination)
     * @param val           matrix value (to update). If 0, it removes all the previous links of the same triple
     */
    public void addTriple(Entity subject, Relation predicate, Entity object, double val)  {
        if (val==0) {
            remTriple(subject,predicate,object);
        } else if (getTriple(subject,predicate,object)==0) {
        	//subject.toResource(mod).addProperty(predicate.getProperty(), (RDFNode)object.toResource(mod));
            //Statement statement = mod.createStatement(subject.toResource(mod),predicate.getProperty(),object.toResource(mod));
            //mod.add(statement);
            predicate.set(subject.getIndex(), object.getIndex(),val);
            layers.put(predicate.getName(), predicate); //IMPORTANT: update the map
        }
    }
    
    public boolean hasTriple(Entity subject, Relation predicate, Entity object) {
        return ( AbstractModelQuery.query(this, subject, predicate, object).size() > 0);
    }
    
    public boolean hasTriple(int subject, Relation predicate, int object) {
        Entity src = getVertex(subject);
        Entity dst = getVertex(object);
        if (src==null || dst==null) {
            return false;
        } else {
            return hasTriple(src,predicate,dst);
        }
    }
    
    /**
     * Returns the weight of the given triple's link
     * @param subject       Triple's subject (source)
     * @param predicate     Triple's predicate (link type/Layers)
     * @param object        Triple's object (destination)
     * @return 
     */
    public double getTriple(Entity subject, Relation predicate, Entity object) {
    	SortedSet<ERTriple> s = AbstractModelQuery.query(this, subject, predicate, object);
       
        if (s.isEmpty())
        	return 0;
        	
        double counting = 0;
        
        for (ERTriple x : s) {
            counting += predicate.get(x.getSource().getIndex(), x.getDestination().getIndex());
        }
        return (counting / s.size());
    }
    
    /**
     * Removes all the triple entries
     * @param subject       Triple's subject (source)
     * @param predicate     Triple's predicate (link type/Layers)
     * @param object        Triple's object (destination)
     */
    public void remTriple(Entity subject, Relation predicate, Entity object) {
        
    	SortedSet<ERTriple> s = AbstractModelQuery.query(this, subject, predicate, object);
    	for (ERTriple x : s) {
    		 PropertyGraph layer = getPropertyGraph(x.getRelation());
    		 layer.rem(x.getSource().getIndex(), x.getDestination().getIndex());
    	}

    }
   
    
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "MultiLayerGraph";
    }

    @Override
    public IGraph<Entity> rename(String new_name) {
        return this;
    }

    @Override @Deprecated
    public void setVertex(String name, Entity val) {
        throw new UnsupportedOperationException("Deprecated method.");
    }
    
    @Override @Deprecated
    public void addVertex(String name, Entity val) {
        throw new UnsupportedOperationException("Deprecated method.");
    }

    @Override @Deprecated
    public boolean addEdge(String left, String right, double value) {
        throw new UnsupportedOperationException("It has no sense to add an edge with a given value for every possible layer");
    }

    @Override @Deprecated
    public boolean addEdge(String left, String right) {
        throw new UnsupportedOperationException("It has no sense to add an edge with a given value for every possible layer."); //To change body of generated methods, choose Tools | Templates.
    }
    

    /**
     * Serializes the MultiLayerGraph as a string
     */
    /*private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();
        
        this.mod.write(s, "Turtle");
    }*/

    /**
     * Deserializes the MultiLayerGraph
     */
    /*private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        this.mod.read(s, (String)s.readObject());
        recheck();
    }*/
    
    public static MultiLayerGraph create(IMatrix m, List<String> idRows, List<String> idCols, String relUri) {
        if (m.nRows() != idRows.size())
            return null;
        if (m.nCols() != idCols.size())
            return null;
        MultiLayerGraph obj = new MultiLayerGraph();
        Relation r = obj.newLinkType(relUri);
        for (Cell<Integer, Integer, Double> x: m.getValueRange()) {
            String nRow = idRows.get((Integer)x.getRowKey());
            String nCol = idRows.get((Integer)x.getColumnKey());
            Entity src = obj.addVertex(nRow);
            Entity dst = obj.addVertex(nCol);
            obj.addTriple(src, r,dst,1);
        }
        return obj;
    }
    
    public static MultiLayerGraph create(Collection<ERTriple> ltriple) {
        MultiLayerGraph obj = new MultiLayerGraph();
        for (ERTriple x:ltriple) {
            Entity src = obj.addVertex(x.getSource().getFullUri());
            Entity dst = obj.addVertex(x.getDestination().getFullUri());
            Relation r = obj.newLinkType(x.getRelation().getName());
            try {
                obj.addTriple(src, r, dst, 1);
            } catch (Throwable ex) {
                Logger.getLogger(MultiLayerGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }

   
}
