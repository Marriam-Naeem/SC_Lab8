import static org.junit.Assert.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
/**
* Tests for instance methods of Graph.
*
* <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
* methods to this class, or change the spec of {@link #emptyInstance()}.
* Your tests MUST only obtain Graph instances by calling emptyInstance().
* Your tests MUST NOT refer to specific concrete implementations.
* 
* @laiba_abdullah
*/
public abstract class GraphInstanceTest {
   /**
    * Overridden by implementation-specific test classes.
    *
    * @return a new empty graph of the particular implementation being tested
    */
   public abstract Graph<String> emptyInstance();
   @Test(expected = AssertionError.class)
   public void testAssertionsEnabled() {
       assert false; // make sure assertions are enabled with VM argument: -ea
   }
   @Test
   public void testInitialVerticesEmpty() {
       assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
   }
   // Testing strategy for vertices():
   // - Add vertices to an empty graph and check if vertices() returns the correct set.
   // - Add vertices to a non-empty graph and check if vertices() returns the correct set.
   @Test
   public void testVertices() {
       Graph<String> graph = emptyInstance();
       Set<String> expectedVertices = new HashSet<>();
       // Add vertices to an empty graph
       graph.add("A");
       expectedVertices.add("A");
       assertEquals(expectedVertices, graph.vertices());
       // Add vertices to a non-empty graph
       graph.add("B");
       expectedVertices.add("B");
       assertEquals(expectedVertices, graph.vertices());
   }
   // Testing strategy for sources(String target):
   // - Add edges between vertices and check if sources() returns the correct set of sources.
   // - Add self-loop edges and check if sources() includes the vertex itself.
   @Test
   public void testSources() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Test sources for "B"
       Set<String> expectedSourcesB = new HashSet<>();
       expectedSourcesB.add("A");
       expectedSourcesB.add("C");
       assertEquals(expectedSourcesB, graph.sources("B"));
       // Test sources for "A" (no incoming edges)
       assertEquals(Collections.emptySet(), graph.sources("A"));
       // Test sources for non-existing vertex
       assertEquals(Collections.emptySet(), graph.sources("D"));
       // Add a self-loop edge
       graph.set("A", "A", 1);
       // Test sources for "A" (including the vertex itself)
       expectedSourcesB.add("A");
       assertEquals(expectedSourcesB, graph.sources("A"));
   }
   // Testing strategy for set(source, target, weight):
   // - Add edges between vertices and check if set() correctly adds or updates the edge weight.
   // - Add self-loop edges and check if set() correctly adds or updates the self-loop edge weight.
   @Test
   public void testSet() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Test edge weights
       assertEquals(2, graph.set("A", "B", 5)); // Update weight
       assertEquals(3, graph.set("C", "B", 7)); // Update weight
       assertEquals(0, graph.set("A", "C", 1)); // Add new edge
       // Test self-loop edge
       assertEquals(0, graph.set("A", "A", 10)); // Add self-loop edge
       assertEquals(10, graph.set("A", "A", 15)); // Update self-loop edge
   }
   // Testing strategy for remove(String vertex):
   // - Remove vertices with and without edges and check if the removal is successful.
   // - Remove non-existing vertices and check if it has no effect on the graph.
   @Test
   public void testRemoveVertex() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Remove vertices
       assertTrue(graph.remove("A"));
       assertEquals(1, graph.vertices().size());
       assertFalse(graph.remove("D")); // Non-existing vertex
       // Add a self-loop edge
       graph.set("B", "B", 5);
       // Remove vertex with self-loop edge
       assertTrue(graph.remove("B"));
       assertEquals(1, graph.vertices().size());
   }
   // Testing strategy for targets(String source):
   // - Add edges between vertices and check if targets() returns the correct set of targets.
   // - Add self-loop edges and check if targets() includes the vertex itself.
   @Test
   public void testTargets() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Test targets for "A"
       Set<String> expectedTargetsA = new HashSet<>();
       expectedTargetsA.add("B");
       assertEquals(expectedTargetsA, graph.targets("A"));
       // Test targets for "B"
       assertEquals(Collections.emptySet(), graph.targets("B")); // No outgoing edges
       // Test targets for non-existing vertex
       assertEquals(Collections.emptySet(), graph.targets("D"));
       // Add a self-loop edge
       graph.set("A", "A", 1);
       // Test targets for "A" (including the vertex itself)
       expectedTargetsA.add("A");
       assertEquals(expectedTargetsA, graph.targets("A"));
   }
   // Testing strategy for remove(String source, String target):
   // - Remove edges between vertices and check if remove() correctly removes the edge.
   // - Remove non-existing edges and check if it has no effect on the graph.
   // - Remove self-loop edges.
   @Test
   public void testRemoveEdge() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Test remove edge
       assertTrue(graph.remove("A", "B"));
       assertFalse(graph.remove("D", "C")); // Non-existing edge
       // Add a self-loop edge
       graph.set("A", "A", 1);
       // Test remove self-loop edge
       assertTrue(graph.remove("A", "A"));
       assertFalse(graph.remove("B", "B")); // Non-existing self-loop edge
   }
   // Testing strategy for vertices():
   // - Add vertices to an empty graph and check if vertices() returns the correct set.
   // - Add vertices to a non-empty graph and check if vertices() returns the correct set.
   @Test
   public void testVerticesAfterRemove() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Remove vertices
       graph.remove("A");
       graph.remove("D"); // Non-existing vertex
       // Test vertices after removal
       Set<String> expectedVertices = new HashSet<>();
       expectedVertices.add("B");
       expectedVertices.add("C");
       assertEquals(expectedVertices, graph.vertices());
   }
   // Testing strategy for vertices():
   // - Add vertices to an empty graph and check if vertices() returns the correct set.
   // - Add vertices to a non-empty graph and check if vertices() returns the correct set.
   @Test
   public void testVerticesAfterRemoveEdge() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       // Add edges between vertices
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       // Remove edges
       graph.remove("A", "B");
       graph.remove("D", "C"); // Non-existing edge
       // Test vertices after removal of edges
       Set<String> expectedVertices = new HashSet<>();
       expectedVertices.add("A");
       expectedVertices.add("B");
       expectedVertices.add("C");
       assertEquals(expectedVertices, graph.vertices());
   }
   // Additional test for toString()
   // Testing strategy:
   // - Create a graph with vertices and edges.
   // - Check if the toString() representation is as expected.
   @Test
   public void testToString() {
       Graph<String> graph = emptyInstance();
       graph.add("A");
       graph.add("B");
       graph.add("C");
       graph.set("A", "B", 2);
       graph.set("C", "B", 3);
       graph.set("A", "A", 1);
       String expectedToString = "Graph with vertices: [A, B, C], edges: [(A, B, 2), (C, B, 3), (A, A, 1)]";
       assertEquals(expectedToString, graph.toString());
   }
}
