/**
 * Interface for attackers
 *
 * @author Miles Li
 * @version 1.0
 */
public interface Attacker
{
    /** Attack
     *
     * @param toRight the boolean to test if attack is to right direction
     */
    public void attack(boolean toRight);

    /** Check if the attack is valid */
    public void checkValidAttack();
}
