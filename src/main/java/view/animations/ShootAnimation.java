package view.animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Configurations;

public class ShootAnimation extends Transition {
    ImageView bullet;
    ImageView boss;
    GameMenuController controller;

    @Override
    protected void interpolate(double frac) {
        bullet.setX(bullet.getX() + Configurations.SHOOTING_SPEED);
        if (controller.hasCollision(bullet, boss)) {
            controller.getBoss().hurt(Configurations.difficulty.INJURY_EFFECT * 2 / 100);
            bullet.setOpacity(0);
            controller.updateProgressBars();
            this.stop();
        }
    }

    public ShootAnimation(ImageView bullet, ImageView boss, GameMenuController controller) {
        this.controller = controller;
        this.bullet = bullet;
        this.boss = boss;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(INDEFINITE);
    }
}
