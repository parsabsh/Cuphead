package enums;

public enum Difficulty {
    EASY(20, 50, 150),
    MEDIUM(10, 100, 100),
    HARD(4, 150, 50);

    public final int INITIAL_LIVES;
    public final int VULNERABILITY_COEFFICIENT;
    public final int INJURY_EFFECT;

    Difficulty(int INITIAL_LIVES, int VULNERABILITY_COEFFICIENT, int INJURY_EFFECT) {
        this.INITIAL_LIVES = INITIAL_LIVES;
        this.VULNERABILITY_COEFFICIENT = VULNERABILITY_COEFFICIENT;
        this.INJURY_EFFECT = INJURY_EFFECT;
    }
}
