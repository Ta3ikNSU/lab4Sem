package Server;

import mvc.Cell;
import mvc.GameField;
import mvc.Sign;
import observation.Observable;
import observation.Observer;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

enum GameStatus {
    IN_PROCESS,
    x_WIN,
    o_WIN,
    DRAW
}
enum moveStatus {
    MOVE_IS_CORRECT,
    MOVE_IS_WRONG
}
public class Model implements Observable {
    public GameStatus gameStatus;
    ArrayList<Observer> observers;
    GameField field;
    private Sign curSign;

    public Model() {
        observers = new ArrayList<Observer>();
        field = new GameField();
        gameStatus = GameStatus.IN_PROCESS;
    }

    public moveStatus doMove(Point move){
        if(getCell(move) == Cell.Empty){
            if(curSign == Sign.CROSS) {
                setCell(move, Cell.Cross);
                curSign = Sign.ZERO;
            }
            else {
                setCell(move, Cell.Zero);
                curSign = Sign.CROSS;
            }
            this.gameAnalysis();
            this.notifyObservers();
            return moveStatus.MOVE_IS_CORRECT;
        }
        return moveStatus.MOVE_IS_WRONG;
    }

    public void gameAnalysis() {
        checkDiagonal();
        if (gameStatus == GameStatus.IN_PROCESS) {
            for (int i = 0; i < 3; i++) {
                checkLine(i);
                checkRow(i);
                if (gameStatus != GameStatus.IN_PROCESS) return;
            }
        }
        if (checkDraw()) gameStatus = GameStatus.DRAW;
    }

    private void checkLine(int index) {
        if ((field.getCell(new Point(index, 0)) == Cell.Cross) &&
                (field.getCell(new Point(index, 1)) == Cell.Cross) &&
                (field.getCell(new Point(index, 2)) == Cell.Cross))
            gameStatus = GameStatus.x_WIN;
        if ((field.getCell(new Point(index, 0)) == Cell.Zero) &&
                (field.getCell(new Point(index, 1)) == Cell.Zero) &&
                (field.getCell(new Point(index, 2)) == Cell.Zero))
            gameStatus = GameStatus.o_WIN;
    }

    private void checkDiagonal() {
        if ((field.getCell(new Point(0, 0)) == Cell.Cross) &&
                (field.getCell(new Point(1, 1)) == Cell.Cross) &&
                (field.getCell(new Point(2, 2)) == Cell.Cross))
            gameStatus = GameStatus.x_WIN;
        if ((field.getCell(new Point(0, 0)) == Cell.Zero) &&
                (field.getCell(new Point(1, 1)) == Cell.Zero) &&
                (field.getCell(new Point(2, 2)) == Cell.Zero))
            gameStatus = GameStatus.o_WIN;
        if ((field.getCell(new Point(0, 2)) == Cell.Cross) &&
                (field.getCell(new Point(1, 1)) == Cell.Cross) &&
                (field.getCell(new Point(2, 0)) == Cell.Cross))
            gameStatus = GameStatus.x_WIN;
        if ((field.getCell(new Point(0, 2)) == Cell.Zero) &&
                (field.getCell(new Point(1, 1)) == Cell.Zero) &&
                (field.getCell(new Point(2, 0)) == Cell.Zero))
            gameStatus = GameStatus.o_WIN;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field.getCell(new Point(i, j)) == Cell.Empty) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setCell(Point point, Cell newCell) {
        if (point.getX() >= 3 || point.getY() >= 3) {
            throw new InvalidParameterException("Cord is wrong. Try again");
        }
        if (field.getCell(point) != Cell.Empty) {
            throw new InvalidParameterException("Cell not empty");
        } else {
            field.setCell(point, newCell);
        }
    }

    public Cell getCell(Point point) {
        if (point.getX() >= 3 || point.getY() >= 3) {
            throw new InvalidParameterException("Cord is wrong. Try again");
        }
       return field.getCell(point);
    }

    private void checkRow(int index) {
        if ((field.getCell(new Point(0, index)) == Cell.Cross) &&
                (field.getCell(new Point(1, index)) == Cell.Cross) &&
                (field.getCell(new Point(2, index)) == Cell.Cross))
            gameStatus = GameStatus.x_WIN;
        if ((field.getCell(new Point(0, index)) == Cell.Zero) &&
                (field.getCell(new Point(1, index)) == Cell.Zero) &&
                (field.getCell(new Point(2, index)) == Cell.Zero))
            gameStatus = GameStatus.o_WIN;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.printField(this);
        }
    }
}
