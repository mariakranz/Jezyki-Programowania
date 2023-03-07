package Zadanie2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
public class Leaf {
    public static final int CELLS_INCREMENTATION_TIME = 1000;
    private static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 10;
    public static Rectangle boardSize;
    public static ArrayList<ArrayList<AtomicInteger>> cells;


    Leaf(int width, int height, int numberOfSnails){
        boardSize = new Rectangle(width,height);
        cells = new ArrayList<>(height);    //rzedy

        Random random = new Random();

        for(int i = 0; i<width; i++){
            ArrayList<AtomicInteger> row = new ArrayList<>(height);     //row=wiersz (y)
            for(int j = 0; j<height; j++){
                row.add(new AtomicInteger(random.nextInt(MIN_VALUE,MAX_VALUE+1)));
            }
            cells.add(row);     //cells to kolumny (x)          [0kolumna,1wiersz]--> [0,1]-> [x,y]
        }

        for(int i=0; i<numberOfSnails; i++){
            new Snail(this);
        }
        UpdatingThread update = new UpdatingThread(this);
        update.start();
    }

    public synchronized void incrementCells(){
        cells.forEach(row -> row.forEach(value->{
                if(value.get()<MAX_VALUE) value.incrementAndGet();
            }));
    }

    public synchronized void decrementCell(Point cellPosition){  //zmniejsza wartosc 1 komorki z przedkoscia v

        if(cells.get(cellPosition.x).get(cellPosition.y).get()>MIN_VALUE) cells.get(cellPosition.x).get(cellPosition.y).decrementAndGet();
    }
}

