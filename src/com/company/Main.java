package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static final int XMIN = 0;
    public static final int YMIN = 0;
    public static final int XMAX = 400;
    public static final int YMAX = 600;


    public static void main(String[] args) {
        Plot[][] farm;

        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;

        farm = prepFarm(getPlots(in, out));
        ArrayList<Integer> plotSizes = new ArrayList<>();

        for(int i = XMIN; i < XMAX; i++){
            for(int j = YMIN; j < YMAX; j++){
                if(!farm[i][j].visited && farm[i][j].isFertile){
                    plotSizes.add(getFertileArea(farm, new Coordinate(i,j)));
                }
            }
        }



    }

    public static int getFertileArea(Plot[][] farm, Coordinate startPoint){
        int area = 0;
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(startPoint);

        while(!queue.isEmpty()){
            Coordinate here = queue.poll();
            area++;
            farm[here.x][here.y].visited = true;

            if(here.x - 1 >= XMIN && !farm[here.x - 1][here.y].visited && farm[here.x - 1][here.y].isFertile){
                queue.add(new Coordinate(here.x - 1, here.y));
            }
            if(here.x + 1 < XMAX && !farm[here.x + 1][here.y].visited && farm[here.x + 1][here.y].isFertile){
                queue.add(new Coordinate(here.x + 1, here.y));
            }
            if(here.y - 1 >= YMIN && !farm[here.x][here.y - 1].visited && farm[here.x][here.y - 1].isFertile){
                queue.add(new Coordinate(here.x, here.y - 1));
            }
            if(here.y + 1 < YMAX && !farm[here.x][here.y + 1].visited && farm[here.x][here.y + 1].isFertile){
                queue.add(new Coordinate(here.x, here.y + 1));
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

        return (BoundingBox[]) plots.toArray();
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

    public static Plot[][] prepFarm(BoundingBox[] barrenLandPlots){
        Plot[][] farm = new Plot[XMAX][YMAX];

        for(int i = XMIN; i < XMAX; i++){
            for(int j = YMIN; j < YMAX; j++){
                farm[i][j] = new Plot();
            }
        }

        for(BoundingBox plot : barrenLandPlots) {
            for(int i = plot.lowerLeft.x; i <= plot.upperRight.x; i++){
                for(int j = plot.lowerLeft.y; j <= plot.upperRight.y; j++){
                    farm[i][j].isFertile = false;
                    farm[i][j].visited = true;

                }
            }
        }

        return farm;
    }
}


