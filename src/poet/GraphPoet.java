/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
* Redistribution of original or derived work requires permission of course staff.
*/
package poet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import graph.Graph;
public class GraphPoet {
   private final Graph<String> wordGraph;
   private final List<String> wordList;
  
   public GraphPoet(File textFile) throws IOException {
       wordList = extractWordsFromFile(textFile);
       wordGraph = buildWordGraph(wordList);
       checkRepresentation();
   }
  
   private void checkRepresentation() {
       assert wordGraph != null;
   }
   private List<String> extractWordsFromFile(File file) throws IOException {
       List<String> words = new ArrayList<>();
       try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
           while (scanner.hasNext()) {
               words.add(scanner.next().toLowerCase());
           }
       }
       assert words != Collections.EMPTY_LIST;
       return words;
   }
   private Graph<String> buildWordGraph(List<String> words){
       Graph<String> graph = Graph.empty();
      
       for (int i = 0; i < words.size(); i++) {
           String source = words.get(i);
           graph.add(source);
           if ((i + 1) >= words.size()) {
               break;
           }
           String target = words.get(i + 1);
           int prevCount = graph.set(source, target, 1);
           graph.set(source, target, prevCount + 1);
       }
       return graph;
   }
   public List<String> getWordsFromCorpus() {
       return Collections.unmodifiableList(wordList);
   }
   public String createPoem(String input) {
       String[] inputWords = input.split("\\s");
       StringBuilder poem = new StringBuilder(input);
       int fromIndex = 0;
      
       for (int i = 0; i < inputWords.length; i++) {
           if (i + 1 >= inputWords.length) {
               break;
           }
           Map<String, Integer> word1Targets = wordGraph.targets(inputWords[i].toLowerCase());
           Map<String, Integer> word2Sources = wordGraph.sources(inputWords[i+1].toLowerCase());
           Set<String> probableBridges = word1Targets.keySet();
          
           List<String> allBridges = probableBridges.stream()
                   .filter(possibleBridge -> word2Sources.containsKey(possibleBridge))
                   .collect(Collectors.toList());
          
           if (!allBridges.isEmpty()) {
               Random rand = new Random();
               int  n = rand.nextInt(allBridges.size());
               String bridge = allBridges.get(n);
               int insertAt = poem.indexOf(inputWords[i+1], fromIndex);
               poem.insert(insertAt, bridge + " ");
           }
       }
       checkRepresentation();
       return poem.toString();
   }
  
   @Override
   public String toString() {
       return wordGraph.toString();
   }
}

