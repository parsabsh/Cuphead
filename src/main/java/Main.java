import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Controller controller = new Controller(primaryStage);
        controller.run();
    }
}
