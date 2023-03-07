package Zadanie1;

public class CircleThread extends Thread {
    Circle circle;
    CircleThread(Circle circle){
        this.circle=circle;
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            long sleepTime = circle.timeOfRotation(1);
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(circle.getAngularVelocity()!=0) circle.rotate(1);
        }
    }
}
