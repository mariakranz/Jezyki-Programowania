package Zadanie2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LeafPanel extends JPanel implements ActionListener {
    private static HashMap<Integer, Color> colorOfInteger;
    Leaf leaf;
    private static final Dimension PANEL_SIZE = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    private static final int REPAINTING_TIME_VALUE = 100; // in milliseconds

    LeafPanel(Leaf leaf){

        this.leaf = leaf;
        this.setPreferredSize(PANEL_SIZE);
        colorOfInteger = assignColors();

        Timer timer = new Timer(REPAINTING_TIME_VALUE,this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;


        int xRectangle;
        int yRectangle;
        int rectangleWidthOnThePanel = (int) (this.getWidth()/ Leaf.boardSize.getWidth());
        int rectangleHeightOnThePanel = (int) (this.getHeight()/ Leaf.boardSize.getHeight());

        for(int column = 0; column<Leaf.boardSize.width; column++){
            xRectangle = column*rectangleWidthOnThePanel;
            for(int row = 0; row < Leaf.boardSize.height; row++){
                yRectangle = row*rectangleHeightOnThePanel;
                g2D.setColor(colorOfInteger.get(Leaf.cells.get(column).get(row).get()));
                g2D.fillRect(xRectangle,yRectangle,rectangleWidthOnThePanel,rectangleHeightOnThePanel);
            }
        }

        int snailCircleDiameter = Math.min(rectangleWidthOnThePanel,rectangleHeightOnThePanel);
        int snailCircleRadius = snailCircleDiameter/2;
        int xSnailCircleDisplacement = rectangleWidthOnThePanel/2-snailCircleRadius;
        int ySnailCircleDisplacement = rectangleHeightOnThePanel/2-snailCircleRadius;
        for(Snail snail: Snail.listOfSnails){
            g2D.setColor(Color.red);

            Point abstractPosition = snail.getCurrentPositionOfTheSnail();
            g2D.fillOval(abstractPosition.x*rectangleWidthOnThePanel+xSnailCircleDisplacement,abstractPosition.y*rectangleHeightOnThePanel+ySnailCircleDisplacement,snailCircleDiameter,snailCircleDiameter);

        }
    }
    private static HashMap<Integer,Color> assignColors(){
        int numberOfColors = Leaf.MAX_VALUE+1;
        int green = 255;
        int p = green/numberOfColors;
        HashMap<Integer,Color> listOfColors = new HashMap<>(numberOfColors);

        for(int i = 0; i<numberOfColors; i++){
            listOfColors.put(i,new Color(0,green,0));
            green-=p;
        }
        return listOfColors;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private static void createAndShowGUI(int width, int height, int numberOfSnails) {
        JFrame frame = new JFrame("Zadanie 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new LeafPanel(new Leaf(width,height, numberOfSnails));
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    private static void createExceptionFrame(){
        JFrame frame = new JFrame("Wyjatek");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        JLabel label = new JLabel("Wprowadzono bledne dane.");
        frame.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        try {
            int w = Integer.parseInt(args[0]);  //szerokosc
            int h = Integer.parseInt(args[1]);  //wysokosc
            int n = Integer.parseInt(args[2]);  //ilosc slimakow
            if (!(w>0 && h>0 && n>0)) throw new IllegalArgumentException();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(w, h, n);
                }
            });
        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            createExceptionFrame();
        }
    }
}
