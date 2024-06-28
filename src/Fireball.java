import bagel.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Implementation of Fireballs and their functionalities
 * Added the random movement
 *
 * @author Miles Li
 * @version 1.0
 * @since 27/04/2024
 */
public class Fireball extends SolidBody implements Harmful
{
    private final Image FIREBALL;
    private final double WINDOW_WIDTH;
    private boolean harmful;
    private final boolean toRight;
    private final double DAMAGE;
    private final double FLY_VELOCITY;

    public Fireball(double x, double y, boolean toRight)
    {
        super(x, y);
        Properties appProp = new Properties();
        harmful = true;
        this.toRight = toRight;
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        FIREBALL = new Image(appProp.getProperty("gameObjects.fireball.image"));
        DAMAGE = Double.parseDouble(appProp.getProperty("gameObjects.fireball.damageSize"));
        FLY_VELOCITY = Double.parseDouble(appProp.getProperty("gameObjects.fireball.speed"));
        WINDOW_WIDTH = Double.parseDouble(appProp.getProperty("windowWidth"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.fireball.radius")));
        // Set the relative speed
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.platform.speed")));
    }

    public boolean isHarmful() {return harmful;}

    /** Draw fireballs */
    public void drawFireball()
    {
        double x = super.getX();
        double y = super.getY();
        if (harmful)
            FIREBALL.draw(x, y);
    }

    /**
     * Fireball moves
     *
     * @param player the player in the game
     * @param platform the platform in the game
     */
    @Override
    public void move(Player player, Platform platform)
    {
        double x = super.getX();
        if (x > WINDOW_WIDTH || x < 0)
            this.harmful = false;

        if (toRight)
            x += FLY_VELOCITY;
        else
            x -= FLY_VELOCITY;
        super.setX(x);
        super.move(player, platform);
    }

    /**
     * Make damage on a viable entity
     *
     * @param target target of attack
     */
    @Override
    public void damage(Vitality target)
    {
        if (target instanceof Player)
        {
            Player player = (Player) target;
            if (collide(player) && harmful)
            {
                if (player.getInvincibleTime() <= 0)
                    player.setHealth(player.getHealth() - DAMAGE);
                harmful = false;
            }
        }

        // Enemy boss below
        if (target instanceof EnemyBoss)
        {
            EnemyBoss boss = (EnemyBoss) target;
            if (collide(boss) && harmful && boss.isAlive())
            {
                boss.setHealth(boss.getHealth() - DAMAGE);
                harmful = false;
            }
        }
    }
}
