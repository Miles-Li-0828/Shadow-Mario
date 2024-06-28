import bagel.Input;

/**
 * Implementation of all collectable entities
 *
 * @author Miles Li
 * @version 2.0
 */
abstract class Collectable extends SolidBody
{
    public Collectable(double x, double y)
    {
        super(x, y);
    }

    /**
     * Player collect the entity
     *
     * @param player the player in the game
    */
    public abstract void collect(Player player);

    /**
     * Functionalities of collectable bodies
     *
     * @param player the player in the game
     * @param platform the platform in the game
     * @param input event handing
     * */
    @Override
    public void update(Input input, Player player, Platform platform)
    {
        super.update(input, player, platform);
    }
}
