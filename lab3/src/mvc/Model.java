package mvc;

import observation.Observable;
import observation.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Model implements Observable {
    public GameStatus gameStatus;
    public int score = 30;
    public ArrayList<Observer> observers;
    long prevMoment = System.currentTimeMillis();
    long prevMomentForUpdateSpeed = System.currentTimeMillis();
    ;
    private Blob player = new Blob(30, 30, 50);
    private Vector<Enemies> enemies = new Vector<Enemies>();
    private Vector<Dot> dots = new Vector<Dot>();
    private Render render;

    public Model() {
        observers = new ArrayList<>();
        gameStatus = GameStatus.IN_PROCESS;
    }

    public Vector<Enemies> getEnemies() {
        return enemies;
    }

    public Vector<Dot> getDots() {
        return dots;
    }

    public Blob getPlayer() {
        return player;
    }

    public void doMove(Point move) {
        if (System.currentTimeMillis() - prevMoment > 20) {
            prevMoment = System.currentTimeMillis();
            if (enemies.size() < 2) {
                if (enemies.size() == 0) {
                    Enemies newEn = new Enemies((player.getX() + 500) % 900, player.getY(), player.size + player.size / 10);
                    enemies.add(newEn);
                }
                Enemies newEn = new Enemies(player.getX(), (player.getY() + 500) % 900, player.size + player.size / 10);
                enemies.add(newEn);
            }
            player.setX((int) (player.getX() + Math.abs(move.getX()-450) % 6 * Math.signum(move.getX() - 450)));
            player.setY((int) (player.getY() + Math.abs(move.getY()-450) % 6  * Math.signum(move.getY() - 450)));
            player.cordUpdate();
            Random rand = new Random();
            for (int i = 0; i < enemies.size(); i++) {
                var en = enemies.get(i);
//                if (System.currentTimeMillis() - prevMomentForUpdateSpeed > 500) {
//                    en.setSpeedX((int) (Math.sin(System.currentTimeMillis()) * 10) * rand.nextInt()%2);
//                    en.setSpeedY((int) (Math.sin(System.currentTimeMillis()) * 10) * rand.nextInt()%2);
//                    prevMomentForUpdateSpeed = System.currentTimeMillis();
//                }
//                en.setX((int) (en.getX() + player.getX() * ((double) en.getSpeedX())/1000));
//                en.setY((int) (en.getY() + player.getY() * ((double) en.getSpeedY())/1000));
                en.setX(en.getX() + rand.nextInt() % 5);
                en.setY(en.getY() + rand.nextInt() % 5);
                en.cordUpdate();
            }
            if (dots.size() < 10) {
                Dot newDot = new Dot(rand.nextInt() % 450 + 450, rand.nextInt() % 450 + 450, player.size / 5);
                dots.add(newDot);
            }
            gameAnalysis();
        }
    }

    public void gameAnalysis() {
        if (score < 600 && score > 0) {
            Point cordOfCenterOfPlarsCircle = new Point(player.getX() + player.getSizePoint(), player.getY() + player.getSizePoint());
            for (int i = 0; i < enemies.size(); i++) {
                Enemies en = enemies.get(i);
                Point cordOfCenterOfEnemsCircle = new Point(en.getX() + en.getSizePoint(), en.getY() + en.getSizePoint());
                if (Math.sqrt
                        (Math.pow((cordOfCenterOfEnemsCircle.getX() - cordOfCenterOfPlarsCircle.getX()), 2)
                                +
                                (Math.pow((cordOfCenterOfEnemsCircle.getY() - cordOfCenterOfPlarsCircle.getY()), 2))
                        ) <= player.getSizePoint() + en.getSizePoint()) {
                    if (en.getSizePoint() > player.getSizePoint())
                        gameStatus = GameStatus.LOSE;
                    else enemies.remove(en);
                }
            }
            for (int i = 0; i < dots.size(); i++) {
                Dot dot = dots.get(i);
                Point cordOfCenterOfDotsCircle = new Point(dot.getX() + dot.getSizePoint(), dot.getY() + dot.getSizePoint());
                if (Math.sqrt
                        (Math.pow((cordOfCenterOfDotsCircle.getX() - cordOfCenterOfPlarsCircle.getX()), 2)
                                +
                                (Math.pow((cordOfCenterOfDotsCircle.getY() - cordOfCenterOfPlarsCircle.getY()), 2))
                        ) <= player.getSizePoint() + dot.getSizePoint()) {
                    score += dot.size;
                    player.setSize(player.getSizePoint() + dot.size);
                    dots.remove(dot);
                }
                for (int j = 0; j < enemies.size(); j++) {
                    Enemies en = enemies.get(j);
                    Point cordOfCenterOfEnemsCircle = new Point(en.getX() + en.getSizePoint(), en.getY() + en.getSizePoint());
                    if (Math.sqrt
                            (Math.pow((cordOfCenterOfEnemsCircle.getX() - cordOfCenterOfDotsCircle.getX()), 2)
                                    +
                                    (Math.pow((cordOfCenterOfEnemsCircle.getY() - cordOfCenterOfDotsCircle.getY()), 2))
                            ) <= en.getSizePoint() + dot.getSizePoint()) {
                        en.size += dot.size;
                        dots.remove(dot);
                    }
                }
            }
            notifyObservers();
        }
        if (score > 600) {
            System.out.println("Your win");
            gameStatus = GameStatus.WIN;
        } else if (score == 0) {
            System.out.println("Your lose");
            gameStatus = GameStatus.LOSE;
        }
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
