package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL xmlURL = getClass().getResource("sample.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlURL);
        Parent root = loader.load(xmlURL.openStream()); //загрузили интерфейс с fxml-я

        Observer cntr = loader.getController();
        Processing processing = new Processing(cntr, root, cntr.getStateMachineReference()); //обработчик кадров
        cntr.initialization(processing);

        primaryStage.setTitle("Snake"); //название окна
        InputStream iconStream = getClass().getResourceAsStream("resources/icon.png"); //получили изображение
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image); //сменили иконку приложения
        primaryStage.setWidth(800);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false); //запрет на изменение размера окна

        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.show();

    }
}
