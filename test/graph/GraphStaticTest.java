import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 * 
 * <p>PS2 instructions: You MUST NOT add constructors, fields, or non-@Test
 * methods to this class or change the spec of Graph.empty(). Your tests MUST
 * only refer to the Graph interface. You MUST NOT refer to specific concrete
 * implementations.
 * 
 * <p>The testing strategy for the static Graph.empty() method is outlined in
 * the comments above the corresponding test case. The test cases aim to verify
 * the correctness of the empty() method and its behavior.
 * 
 * @laiba_abdullah
 */
public class GraphStaticTest {

    /**
     * Ensures that assertions are enabled in the testing environment.
     * 
     * @throws AssertionError if assertions are not enabled
     */
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /**
     * Tests that the vertices() method of the graph returned by Graph.empty() is
     * empty.
     * 
     * <p>Testing Strategy:
     * - Call Graph.empty() to obtain an empty graph.
     * - Check if the vertices() method returns an empty set.
     */
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }

    /**
     * Tests that the empty() method returns a new instance of the Graph interface.
     * 
     * <p>Testing Strategy:
     * - Call Graph.empty() multiple times.
     * - Check if the returned instances are not the same object reference.
     */
    @Test
    public void testEmptyReturnsNewInstance() {
        Graph<String> graph1 = Graph.empty();
        Graph<String> graph2 = Graph.empty();

        assertNotSame("expected empty() to return new instances", graph1, graph2);
    }

    // Additional test cases for other vertex label types in Problem 3.2:
    // - Add tests for vertex label types other than String, if applicable.

}
