import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.Random;

/**
 * Implementation of Functionalities of a flying platform
 *
 * @author Miles Li
 * @version 1.0
 * @since 25/04/2024
 */
public class FlyPlatform extends SolidBody
{
    private final Image FLY_PLATFORM;
    private final double RELATIVE_VELOCITY;
    private final double MAX_DISPLACEMENT;
    private final double HALF_HEIGHT;
    private final double HALF_LENGTH;
    private boolean toRight;
    private double displacement;

    private static final Random random = new Random();

    public FlyPlatform(double x, double y)
    {
        super(x, y);
        Properties appProp = new Properties();
        displacement = 0;
        toRight = random.nextBoolean();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        FLY_PLATFORM = new Image(appProp.getProperty("gameObjects.flyingPlatform.image"));
        RELATIVE_VELOCITY = Double.parseDouble(appProp.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        MAX_DISPLACEMENT = Double.parseDouble(appProp.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        HALF_HEIGHT = Double.parseDouble(appProp.getProperty("gameObjects.flyingPlatform.halfHeight"));
        HALF_LENGTH = Double.parseDouble(appProp.getProperty("gameObjects.flyingPlatform.halfLength"));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.flyingPlatform.speed")));
    }

    /** Draw flying platforms */
    public void drawFlyPlatform()
    {
        double x = super.getX();
        double y = super.getY();
        FLY_PLATFORM.draw(x, y);
    }

    /**
     * Flying Platform move
     *
     * @param player the player in the game
     * @param platform the platform in the game
     */
    @Override
    public void move(Player player, Platform platform)
    {
        double x = super.getX();
        if (displacement >= MAX_DISPLACEMENT || displacement <= -MAX_DISPLACEMENT)
            toRight = !toRight;
        if (toRight)
        {
            x += RELATIVE_VELOCITY;
            displacement += RELATIVE_VELOCITY;
        }
        else
        {
            x -= RELATIVE_VELOCITY;
            displacement -= RELATIVE_VELOCITY;
        }
        super.setX(x);
        super.move(player, platform);
    }

    /**
     * Check if player is stand on the platform
     *
     * @param player the player in the game
     */
    public void beStoodOn(Player player)
    {
        double platFormTopY = super.getY() - HALF_HEIGHT;
        double playerX = player.getX();

        /*
        * If player is in the range of a flying platform,
        * change the ground level relative to player
        **/
        if ((Math.abs(playerX - super.getX()) <= HALF_LENGTH) &&
                ((super.getY() - player.getY()) >= HALF_HEIGHT))
        {
            if (player.getHighestLevel() >= platFormTopY)
            {
                player.setGroundLevel(platFormTopY);
                player.setHighestLevel(platFormTopY);
            }
        }
    }

    /**
     * Update flying platform in the game
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawFlyPlatform();
        this.beStoodOn(player);
    }
}
