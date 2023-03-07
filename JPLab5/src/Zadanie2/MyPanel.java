package Zadanie2;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Locale;

public class MyPanel extends JPanel implements ActionListener{
    private final ArrayList<JButton> listOfAlphabetButtons;
    private final ArrayList<JButton> listOfOperationButtons;
    private final JButton DMButton;
    private final JButton CButton;
    private final JButton CEButton;
    private final JButton solveButton;
    private static JTextArea textArea;

    MyPanel(){
        super(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        DMButton = new JButton("D/M");
        Dimension btnSize = new Dimension(70, 40);
        DMButton.setPreferredSize(btnSize);
        add(DMButton,gbc);
        DMButton.addActionListener(this);


        listOfAlphabetButtons = new ArrayList<>(8);
        listOfAlphabetButtons.add(new JButton("abc"));
        listOfAlphabetButtons.add(new JButton("def"));
        listOfAlphabetButtons.add(new JButton("ghi"));
        listOfAlphabetButtons.add(new JButton("jkl"));
        listOfAlphabetButtons.add(new JButton("mno"));
        listOfAlphabetButtons.add(new JButton("pqr"));
        listOfAlphabetButtons.add(new JButton("stuv"));
        listOfAlphabetButtons.add(new JButton("wxyz"));

        int xPosition = gbc.gridx;
        for (JButton button: listOfAlphabetButtons) {
            int i = xPosition+1;
            gbc.gridx = i%3;
            gbc.gridy= i/3;
            button.setPreferredSize(btnSize);
            add(button,gbc);
            button.addActionListener(this);
            button.addMouseListener(new MouseListener() {
                final int numberOfLetters = button.getText().length();

                @Override
                public void mouseClicked(MouseEvent e) {
                    //numberOfClicks = e.getClickCount();
                    textArea.append(String.valueOf(button.getText().charAt((e.getClickCount()+numberOfLetters-1)%(numberOfLetters))));
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            xPosition=i;
        }

        gbc.gridx = 0;
        gbc.gridy+=1;
        gbc.weighty = 10.0;
        CEButton = new JButton("CE");
        CEButton.setPreferredSize(btnSize);
        add(CEButton,gbc);
        CEButton.addActionListener(this);

        gbc.gridx +=1;
        CButton = new JButton("C");
        CButton.setPreferredSize(btnSize);
        add(CButton,gbc);
        CButton.addActionListener(this);


        listOfOperationButtons = new ArrayList<>(3);
        listOfOperationButtons.add(new JButton("+"));
        listOfOperationButtons.add(new JButton("-"));
        listOfOperationButtons.add(new JButton("/"));
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 0.0;
        for (JButton button: listOfOperationButtons) {
            button.setPreferredSize(btnSize);
            add(button,gbc);
            button.addActionListener(this);
            gbc.gridy++;
        }

        solveButton = new JButton("=");
        solveButton.setPreferredSize(btnSize);
        add(solveButton,gbc);
        solveButton.addActionListener(this);

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridheight=4;
        textArea = new JTextArea(10,10);

        textArea.setBorder(LineBorder.createBlackLineBorder());
        textArea.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scroll,gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==DMButton){
            for (JButton button: listOfAlphabetButtons) {
                convertButtonsCase(button);
            }
        } else if (e.getSource()==CEButton) {
            String text = textArea.getText();
            textArea.replaceRange("",text.length()-1,text.length());
        } else if (e.getSource()==CButton) {
            textArea.setText("");
        } else if (e.getSource()==solveButton) {
            Calculator calc = new Calculator(textArea.getText());
            textArea.append("=");
            try{
                textArea.append(calc.makeCalculations());
            }catch(IllegalArgumentException exception){
                createAndShowExeptionFrame();
            }

        } else{
            for (JButton button: listOfOperationButtons){
                if(e.getSource() == button) textArea.append(String.valueOf(button.getText().charAt(0)));
            }
        }
    }

    private void convertButtonsCase(JButton button){
        String text = button.getText();
        if(Character.isUpperCase(text.charAt(0))) button.setText(text.toLowerCase());
        else button.setText(text.toUpperCase(Locale.ROOT));
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Zadanie 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new MyPanel();
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowExeptionFrame(){
        JFrame frame = new JFrame("Wyjatek");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComponent newContentPane = new JPanel();

        newContentPane.setLayout(new FlowLayout());
        newContentPane.add(new JLabel("Wprowadzono bledne dane."));

        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
