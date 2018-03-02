import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Digraph
{
    // Necessary fields for graph
    private HashMap<String, Integer> cityIndex;
    private HashMap<String, String> cityName;
    private HashMap<Integer, String> cityInitial;
    private int[][] graph; // size 21 (0-20, 0 blank), graph[source][target]
    private String[] cityData; // size 21 (0-20, 0 blank)

    // Nodes for Dijkstra's Algorithm
    private class DNode
    {
        private String cityCode;
        private Double distance;

        public DNode(String code, Double dist)
        {
            cityCode = code;
            distance = dist;
        }

        // Getters
        public String getCityCode() { return cityCode; }
        public Double getDistance() { return distance; }

        // Setters
        public void setDistance(Double dist) { distance = dist; }
    }

    public Digraph(String cityFile, String roadFile)
    {
        graph = new int[21][21];
        cityIndex = new HashMap<String, Integer>();
        cityName = new HashMap<String, String>();
        cityInitial = new HashMap<Integer, String>();
        cityData = new String[21];

        // initialize graph with no directed roads
        for (int i = 0; i < 21; ++i)
        {
            for (int j = 0; j < 21; ++j)
                graph[i][j] = 0;
        }

        loadCityDataFrom(cityFile);
        loadRoadDataFrom(roadFile);
    }

    public void insertRoad(String from, String to, String dist)
    {
        if (cityIndex.get(from) == null)
        {
            System.out.println("From city doesn't exist");
            return;
        }
        else if (cityIndex.get(to) == null)
        {
            System.out.println("To city doesn't exist");
            return;
        }
        else if (Integer.parseInt(dist) < 1)
        {
            System.out.println("Invalid distance value");
            return;
        }

        int fromIndex = cityIndex.get(from);
        int toIndex = cityIndex.get(to);
        int distance = Integer.parseInt(dist);

        if (graph[fromIndex][toIndex] > 0)
            System.out.println("The road from " + cityName.get(from) + " to " +
                                cityName.get(to) + " already exists");
        else
        {
            graph[fromIndex][toIndex] = distance;
            System.out.println("You have inserted a road from " + cityName.get(from)
                                + " to " + cityName.get(to) + " with a distance of "
                                + dist);
        }
    }

    public void removeRoad(String from, String to)
    {
        if (cityIndex.get(from) == null)
        {
            System.out.println("From city doesn't exist");
            return;
        }
        else if (cityIndex.get(to) == null)
        {
            System.out.println("To city doesn't exist");
            return;
        }

        int fromIndex = cityIndex.get(from);
        int toIndex = cityIndex.get(to);

        if (graph[fromIndex][toIndex] == 0)
            System.out.println("The road from " + cityName.get(from) + " to " +
                                cityName.get(to) + " does not exist");
        else
        {
            graph[fromIndex][toIndex] = 0;
            System.out.println("You have removed the road from " + cityName.get(from)
                                + " to " + cityName.get(to));
        }
    }

    public void query(String code)
    {
        if (cityIndex.get(code) != null)
            System.out.println(cityData[cityIndex.get(code)]);
        else
            System.out.println("City code does not exist");
    }

    public void shortestPath(String from, String to)
    {
        if (cityIndex.get(from) == null)
        {
            System.out.println("From city doesn't exist");
            return;
        }
        else if (cityIndex.get(to) == null)
        {
            System.out.println("To city doesn't exist");
            return;
        }

        double[] dist = new double[21];
        int[] prev = new int[21];
        List<DNode> Q = new ArrayList<DNode>(); // vertex queue
        List<Integer> visited = new ArrayList<Integer>();
        boolean reachedDestination = false;
        String route = to;

        Q.add(new DNode(from, 0.0));

        for (int v = 1; v <= 20; ++v)
        {
            if (v != cityIndex.get(from))
            {
                dist[v] = Double.POSITIVE_INFINITY;
                prev[v] = 0;
            }
        }

        while (Q.size() > 0)
        {
            DNode u = null; // temporary source node for checking queue items
            double alt = 0.0; // getting distance between two vertices
            u = Q.remove(0);

            if (visited.contains(cityIndex.get(u.getCityCode())))
                continue;
            else if (reachedDestination)
                break;

            for (DNode v : neighbors(cityIndex.get(u.getCityCode())))
            {
                int ui = cityIndex.get(u.getCityCode());
                int vi = cityIndex.get(v.getCityCode());

                alt = (dist[ui] + graph[ui][vi]);
                if (alt < dist[vi])
                {
                    dist[vi] = alt;
                    prev[vi] = ui;
                }

                if (v.getCityCode().equals(to))
                    reachedDestination = true;
                else
                    Q.add(v);
            }

            visited.add(cityIndex.get(u.getCityCode()));
        }

        int current = cityIndex.get(to);
        while (prev[current] > 0)
        {
            current = prev[current];
            if (current > 0)
                route = (cityInitial.get(current) + ", " + route);
        }

        System.out.println("The minimum distance between " + cityName.get(from) +
            " and " + cityName.get(to) + " is " + (int)dist[cityIndex.get(to)] +
            " through the route: " + route);
    }

    // BEGIN PRIVATE METHODS

    private void loadCityDataFrom(String file)
    {
        try
        {
            File f = new File(file);
            Scanner reader = new Scanner(f);
            String[] line;

            while (reader.hasNextLine())
            {
                line = reader.nextLine().trim().split("\\s+");
                cityIndex.put(line[1], Integer.parseInt(line[0]));
                cityInitial.put(Integer.parseInt(line[0]), line[1]);
                cityName.put(line[1], String.join(" ", line[2].split("_")));
                cityData[Integer.parseInt(line[0])] = String.join(" ", line);
            }

            reader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void loadRoadDataFrom(String file)
    {
        try
        {
            File f = new File(file);
            Scanner reader = new Scanner(f);
            String[] line;
            int[] lineData = new int[3];

            while (reader.hasNextLine())
            {
                line = reader.nextLine().trim().split("\\s+");
                for (int i = 0; i < line.length; ++i)
                    lineData[i] = Integer.parseInt(line[i]);

                graph[lineData[0]][lineData[1]] = lineData[2];
            }

            reader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private List<DNode> neighbors(int vertex)
    {
        List<DNode> result = new ArrayList<DNode>();

        for (int i = 1; i <= 20; ++i)
        {
            if (graph[vertex][i] > 0)
                result.add(new DNode(cityInitial.get(i), (double)graph[vertex][i]));
        }

        result.sort(Comparator.comparing(n -> n.getDistance()));
        return result;
    }
}
