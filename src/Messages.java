import bagel.DrawOptions;
import bagel.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Show all the messages before game starts, player win hte game, and player lose the game.
 * Set Message as a Singleton since there will be only one Message class control message system
 *
 * @author Miles Li
 * @version 2.0
 * @since 28/04/2024
 */
public class Messages
{
    private Font title;
    private Font prompt;
    private final Properties messageProp;
    private final Properties appProp;
    private String fontFile;
    private static Messages __instance__ = null;

    private double windowWidth;
    private double promptWidth;

    /* Constructor */
    private Messages()
    {
        messageProp = new Properties();
        appProp = new Properties();

        // Input the properties
        try
        {
            BufferedReader messages = new BufferedReader(new FileReader("res/message_en.properties"));
            BufferedReader features = new BufferedReader(new FileReader("res/app.properties"));
            messageProp.load(messages);
            appProp.load(features);

            fontFile = appProp.getProperty("font");
            title = new Font(fontFile, Integer.parseInt(appProp.getProperty("title.fontSize")));
            windowWidth = Double.parseDouble(appProp.getProperty("windowWidth"));
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    /** Set Message as a Singleton. Get instance */
    public static Messages getInstance()
    {
        if (__instance__ == null)
            __instance__ = new Messages();
        return __instance__;
    }

    /* Show messages before game started */
    protected void beforeStartMessage()
    {
        prompt = new Font(fontFile, Integer.parseInt(appProp.getProperty("instruction.fontSize")));

        promptWidth = prompt.getWidth(messageProp.getProperty("instruction"));

        title.drawString(messageProp.getProperty("title"),
                Double.parseDouble(appProp.getProperty("title.x")),
                Double.parseDouble(appProp.getProperty("title.y")));
        prompt.drawString(messageProp.getProperty("instruction"),
                (windowWidth - promptWidth) / 2,
                Double.parseDouble(appProp.getProperty("instruction.y")));
    }

    /* Show messages when player win the game */
    protected void winMessage()
    {
        prompt = new Font(fontFile, Integer.parseInt(appProp.getProperty("message.fontSize")));

        promptWidth = prompt.getWidth(messageProp.getProperty("gameWon"));

        prompt.drawString(messageProp.getProperty("gameWon"),
                (windowWidth - promptWidth) / 2,
                Double.parseDouble(appProp.getProperty("message.y")));
    }

    /* Show messages when player lose */
    protected void loseMessage()
    {
        prompt = new Font(fontFile, Integer.parseInt(appProp.getProperty("message.fontSize")));

        promptWidth = prompt.getWidth(messageProp.getProperty("gameOver"));

        prompt.drawString(messageProp.getProperty("gameOver"),
                (windowWidth - promptWidth) / 2,
                Double.parseDouble(appProp.getProperty("message.y")));
    }

    /* Show player's status. */
    protected void playerStatus(Player player)
    {
        Font health = new Font(fontFile, Integer.parseInt(appProp.getProperty("playerHealth.fontSize")));
        Font score = new Font(fontFile, Integer.parseInt(appProp.getProperty("score.fontSize")));
        health.drawString(messageProp.getProperty("health") + Math.round(player.getHealth() * 100),
                Double.parseDouble(appProp.getProperty("playerHealth.x")),
                Double.parseDouble(appProp.getProperty("playerHealth.y")));
        score.drawString(messageProp.getProperty("score") + player.getScore(),
                Double.parseDouble(appProp.getProperty("score.x")),
                Double.parseDouble(appProp.getProperty("score.y")));
    }

    /* Show enemy boss' status. */
    protected void enemyBossStatus(EnemyBoss boss)
    {
        int red = 255, green = 0, blue = 0;
        Font health = new Font(fontFile, Integer.parseInt(appProp.getProperty("enemyBossHealth.fontSize")));
        DrawOptions colour = new DrawOptions();
        colour.setBlendColour(red, green, blue);
        health.drawString(messageProp.getProperty("health") + Math.round(boss.getHealth() * 100),
                Double.parseDouble(appProp.getProperty("enemyBossHealth.x")),
                Double.parseDouble(appProp.getProperty("enemyBossHealth.y")),
                colour);
    }
}
