package Zadanie2;

import java.awt.*;
import java.util.HashMap;

public class SnailThread extends Thread{
    int timeOdSleep; //in milliseconds
    int velocityOfEatingTheCell; //in milliseconds
    Leaf leaf;
    Snail snail;
    SnailThread(int t,  int v, Snail snail, Leaf leaf){
        this.snail = snail;
        this.leaf = leaf;
        timeOdSleep =t;
        velocityOfEatingTheCell = v;
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            HashMap<Point,Integer> possibleMoves = snail.takePointsAndValuesAroundTheSnail();
            if (possibleMoves.size()==0) {
                try {
                    sleep(timeOdSleep);     //nie wiem czy w czasie uspienia moze jesc liscia
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else snail.setCurrentPositionOfTheSnail(snail.decideNextPositionOfTheSnail(possibleMoves));

            leaf.decrementCell(snail.getCurrentPositionOfTheSnail());
            try {
                sleep(velocityOfEatingTheCell);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
