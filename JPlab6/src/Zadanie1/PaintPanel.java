package Zadanie1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaintPanel extends JPanel implements ActionListener {
    private static final Dimension panelSize = new Dimension(400,400);
    private static final Point bigCircleCentreOnThePanel = new Point(panelSize.width/2, panelSize.height/2);
    public static final int bigCircleRadius = (int) bigCircleCentreOnThePanel.getX()-20;
    Timer timer = new Timer (16,this);  //odswiezanie panelu co 16 ms
    PaintPanel(){
        setPreferredSize(panelSize);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        int bigCircleDiameter= bigCircleRadius*2;
        g2D.drawOval((int) bigCircleCentreOnThePanel.getX()-bigCircleRadius, (int) bigCircleCentreOnThePanel.getY()-bigCircleRadius, bigCircleDiameter,bigCircleDiameter);
        for(int i = 0; i<Circle.listOfCircles.size(); i++){
            //oblicz polozenie i narysuj
            Point p = placeCircleOnThePanel(Circle.listOfCircles.get(i));
            g2D.setColor(Circle.listOfCircles.get(i).getColor());
            g2D.fillOval((int)p.getX(),(int)p.getY(),Circle.RADIUS*2,Circle.RADIUS*2);
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==timer){
            repaint();
        }
    }

    //konwertowanie polozenia kolka jako punktu na panelu
    private Point findPointPlaceOnThePanel(Circle circle){

        int xTeor = (int) Math.round(PaintPanel.bigCircleRadius * Math.cos(Math.toRadians((circle.getAngleInDegrees()))));
        int yTeor = (int) Math.round(PaintPanel.bigCircleRadius * Math.sin(Math.toRadians(circle.getAngleInDegrees())));

        int x = xTeor+(int) bigCircleCentreOnThePanel.getX();
        int y = (int) bigCircleCentreOnThePanel.getY()-yTeor;
        return new Point(x,y);
    }

    private Point placeCircleOnThePanel( Circle circle){
        Point point = findPointPlaceOnThePanel(circle);
        int x = (int) (point.getX()-Circle.RADIUS);
        int y = (int) (point.getY()-Circle.RADIUS);
        return new Point(x,y);
    }
}

