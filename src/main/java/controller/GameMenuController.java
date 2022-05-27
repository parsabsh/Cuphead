package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Boss;
import model.Configurations;
import model.CupHead;
import view.GameMenu;

public class GameMenuController {
    private final CupHead cupHead = new CupHead(85, 150);
    private final Boss boss = new Boss(760, 14);
    private final GameMenu gameMenu;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public CupHead getCupHead() {
        return cupHead;
    }

    public Boss getBoss() {
        return boss;
    }

    public Timeline getFlyingAnimation(ImageView bossImageView) {
        Image[] frames = boss.getFrames();
        Timeline flying = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bossImageView.imageProperty(), frames[0])),
                new KeyFrame(Duration.millis(100), new KeyValue(bossImageView.imageProperty(), frames[1])),
                new KeyFrame(Duration.millis(200), new KeyValue(bossImageView.imageProperty(), frames[2])),
                new KeyFrame(Duration.millis(300), new KeyValue(bossImageView.imageProperty(), frames[3])),
                new KeyFrame(Duration.millis(400), new KeyValue(bossImageView.imageProperty(), frames[4])),
                new KeyFrame(Duration.millis(500), new KeyValue(bossImageView.imageProperty(), frames[5])),
                new KeyFrame(Duration.millis(600), new KeyValue(bossImageView.imageProperty(), frames[0]))
        );
        flying.setCycleCount(Timeline.INDEFINITE);
        return flying;
    }


    public boolean hasCollision(Node first, Node second) {
        return first.intersects(second.getBoundsInParent());
    }

    public void updateProgressBars() {
        gameMenu.getCupHeadLives().setProgress((double)cupHead.getLives()/ Configurations.difficulty.INITIAL_LIVES);
        gameMenu.getBossLives().setProgress((double)boss.getLives()/ Configurations.BOSS_INITIAL_LIVES);
    }

    public Timeline getBossShootAnimation(ImageView bossImageView) {
        Image[] frames = boss.getShootFrames();
        Timeline shoot = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bossImageView.imageProperty(), frames[0])),
                new KeyFrame(Duration.millis(100), new KeyValue(bossImageView.imageProperty(), frames[1])),
                new KeyFrame(Duration.millis(200), new KeyValue(bossImageView.imageProperty(), frames[2])),
                new KeyFrame(Duration.millis(300), new KeyValue(bossImageView.imageProperty(), frames[3])),
                new KeyFrame(Duration.millis(400), new KeyValue(bossImageView.imageProperty(), frames[4])),
                new KeyFrame(Duration.millis(500), e -> gameMenu.bossShoot(), new KeyValue(bossImageView.imageProperty(), frames[5])),
                new KeyFrame(Duration.millis(600), new KeyValue(bossImageView.imageProperty(), frames[6])),
                new KeyFrame(Duration.millis(700), new KeyValue(bossImageView.imageProperty(), frames[7])),
                new KeyFrame(Duration.millis(800), new KeyValue(bossImageView.imageProperty(), frames[8])),
                new KeyFrame(Duration.millis(900), new KeyValue(bossImageView.imageProperty(), frames[9])),
                new KeyFrame(Duration.millis(1000), new KeyValue(bossImageView.imageProperty(), frames[0]))
        );
        return shoot;
    }
}
