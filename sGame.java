package UI;

import java.util.*;
import org.newdawn.slick.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class sGame extends BasicGame {

    Image brick, land, stone, water, tank0, tank1, tank2, tank3, tank4,coins,health;
    private String response, resType;
    SpriteSheet tileSheet;
    SpriteSheet landSheet;
    SpriteSheet brickTilesht;
    SpriteSheet waterTilesht;
    SpriteSheet stoneTilesht;
    SpriteSheet tanks;
    TiledMap map;
    String playerNum;
    String bricksArr[], stonesArr[], waterArr[];
    Vector<Tank> tankDetails;
    Vector<Brick> brickDetails;
    Vector<Stone> stoneDetails;
    Vector<Water> waterDetails;
    Vector<CoinPile> coinPileDetails;
    Vector<Health> healthDetails;
    float count = 0;
    int x;
    int y;
    MyServer srvr;
    
    //New Timer
    Timer timer;
    MyTimer mt;

    public sGame() {
        super("TankFighters");
        brickDetails = new Vector<Brick>();
        stoneDetails = new Vector<Stone>();
        waterDetails = new Vector<Water>();
        tankDetails = new Vector<Tank>();
        coinPileDetails = new Vector<CoinPile>();
        healthDetails = new Vector<Health>();
        
        mt=new MyTimer();
        
        //New Timer
        timer=new Timer();
        timer.schedule(mt,0);
        
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        map = new TiledMap("Images/test1.tmx");
        tileSheet = new SpriteSheet("Images/mapBasic.png", 32, 32);
        tanks = new SpriteSheet("Images/MulticolorTanks.png", 32, 32);
        brickTilesht=new SpriteSheet("Images/brickwall1.png", 32, 32);
        stoneTilesht=new SpriteSheet("Images/StoneWall.jpg", 32,32);
        waterTilesht=new SpriteSheet("Images/water.png", 32,32);
        
        brick=brickTilesht.getSprite(1,1);
        land = tileSheet.getSprite(1, 11); 
        stone = stoneTilesht.getSprite(0,1);
        water = waterTilesht.getSprite(0, 0);
        tank0 = tanks.getSprite(0, 1);
        tank1 = tanks.getSprite(0, 2);
        tank2 = tanks.getSprite(0, 0);
        tank3 = tanks.getSprite(0, 3);
        tank4 = tanks.getSprite(0, 4);
        coins=new Image("Images/dollar_coin.png");
        health=new Image("Images/health1.png");
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {


        count += delta / 1000f;
        if (count >= 1.0) {
            x += 32;
            y += 32;
            count = 0;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {

        map.render(0,0);
        Brick brk;
        Stone stne;
        Water wtr;
        Tank tnk;
        CoinPile cp;
        Health hlth;
        
        //g.d

       //System.err.println(mt.getTimerVal());

        if (!this.brickDetails.isEmpty()) {
            for (int a = 0; a < this.brickDetails.size(); a++) {
                brk = this.brickDetails.get(a);
                brick.draw(brk.getxCord(), brk.getyCord());
            }
        }

        if (!this.stoneDetails.isEmpty()) {
            for (int a = 0; a < this.stoneDetails.size(); a++) {
                stne = this.stoneDetails.get(a);
                stone.draw(stne.getxCord(), stne.getyCord());
            }
        }

        if (!this.waterDetails.isEmpty()) {
            for (int a = 0; a < this.waterDetails.size(); a++) {
                wtr = this.waterDetails.get(a);
                water.draw(wtr.getxCord(), wtr.getyCord());
            }
        }

        if (!this.tankDetails.isEmpty()) {
            for (int a = 0; a < this.tankDetails.size(); a++) {
                tnk = this.tankDetails.get(a);
                if(tankDetails.get(a).rotate){
                    tankDetails.get(a).getTankImg().rotate(tankDetails.get(a).getAngel());
                    //System.out.println(tankDetails.get(a).getAngel());
                    tankDetails.get(a).rotate=false;
                }
                g.setColor(Color.pink);
                g.drawString(tankDetails.get(a).name,724, 134+a*32);
                g.drawString(tankDetails.get(a).getCoins()+"", 782, 134+a*32);
                g.drawString(tankDetails.get(a).getCoins()+"",858, 134+a*32);
                
                if(tankDetails.get(a).getHealth()<50&&tankDetails.get(a).getHealth()>25){
                    g.setColor(Color.orange);
                    
                }
                else if(tankDetails.get(a).getHealth()<25){
                    g.setColor(Color.red);
                    
                }
                else{
                    g.setColor(Color.decode("0x33cc00"));
                }
                g.drawString(tankDetails.get(a).getHealth()+"",938, 134+a*32);
                
                tankDetails.get(a).getTankImg().draw(tnk.getxCord(), tnk.getyCord());
                
            }
        }
        
        
        if (!this.coinPileDetails.isEmpty()) {
            for (int a = 0; a < this.coinPileDetails.size(); a++) {
                cp = this.coinPileDetails.get(a);
                if(!(mt.getTimerVal()>=cp.getAppearTime()+cp.getLifeTime()/1000)){
                    cp.getPileImage().draw(cp.getxCord(),cp.getyCord(),32,32);
                }
                else{
                    coinPileDetails.remove(a);
                }
            }
        }
        
        
        if (!this.healthDetails.isEmpty()) {
            for (int a = 0; a < this.healthDetails.size(); a++) {
                hlth = this.healthDetails.get(a);
                if(!(mt.getTimerVal()>=hlth.getAppearTime()+hlth.getLifeTime()/1000)){
                    hlth.getPileImage().draw(hlth.getxCord(),hlth.getyCord(),32,32);
                }
                else{
                    healthDetails.remove(a);
                }
            }
        }

        g.setColor(Color.red);
        for (int a = 0; a <= 640; a += 32) {
            g.drawLine(0, a, 640, a);
            g.drawLine(a, 0, a, 640);
        }
         g.setColor(Color.yellow);
         g.drawRect(704, 96, 288, 192);
         
         for(int a=1;a<6;a++){
             g.drawLine(704,96+32*a+1, 992, 96+32*a+1);
         }
         g.drawLine(768,96,768,288);
         g.drawLine(848,96,848,288);
         g.drawLine(928,96,928,288);   
         
         g.setColor(Color.pink);
         g.drawString("Score Board",800,70);
         
         g.setColor(Color.cyan);
         g.drawString("Player",710,102);
         g.drawString("Coins$",788,102);
         g.drawString("Points",858,102);
         g.drawString("Health",933,102);
         
         g.setColor(Color.yellow);
         g.drawString("Our Player " +playerNum,700,320);
         g.drawString("Members:",700,359);
         g.drawString("Sudharma Priyadarshani",758,391);
         g.drawString("Yasima Dewmini",758,423);
         
         
    }

    public void setResponse(String res) {
        this.response = res;
    }

    public String getResponse() {
        return this.response;
    }

    public void stringProcessor(String str) {

        StringTokenizer str1, str2;
        String temp;
        String[] tempArr;
        Image tempImg = null;

        this.resType = str.charAt(0) + "";

        if (resType.equals("I")) {
            playerNum= "P"+str.charAt(3);
            str1 = new StringTokenizer(str.substring(5, str.length() - 1), ":");

            temp = str1.nextToken();
            this.bricksArr = temp.split(";");

            for (int a = 0; a < this.bricksArr.length; a++) {
                Brick brk = new Brick(brick);
                str2 = new StringTokenizer(bricksArr[a], ",");
                brk.setxyDamage(Integer.parseInt(str2.nextToken()) * 32, Integer.parseInt(str2.nextToken()) * 32, 0);
                brickDetails.add(brk);
            }

            temp = str1.nextToken();
            this.stonesArr = temp.split(";");
            for (int a = 0; a < this.stonesArr.length; a++) {
                Stone stne = new Stone(stone);
                str2 = new StringTokenizer(stonesArr[a], ",");
                stne.setxy(Integer.parseInt(str2.nextToken()) * 32, Integer.parseInt(str2.nextToken()) * 32);
                stoneDetails.add(stne);
            }

            temp = str1.nextToken();
            this.waterArr = temp.split(";");
            for (int a = 0; a < this.waterArr.length; a++) {
                Water wtr = new Water(water);
                str2 = new StringTokenizer(waterArr[a], ",");
                wtr.setxy(Integer.parseInt(str2.nextToken()) * 32, Integer.parseInt(str2.nextToken()) * 32);
                waterDetails.add(wtr);
            }
        } 
        
        else if (resType.equals("S")) {
            str1 = new StringTokenizer(str.substring(2, str.length() - 1), ":");
            int tokens=str1.countTokens();
            for (int a = 0; a < tokens; a++) {
                temp = str1.nextToken();
                tempArr = temp.split(";");

                if (Integer.parseInt(tempArr[0].charAt(1) + "") == 0) {
                    tempImg = tank0;
                } else if (Integer.parseInt(tempArr[0].charAt(1) + "") == 1) {
                    tempImg = tank1;
                } else if (Integer.parseInt(tempArr[0].charAt(1) + "") == 2) {
                    tempImg = tank2;
                } else if (Integer.parseInt(tempArr[0].charAt(1) + "") == 3) {
                    tempImg = tank3;
                } else if (Integer.parseInt(tempArr[0].charAt(1) + "") == 4) {
                    tempImg = tank4;
                }

                Tank tnk = new Tank(tempImg,1);
                str2 = new StringTokenizer(tempArr[1], ",");
                tnk.name = tempArr[0];
                tnk.setxyCord(Integer.parseInt(str2.nextToken()) * 32, Integer.parseInt(str2.nextToken()) * 32);
                tankDetails.add(tnk);
            }
        }
        
        else if(resType.equals("G")){
            str1 = new StringTokenizer(str.substring(2, str.length() - 1), ":");
            int tokens=str1.countTokens();
            System.out.println("$$$$$"+str.substring(2, str.length() - 1));
            
            for (int a = 0; a < tokens; a++) {
                temp = str1.nextToken();
                tempArr=temp.split(";");
                //System.out.println("Score"+tempArr[0]+"--->"+tempArr[6]);
                
                
                
                str2=new StringTokenizer(tempArr[1],",");
                if((temp.charAt(0)+"").equals("P")){
                    for(int b=0;b<tankDetails.size();b++){
                        if(tankDetails.get(b).name.equals(tempArr[0])){
                            tankDetails.get(b).setxyCord(Integer.parseInt(str2.nextToken())*32,Integer.parseInt(str2.nextToken())*32);
                            tankDetails.get(b).setAngel((Integer.parseInt(tempArr[2])-tankDetails.get(b).getDirection())*90);
                            tankDetails.get(b).setDirection(Integer.parseInt(tempArr[2]));
                            tankDetails.get(b).rotate=true;
                            
                            tankDetails.get(b).setCoinHealthPoints(Integer.parseInt(tempArr[5]),Integer.parseInt(tempArr[4]),Integer.parseInt(tempArr[6]));
                            
                            //removing acquired coin piles
                            for(int d=0;d<coinPileDetails.size();d++){
                                if((tankDetails.get(b).getxCord()==coinPileDetails.get(d).getxCord())&&(tankDetails.get(b).getyCord()==coinPileDetails.get(d).getyCord())){
                                    coinPileDetails.remove(d);
                                }
                            }
                            
                            //removing acquired health packs
                            for(int d=0;d<healthDetails.size();d++){
                                if((tankDetails.get(b).getxCord()==healthDetails.get(d).getxCord())&&(tankDetails.get(b).getyCord()==healthDetails.get(d).getyCord())){
                                    healthDetails.remove(d);
                                }
                            }
                            //System.out.println("&&&&&&&"+tempArr[2]);
                        }
                    }
                }
            }
        } 
        
        else if(resType.equals("C")&&(str.charAt(1) + "").equals(":")){
            str1 = new StringTokenizer(str.substring(2, str.length() - 1), ":");
            int tokens=str1.countTokens();
            CoinPile cp=new CoinPile(mt.getTimerVal(),coins);
            
            temp = str1.nextToken();
            str2=new StringTokenizer(temp,",");
            cp.setXY(Integer.parseInt(str2.nextToken())*32,Integer.parseInt(str2.nextToken())*32);
            cp.setLifeTime(Integer.parseInt(str1.nextToken()));
            cp.setAppearTime(mt.getTimerVal());
            coinPileDetails.add(cp);
        }
        
        else if(resType.equals("L")){
            str1 = new StringTokenizer(str.substring(2, str.length() - 1), ":");
            int tokens=str1.countTokens();
            Health hlth=new Health(mt.getTimerVal(),health);
            
            temp = str1.nextToken();
            str2=new StringTokenizer(temp,",");
            hlth.setXYCord(Integer.parseInt(str2.nextToken())*32,Integer.parseInt(str2.nextToken())*32);
            hlth.setLifeTime(Integer.parseInt(str1.nextToken()));
            hlth.setAppearTime(mt.getTimerVal());
            healthDetails.add(hlth);
        }
    }
}
