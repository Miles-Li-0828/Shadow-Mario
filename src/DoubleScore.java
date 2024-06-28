import bagel.Image;
import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implement functionalities of Double Score Power
 *
 * @author Miles Li
 * @version 1.0
 * @since 25/04/2024
 */
public class DoubleScore extends Collectable
{
    private final Image DOUBLE_SCORE;
    private boolean collected;
    private final double MAX_FRAMES;

    public DoubleScore(double x, double y)
    {
        super(x, y);
        Properties appProp = new Properties();
        collected = false;
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        DOUBLE_SCORE = new Image(appProp.getProperty("gameObjects.doubleScore.image"));
        MAX_FRAMES = Double.parseDouble(appProp.getProperty("gameObjects.doubleScore.maxFrames"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.doubleScore.radius")));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.doubleScore.speed")));
    }

    /** Draw Double Score Cherries */
    public void drawDoubleScore()
    {
        double x = super.getX();
        double y = super.getY();
        DOUBLE_SCORE.draw(x, y);
    }

    /**
     * Collect Double Score Cherry, Get superpower
     *
     * @param player the player in the game
     */
    @Override
    public void collect(Player player)
    {
        if (collide(player) && !collected)
        {
            player.setDoubleScoreTime(MAX_FRAMES);
            collected = true;
        }
    }

    /**
     * Override the move method, if collected, fly away
     *
     * @param player the player in the game
     * @param platform the platform in the game
     */
    @Override
    public void move(Player player, Platform platform)
    {
        super.move(player, platform);
        if (collected)
        {
            double COLLECTED_SPEED = -10;
            super.setY(super.getY() + COLLECTED_SPEED);
        }
    }

    /**
     * Update functionalities of double score power
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawDoubleScore();
        this.collect(player);
    }
}
