import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class Digraph
{
    // Necessary fields for graph
    private HashMap<String, Integer> cityIndex;
    private HashMap<String, String> cityName;
    private int[][] graph; // size 21 (0-20, 0 blank), graph[source][target]
    private String[] cityData; // size 21 (0-20, 0 blank)

    public Digraph(String cityFile, String roadFile)
    {
        graph = new int[21][21];
        cityIndex = new HashMap<String, Integer>();
        cityName = new HashMap<String, String>();
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
            System.out.println("The road from " + cityName[from] + " to " +
                                cityName[to] + " already exists");
        else
        {
            graph[fromIndex][toIndex] = distance;
            System.out.println("You have inserted a road from " + cityName[from]
                                + " to " cityName[to] + " with a distance of "
                                + dist);
        }
    }

    public void query(String code)
    {
        if (cityIndex.get(code) != null)
            System.out.println(cityData[cityIndex.get(code)]);
        else
            System.out.println("City code does not exist");
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
}
