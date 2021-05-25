package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.util.Duration;

public class Processing {
    private final Timeline timeline;

    private final Observer observer;

    private final Parent root;

    private final GameModel worldStates; //способ представления игрового мира в памяти

    public Processing(Observer observer, Parent root, GameModel worldStates) {
        this.worldStates = worldStates;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(200), this::doStep)); //будет запускать функцию doStep с определенным промежутком времени
        this.timeline.setCycleCount(Timeline.INDEFINITE); //бесконечный цикл работы
        this.observer = observer;
        this.root = root;
    }

    private void doStep(ActionEvent actionEvent) {
        turnOffProc();
        root.requestFocus(); //нужно, чтобы кнопки(которые нажимабельны на экране) не прерывали eventListener наших кнопок(которые на клавиатуре) во время игры
        int errCode = UpdateModel(); //считаем логику
        if (observer.isWeWin()) { //если прошли уровень
            turnOffProc();
            observer.numLevelNow++; //новый уровень
            observer.loadNextLevel();
        } else if (errCode == GameModel.ErrCodeSuccess) {
            observer.draw(); //отрисовка кадра
            observer.isChangedOnThisFrame = false; //позволяет снова считать направление змейки для след кадра
            turnOnProc();
        } else if (errCode == GameModel.ErrCodeStopGame) { //проиграли
            observer.playButton.setText("Restart");
            observer.isOnProc = false;
            observer.drawWasted();
            turnOffProc();
        }

    }

    private int UpdateModel() { //блок просчета логики
        return worldStates.workLogic(); //просчет пути змейки
    }

    public void turnOnProc() {
        timeline.play();
    }

    public void turnOffProc() {
        timeline.stop();
    }
}
