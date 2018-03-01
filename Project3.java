import java.util.Scanner;

public class Project3
{
    private Scanner scan;

    public static void main(String[] args)
    {
        Digraph dgraph = new Digraph("city.dat", "road.dat");
        boolean done = false;
        scan = new Scanner(System.in);

        while (done == false)
        {
            done = menuCommandLoop();
            System.out.print("\n");
        }
    }

    private static boolean menuCommandLoop()
    {
        String command = "";
        String cityCode = "";
        int distance = 0;

        System.out.print("Command?  ");
        command = scan.nextLine();

        if (command.equalsIgnoreCase("Q"))
            return false;
        else if (command.equalsIgnoreCase("D"))
            return false;
        else if (command.equalsIgnoreCase("I"))
        {
            System.out.print("City codes and distance:  ");
            String[] insertInput = scan.nextLine().trim().split("\\s+");
            dgraph.insertRoad(insertInput[0], insertInput[1], insertInput[2]);
            return false;
        }
        else if (command.equalsIgnoreCase("R"))
            return false;
        else if (command.equalsIgnoreCase("H"))
            return false;
        else if (command.equalsIgnoreCase("E"))
            return true;
        else
        {
            System.out.println("Invalid input!");
            return false;
        }
    }
}
