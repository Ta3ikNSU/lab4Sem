package mvc;

import observation.Observable;
import observation.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Model implements Observable {
    public GameStatus gameStatus;
    public int score = 30;
    public ArrayList<Observer> observers;
    long prevMoment = System.currentTimeMillis();
    private Blob player = new Blob(30, 30, 50);
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private Render render;

    public Model() {
        observers = new ArrayList<>();
        gameStatus = GameStatus.IN_PROCESS;
    }

    public ArrayList<Enemies> getEnemies() {
        return enemies;
    }

    public ArrayList<Dot> getDots() {
        return dots;
    }

    public Blob getPlayer() {
        return player;
    }

    public void doMove(Point move) {
        if (System.currentTimeMillis() - prevMoment > 100) {
            prevMoment = System.currentTimeMillis();
            if (enemies.size() < 2) {
                if (enemies.size() == 0) {
                    Enemies newEn = new Enemies(player.getX() + 500 % 900, player.getY(), player.size + player.size / 10);
                    enemies.add(newEn);
                }
                Enemies newEn = new Enemies(player.getX(), player.getY() + 500 % 900, player.size + player.size / 10);
                enemies.add(newEn);
            }
            System.out.println("move");
            player.setX(player.getX() + ((int) move.getX() - 450) / 20);
            player.setY(player.getY() + ((int) move.getY() - 450) / 20);
            player.cordUdpate();
            Random rand = new Random();
            for (var en : enemies) {
                en.setX(en.getX() + rand.nextInt() % 900 / 20 * (rand.nextInt() % 2 - 1));
                en.setY(en.getY() + rand.nextInt() % 900 / 20 * (rand.nextInt() % 2 - 1));
                en.cordUdpate();

            }

            if (dots.size() < 10) {
                dots.add(new Dot(rand.nextInt() % 900, rand.nextInt() % 900, player.size / 5));
            }

            gameAnalysis();
        }
    }

    public void gameAnalysis() {
        if (score > 2500) {
            System.out.println("Your win");
            gameStatus = GameStatus.WIN;
        } else if (score == 0) {
            System.out.println("Your lose");
            gameStatus = GameStatus.LOSE;
        } else {
            for (var en : enemies) {
                if (Math.sqrt
                        (Math.pow((en.getX() + en.getSizePoint()) - (player.getX() + player.getSizePoint()), 2)
                                - Math.pow((en.getY() + en.getSizePoint()) - (player.getY() + player.getSizePoint()), 2))
                        < en.getSizePoint() + player.getSizePoint()) {
                    gameStatus = GameStatus.LOSE;
                }
            }
            for (var dot : dots) {
                if (Math.sqrt
                        (Math.pow((dot.getX() + dot.getSizePoint()) - (player.getX() + player.getSizePoint()), 2)
                                - Math.pow((dot.getY() + dot.getSizePoint()) - (player.getY() + player.getSizePoint()), 2))
                        < dot.getSizePoint() + player.getSizePoint()) {
                    score+=dot.size;
                    dots.remove(dot);
                }
            }
        }
        notifyObservers();
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
