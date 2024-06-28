import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Implementation of enemy boss and functionality
 *
 * @author Miles Li
 * @version 1.0
 * @since 27/04/2024
 */
public class EnemyBoss extends SolidBody implements Vitality, Attacker
{
    private final Image ENEMY_BOSS;
    private final static double DIE_VELOCITY = 2;
    private double health;
    private boolean alive;
    private final double WINDOW_HEIGHT;
    private List<Fireball> fireballs;
    private Random random;
    private int randomToken;
    private final static int ATTACK_ROUND = 100; // The bound of random


    public EnemyBoss(double x, double y)
    {
        super(x, y);
        alive = true;
        fireballs = new ArrayList<>();
        random = new Random();
        randomToken = random.nextInt(ATTACK_ROUND + 1);
        Properties appProp = new Properties();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        ENEMY_BOSS = new Image(appProp.getProperty("gameObjects.enemyBoss.image"));
        WINDOW_HEIGHT = Double.parseDouble(appProp.getProperty("windowHeight"));
        health = Double.parseDouble(appProp.getProperty("gameObjects.enemyBoss.health"));
        super.setRadius(Double.parseDouble(appProp.getProperty("gameObjects.enemyBoss.radius")));
        super.setMovingSpeed(Double.parseDouble(appProp.getProperty("gameObjects.enemyBoss.speed")));
    }

    public double getHealth() {return health;}
    public boolean isAlive() {return alive;}
    public int getRandomToken() {return randomToken;}

    public void setHealth(double health) {this.health = health;}
    public void setRandomToken(int randomToken) {this.randomToken = randomToken;}

    /** Draw enemy boss */
    public void drawEnemyBoss()
    {
        double x = super.getX();
        double y = super.getY();
        if (y < WINDOW_HEIGHT + super.getRadius())
        {
            ENEMY_BOSS.draw(x, y);
        }
    }

    /**
     * Boss move
     *
     * @param player the player in the game
     * @param platform the platform in the game
     */
    @Override
    public void move(Player player, Platform platform)
    {
        if (!alive)
            super.setY(super.getY() + DIE_VELOCITY);
        super.move(player, platform);
    }

    /**
     * Boss attack
     *
     * @param toRight right direction
     */
    @Override
    public void attack(boolean toRight)
    {
        fireballs.add(new Fireball(super.getX(), super.getY(), toRight));
    }

    /** Check if attack is valid */
    @Override
    public void checkValidAttack()
    {
        fireballs.removeIf(fireball -> !fireball.isHarmful());
    }

    /** check if Boss dies */
    @Override
    public void ifDie()
    {
        if (this.health <= 0)
        {
            this.alive = false;
        }
    }

    /**
     * Boss functionalities
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
        this.drawEnemyBoss();
        this.ifDie();
        for (Fireball fireball: fireballs)
        {
            fireball.drawFireball();
            fireball.move(player, platform);
            fireball.damage(player);
        }
        this.checkValidAttack();
    }
}
