package com.company;

public class Main {

    public static final boolean BARREN = false;
    public static final boolean FERTILE = true;
    public static final int XMIN = 0;
    public static final int YMIN = 0;
    public static final int XMAX = 400;
    public static final int YMAX = 600;



    public static void main(String[] args) {
        boolean[][] farm;



    }

    public boolean[][] prepFarm(BoundingBox[] barrenLandPlots){
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


