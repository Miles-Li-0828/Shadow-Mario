import bagel.Input;
import bagel.Keys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Level class used to control the levels
 *
 * @author Miles Li
 * @version 1.0
 */
public abstract class Level {
    private final Messages messages;
    private Player player;
    private EndFlag endFlag;
    private Platform platform;
    private EnemyBoss boss;
    private List<Enemy> enemies;
    private List<FlyPlatform> flyPlatforms;
    private List<Collectable> collectables;

    public Level()
    {
        messages = Messages.getInstance();
    }

    /* Getters and setters */
    public EndFlag getEndFlag() {return endFlag;}
    public EnemyBoss getBoss() {return boss;}
    public Player getPlayer() {return player;}
    public Platform getPlatform() {return platform;}
    public Messages getMessages() {return messages;}
    public List<Collectable> getCollectables() {return collectables;}
    public List<Enemy> getEnemies() {return enemies;}
    public List<FlyPlatform> getFlyPlatforms() {return flyPlatforms;}

    public void setPlayer(Player player) {this.player = player;}
    public void setPlatform(Platform platform) {this.platform = platform;}

    /**
     * Update the game
     *
     * @param input keyboard event handling
     */
    public abstract void update(Input input);

    /**
     * Read the file from levels.
     *
     * @param configFile the configuration file name
     */
    public void setUpLevel(String configFile)
    {
        enemies = new ArrayList<>();
        collectables = new ArrayList<>();
        flyPlatforms = new ArrayList<>();
        try
        {
            Properties levelProp = new Properties();
            levelProp.load(new BufferedReader(new FileReader("res/app.properties")));

            String fileName = levelProp.getProperty(configFile);

            // Read entities from scv file
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = buffer.readLine()) != null)
            {
                String[] info = line.split(",");
                String objectName = info[0];
                double x = Double.parseDouble(info[1]);
                double y = Double.parseDouble(info[2]);
                switch (objectName)
                {
                    case "PLATFORM":
                        platform = new Platform(x, y);
                        break;
                    case "PLAYER":
                        player = new Player(x, y);
                        break;
                    case "END_FLAG":
                        endFlag = new EndFlag(x, y);
                        break;
                    case "ENEMY":
                        enemies.add(new Enemy(x, y));
                        break;
                    case "COIN":
                        collectables.add(new Coin(x, y));
                        break;
                    case "DOUBLE_SCORE":
                        collectables.add(new DoubleScore(x, y));
                        break;
                    case "INVINCIBLE_POWER":
                        collectables.add(new Invincible(x, y));
                        break;
                    case "FLYING_PLATFORM":
                        flyPlatforms.add(new FlyPlatform(x, y));
                        break;
                    case "ENEMY_BOSS":
                        boss = new EnemyBoss(x, y);
                        break;
                }
            }
            buffer.close();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    /**
     * Control the process of game
     *
     * @param shadowMario The object of ShadowMario game
     * @param input Keyboard handling
     */
    public void gameController(ShadowMario shadowMario, Input input)
    {
        if (!shadowMario.isGameStarted() && !shadowMario.isShowingMessage())
        {
            messages.beforeStartMessage();
        }
        else if (!player.isAlive() && player.outOfTheWindow())
        {
            shadowMario.setGameStarted(false);
            messages.loseMessage();
            shadowMario.setShowingMessage(!input.wasPressed(Keys.SPACE) || shadowMario.isGameStarted());
        }
        else if (endFlag.collide(player))
        {
                shadowMario.setGameStarted(false);
                messages.winMessage();
            shadowMario.setShowingMessage(!input.wasPressed(Keys.SPACE) || shadowMario.isGameStarted());
        }
    }

    /**
     * The functionalities of player
     *
     * @param input keyboard event handling
     */
    public void playerFunc(Input input)
    {
        // Calculate the distance between boss and player
        messages.playerStatus(player);

        // Update everything
        player.update(input);
    }
}