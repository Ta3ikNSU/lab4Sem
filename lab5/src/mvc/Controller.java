package mvc;

import Server.GameStatus;
import Server.Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
    Model model;
    Render render;

    public Controller() {
        model = new Model();
        render = new Render(model);
        model.addObserver(render);
        render.getFrame().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println(e.getPoint());
        model.doMove(new Point(e.getX()/100, e.getY()/100));
        if(model.gameStatus != GameStatus.IN_PROCESS) model.observers.clear();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
