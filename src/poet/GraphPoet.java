package poet;

import graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());

        // Process lines to build the word affinity graph
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                String currentWord = words[i].toLowerCase();
                String nextWord = words[i + 1].toLowerCase();
                graph.add(currentWord);
                graph.add(nextWord);
                int weight = graph.set(currentWord, nextWord, graph.targets(currentWord).getOrDefault(nextWord, 0) + 1);
                if (weight == 0) {
                    // Increment the weight of the existing edge
                    graph.set(currentWord, nextWord, weight + 1);
                }
            }
        }
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputWords.length - 1; i++) {
            String currentWord = inputWords[i].toLowerCase();
            String nextWord = inputWords[i + 1].toLowerCase();
            result.append(currentWord).append(" ");
            String bridgeWord = findBridgeWord(currentWord, nextWord);
            result.append(bridgeWord).append(" ");
        }
        result.append(inputWords[inputWords.length - 1]);

        return result.toString();
    }

    private String findBridgeWord(String currentWord, String nextWord) {
        Map<String, Integer> targets = graph.targets(currentWord);
        Set<String> commonTargets = targets.keySet();
        commonTargets.retainAll(graph.sources(nextWord).keySet());

        if (!commonTargets.isEmpty()) {
            int maxWeight = 0;
            String bridgeWord = "";
            for (String commonTarget : commonTargets) {
                int weight = targets.get(commonTarget);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    bridgeWord = commonTarget;
                }
            }
            return bridgeWord;
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return graph.toString();
    }
}