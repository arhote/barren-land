package com.company;

public class BoundingBox {
    Coordinate lowerLeft;
    Coordinate upperRight;

    public BoundingBox(String coords){
        String[] vals = coords.split(" ");
        this.lowerLeft = new Coordinate(Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
        this.upperRight = new Coordinate(Integer.parseInt(vals[2]), Integer.parseInt(vals[3]));
    }

    public BoundingBox(Coordinate lowerLeft, Coordinate upperRight){
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }
}
