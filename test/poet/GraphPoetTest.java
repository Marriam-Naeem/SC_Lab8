/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
* Redistribution of original or derived work requires permission of course staff.
*/
package poet;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
public class GraphPoetTest {
   @Test(expected=AssertionError.class)
   public void testAsserts() {
       assert false;
   }
   private static final GraphPoet createGraph(String source) {
       try {
           final File corpusFile = new File(source);
           GraphPoet graphPoet = new GraphPoet(corpusFile);
           return graphPoet;
       } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
       }
   }
   final GraphPoet singleWordGraph = createGraph("test/poet/SingleWord.txt");
   final GraphPoet oneLineGraph = createGraph("test/poet/OneLine.txt");
   final GraphPoet multiLineGraph = createGraph("test/poet/MultiLine.txt");
   @Test
   public void testSingleWordGraph() {       
       List<String> words = singleWordGraph.getWordsFromCorpus();
      
       assertEquals("Expected one word in corpus",
               1, words.size());
       assertTrue("Expected the word in lowercase",
               words.contains("example!"));
   }
   @Test
   public void testOneLineGraph() {
       List<String> words = oneLineGraph.getWordsFromCorpus();
      
       assertEquals("Expected all words in corpus",
               13, words.size());
       assertTrue("Expected words in lowercase",
               words.contains("to"));
   }
   @Test
   public void testMultiLineGraph() {
       List<String> words = multiLineGraph.getWordsFromCorpus();
      
       assertNotEquals("Expected non-empty list", Collections.EMPTY_LIST, words);
       assertTrue("Expected words in lowercase",
               words.contains("don't"));
       assertTrue("Expected words in lowercase",
               words.contains("sample"));
   }
   @Test
   public void testPoemSingleWord() {
       String input = "Java";
       String output = multiLineGraph.createPoem(input);
      
       assertEquals("Expected unchanged input", input, output);
   }
   @Test
   public void testPoemMultipleWords() {
       String input = "Create beautiful and meaningful code!";
       String output = oneLineGraph.createPoem(input);
       String expected = "Create beautiful new life and meaningful code!";
      
       assertEquals("Expected poetic output with words in input unchanged",
               expected, output);
   }
   @Test
   public void testPoemMultipleAdjacencies() {
       String input = "you CAN do";
       String output = multiLineGraph.createPoem(input);
      
       assertNotEquals("Expected a bridge word inserted",
               input, output);
       assertTrue("Expected input words unchanged",
               output.contains("you") && output.contains("CAN"));
       assertTrue("Expected correct bridge word",
               output.contains("write")
               || output.contains("walk")
               || output.contains("build")
               || output.contains("cut")
               || output.contains("jump"));
   }
}

