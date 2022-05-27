package view;

import controller.Controller;
import controller.GameMenuController;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import model.Configurations;
import view.animations.BossShootAnimation;
import view.animations.ShootAnimation;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    GameMenuController controller = new GameMenuController(this);

    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView cupHead;
    @FXML
    private ImageView boss;
    @FXML
    private ProgressBar cupHeadLives;
    @FXML
    private ProgressBar bossLives;
    @FXML
    private Label cupHeadLivesLabel;
    @FXML
    private Label bossLivesLabel;
    @FXML
    private Label score;

    boolean isUpPressed = false;
    boolean isDownPressed = false;
    boolean isLeftPressed = false;
    boolean isRightPressed = false;
    boolean isEnd = false;
    Timeline backgroundAnimation;
    Timeline movementOfBoss;
    Timeline flyingOfBoss;
    Timeline timerForBossShoot;
    ShootAnimation shoot;
    BossShootAnimation BossShoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cupHead.xProperty().bind(controller.getCupHead().xProperty());
        cupHead.yProperty().bind(controller.getCupHead().yProperty());
        cupHeadLives.setProgress(1);
        bossLives.setProgress(1);
        cupHeadLivesLabel.textProperty().bind(controller.getCupHead().livesProperty().asString());
        bossLivesLabel.textProperty().bind(controller.getBoss().livesProperty().asString());
        if (Controller.getLoggedInPlayer() == null) {
            score.setText("You're a guest");
        } else {
            score.textProperty().bind(Controller.getLoggedInPlayer().scoreProperty().asString());
        }

        DoubleProperty xPositionOfBackgrounds = new SimpleDoubleProperty(0);
        xPositionOfBackgrounds.addListener((observable, oldValue, newValue) -> setBackgroundPositions(pane, xPositionOfBackgrounds.get()));
        backgroundAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPositionOfBackgrounds, 0)),
                new KeyFrame(Duration.seconds(600), new KeyValue(xPositionOfBackgrounds, -320000))
        );
        backgroundAnimation.setCycleCount(Timeline.INDEFINITE);
        backgroundAnimation.play();

        movementOfBoss = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(boss.yProperty(), 14)),
                new KeyFrame(Duration.seconds(4), new KeyValue(boss.yProperty(), 320))
        );
        movementOfBoss.setCycleCount(Timeline.INDEFINITE);
        movementOfBoss.setAutoReverse(true);
        movementOfBoss.play();

        flyingOfBoss = controller.getFlyingAnimation(boss);
        flyingOfBoss.play();

        timerForBossShoot = new Timeline(new KeyFrame(Duration.millis(7000), event -> {
            flyingOfBoss.pause();
            Timeline bossShoot = controller.getBossShootAnimation(boss);
            bossShoot.play();
            bossShoot.setOnFinished(e -> flyingOfBoss.playFrom(Duration.millis(500)));
        }));
        timerForBossShoot.setCycleCount(Timeline.INDEFINITE);
        timerForBossShoot.play();

        cupHead.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                isUpPressed = true;
            }
            if (event.getCode() == KeyCode.DOWN) {
                isDownPressed = true;
            }
            if (event.getCode() == KeyCode.LEFT) {
                isLeftPressed = true;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                isRightPressed = true;
            }
            if (event.getCode() == KeyCode.SPACE) {
                shoot();
            }
            move();
        });

        cupHead.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP) {
                isUpPressed = false;
            }
            if (event.getCode() == KeyCode.DOWN) {
                isDownPressed = false;
            }
            if (event.getCode() == KeyCode.LEFT) {
                isLeftPressed = false;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                isRightPressed = false;
            }
        });

        controller.getCupHead().livesProperty().addListener(e -> checkForEndOfGame());
        controller.getBoss().livesProperty().addListener(e -> checkForEndOfGame());
    }


    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameMenu.fxml").toExternalForm()));
                scene = new Scene(root);
                root.getChildren().get(0).requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    private void setBackgroundPositions(Region region, double x) {
        String style = "-fx-background-position: " +
                "left " + 0 + "px bottom," +
                "left " + x / 4 + "px top," +
                "left " + x / 3 + "px bottom," +
                "left " + x / 2 + "px bottom," +
                "left " + x + "px bottom;";
        region.setStyle(style);
    }

    private void move() {
        if (isUpPressed) controller.getCupHead().moveUp();
        if (isDownPressed) controller.getCupHead().moveDown();
        if (isLeftPressed) controller.getCupHead().moveLeft();
        if (isRightPressed) controller.getCupHead().moveRight();
        if (cupHead.intersects(boss.getLayoutBounds())) {
            controller.getCupHead().hurt(Configurations.difficulty.VULNERABILITY_COEFFICIENT * 2 / 100);
            controller.updateProgressBars();
            controller.getCupHead().setX(85);
            controller.getCupHead().setY(150);
        }
    }

    private void shoot() {
        ImageView bullet = new ImageView(new Image("/frames/images/bullet.png"));
        bullet.setLayoutX(0);
        bullet.setLayoutY(0);
        bullet.setX(cupHead.getX() + 90);
        bullet.setY(cupHead.getY() + 40);
        pane.getChildren().add(bullet);
        shoot = new ShootAnimation(bullet, boss, controller);
        try {
            AudioInputStream soundEffect = AudioSystem.getAudioInputStream(Controller.class.getResourceAsStream("/musics/shoot_effect.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(soundEffect);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        shoot.play();
    }

    public void bossShoot() {
        ImageView egg = new ImageView(new Image("/frames/images/egg.png"));
        egg.setLayoutX(0);
        egg.setLayoutY(0);
        egg.setX(boss.getX() + 10);
        egg.setY(boss.getY() + 120);
        egg.setFitWidth(102);
        egg.setFitHeight(87);
        pane.getChildren().add(egg);
        BossShoot = new BossShootAnimation(egg, boss, cupHead, controller);
        BossShoot.play();
    }

    private void checkForEndOfGame() {
        if (controller.getBoss().getLives() <= 0 && !isEnd) {
            isEnd = true;
            setup(cupHead);
            if (Controller.getLoggedInPlayer() != null) {
                Controller.getLoggedInPlayer().setScore(Controller.getLoggedInPlayer().getScore() + controller.getCupHead().getLives());
            }
            endGame();
            window.setScene(Controller.getWinMenu().getScene());
        } else if (controller.getCupHead().getLives() <= 0) {
            isEnd = true;
            setup(cupHead);
            if (Controller.getLoggedInPlayer() != null && !isEnd) {
                Controller.getLoggedInPlayer().setScore(Controller.getLoggedInPlayer().getScore() - controller.getBoss().getLives());
            }
            endGame();
            window.setScene(Controller.getLoseMenu().getScene());
        }
    }

    private void endGame() {
        backgroundAnimation.stop();
        movementOfBoss.stop();
        flyingOfBoss.stop();
        timerForBossShoot.stop();
        if (shoot != null)
            shoot.stop();
        if (BossShoot != null)
            BossShoot.stop();
    }

    public ProgressBar getCupHeadLives() {
        return cupHeadLives;
    }

    public ProgressBar getBossLives() {
        return bossLives;
    }
}
