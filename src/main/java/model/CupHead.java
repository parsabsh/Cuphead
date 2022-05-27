package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CupHead {
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();
    private final IntegerProperty lives = new SimpleIntegerProperty();

    public CupHead(int x, int y) {
        this.x.setValue(x);
        this.y.setValue(y);
        lives.setValue(Configurations.difficulty.INITIAL_LIVES);
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

    public void moveUp() {
        if (y.get() - 10 >= 60)
            y.setValue(y.get() - Configurations.SPEED_OF_CUP_HEAD);
    }

    public void moveDown() {
        if (y.get() + 80 + 10 <= 720)
            y.setValue(y.get() + Configurations.SPEED_OF_CUP_HEAD);
    }

    public void moveLeft() {
        if (x.get() - 10 >= 0)
            x.setValue(x.get() - Configurations.SPEED_OF_CUP_HEAD);
    }

    public void moveRight() {
        if (x.get() + 90 + 10 <= 1280)
            x.setValue(x.get() + Configurations.SPEED_OF_CUP_HEAD);
    }

    public void hurt(int amount) {
        lives.setValue(lives.get() - amount);
    }
}
