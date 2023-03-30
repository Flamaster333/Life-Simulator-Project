package PO_java.LifeSymulator.Tools;

import java.util.Objects;

public class Cords {
    private int x;
    private int y;

    public Cords(){}
    public Cords(int x, int y){
        this.x = x;
        this.y = y;
    }

    //Settery
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setCords(Cords cords){
        this.x = cords.x;
        this.y = cords.y;
    }

    //Gettery
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Cords getCords(){
        return new Cords(x, y);
    }

    //Porównywanie cordów
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cords cords = (Cords) o;
        return x == cords.x && y == cords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
