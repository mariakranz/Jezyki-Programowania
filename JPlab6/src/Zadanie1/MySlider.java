package Zadanie1;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MySlider extends JSlider implements ChangeListener {
    private final Circle circle;      //powiazanie slidera z kolkiem

    MySlider(int min, int max, int value) throws  IllegalArgumentException{
        super(min, max, value);
        circle = new Circle(this);
        circle.setAngularVelocity(value);

        setMajorTickSpacing(60);
        setMinorTickSpacing(30);
        setPaintTicks(true);
        setPaintLabels(true);

        addChangeListener(this);
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        circle.setAngularVelocity(this.getValue());
    }
}
