package game;

import UI.Client;
import UI.Client;
import UI.GameInit;
import UI.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import UI.sGame;
import UI.sGame;

public class Game {

    public Game() throws IOException, SlickException {
        sGame sg = new sGame();
        final AppGameContainer app = new AppGameContainer(sg);

        GameInit myGamePlay = new GameInit();
        Client myClient = Client.getInstance();
        Server myServer = Server.getInstance();
        
        myServer.setGame(sg);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    app.setDisplayMode(1056, 640, false);
                    app.setTargetFrameRate(26);
                    app.setShowFPS(false);
                    Display.setLocation(-1, -1);
                    app.start();
                } catch (SlickException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        th.start();

        myServer.getGamePlay(myGamePlay);
        myClient.clientAct();
        myServer.serverAct();

    }
    
    public static void main(String args[]) throws SlickException, IOException {
        Game game = new Game();
    }
}

