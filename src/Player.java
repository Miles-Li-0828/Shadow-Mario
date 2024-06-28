import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Implementation of player and functionality
 *
 * @author Miles Li
 * @version 2.0
 * @since 25/04/2024
 */
public class Player implements Vitality, Attacker
{
    private final Image PLAYER_FACE_RIGHT;
    private final Image PLAYER_FACE_LEFT;
    private final static double JUMP_VELOCITY = -20;
    private final static double GRAVITY_ACCELERATION = 1;
    private final static double DIE_VELOCITY = 2;
    private final double RADIUS, WINDOW_HEIGHT;
    private int score;
    private double x, y;
    private double highestLevel;
    private double groundLevel;
    private final double iniLevel;
    private double yVelocity;
    private double health;
    private double doubleScoreTime, invincibleTime;
    private boolean faceRight;
    private boolean alive;
    private boolean movingToRight, movingToLeft, grounded;
    private final List<Fireball> fireballs;

    /* Constructor */
    public Player(double x, double y)
    {
        Properties appProp = new Properties();
        this.x = x;
        this.y = y;
        yVelocity = 0;
        score = 0;
        doubleScoreTime = 0;
        invincibleTime = 0;
        faceRight = true;
        alive = true;
        grounded = true;
        movingToRight = movingToLeft = false;
        fireballs = new ArrayList<>();
        try
        {
            BufferedReader propertiesBuffer = new BufferedReader(new FileReader("res/app.properties"));
            appProp.load(propertiesBuffer);
        }
        catch (Exception e) {e.getStackTrace();}

        PLAYER_FACE_RIGHT = new Image(appProp.getProperty("gameObjects.player.imageRight"));
        PLAYER_FACE_LEFT = new Image(appProp.getProperty("gameObjects.player.imageLeft"));
        WINDOW_HEIGHT = Double.parseDouble(appProp.getProperty("windowHeight"));
        health = Double.parseDouble(appProp.getProperty("gameObjects.player.health"));
        RADIUS = Double.parseDouble(appProp.getProperty("gameObjects.player.radius"));
        iniLevel = y;
        groundLevel = y;
        highestLevel = y;
    }

    /**
     * Getters and Setters
     */
    public int getScore() {return score;}
    public double getHealth() {return health;}
    public boolean isAlive() {return alive;}
    public boolean isMovingToRight() {return movingToRight;}
    public boolean isMovingToLeft() {return movingToLeft;}
    public boolean isGrounded() {return grounded;}
    public boolean isFaceRight() {return faceRight;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getRadius() {return RADIUS;}
    public double getIniLevel() {return iniLevel;}
    public double getDoubleScoreTime() {return doubleScoreTime;}
    public double getInvincibleTime() {return invincibleTime;}
    public double getHighestLevel() {return highestLevel;}
    public List<Fireball> getFireballs() {return fireballs;}

    public void setScore(int score) {this.score = score;}
    public void setY(double y) {this.y = y;}
    public void setHealth(double health) {this.health = health;}
    public void setFaceRight(boolean faceRight) {this.faceRight = faceRight;}
    public void setMovingToRight(boolean movingToRight) {this.movingToRight = movingToRight;}
    public void setMovingToLeft(boolean movingToLeft) {this.movingToLeft = movingToLeft;}
    public void setDoubleScoreTime(double doubleScoreTime) {this.doubleScoreTime = doubleScoreTime;}
    public void setInvincibleTime(double invincibleTime) {this.invincibleTime = invincibleTime;}
    public void setGroundLevel(double groundLevel) {this.groundLevel = groundLevel;}
    public void setHighestLevel(double highestLevel) {this.highestLevel = highestLevel;}

    /** Draw player in the screen */
    public void drawPlayer()
    {
        if (!faceRight)
            PLAYER_FACE_LEFT.draw(x, y);
        else
            PLAYER_FACE_RIGHT.draw(x, y);
    }

    /** Jump */
    public void jump()
    {
        yVelocity += JUMP_VELOCITY;
    }


    /** The physical engine for jumping */
    public void physicalEngine()
    {
        if (doubleScoreTime > 0)
            doubleScoreTime--;
        if (invincibleTime > 0)
            invincibleTime--;

        /* Based on the concept of Game timing system, detect the action of falling first */
        // If it is not falling down
        if (!(y >= groundLevel && yVelocity >= 0 && alive))
        {
            grounded = false;
            y += yVelocity;
            yVelocity += GRAVITY_ACCELERATION;
        }

        // If it is falling down
        if (y >= groundLevel && yVelocity >= 0 && alive)
        {
            this.grounded = true;
            yVelocity = 0;
            y = groundLevel;
            if (groundLevel == iniLevel)
                this.highestLevel = iniLevel;
        }
        // If player die, drop out of the screen
        else if (!alive)
        {
            yVelocity = DIE_VELOCITY;
            y += yVelocity;
        }
    }

    /** Player Die */
    @Override
    public void ifDie()
    {
        if (this.health <= 0)
        {
            this.alive = false;
        }
    }

    /** Check if player is still in the window */
    public boolean outOfTheWindow() {return this.y >= WINDOW_HEIGHT;}

    /**
     * Player attack
     *
     * @param toRight if attack is to right direction
     */
    @Override
    public void attack(boolean toRight)
    {
        fireballs.add(new Fireball(x, y, toRight));
    }

    /** Cheek if player's attack is valid */
    @Override
    public void checkValidAttack()
    {
        fireballs.removeIf(fireball -> !fireball.isHarmful());
    }

    /**
     * Player functionalities
     *
     * @param input keyboard event handling
     */
    public void update(Input input)
    {
        /* Set the motion of players */
        this.drawPlayer();
        this.physicalEngine();
        this.ifDie();

        // Move the player
        if (input.isDown(Keys.RIGHT) && this.isAlive())
        {
            this.setMovingToRight(true);
            this.setMovingToLeft(false);
            this.setFaceRight(true);
        }
        else if (input.isDown(Keys.LEFT) && this.isAlive())
        {
            this.setMovingToLeft(true);
            this.setMovingToRight(false);
            this.setFaceRight(false);
        }
        else
        {
            this.setMovingToLeft(false);
            this.setMovingToRight(false);
        }

        // Player jump
        if (this.isGrounded() && input.isDown(Keys.UP))
        {
            this.jump();
        }
    }

    /**
     * Shoot fireballs
     *
     * @param boss the enemy boss in the game
     * @param platform the platform in the game
     */
    public void shootFireballs(Platform platform, EnemyBoss boss)
    {
        for (Fireball fireball: this.getFireballs())
        {
            fireball.drawFireball();
            fireball.move(this, platform);
            fireball.damage(boss);
        }
        this.checkValidAttack();
    }
}

