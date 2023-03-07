package Zadanie2;

import static Zadanie2.Leaf.CELLS_INCREMENTATION_TIME;

public class UpdatingThread extends Thread{
    Leaf leaf;
    UpdatingThread(Leaf leaf){
        this.leaf = leaf;
    }
    @Override
    public void run() {
        super.run();

        while(true) {
            //inkrementuje wartosci komorek, blokuje wtedy liść dla innych watkow
            leaf.incrementCells();
            try {
                sleep(CELLS_INCREMENTATION_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
