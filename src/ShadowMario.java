import bagel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 * Please enter your name below
 * @author Miles Li
 * @version 2.0
 * @since 30/03/2024
 */
public class ShadowMario extends AbstractGame
{
    private static ShadowMario __instance__ = null;
    private final Image BACKGROUND_IMAGE;
    private boolean gameStarted;
    private boolean showingMessage;

    private Level level = new Level1();

    /* Getters and setters*/
    public boolean isGameStarted() {return gameStarted;}
    public boolean isShowingMessage() {return showingMessage;}

    public void setGameStarted(boolean gameStarted) {this.gameStarted = gameStarted;}
    public void setShowingMessage(boolean showingMessage) {this.showingMessage = showingMessage;}

    /**
     * The constructor
     */
    private ShadowMario(Properties game_props, Properties message_props)
    {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
                Integer.parseInt(game_props.getProperty("windowHeight")),
                message_props.getProperty("title"));
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        // you can initialise other values from the property files here
        gameStarted = false;
        showingMessage = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args)
    {
        Properties game_props = new Properties();
        Properties message_props = new Properties();

        try
        {
            game_props.load(new FileInputStream("res/app.properties"));
            message_props.load(new FileInputStream("res/message_en.properties"));
        } catch(IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }

        ShadowMario game = ShadowMario.getInstance(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input)
    {
        // close window
        if (input.wasPressed(Keys.ESCAPE))
        {
            Window.close();
        }

        // Set up the background
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        /* When game started, do below */
        if (gameStarted)
        {
            level.update(input);
        }

        // Show the starting message and detect if game starts
        gameEndController(input);
    }

    /**
    * Set ShadowMario as a singleton
    * get instance
    */
    public static ShadowMario getInstance(Properties game_props, Properties message_props)
    {
        if (__instance__ == null)
            __instance__ = new ShadowMario(game_props, message_props);
        return __instance__;
    }

    /* Functionalities of Message */
    private void gameEndController(Input input)
    {
        if (input.wasPressed(Keys.NUM_1) && !gameStarted && !showingMessage)
        {
            gameStarted = true;
            level = new Level1();
            level.setUpLevel("level1File");
        }
        else if (input.wasPressed(Keys.NUM_2) && !gameStarted && !showingMessage)
        {
            gameStarted = true;
            level = new Level2();
            level.setUpLevel("level2File");
        }
        else if (input.wasPressed(Keys.NUM_3) && !gameStarted && !showingMessage)
        {
            gameStarted = true;
            level = new Level3();
            level.setUpLevel("level3File");
        }

        level.gameController(this, input);
    }
}