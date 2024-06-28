import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implement functionalities of coins
 *
 * @author Miles Li
 * @version 2.0
 * @since 02/04/2024
 */
public class Coin extends Collectable
{
    private final Image COIN;
    private boolean collected;
    private final int SCORE_SIZE;

    public Coin(double x, double y)
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

        COIN = new Image(appProp.getProperty("gameObjects.coin.image"));
        SCORE_SIZE = Integer.parseInt(appProp.getProperty("gameObjects.coin.value"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.coin.radius")));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.coin.speed")));
    }

    /** Draw coins */
    public void drawCoin()
    {
        double x = super.getX();
        double y = super.getY();
        COIN.draw(x, y);
    }

    /**
     * Handles the collection of the coin by the player.
     *
     * @param player the player in the game
     */
    @Override
    public void collect(Player player)
    {
        if (collide(player) && !collected)
        {
            if (player.getDoubleScoreTime() > 0)
                player.setScore(player.getScore() + 2 * SCORE_SIZE);
            else
                player.setScore(player.getScore() + SCORE_SIZE);
            collected = true;
        }
    }

    /**
     * Override the move method, if collected, fly away
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * */
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
     * Update functionalities of Coins
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     * */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawCoin();
        this.collect(player);
    }
}
