package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final boolean BARREN = false;
    public static final boolean FERTILE = true;
    public static final int XMIN = 0;
    public static final int YMIN = 0;
    public static final int XMAX = 400;
    public static final int YMAX = 600;
    public static final boolean DEBUG = true;
    public static boolean FILENOTFOUND = false;
    public static final boolean GENERATETEST = true;





    public static void main(String[] args) {
        boolean[][] farm;

        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;



        farm = prepFarm(getPlots(in, out));

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

        return (BoundingBox[]) plots.toArray();
    }

    public static boolean validPlot(int minX, int minY, int maxX, int maxY){
        boolean result;

        result = minX >= XMIN && minX <= XMAX;
        if(result) result = maxX >= XMIN && maxX <= XMAX;
        if(result) result = minY >= YMIN && minY <= YMAX;
        if(result) result = maxY >= YMIN && maxY <= YMAX;

        if(result) result = minX < maxX && minY < maxY;

        return result;
    }

    public static boolean[][] prepFarm(BoundingBox[] barrenLandPlots){
        boolean[][] farm = new boolean[XMAX][YMAX];

        for(int i = XMIN; i < XMAX; i++){
            for(int j = YMIN; j < YMAX; j++){
                farm[i][j] = FERTILE;
            }
        }

        for(BoundingBox plot : barrenLandPlots) {
            for(int i = plot.lowerLeft.x; i <= plot.upperRight.x; i++){
                for(int j = plot.lowerLeft.y; j <= plot.upperRight.y; j++){
                    farm[i][j] = BARREN;
                }
            }
        }

        return farm;
    }
}


