import bagel.Image;
import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implement functionalities of Invincible Power
 *
 * @author Miles Li
 * @version 1.0
 * @since 25/04/2024
 */
public class Invincible extends Collectable
{
    private final Image INVINCIBLE;
    private boolean collected;
    private final double MAX_FRAMES;

    Properties appProp;
    BufferedReader propertiesBuffer;

    public Invincible(double x, double y)
    {
        super(x, y);
        appProp = new Properties();
        collected = false;
        try
        {
            propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        INVINCIBLE = new Image(appProp.getProperty("gameObjects.invinciblePower.image"));
        MAX_FRAMES = Double.parseDouble(appProp.getProperty("gameObjects.invinciblePower.maxFrames"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.invinciblePower.radius")));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.invinciblePower.speed")));
    }

    /** Draw invincible stars */
    public void drawInvinciblePower()
    {
        double x = super.getX();
        double y = super.getY();
        INVINCIBLE.draw(x, y);
    }

    /**
     * Collect Invincible stars, Get superpower
     *
     * @param player the player in the game
     */
    @Override
    public void collect(Player player)
    {
        if (collide(player) && !collected)
        {
            player.setInvincibleTime(MAX_FRAMES);
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
     * Update all the functionalities of invincible power
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawInvinciblePower();
        this.collect(player);
    }
}
