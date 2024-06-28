import bagel.AbstractGame;
import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Parent class of rigid bodies which are able to move and collide with player
 *
 * @author Miles Li
 * @version 1.0
 * @since 01/04/2024
 */
public abstract class SolidBody
{
    private double x, y, radius;
    private double movingSpeed;

    public SolidBody(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters.
    public double getX() {return x;}
    public double getY() {return y;}
    public double getRadius() {return radius;}

    public void setY(double y) {this.y = y;}
    public void setX(double x) {this.x = x;}
    public void setRadius(double radius) {this.radius = radius;}
    public void setMovingSpeed(double movingSpeed) {this.movingSpeed = movingSpeed;}

    /**
     * Move the solid body
     *
     * @param player the player in the game
     * @param platform the platform in the game
     */
    public void move(Player player, Platform platform)
    {
        if (player.isMovingToRight() && platform.isMovableToLeft())
        {
            x -= movingSpeed;
        }
        else if (player.isMovingToLeft() && platform.isMovableToRight())
        {
            x += movingSpeed;
        }
    }

    /**
     * When player collide with the body
     *
     * @param body the body will be collided
     */
    public boolean collide(Vitality body)
    {
        double collideDistance;
        double distance;
        // if body is player
        if (body instanceof Player)
        {
            Player player = (Player) body;
            collideDistance = player.getRadius() + this.radius;
            distance = Math.sqrt(Math.pow(player.getY() - y, 2) + Math.pow(player.getX() - x, 2));
        }
        else
        {
            EnemyBoss boss = (EnemyBoss) body;
            collideDistance = boss.getRadius() + this.radius;
            distance = Math.sqrt(Math.pow(boss.getY() - y, 2) + Math.pow(boss.getX() - x, 2));
        }
        return distance <= collideDistance;
    }

    /**
     * Implement all the functionalities
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     */
    public void update(Input input, Player player, Platform platform)
    {
        this.move(player, platform);
    }
}
