package Zadanie2;

import java.util.ArrayList;

public class Calculator {
    String text;
    Calculator (String text){
        this.text = text;
    }
    private char getOperator(){
        int numberOfOperators=0;
        char operator=' ';
        
        if(text.contains("+")){
            numberOfOperators++;
            operator = '+';
        }
        if(text.contains("-")){
            numberOfOperators++;
            operator = '-';
        }
        if(text.contains("/")){
            numberOfOperators++;
            operator = '/';
        }
        
        if (numberOfOperators==1) return operator;
        else throw new IllegalArgumentException();
    }
    
    private String[] formatText() throws IllegalArgumentException{
        String[] splittedText = text.split("[/+,//,-]");
        if(splittedText.length == 2){
            if(splittedText[0].matches("[a-zA-Z]+") && splittedText[1].matches("[a-zA-Z]+")) return splittedText;
        }
        throw new IllegalArgumentException();
    }

    private String calculteResult(String[] components, char operation) throws IllegalArgumentException{

        switch (operation){
            case '+':
                return components[0] + components[1];
            case '-':
                return subtracting(components[0],components[1]);
            case '/':
                return dividing(components[0],components[1]);
        }
        throw new IllegalArgumentException();
    }

    private String subtracting(String firstComponent, String secondComponent){
        return firstComponent.replace(secondComponent,"");
    }

    private String dividing(String dividend, String divisor) {  //dzielna, dzielnik
        String secCompCopy = divisor;
        String tempSecCompCopy = secCompCopy;

        int numberOfLetters = 0;
        ArrayList<String> listOfResults = new ArrayList<>();

        for (int i = 0; i < divisor.length(); i++) {
            for (int j = 0; j < secCompCopy.length(); j++) {
                if (dividend.contains(tempSecCompCopy) && tempSecCompCopy.length() >= numberOfLetters) {
                    numberOfLetters = tempSecCompCopy.length();
                    listOfResults.add(tempSecCompCopy);
                }
               tempSecCompCopy = tempSecCompCopy.substring(0,tempSecCompCopy.length()-1);
            }
            secCompCopy = secCompCopy.substring(1);
            tempSecCompCopy=secCompCopy;
        }

        return formatValuesFromArrayList(listOfResults);
    }

    private String formatValuesFromArrayList(ArrayList<String> list){   //zostawia tylko najdluzsze wyrazy
        if(list.size()!=0) {
            int max = list.stream().map(String::length).max(Integer::compareTo).get();

            String output = "";
            for (String value : list) {
                if (value.length() == max) output = String.format("%s,", output + value);
            }
            return output.substring(0, output.length() - 1);
        }
        return "0";  //gdy nie udalo sie znalezc ciagu wyrazow
    }

    public String makeCalculations()throws IllegalArgumentException{

        return calculteResult(formatText(),getOperator());
    }
}



