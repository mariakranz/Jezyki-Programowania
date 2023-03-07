package Zadanie1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyPanel1 extends JPanel implements ActionListener {
    ArrayList<JTextField> aTF = new ArrayList<>(3);
    ArrayList<JTextField> bTF = new ArrayList<>(3);
    ArrayList<JTextField> cTF = new ArrayList<>(4);
    JTextField operationTF = new JTextField(1);
    JButton solveBTN = new JButton("Rozwiaz");
    JTextArea solveJA = new JTextArea("Rozwiazanie",3,3);
    MyPanel1(){
        this.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        for(int i=0; i<3; i++){
            gbc.gridx=i;
            JTextField aX = new JTextField(String.format("a%d",i),4);
            this.add(aX,gbc);
            aTF.add(aX);
        }

        gbc.gridy = 1;
        for(int i=0; i<3; i++){
            gbc.gridx=i;
            JTextField bX = new JTextField(String.format("b%d",i),4);
            this.add(bX,gbc);
            bTF.add(bX);
        }

        gbc.gridy = 2;
        for(int i=0; i<4; i++){
            gbc.gridx=i;
            JTextField cX = new JTextField(String.format("c%d",i),4);
            this.add(cX,gbc);
            cTF.add(cX);
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth=3;
        JLabel instrLabel = new JLabel("Znak operacji:");
        this.add(instrLabel,gbc);
        gbc.gridx = 3;
        this.add(operationTF,gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight=2;
        this.add(solveBTN,gbc);
        solveBTN.addActionListener(this);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridheight=2;
        this.add(solveJA,gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==solveBTN){

            try {
                long A = calculateA();
                long B = calculateB();
                long C = calculateC(A,B);
                findVariables(C);
            }catch( NullPointerException |IllegalArgumentException | IndexOutOfBoundsException | ArithmeticException exception){
                exeptionFrame();
            }
        }
    }

    private long calculateA()throws NumberFormatException, NullPointerException{
        ArrayList<Integer> listOfValues = convertToNumbers(aTF);
        return listOfValues.get(0)*100+listOfValues.get(1)*10+listOfValues.get(2);
    }

    private long calculateB()throws NumberFormatException,NullPointerException{
        ArrayList<Integer> listOfValues = convertToNumbers(bTF);
        return listOfValues.get(0)*100+listOfValues.get(1)*10+listOfValues.get(2);
    }

    private long calculateC(long A, long B)throws IllegalArgumentException{
        String text = operationTF.getText();
        return switch (text) {
            case "+" -> A + B;
            case "-" -> A - B;
            case "*" -> A * B;
            default -> throw new IllegalArgumentException();
        };
    }
    private ArrayList<Integer> convertToNumbers(ArrayList<JTextField> textFields) throws NullPointerException, NumberFormatException{
        ArrayList<Integer> listOfValues = new ArrayList<>(3);
        for (JTextField textField: textFields) {
            String text = textField.getText();
            int number = Integer.parseInt(text);
            listOfValues.add(number);
        }
        return listOfValues;
    }

    private double calculateVariable(long C, long variablesComponents, long nonVariablesComponents)throws ArithmeticException{
        if (variablesComponents == 0) throw new ArithmeticException();
        return (double)(C-nonVariablesComponents)/variablesComponents;
    }
    private void findVariables(long C) throws IllegalArgumentException, IndexOutOfBoundsException, ArithmeticException {
        long variablesComponents = 0;
        long nonVariableComponents = 0;
        int multiplier = 1;

        ArrayList<String> tableOfVariables = new ArrayList<>(); //tablica zawierajaca wszystkie zmienne ktore beda w polach c

        for (int i = cTF.size()-1; i>=0; i--) {
            String text =  cTF.get(i).getText();
            if(text.matches("[A-z]")){
                tableOfVariables.add(text);
                if(areVariablesTheSame(tableOfVariables)) {
                    variablesComponents += multiplier;
                }else throw new IllegalArgumentException();     //uzyte zostaly dwa rozne symbole
            }else{
                int number = Integer.parseInt(text);
                nonVariableComponents+= (long) number *multiplier;
            }
            multiplier*=10;
        }

        double variableValue = calculateVariable(C,variablesComponents,nonVariableComponents);

        printVariableOnTextArea(tableOfVariables.get(0),variableValue);     //moze byc 0 w tablicy (wyjatek)
    }

    private boolean areVariablesTheSame(ArrayList<String> list) throws IndexOutOfBoundsException{
        return list.isEmpty() || list.stream()
                .allMatch(list.get(0)::equals);
    }

    private void printVariableOnTextArea(String variable, double variableValue){
        solveJA.setText(String.format("%s = %f",variable,variableValue));
    }

    private static void exeptionFrame(){
        JFrame frame = new JFrame("Wyjatek");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel messageLabel = new JLabel("Wprowadzono bledne dane");
        panel.add(messageLabel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Zadanie 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new MyPanel1();
        newContentPane.setOpaque(true); //content panes must be opaque
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
