package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Point {
    private Double x;
    private Double y;
    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }
}
