package Zadanie1;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private static void createAndShowGUI(int n) {
        JFrame frame = new JFrame("Zadanie1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(0, 0);
        try {
            SliderPanel sliderPanel = new SliderPanel( n);
            PaintPanel paintPanel = new PaintPanel();
            frame.getContentPane().add(sliderPanel);
            frame.getContentPane().add(paintPanel, BorderLayout.LINE_START);
        }catch(IllegalArgumentException exception){
            createExceptionFrame("Kolka nie mieszcza sie na panelu");
        }
        frame.pack();
        frame.setVisible(true);
    }
    public static void createExceptionFrame(String labelText){
        JFrame frame = new JFrame("Wyjatek");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        JLabel label = new JLabel(labelText);
        frame.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {  //argument = liczba kolek
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI(Integer.parseInt(args[0]));
                }catch(NumberFormatException | IndexOutOfBoundsException exception){
                    createExceptionFrame("Wprowdzono bledne dane.");
                }
            }
        });

    }
}
