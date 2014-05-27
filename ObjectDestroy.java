package UI;

import UI.Map;
import UI.Life;
import UI.Coin;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ObjectDestroy {

    int time;
    Map map;

    public ObjectDestroy(int time, Coin c) {
        this.time = time;
        final Coin coin = c;
        map = Map.getInstance();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            // Variables as member variables instead of local variables in main()
            @Override
            public void run() {
                System.out.println("before remove " + map.getCoin().size());
                map.getCoin().remove(coin);
                coin.setLife(0);
                coin.setValue(0);
                System.out.println("removed coin " + map.getCoin().size());

            }
        }, time);
    }

    public ObjectDestroy(int time, Life l) {
        this.time = time;
        final Life life = l;
        map = Map.getInstance();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            // Variables as member variables instead of local variables in main()
            @Override
            public void run() {
                map.getLife().remove(life);
                life.setLife(0);
            }
        }, time);
    }
}
