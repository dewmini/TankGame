package UI;

import java.net.*;
import java.io.*;
import java.util.*;
import org.newdawn.slick.AppGameContainer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MyServer extends Thread {

    private ServerSocket serSocket;
    Socket serverSideSocket = new Socket();
    MyClient client = new MyClient();
    BufferedReader gameOutPut;
    String response = "";
    String turn;//Direction to turn 
    int listenPort = 7000;
    int currentDirection, move;//current direction and direction to move
    int pathCounter;//use to go through the path vector
    Vertex current;//holds the current vertex
    sGame simpleGame;
    BFS bfs;
    Vector<Vertex> pathVertices;
    int check;

    public MyServer(sGame sg) {
        this.simpleGame = sg;
        bfs = new BFS();
        pathVertices = new Vector<Vertex>();
        this.currentDirection = 0;
        current = bfs.grid[0][0];
        check = 0;
    }

    public void run() {
        StringTokenizer str1;
        try {
            serSocket = new ServerSocket(7000);
            while (true) {
                serverSideSocket = serSocket.accept();
                gameOutPut = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                response = gameOutPut.readLine();
                System.out.println(response);
                this.simpleGame.stringProcessor(response);

                //Set the game grid at the initializing response
                if ((response.charAt(0) + "").equals("I")) {
                    str1 = new StringTokenizer(response.substring(5, response.length() - 1), ":");

                    bfs.setGrid(str1.nextToken(), "B");
                    bfs.setGrid(str1.nextToken(), "S");
                    bfs.setGrid(str1.nextToken(), "W");
                    bfs.setTree(0, 0, 12, 19, pathVertices);
                    pathCounter = pathVertices.size() - 1;
                }


                //go along the path


                serverSideSocket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void giveCommands(String gameComnd) {
        System.out.println(gameComnd);
        client.clientConnection(gameComnd);
    }

    public String calculatedTurns(Vertex currentPos, Vertex nextPos) {
        int checkX;
        int checkY;

        checkX = nextPos.xCord - currentPos.xCord;
        checkY = nextPos.yCord - currentPos.yCord;

        if (checkX == 0 && checkY == -1) {
            return "UP#";
        } else if (checkX == 1 && checkY == 0) {
            return "RIGHT#";
        } else if (checkX == 0 && checkY == 1) {
            return "DOWN#";
        } else if (checkX == -1 && checkY == 0) {
            return "LEFT#";
        } else {
            return "Error";
        }
    }


}
