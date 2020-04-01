import java.io.PrintStream;
import java.util.*;

public class Main {

    public static final int XMIN = 0;
    public static final int YMIN = 0;
    public static final int XMAX = 400;
    public static final int YMAX = 600;


    public static void main(String[] args) {
        boolean[][] isFertile;
        boolean[][] visited = new boolean[XMAX][YMAX];

        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;

        isFertile = prepFarm(getPlots(in, out), visited);
        ArrayList<Integer> plotSizes = new ArrayList<>();

        for(int i = XMIN; i < XMAX; i++){
            for(int j = YMIN; j < YMAX; j++){
                if(!visited[i][j] && isFertile[i][j]){
                    plotSizes.add(getFertileArea(isFertile, visited, new Coordinate(i,j)));
                }
            }
        }

        printAreas(out, plotSizes);

    }

    public static void printAreas(PrintStream out, ArrayList<Integer> plotSizes){
        Integer[] plots = plotSizes.toArray(new Integer[0]);

        Arrays.sort(plots);

        for(int i : plots){
            out.print(i + " ");
        }
    }

    public static int getFertileArea(boolean[][] isFertile, boolean[][] visited, Coordinate startPoint){
        int area = 0;
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(startPoint);

        while(!queue.isEmpty()){
            Coordinate here = queue.poll();
            area++;
            visited[here.x][here.y] = true;

            if(here.x - 1 >= XMIN && !visited[here.x - 1][here.y] && isFertile[here.x - 1][here.y]){
                queue.add(new Coordinate(here.x - 1, here.y));
                visited[here.x - 1][here.y] = true;
            }
            if(here.x + 1 < XMAX && !visited[here.x + 1][here.y] && isFertile[here.x + 1][here.y]){
                queue.add(new Coordinate(here.x + 1, here.y));
                visited[here.x + 1][here.y] = true;
            }
            if(here.y - 1 >= YMIN && !visited[here.x][here.y - 1] && isFertile[here.x][here.y - 1]){
                queue.add(new Coordinate(here.x, here.y - 1));
                visited[here.x][here.y - 1] = true;
            }
            if(here.y + 1 < YMAX && !visited[here.x][here.y + 1] && isFertile[here.x][here.y + 1]){
                queue.add(new Coordinate(here.x, here.y + 1));
                visited[here.x][here.y + 1] = true;
            }
        }

        return area;
    }

    public static BoundingBox[] getPlots(Scanner in, PrintStream out){
        ArrayList<BoundingBox> plots = new ArrayList<>();

        while(true) {
            out.println("Please enter a barren land rectangle: (-1 to stop)");

            String inputText = in.nextLine();
            String[] inputs = inputText.split(" ");
            try {
                if (Integer.parseInt(inputs[0]) == -1) {
                    break;
                } else {
                    int minX = Integer.parseInt(inputs[0]);
                    int minY = Integer.parseInt(inputs[1]);
                    int maxX = Integer.parseInt(inputs[2]);
                    int maxY = Integer.parseInt(inputs[3]);

                    if (validPlot(minX, minY, maxX, maxY)) {
                        plots.add(new BoundingBox(minX, minY, maxX, maxY));
                    } else {
                        throw new PlotException();
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                out.println("Please only input valid land rectangles.");
                out.println("Valid rectangles are of the following format:");
                out.println("minX minY maxX maxY");
            } catch (PlotException e) {
                out.println("Please only input valid land rectangles.");
                out.println("minX and maxX must be between 0 and 399 inclusive.");
                out.println("minY and maxY must be between 0 and 599 inclusive.");
                out.println("minX and minY must be less than maxX and maxY respectively.");
            }

        }

        return plots.toArray(new BoundingBox[0]);
    }

    public static boolean validPlot(int minX, int minY, int maxX, int maxY){
        boolean result;

        result = minX >= XMIN && minX < XMAX;
        if(result) result = maxX >= XMIN && maxX < XMAX;
        if(result) result = minY >= YMIN && minY < YMAX;
        if(result) result = maxY >= YMIN && maxY < YMAX;

        if(result) result = minX < maxX && minY < maxY;

        return result;
    }

    public static boolean[][] prepFarm(BoundingBox[] barrenLandPlots, boolean[][] visited){
        boolean[][] isFertile = new boolean[XMAX][YMAX];

        for(int i = XMIN; i < XMAX; i++){
            for(int j = YMIN; j < YMAX; j++){
                isFertile[i][j] = true;
            }
        }

        for(BoundingBox plot : barrenLandPlots) {
            for(int i = plot.lowerLeft.x; i <= plot.upperRight.x; i++){
                for(int j = plot.lowerLeft.y; j <= plot.upperRight.y; j++){
                    isFertile[i][j] = false;
                    visited[i][j] = true;

                }
            }
        }

        return isFertile;
    }
}


