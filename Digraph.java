import java.util.HashMap;

public class Digraph
{
    // Necessary fields for graph
    private HashMap<String, Integer> cityIndex;
    private int[][] graph; // size 21 (0-20, 0 blank), graph[source][target]
    private String[] cityData; // size 21 (0-20, 0 blank)
}
