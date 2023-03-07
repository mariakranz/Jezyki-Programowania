package Zadanie1;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SliderPanel extends JPanel{
    SliderPanel( int numberOfSliders) throws IllegalArgumentException{
        super(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        Random random = new Random();
        for(int i=0; i<numberOfSliders; i++){
            MySlider slider = new MySlider(0, 360, random.nextInt(360));
            add(slider,gbc);

            JPanel colorPanel = new JPanel();
            colorPanel.setBackground(slider.getCircle().getColor());
            gbc.gridx=1;
            add(colorPanel,gbc);

            gbc.gridx=0;
            gbc.gridy++;
        }


    }
}
