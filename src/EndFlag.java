import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implementation of end flag and its functionality
 *
 * @author Miles Li
 * @version 1.0
 * @since 01/04/2024
 */
public class EndFlag extends SolidBody
{
    private final Image END_FLAG;

    // Constructor
    public EndFlag(double x, double y)
    {
        super(x, y);
        Properties appProp = new Properties();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        END_FLAG = new Image(appProp.getProperty("gameObjects.endFlag.image"));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.endFlag.speed")));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.endFlag.radius")));
    }

    /** Draw the end flag */
    public void drawEndFlag()
    {
        double x = super.getX();
        double y = super.getY();
        END_FLAG.draw(x, y);
    }

    /**
     * EndFlag's functionalities
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawEndFlag();
    }
}
