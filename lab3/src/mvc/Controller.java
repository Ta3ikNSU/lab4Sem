package mvc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Controller implements MouseMotionListener {
    Model model;
    Render render;

    public Controller() {
        model = new Model();
        render = new Render(model);
        model.addObserver(render);
        render.addMouseMotionListener(this);
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (model.gameStatus != GameStatus.IN_PROCESS) {
            model.observers.clear();
        } else {
            this.model.doMove(e.getPoint());
        }
        //        Logger.getLogger(getClass().getName()).info("MOVE" + e.getPoint());

    }
}
