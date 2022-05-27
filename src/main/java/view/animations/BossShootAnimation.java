package view.animations;

import controller.GameMenuController;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Configurations;

public class BossShootAnimation extends Transition {
    ImageView egg;
    ImageView boss;
    ImageView cupHead;
    GameMenuController controller;
    RotateTransition rotate;

    public BossShootAnimation(ImageView egg, ImageView boss, ImageView cupHead, GameMenuController controller) {
        this.controller = controller;
        this.egg = egg;
        this.boss = boss;
        this.cupHead = cupHead;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(INDEFINITE);
        rotate = new RotateTransition(Duration.millis(1000), egg);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(Transition.INDEFINITE);
        rotate.play();
    }

    @Override
    protected void interpolate(double frac) {
        egg.setX(egg.getX() - Configurations.SHOOTING_SPEED);
        if (controller.hasCollision(egg, cupHead)) {
            controller.getCupHead().hurt(Configurations.difficulty.VULNERABILITY_COEFFICIENT * 2 / 100);
            egg.setOpacity(0);
            controller.updateProgressBars();
            rotate.stop();
            this.stop();
        }
    }
}
