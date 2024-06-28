import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Implement everything in Level 1
 *
 * @author Miles Li
 * @version 1.0
 */
public class Level1 extends Level
{
    public Level1()
    {
        super();
    }

    /**
     * Update the game
     *
     * @param input keyboard event handling
     */
    @Override
    public void update(Input input)
    {
        /* Set the motion of players */
        super.playerFunc(input);

        /* Platform functionality */
        super.getPlatform().update(super.getPlayer());

        /* Update endFlag functionalities */
        super.getEndFlag().update(input, super.getPlayer(), super.getPlatform());

        /* Enemy functionalities */
        for (Enemy enemy: super.getEnemies())
            enemy.update(input, super.getPlayer(), super.getPlatform());

        /* Collectable stuffs functionalities */
        for (Collectable collectable: super.getCollectables())
            collectable.update(input, super.getPlayer(), super.getPlatform());
    }
}
