import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implementation of platform and functionality
 *
 * @author Miles Li
 * @version 2.0
 * @since 25/04/2024
 */
public class Platform
{
    private final Image PLATFORM;
    private double x, y;
    private final double MOVING_SPEED;
    private final double WIDTH;
    private final double WINDOW_WIDTH;
    private boolean movableToRight, movableToLeft;

    // Constructor
    public Platform(double x, double y)
    {
        this.x = x;
        this.y = y;
        movableToRight = true;
        movableToLeft = true;
        Properties appProp = new Properties();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        PLATFORM = new Image(appProp.getProperty("gameObjects.platform.image"));
        MOVING_SPEED = Double.parseDouble(appProp.getProperty("gameObjects.platform.speed"));
        WIDTH = PLATFORM.getWidth();
        WINDOW_WIDTH = Double.parseDouble(appProp.getProperty("windowWidth"));
    }

    /** Getters */
    public boolean isMovableToLeft() {return movableToLeft;}
    public boolean isMovableToRight() {return movableToRight;}

    /** Draw the platform */
    public void drawPlatform()
    {
        PLATFORM.draw(x, y);
    }

    /**
     * if player is moving, move the platform
     *
     * @param player the player in the game
     */
    public void move(Player player)
    {
        if (x <= -WIDTH / 2 + WINDOW_WIDTH)
        {
            movableToRight = true;
            movableToLeft = false;
        }
        else if (x >= WIDTH / 2)
        {
            movableToLeft = true;
            movableToRight = false;
        }
        else
        {
            movableToLeft = movableToRight = true;
        }

        // Move the platform
        if (player.isMovingToRight() && x > -WIDTH / 2 + WINDOW_WIDTH + 1)
        {
            x -= MOVING_SPEED;
        }
        else if (player.isMovingToLeft() && x < WIDTH / 2)
        {
            x += MOVING_SPEED;
        }
    }

    /**
     * Platform functionalities
     *
     * @param player player
     */
    public void update(Player player)
    {
        this.drawPlatform();
        this.move(player);
    }
}
