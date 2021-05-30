package mvc;

import java.awt.*;
import java.util.Vector;

;

public class GameField {
    public final int sizeField = 3;

    private Vector<Vector<Cell>> field = new Vector<>();

    public GameField(){
        for(int i = 0; i < sizeField; i++){
            Vector<Cell> newLine = new Vector<Cell>();
            for(int j =0 ; j < sizeField; j++){
                newLine.add(Cell.Empty);
            }
            field.add(newLine);
        }
    }

    public Cell getCell(Point point){
        return field.get(point.x).get(point.y);
    }

    public void setCell(Point point, Cell newCell){
        field.get(point.x).set(point.y, newCell);
    }
}
