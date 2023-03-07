package Zadanie1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Circle {
    public static ArrayList<Circle> listOfCircles = new ArrayList<>();
    private static final int angleThatRadiusOfCircleTake = findAngleInDegrees();
    private static ArrayList<Integer> startingPositions = findStartingAngles();

    public static final int RADIUS = 10;
    private final MySlider slider;
    private double angularVelocity;  //predkosc katowa kolka
    private final Color color;
    private int angleInDegrees;  //kat na ktorym znajduje sie srodek kola


    Circle(MySlider slider) throws IllegalArgumentException{
        this.slider=slider;
        listOfCircles.add(this);

        //random color
        Random random = new Random();
        color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));  //zrobic tez tablice zajetych kolorow

        //randomAngle
        try {
            int startingAngle = chooseStaringAngle();
            rotate(startingAngle);
        }catch(ArrayIndexOutOfBoundsException exception){
            throw new IllegalArgumentException();
        }

        CircleThread thread = new CircleThread(this);
        thread.start();
    }

    public void setAngularVelocity(double value){  // prekosc katowa = zmiana kata w czasie
        angularVelocity = Math.toRadians(value);
        //angularVelocity.notify();
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public int getAngleInDegrees() {
        return angleInDegrees;
    }

    public Color getColor() {
        return color;
    }

    public long timeOfRotation(int angleDegrees){ // kat w stopniach
        if(angularVelocity==0){     //zrobic żeby dopóki jest prędkość 0 to wątek czeka na powiadomienie ze juz nie jest 0
            return 0;
        }
        return (long) (1000*Math.toRadians(angleDegrees)/angularVelocity);
    }

    public synchronized void rotate(int angleDegrees){
        int definiteAngle = (this.angleInDegrees+angleDegrees)%360; //rzeczywisty kąt (kąt przesuniecia+ kąt jaki kólko obecnie zajmuje)

        Circle circleInFront = isCircleOnThisAngle(definiteAngle);
        if(circleInFront ==null) {
            this.angleInDegrees = definiteAngle;
        }else slider.setValue((int)Math.round(Math.toDegrees(circleInFront.getAngularVelocity())));
    }

    private Circle isCircleOnThisAngle(int angleInDegrees){  //kolka zajmuja x stopni
        int angleInDegreesWithSizeOfCircle = (angleInDegrees+angleThatRadiusOfCircleTake)%360;
        for(int i = 0; i<listOfCircles.size(); i++){
            if(listOfCircles.get(i).getAngleInDegrees() == angleInDegreesWithSizeOfCircle) return listOfCircles.get(i);
        }
        return null;
    }

    private static int findAngleInDegrees(){
        double pom = (double) RADIUS/PaintPanel.bigCircleRadius;
        double cosAngle = 1-(pom*pom)/2;
        return (int)Math.ceil(Math.toDegrees(Math.acos(cosAngle)));
    }

    private static ArrayList<Integer> findStartingAngles(){
        ArrayList<Integer> startingAngles = new ArrayList<>();
        for(int i =0; i<360; i+=angleThatRadiusOfCircleTake*2){
            startingAngles.add(i);
        }
        return startingAngles;
    }


    private synchronized int chooseStaringAngle() throws ArrayIndexOutOfBoundsException{
        Random random = new Random();

        if(startingPositions.size() >0 ) {
            int randomIndex = random.nextInt(startingPositions.size());
            int randomAngle = startingPositions.get(randomIndex);
            startingPositions.remove(randomIndex);
            return randomAngle;
        }
        throw new ArrayIndexOutOfBoundsException();  //kolek jest wiecej niz mozliwych katow do zajecia
    }

}
