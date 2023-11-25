/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph using vertices.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Each Vertex in 'vertices' represents a vertex in the graph.
    //   Edges between vertices are represented by the adjacency list of each Vertex.
    
    // Representation invariant:
    //   Vertices in 'vertices' must have unique labels.
    //   'vertices' should not contain null.
    //   The adjacency list of each Vertex should not contain null labels.
    
    // Safety from rep exposure:
    //   'vertices' is private and final.
    //   'Vertex' is a private class, and its internal representation is not exposed.
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // Check representation invariant
    private void checkRep() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            if (vertexLabels.contains(vertex.getLabel()) || vertex.getLabel() == null) {
                throw new RuntimeException("Invalid representation");
            }
            vertexLabels.add(vertex.getLabel());
            vertex.checkRep();
        }
    }
    
    @Override
    public boolean add(String vertex) {
        Vertex newVertex = new Vertex(vertex);
        if (!vertices.contains(newVertex)) {
            vertices.add(newVertex);
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findVertex(source);
        Vertex targetVertex = findVertex(target);

        if (sourceVertex != null && targetVertex != null) {
            int previousWeight = sourceVertex.setTarget(target, weight);
            checkRep();
            return previousWeight;
        } else {
            return -1;  // Return -1 for both new edges and edges with null vertices
        }
    }



    @Override
    public boolean remove(String vertex) {
        Vertex removedVertex = findVertex(vertex);
        if (removedVertex != null) {
            vertices.remove(removedVertex);
            checkRep();

            // Remove the vertex from the targets of other vertices
            for (Vertex v : vertices) {
                v.removeTarget(vertex);
            }

            checkRep();
            return true;
        }
        return false;
    }




    @Override
    public Set<String> vertices() {
        Set<String> vertexSet = new HashSet<>();
        for (Vertex v : vertices) {
            vertexSet.add(v.getLabel());
        }
        return vertexSet;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sourceMap = new HashMap<>();
        for (Vertex v : vertices) {
            int weight = v.getWeight(target);
            if (weight != -1) {
                sourceMap.put(v.getLabel(), weight);
            }
        }
        return sourceMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Vertex sourceVertex = findVertex(source);
        if (sourceVertex != null) {
            return sourceVertex.getTargets();
        }
        return Collections.emptyMap();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Find a vertex in the list of vertices by its label.
     *
     * @param label The label of the vertex to find.
     * @return The vertex with the given label, or null if not found.
     */
    private Vertex findVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }
}

/**
 * Represents a vertex in the graph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is up to you.
 */
class Vertex {
    
    private final String label;
    private final Map<String, Integer> adjacencyList; // Represents the edges and weights
    
    // Abstraction function:
    //   Each Vertex represents a vertex in the graph with a unique label.
    //   'adjacencyList' represents the edges going from this vertex to other vertices,
    //   where the key is the target vertex label and the value is the weight of the edge.
    
    // Representation invariant:
    //   'label' should not be null.
    //   'adjacencyList' should not contain null labels.
    
    // Safety from rep exposure:
    //   'label' and 'adjacencyList' are private and final.
    //   'adjacencyList' is immutable as it is declared final.
    
    
    // Constructor
    public Vertex(String label) {
        this.label = label;
        this.adjacencyList = new HashMap<>();
        checkRep();
    }
    
    // Check representation invariant
    public void checkRep() {
        if (label == null) {
            throw new RuntimeException("Vertex label cannot be null");
        }
        if (adjacencyList.containsKey(null)) {
            throw new RuntimeException("Adjacency list contains null labels");
        }
    }
    
    /**
     * Get the label of the vertex.
     *
     * @return The label of the vertex.
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Add or update a target vertex with a given weight.
     *
     * @param target The label of the target vertex.
     * @param weight The weight of the edge.
     * @return The previous weight of the edge, or -1 if the edge is new.
     */
    public int setTarget(String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be non-negative");
        }
        
        // Check if the target is in the adjacency list
        if (adjacencyList.containsKey(target)) {
            int previousWeight = adjacencyList.get(target);
            adjacencyList.put(target, weight);
            checkRep();
            return previousWeight;  // Return the previous weight
        } else {
            // If the target is not in the adjacency list, add it
            adjacencyList.put(target, weight);
            checkRep();
            return -1;  // Return -1 for a new edge
        }
    }





    
    /**
     * Remove the target vertex from the adjacency list.
     *
     * @param target The label of the target vertex to remove.
     */
    public void removeTarget(String target) {
        System.out.println("Removing target: " + target);
        adjacencyList.remove(target);
        checkRep();
    }

    
    /**
     * Remove all targets from the adjacency list.
     */
    public void removeTargets() {
        adjacencyList.clear();
        checkRep();
    }
    
    /**
     * Get the weight of the edge to the target vertex.
     *
     * @param target The label of the target vertex.
     * @return The weight of the edge, or -1 if the edge does not exist.
     */
    public int getWeight(String target) {
        return adjacencyList.getOrDefault(target, -1);
    }
    
    /**
     * Get a map of target vertices and their weights.
     *
     * @return The map of target vertices and their weights.
     */
    public Map<String, Integer> getTargets() {
        return Collections.unmodifiableMap(adjacencyList);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex ").append(label).append(": ");
        for (Map.Entry<String, Integer> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" (").append(entry.getValue()).append("), ");
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return label.equals(vertex.label);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 8fa5b2da0939ba296f31ed25e2cda54623366e31
