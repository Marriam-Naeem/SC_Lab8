// ConcreteEdgesGraph.java
package graph;

import java.util.*;

/**
 * An implementation of Graph using concrete edges.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a directed graph with weighted edges where vertices are
    //   stored in 'vertices' and edges are stored in 'edges'.
    // Representation invariant:
    //   - Vertices in 'edges' must be present in 'vertices'.
    //   - Each edge must have a non-negative weight.
    // Safety from rep exposure:
    //   - All fields are private and final.
    //   - 'vertices' is immutable (HashSet).
    //   - 'edges' is immutable (ArrayList).
    
    /**
     * Constructs an empty concrete edges graph.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    /**
     * Check the representation invariant.
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
            assert edge.getWeight() >= 0;
        }
    }
    
    /**
     * @see Graph#add(Object)
     */
    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }
    
    /**
     * @see Graph#set(Object, Object, int)
     */
    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source) || !vertices.contains(target)) {
            throw new IllegalArgumentException("Source or target vertex does not exist");
        }

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int prevWeight = edge.getWeight();
                edge.setWeight(weight);
                checkRep();
                return prevWeight;
            }
        }

        Edge newEdge = new Edge(source, target, weight);
        edges.add(newEdge);
        checkRep();
        return 0;
    }
    
    /**
     * @see Graph#remove(Object)
     */
    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        // Remove edges connected to the vertex
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        
        vertices.remove(vertex);
        checkRep();
        return true;
    }
    
    /**
     * @see Graph#vertices()
     */
    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }
    
    /**
     * @see Graph#sources(Object)
     */
    @Override
    public Map<String, Integer> sources(String target) {
        if (!vertices.contains(target)) {
            throw new IllegalArgumentException("Target vertex does not exist");
        }

        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    
    /**
     * @see Graph#targets(Object)
     */
    @Override
    public Map<String, Integer> targets(String source) {
        if (!vertices.contains(source)) {
            throw new IllegalArgumentException("Source vertex does not exist");
        }

        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    
    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Vertices: ").append(vertices).append("\n");
        result.append("Edges: ").append(edges).append("\n");
        return result.toString();
    }
}

/**
 * Represents an immutable directed edge with a weight.
 * 
 * <p>PS2 instructions: the specification and implementation of this class are up to you.
 */
class Edge {
    
    private final String source;
    private final String target;
    private int weight;
    
    // Abstraction function:
    //   Represents a directed edge from 'source' to 'target' with weight 'weight'.
    // Representation invariant:
    //   - 'source' and 'target' are non-null strings.
    //   - 'weight' is a non-negative integer.
    // Safety from rep exposure:
    //   - All fields are private and final.
    //   - Strings are immutable.
    //   - Integer is immutable.
    
    /**
     * Constructs an immutable directed edge.
     *
     * @param source The source vertex.
     * @param target The target vertex.
     * @param weight The weight of the edge.
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    /**
     * Check the representation invariant.
     */
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight >= 0;
    }
    
    /**
     * Get the source vertex of the edge.
     *
     * @return The source vertex.
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the target vertex of the edge.
     *
     * @return The target vertex.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Get the weight of the edge.
     *
     * @return The weight of the edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the weight of the edge.
     *
     * @param weight The new weight for the edge.
     */
    public void setWeight(int weight) {
        this.weight = weight;
        checkRep();
    }
    
    /**
     * Convert the edge to a human-readable string.
     *
     * @return A string representation of the edge.
     */
    @Override
    public String toString() {
        return source + " -> " + target + " : " + weight;
    }
}
