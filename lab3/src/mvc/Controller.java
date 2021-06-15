package mvc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

public class Controller implements MouseMotionListener {
    Model model;
    Render render;

    public Controller() {
        model = new Model();
        render = new Render(model);
        model.addObserver(render);
        render.getFrame().addMouseMotionListener(this);
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.model.doMove(e.getPoint());
//        Logger.getLogger(getClass().getName()).info("MOVE" + e.getPoint());
        if (model.gameStatus != GameStatus.IN_PROCESS) model.observers.clear();
    }


}
