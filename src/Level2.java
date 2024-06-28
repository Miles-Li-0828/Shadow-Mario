import bagel.Input;

/**
 * The implementation of level 2
 * Since level 2 just expand the entities from level 1,
 * this class inherit from level 1
 *
 * @author Miles Li
 * @version 1.0
 */
public class Level2 extends Level1
{
    public Level2()
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
        super.update(input);

        // Flying Platform functionalities
        super.getPlayer().setGroundLevel(super.getPlayer().getIniLevel());
        for (FlyPlatform flyPlatform: super.getFlyPlatforms())
            flyPlatform.update(input, super.getPlayer(), super.getPlatform());
    }

}

