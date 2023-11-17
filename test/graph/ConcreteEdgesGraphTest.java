// ConcreteEdgesGraphTest.java
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    @Test
    public void testConcreteEdgesGraphAdd() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        assertTrue(graph.add("A"));
        assertTrue(graph.add("B"));

        // Adding the same vertex should return false
        assertFalse(graph.add("A"));

        assertEquals(Set.of("A", "B"), graph.vertices());
    }
    
    @Test
    public void testConcreteEdgesGraphSet() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");

        assertEquals(0, graph.set("A", "B", 5));

        // Non-existing vertices should throw an exception
        try {
            graph.set("C", "D", 2);
            fail("Expected IllegalArgumentException for non-existing vertices");
        } catch (IllegalArgumentException e) {
            // expected
        }

        assertEquals(5, graph.set("A", "B", 10));

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(Map.of("A", 10), sources);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(Map.of("B", 10), targets);
    }
    
    @Test
    public void testConcreteEdgesGraphRemove() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 5);
        graph.set("A", "C", 8);

        assertTrue(graph.remove("A"));
        assertFalse(graph.remove("D")); // Removing non-existing vertex should return false

        assertEquals(Set.of("B", "C"), graph.vertices());

        // Check that there are no sources or targets for the removed vertex A
        assertTrue(graph.sources("B").isEmpty());
        assertTrue(graph.targets("C").isEmpty());
    }

    
    @Test
    public void testConcreteEdgesGraphVertices() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        assertEquals(Set.of("A", "B", "C"), graph.vertices());
    }
    
    @Test
    public void testConcreteEdgesGraphSources() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 5);
        graph.set("C", "B", 3);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(Map.of("A", 5, "C", 3), sources);
    }
    
    @Test
    public void testConcreteEdgesGraphTargets() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 5);
        graph.set("A", "C", 8);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(Map.of("B", 5, "C", 8), targets);
    }
    
    @Test
    public void testConcreteEdgesGraphToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();

        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 5);
        graph.set("A", "C", 8);

        assertEquals("Vertices: [A, B, C]\nEdges: [A -> B : 5, A -> C : 8]\n", graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A -> B : 5", edge.toString());
    }
    
    @Test
    public void testEdgeGetters() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }
    
    @Test
    public void testEdgeSetWeight() {
        Edge edge = new Edge("A", "B", 5);
        edge.setWeight(10);
        assertEquals(10, edge.getWeight());
    }
}
