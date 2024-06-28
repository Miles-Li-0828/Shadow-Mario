import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

/**
 * Implementation of Enemies and their functionalities
 * Added the random movement
 *
 * @author Miles Li
 * @version 2.0
 * @since 25/04/2024
 */
public class Enemy extends SolidBody implements Harmful
{
    private final Image ENEMY;
    private boolean harmful;
    private boolean toRight;
    private final double DAMAGE;
    private final double RELATIVE_VELOCITY;
    private final double MAX_DISPLACEMENT;
    private double displacement;

    private static final Random random = new Random();

    public Enemy(double x, double y)
    {
        super(x, y);
        Properties appProp = new Properties();
        harmful = true;
        displacement = 0;
        toRight = random.nextBoolean();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        ENEMY = new Image(appProp.getProperty("gameObjects.enemy.image"));
        DAMAGE = Double.parseDouble(appProp.getProperty("gameObjects.enemy.damageSize"));
        RELATIVE_VELOCITY = Double.parseDouble(appProp.getProperty("gameObjects.enemy.randomSpeed"));
        MAX_DISPLACEMENT = Double.parseDouble(appProp.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.enemy.radius")));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.enemy.speed")));
    }

    /** Draw enemies */
    public void drawEnemy()
    {
        double x = super.getX();
        double y = super.getY();
        ENEMY.draw(x, y);
    }

    /** Enemy moves
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
     * Damage player if collided
     *
     * @param body life/ viable body like player or boss
     */
    @Override
    public void damage(Vitality body)
    {
        if (body instanceof Player)
        {
            Player player = (Player) body;
            if (collide(player) && harmful)
            {
                if (player.getInvincibleTime() <= 0)
                    player.setHealth(player.getHealth() - DAMAGE);
                harmful = false;
            }
        }
    }

    /**
     * Update all functionalities of enemy
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawEnemy();
        this.damage(player);
    }
}
