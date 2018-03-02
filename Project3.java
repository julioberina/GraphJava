import java.util.Scanner;

public class Project3
{
    private static Scanner scan;
    private static Digraph dgraph;

    public static void main(String[] args)
    {
        dgraph = new Digraph("city.dat", "road.dat");
        boolean done = false;
        scan = new Scanner(System.in);

        while (done == false)
        {
            done = menuCommandLoop();
            System.out.print("\n");
        }
    }

    private static void displayListOfCommands()
    {
        System.out.println("D Find the minimum distance between two cities");
        System.out.println("I Insert a road by entering two city codes and distance");
        System.out.println("R Remove an existing road by entering two city codes");
        System.out.println("H Display this message");
        System.out.println("E Exit");
    }

    private static boolean menuCommandLoop()
    {
        String command = "";
        String cityCode = "";
        int distance = 0;

        System.out.print("Command?  ");
        command = scan.nextLine();

        if (command.equalsIgnoreCase("Q"))
        {
            System.out.print("City code:  ");
            cityCode = scan.nextLine().toUpperCase();
            dgraph.query(cityCode);
            return false;
        }
        else if (command.equalsIgnoreCase("D"))
        {
            System.out.print("City codes:  ");
            String[] dijkstraInput = scan.nextLine().trim().split("\\s+");
            dgraph.shortestPath(dijkstraInput[0], dijkstraInput[1]);
            return false;
        }
        else if (command.equalsIgnoreCase("I"))
        {
            System.out.print("City codes and distance:  ");
            String[] insertInput = scan.nextLine().trim().split("\\s+");
            dgraph.insertRoad(insertInput[0].toUpperCase(), insertInput[1].toUpperCase(), insertInput[2]);
            return false;
        }
        else if (command.equalsIgnoreCase("R"))
        {
            System.out.print("City codes:  ");
            String[] removeInput = scan.nextLine().trim().split("\\s+");
            dgraph.removeRoad(removeInput[0].toUpperCase(), removeInput[1].toUpperCase());
            return false;
        }
        else if (command.equalsIgnoreCase("H"))
        {
            displayListOfCommands();
            return false;
        }
        else if (command.equalsIgnoreCase("E"))
            return true;
        else
        {
            System.out.println("Invalid input!");
            return false;
        }
    }
}
