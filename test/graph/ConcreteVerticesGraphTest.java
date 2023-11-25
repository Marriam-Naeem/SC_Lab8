package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
	
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    @Test
    public void testAddVertices() {
        Graph<String> graph = emptyInstance();
        
        assertTrue(graph.add("A"));
        assertTrue(graph.add("B"));
        assertFalse(graph.add("A")); // Adding the same vertex again should return false
        
        Set<String> vertices = graph.vertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    @Test
    public void testVertexMethods() {
        Vertex vertex = new Vertex("A");

        assertEquals("A", vertex.getLabel());
        assertEquals(-1, vertex.setTarget("B", 3));  // Set edge to B with weight 3

        assertEquals(3, vertex.setTarget("B", 3));  // Update edge to B with weight 3
        assertEquals(-1, vertex.setTarget("C", 5));  // Set edge to C with weight 5

        assertEquals(3, vertex.getWeight("B")); // Get weight of edge to B
        assertEquals(5, vertex.getWeight("C")); // Get weight of edge to C
        assertEquals(-1, vertex.getWeight("D")); // Edge to D does not exist

        Map<String, Integer> targets = vertex.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.containsKey("B"));
        assertTrue(targets.containsKey("C"));

        // Correct the assertion here
        assertEquals(-1, vertex.setTarget("D", 4));  // Update edge to D with weight 4
    }
    @Test
    public void testSetEdges() {
        Graph<String> graph = emptyInstance();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        assertEquals(-1, graph.set("A", "B", 5)); // Edge does not exist, returns -1
        assertEquals(-1, graph.set("A", "C", 3)); // Edge does not exist, returns -1

        assertEquals(5, graph.set("A", "B", 5)); // Update edge from A to B, returns previous weight

        assertEquals(3, graph.set("A", "C", 3)); // Set edge from A to C with weight 3

        assertEquals(5, graph.set("A", "B", 2)); // Update edge from A to B with weight 2 (returns previous weight)

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(1, sources.size());
        assertEquals(2, sources.get("A").intValue());
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 2);
        graph.set("A", "C", 3);
        graph.set("B", "C", 1);

        assertTrue(graph.remove("A")); // Remove vertex A

        Set<String> vertices = graph.vertices();
        assertEquals(2, vertices.size());
        assertFalse(vertices.contains("A"));

        graph.set("B", "C", 4);

        Map<String, Integer> sourcesC = graph.sources("C");
        assertEquals(1, sourcesC.size());
        assertEquals(4, sourcesC.get("B").intValue());
    }

    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        
        graph.add("A");
        graph.add("B");
        
        graph.set("A", "B", 2);
        graph.set("B", "A", 3);
        
        String expected = "Vertex A: B (2), \n" +
                          "Vertex B: A (3), \n";
        
        assertEquals(expected, graph.toString());
    }
    
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        
        assertEquals("", graph.toString());
    }
    
    @Test
    public void testToStringSingleVertex() {
        Graph<String> graph = emptyInstance();
        
        graph.add("A");
        
        String expected = "Vertex A: \n";
        
        assertEquals(expected, graph.toString());
    }
    
    @Test
    public void testToStringMultipleEdges() {
        Graph<String> graph = emptyInstance();
        
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        graph.set("A", "B", 2);
        graph.set("A", "C", 3);
        graph.set("B", "C", 1);
        
        String expected = "Vertex A: B (2), C (3), \n" +
                          "Vertex B: C (1), \n" +
                          "Vertex C: \n";
        
        assertEquals(expected, graph.toString());
    }
}
