package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class Boss {
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();
    private final IntegerProperty lives = new SimpleIntegerProperty();
    private final Image frame_1 = new Image("/frames/BossFly/1.png");
    private final Image frame_2 = new Image("/frames/BossFly/2.png");
    private final Image frame_3 = new Image("/frames/BossFly/3.png");
    private final Image frame_4 = new Image("/frames/BossFly/4.png");
    private final Image frame_5 = new Image("/frames/BossFly/5.png");
    private final Image frame_6 = new Image("/frames/BossFly/6.png");
    private final Image shootFrame_1 = new Image("/frames/BossShoot/1.png");
    private final Image shootFrame_2 = new Image("/frames/BossShoot/2.png");
    private final Image shootFrame_3 = new Image("/frames/BossShoot/3.png");
    private final Image shootFrame_4 = new Image("/frames/BossShoot/4.png");
    private final Image shootFrame_5 = new Image("/frames/BossShoot/5.png");
    private final Image shootFrame_6 = new Image("/frames/BossShoot/6.png");
    private final Image shootFrame_7 = new Image("/frames/BossShoot/7.png");
    private final Image shootFrame_8 = new Image("/frames/BossShoot/8.png");
    private final Image shootFrame_9 = new Image("/frames/BossShoot/9.png");
    private final Image shootFrame_10 = new Image("/frames/BossShoot/10.png");

    public Boss(int x, int y) {
        this.x.setValue(x);
        this.y.setValue(y);
        lives.setValue(Configurations.BOSS_INITIAL_LIVES);
    }

    public Image[] getFrames() {
        return new Image[]{frame_1, frame_2, frame_3, frame_4, frame_5, frame_6};
    }

    public Image[] getShootFrames() {
        return new Image[]{shootFrame_1, shootFrame_2, shootFrame_3, shootFrame_4, shootFrame_5, shootFrame_6, shootFrame_7, shootFrame_8, shootFrame_9, shootFrame_10};
    }

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public int getLives() {
        return lives.get();
    }

    public IntegerProperty livesProperty() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives.set(lives);
    }

    public void hurt(int amount) {
        lives.setValue(lives.get() - amount);
    }
}
