package Zadanie2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Snail {
    public static ArrayList<Snail> listOfSnails = new ArrayList<>();
    //*druga wersja w zasadzie nie poprawna ale nie zwraca wyjątku - pomyśleć jeszcze
    Random random = new Random();
    private Point currentPositionOfTheSnail=new Point(random.nextInt(Leaf.boardSize.width), random.nextInt((Leaf.boardSize.height)));   //pozycja poczatkowa



    Snail(Leaf leaf){

        listOfSnails.add(this);

        //*pierwotna wersja - czasem zwraca wyjątek potem w kodzie że Point jest null
        //Random random = new Random();
        //currentPositionOfTheSnail = new Point(random.nextInt(Leaf.boardSize.width), random.nextInt((Leaf.boardSize.height)));

        SnailThread snailThread = new SnailThread(Leaf.CELLS_INCREMENTATION_TIME, 200, this, leaf);
        snailThread.start();

    }

    public HashMap<Point, Integer> takePointsAndValuesAroundTheSnail(){

        int xSnail = currentPositionOfTheSnail.x;
        int ySnail = currentPositionOfTheSnail.y;

        HashMap<Point,Integer> valuesAroundTheSnail = new HashMap<>();

        int currentValue = Leaf.cells.get(currentPositionOfTheSnail.x).get(currentPositionOfTheSnail.y).intValue();     //wartosc aktualnej komorki
        int maxValue = currentValue;
        for(int x = -1; x<2; x++){
            int xColumn = xSnail+x;
            if(0<=xColumn && xColumn<=Leaf.cells.size()-1) {
                for (int y = -1; y < 2; y++) {
                    if (x == 0 && y == 0) continue; //bedzie to aktualna pozycja slimaka
                    int yRows = ySnail + y;
                    if(0<=yRows && yRows<=Leaf.cells.get(xColumn).size()-1) {
                        if (!(Leaf.cells.get(xColumn).get(yRows) == null)) {  //to raczej nie potrzebne
                            int valueOnThisPoint = Leaf.cells.get(xColumn).get(yRows).intValue();
                            if (valueOnThisPoint>currentValue && valueOnThisPoint >= maxValue && !isSnailOnThatPoint(xColumn, yRows)) {
                                maxValue = valueOnThisPoint;
                                if (!valuesAroundTheSnail.containsValue(maxValue)) valuesAroundTheSnail.clear();

                                valuesAroundTheSnail.put(new Point(xColumn, yRows), Leaf.cells.get(xColumn).get(yRows).get());

                            }

                        }
                    }
                }
            }
        }

        return valuesAroundTheSnail;
    }

    private boolean isSnailOnThatPoint(int x, int y){
        for(int i = 0; i<listOfSnails.size(); i++){
        //for(Snail snail: listOfSnails){
            if(listOfSnails.get(i).currentPositionOfTheSnail.x==x && listOfSnails.get(i).currentPositionOfTheSnail.y == y) return true;
        }
        return false;
    }

    public Point decideNextPositionOfTheSnail(HashMap<Point, Integer> possibleMoves){
        Set<Point> keys = possibleMoves.keySet();
        Point[] array = keys.toArray(new Point[keys.size()]);

        if(possibleMoves.size()>1){
            Random random = new Random();
            return array[random.nextInt(0, array.length - 1)];
        }else return array[0];
    }
    public void setCurrentPositionOfTheSnail(Point currentPositionOfTheSnail) {
        this.currentPositionOfTheSnail = currentPositionOfTheSnail;
    }
    public Point getCurrentPositionOfTheSnail() {
        return currentPositionOfTheSnail;
    }
}
