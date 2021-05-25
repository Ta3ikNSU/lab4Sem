package sample;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Observer {
    public static final int MYLT = 1;
    private static final int WIDTH = 15 * MYLT; //ширина игрового поля
    private static final int HEIGHT = 13 * MYLT; //высота игрового поля
    private final GameModel worldStates; //способ представления игрового мира в памяти
    private final Affine affine; //штука, которая будет делать за нас афинные преобразования
    public int numLevelNow = 1; //номер уровня на котором мы сейчас
    public Button playButton;
    public boolean isChangedOnThisFrame = false; //нажимали ли что-то за этот кадр?
    public boolean isOnProc = false;
    Processing proc;
    private int countFoodForWin;
    private int countFoodAlready;
    @FXML
    private Canvas mainCanvas; //полотно где отрисовывается графика игрового процесса
    @FXML
    private Label eatText;
    @FXML
    private HBox spaceWhereCounter;
    @FXML
    private Text textInfoWin;
    @FXML
    private Label levelText;
    private boolean isInitialized = false; //чекает, до конца ли проинициализирован контроллер

    public Observer() {
        this.affine = new Affine();

        this.worldStates = new GameModel(WIDTH, HEIGHT, this); //инициализировали класс, хранящий состояние игрового мира
        this.worldStates.setCountOfFood(5); //постоянное число еды на поле
    }

    public static int random(int min, int max) { //функция генерации случайного числа [min, max)
        return (int) ((Math.random() * (max - min)) + min);
    }

    public GameModel getStateMachineReference() {
        return this.worldStates;
    }

    public void initialization(Processing proc) { //приходится инициализировать класс отдельным методом, так как конструктор Controller-а вызывается fxml loader-ом и мы не можем передать туда параметры
        initAfine();
        this.proc = proc;
        initialDraw();
        isInitialized = true; //успешно инициализирован

        worldStates.setWall(3, 3);
        worldStates.setWall(4, 3);
        worldStates.setWall(5, 3);
        worldStates.setWall(6, 3);
    }

    private void initAfine() { //устанавливаем, как будет масштабироваться картинка
        this.affine.appendScale(mainCanvas.getWidth() / WIDTH, mainCanvas.getHeight() / HEIGHT);
    }

    public void onKeyPressed(KeyEvent keyEvent) { //обработчик нажатий кнопок
        if (!isChangedOnThisFrame) {
            PartOfSnake.directionMove dM = worldStates.playerSnake.parts.get(0).myDir;
            if (keyEvent.getCode() == KeyCode.UP && dM != PartOfSnake.directionMove.DOWN && dM != PartOfSnake.directionMove.UP) {
                worldStates.playerSnake.parts.get(0).myDir = PartOfSnake.directionMove.UP;
                isChangedOnThisFrame = true;
            } else if (keyEvent.getCode() == KeyCode.DOWN && dM != PartOfSnake.directionMove.UP && dM != PartOfSnake.directionMove.DOWN) {
                worldStates.playerSnake.parts.get(0).myDir = PartOfSnake.directionMove.DOWN;
                isChangedOnThisFrame = true;
            } else if (keyEvent.getCode() == KeyCode.LEFT && dM != PartOfSnake.directionMove.RIGHT && dM != PartOfSnake.directionMove.LEFT) {
                worldStates.playerSnake.parts.get(0).myDir = PartOfSnake.directionMove.LEFT;
                isChangedOnThisFrame = true;
            } else if (keyEvent.getCode() == KeyCode.RIGHT && dM != PartOfSnake.directionMove.LEFT && dM != PartOfSnake.directionMove.RIGHT) {
                worldStates.playerSnake.parts.get(0).myDir = PartOfSnake.directionMove.RIGHT;
                isChangedOnThisFrame = true;
            }
        }
    }

    public void initialDraw() {
        GraphicsContext g = this.mainCanvas.getGraphicsContext2D();

        //масштабирование игрового мира к размеру экрана
        g.setTransform(this.affine);

        g.setFill(Color.rgb(181, 230, 29));
        g.fillRect(0, 0, 650, 580);

        //Рисуем линии
        g.setFill(Color.GRAY);
        g.setLineWidth(0.025);
        for (int x = 0; x <= WIDTH; x++) {
            g.strokeLine(x, 0, x, HEIGHT);
        }
        for (int y = 0; y <= HEIGHT; y++) {
            g.strokeLine(0, y, WIDTH, y);
        }
        //end Рисуем линии
    }

    public void draw() {
        if (!isInitialized) {
            throw new IllegalStateException("You must first initialized GameModel by initialization() method.");
        }

        GraphicsContext g = this.mainCanvas.getGraphicsContext2D();

        //масштабирование игрового мира к размеру экрана
        g.setTransform(this.affine);

        g.setFill(Color.rgb(181, 230, 29));
        g.fillRect(0, 0, 650, 580);

        DrawFoodAndWall(g);

        DrawSnake(g, worldStates.playerSnake);

        //Рисуем линии
        g.setFill(Color.GRAY);
        g.setLineWidth(0.025);
        for (int x = 0; x <= WIDTH; x++) {
            g.strokeLine(x, 0, x, HEIGHT);
        }
        for (int y = 0; y <= HEIGHT; y++) {
            g.strokeLine(0, y, WIDTH, y);
        }
        //end Рисуем линии


    }

    public void drawWasted() {
        GraphicsContext g = this.mainCanvas.getGraphicsContext2D();
        InputStream iconStream = getClass().getResourceAsStream("resources/WASTED.png");
        Image image = new Image(iconStream);

        Affine a = new Affine();
        a.appendScale(mainCanvas.getWidth(), mainCanvas.getHeight());
        g.setTransform(a);

        g.drawImage(image, 0, 0, 1, 1);
    }

    public void drawWin() {
        GraphicsContext g = this.mainCanvas.getGraphicsContext2D();
        InputStream iconStream = getClass().getResourceAsStream("resources/WIN.png");
        Image image = new Image(iconStream);

        Affine a = new Affine();
        a.appendScale(mainCanvas.getWidth(), mainCanvas.getHeight());
        g.setTransform(a);
        g.drawImage(image, 0, 0, 1, 1);
    }

    private void DrawSnake(GraphicsContext g, PlayerSnake plSn) {
        InputStream iconStream = getClass().getResourceAsStream("resources/mainSnakeHead.png");
        ImageView iv = new ImageView(new Image(iconStream));
        iv.setRotate(plSn.parts.get(0).getRotate(false));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image image = iv.snapshot(params, null);
        g.drawImage(image, plSn.parts.get(0).getX(), plSn.parts.get(0).getY(), 1, 1);

        for (int i = 1; i < plSn.parts.size() - 1; i++) {
            if (!plSn.parts.get(i).isAmaOnTurn) {
                iconStream = getClass().getResourceAsStream("resources/mainSnakeMiddle.png");
            } else {
                iconStream = getClass().getResourceAsStream("resources/mainSnakeTurn.png");
            }
            iv = new ImageView(new Image(iconStream));
            iv.setRotate(plSn.parts.get(i).getRotate(false));
            params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            image = iv.snapshot(params, null);
            //image = new Image(iconStream);
            g.drawImage(image, plSn.parts.get(i).getX(), plSn.parts.get(i).getY(), 1, 1);
        }

        iconStream = getClass().getResourceAsStream("resources/mainSnakeTail.png");
        iv = new ImageView(new Image(iconStream));
        iv.setRotate(plSn.parts.get(plSn.parts.size() - 1).getRotate(true));
        params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        image = iv.snapshot(params, null);
        //image = new Image(iconStream);
        g.drawImage(image, plSn.parts.get(plSn.parts.size() - 1).getX(), plSn.parts.get(plSn.parts.size() - 1).getY(), 1, 1);

    }

    private void DrawFoodAndWall(GraphicsContext g) {
        for (int x = 0; x < this.worldStates.width; x++) {
            for (int y = 0; y < this.worldStates.height; y++) {
                if (this.worldStates.getState(x, y) == GameModel.FOOD) {
                    InputStream iconStream = getClass().getResourceAsStream("resources/foodWhite.png");
                    g.drawImage(new Image(iconStream), x, y, 1, 1);
                } else if (this.worldStates.getState(x, y) == GameModel.WALL) {
                    InputStream iconStream = getClass().getResourceAsStream("resources/wall.png");
                    g.drawImage(new Image(iconStream), x, y, 1, 1);
                }
            }
        }
    }

    @FXML
    private void realizePlayButton() {
        if (!isOnProc) {
            numLevelNow = 1;

            loadNextLevel();

            playButton.setText("Stop");

            eatText.setVisible(true);
            spaceWhereCounter.setVisible(true);
            levelText.setVisible(true);

            isOnProc = true;
        } else {
            proc.turnOffProc();
            playButton.setText("Restart");
            isOnProc = false;
        }
    }

    public void loadNextLevel() {
        worldStates.clearWorld();

        String linkOnLevel = "src\\sample\\level" + String.valueOf(numLevelNow) + ".txt";
        Path fileName = Path.of(linkOnLevel);
        String textWithJson = null;
        try {
            textWithJson = Files.readString(fileName); //считываем файл в String
        } catch (IOException e) {
            drawWin();
            playButton.setText("Play");
            isOnProc = false;
            return;
            //e.printStackTrace();
        }

        Gson gson = new Gson();
        allInfo newInfo = gson.fromJson(textWithJson, allInfo.class); //парсим JSON

        worldStates.addPlayerSnake(new PlayerSnake(worldStates, newInfo.playerSnake.xPos, newInfo.playerSnake.yPos, newInfo.playerSnake.directionHead));

        for (int i = 0; i < newInfo.wallCoord.size(); i++) {
            worldStates.setWall(newInfo.wallCoord.get(i).x, newInfo.wallCoord.get(i).y);
        }

        worldStates.setCountOfFood(newInfo.countOfFood);

        countFoodForWin = newInfo.countFoodForWin;
        countFoodAlready = 0;
        textInfoWin.setText("  " + countFoodAlready + " из " + countFoodForWin);

        levelText.setText("LEVEL " + String.valueOf(numLevelNow));

        for (int i = 0; i < worldStates.getCountOfFood(); i++) { //генерим еду на поле
            worldStates.genFood();
        }

        proc.turnOnProc(); //включили обработчик кадров
    }

    public void plusAlreadyFoodAndReNew() {
        countFoodAlready++;
        textInfoWin.setText("  " + countFoodAlready + " из " + countFoodForWin);
    }

    public boolean isWeWin() {
        return countFoodAlready >= countFoodForWin;
    }

    static class allInfo {
        int countOfFood;
        int countFoodForWin;
        playerSnake playerSnake;
        List<wallCoord> wallCoord;

        private static class playerSnake {
            int xPos;
            int yPos;
            String directionHead;
        }

        private static class wallCoord {
            int x;
            int y;
        }
    }
}
