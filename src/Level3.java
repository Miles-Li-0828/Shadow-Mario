import bagel.Input;
import bagel.Keys;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * The implementation of level 3
 * Since level 3 just expand the entities from level 2,
 * this class inherit from level 2
 *
 * @author Miles Li
 * @version 1.0
 */
public class Level3 extends Level2
{
    private double bossDistance;
    private final int VALID_ATTACK_RAD;
    private final int BOSS_ATTACK_ROUND = 100;
    private final Random random = new Random();
    private int frameCount;

    public Level3()
    {
        super();
        Properties prop = new Properties();
        try
        {
            prop.load(new FileInputStream("res/app.properties"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        VALID_ATTACK_RAD = Integer.parseInt(prop.getProperty("gameObjects.enemyBoss.activationRadius"));
        frameCount = 0;
    }

    /**
     * Update the game
     *
     * @param input keyboard event handling
     */
    @Override
    public void update(Input input)
    {
        super.update(input);
        this.playerFunc(input);

        frameCount++;

        // Boss functionalities
        super.getMessages().enemyBossStatus(super.getBoss());
        if (frameCount == super.getBoss().getRandomToken()
                && bossDistance <= VALID_ATTACK_RAD && super.getBoss().isAlive())
            super.getBoss().attack(false);
        else if (frameCount == BOSS_ATTACK_ROUND)
            super.getBoss().setRandomToken(random.nextInt(BOSS_ATTACK_ROUND + 1));
        super.getBoss().update(input, super.getPlayer(), super.getPlatform());

        if (frameCount == BOSS_ATTACK_ROUND)
            frameCount = 0;
    }

    /**
     * The functionalities of player
     *
     * @param input keyboard event handling
     */
    @Override
    public void playerFunc(Input input)
    {
        bossDistance = Math.sqrt(Math.pow(super.getPlayer().getY() - super.getBoss().getY(), 2) +
                Math.pow(super.getPlayer().getX() - super.getBoss().getX(), 2));

        // Shoot fireball
        if (input.wasPressed(Keys.S) && bossDistance <= VALID_ATTACK_RAD)
            super.getPlayer().attack(super.getPlayer().isFaceRight());
        super.getPlayer().shootFireballs(super.getPlatform(), super.getBoss());
    }

    /**
     * Control the process of game
     *
     * @param shadowMario The object of ShadowMario game
     * @param input Keyboard handling
     */
    @Override
    public void gameController(ShadowMario shadowMario, Input input)
    {
        if (!shadowMario.isGameStarted() && !shadowMario.isShowingMessage())
        {
            super.getMessages().beforeStartMessage();
        }
        else if (!super.getPlayer().isAlive() && super.getPlayer().outOfTheWindow())
        {
            shadowMario.setGameStarted(false);
            super.getMessages().loseMessage();
            shadowMario.setShowingMessage(!input.wasPressed(Keys.SPACE) || shadowMario.isGameStarted());
        }
        else if (!super.getBoss().isAlive() && super.getEndFlag().collide(super.getPlayer()))
        {
            shadowMario.setGameStarted(false);
            super.getMessages().winMessage();
            shadowMario.setShowingMessage(!input.wasPressed(Keys.SPACE) || shadowMario.isGameStarted());
        }
    }
}
