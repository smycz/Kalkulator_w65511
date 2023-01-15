package com.example.projekt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
public class HelloController {
    @FXML
    private TextField inputField;
    @FXML
    private TextField inputField2;
    @FXML
    private TextField mark;
    @FXML
    private TextField outputField;
    private String operator = "";
    public double num1 = 0;
    public double num2 = 0;
    String temporary = "";
    boolean negative = false;
    String result = "";

    @FXML
    private void handleNumberAction(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if(value.equals(".")&&inputField.getText().contains(".")){
            return;
        }
        if(inputField.getText().equals("-0")||inputField.getText().equals("0")){
            if(!value.equals(".")){
                return;
            }
        }
        if(inputField.getText().isEmpty()&&value.equals(".")){
            inputField.setText(inputField.getText() + "0" + value);
        }else if(temporary.equals("-")){
            if(inputField.getText(0,0).equals("-")){
                inputField.setText("");
                inputField.setText(inputField.getText() + value);
                return;
            }
            inputField.setText(temporary + value);
            temporary = "";
        }
        else{
            inputField.setText(inputField.getText() + value);
        }
        if(inputField.getText().equals("-.")){
            inputField.setText("-");
        }
    }

    @FXML
    private void handleOperatorAction(ActionEvent event) {
        if(inputField.getText().equals("-")){
            return;
        }

            String value = ((Button) event.getSource()).getText();
            operator = value;

        if(num1==0&&operator.equals("mod")&&inputField.getText().equals("")){
            operator="";
            return;
        }

        if(inputField2.getText().equals("")&&mark.getText().equals("√")){
            if(operator!="sqrt"){
                operator="";
                return;
            }
        }

        if((!inputField2.getText().equals(""))&&(!mark.getText().equals(""))&&inputField.getText().equals("")){
            mark.setText(operator);
            if(mark.getText().equals("mod")){
                mark.setText("%");
            }
            if(mark.getText().equals("sqrt")){
                mark.setText("√");
            }
            operator="";
            return;
        }
        if(inputField.getText().length()>0){
            if(inputField.getText().charAt(inputField.getText().length()-1)=='.'){
                operator="";
                return;
            }
        }
        if(!mark.getText().isEmpty()){
            mark.setText(operator);
            if(mark.getText().equals("mod")){
                mark.setText("%");
            }
            if(mark.getText().equals("sqrt")){
                mark.setText("√");
            }
        }

        if((value.equals("-")&&!negative&&inputField.getText().equals("")&&inputField2.getText().equals(""))&&mark.getText().equals("")){
            temporary = "-";
            inputField.setText("-");
            negative=true;
            operator="";
            return;
        }
        if(inputField.getText().isEmpty()){
            if(operator.equals("+")||operator.equals("*")||operator.equals("/")||operator.equals("mod")||operator.equals("^")){
                operator="";
                return;
            }
            else if(operator.equals("sqrt")) {
                inputField2.setText("1");
            }
        }
        else {
            if(inputField.getText().equals("-")){
                operator="";
                return;
            }else{
            num1 = Double.parseDouble(inputField.getText());
            inputField2.setText(inputField.getText());
            }
        }
        if(inputField.getText().equals("")||inputField.getText().isEmpty()) {
            inputField2.setText(inputField.getText());
        }
        if(operator.equals("sqrt")){
            mark.setText("√");
        }
        else if(operator.equals("mod")){
            mark.setText("%");
        }
        else {
            mark.setText(operator);
            if(mark.getText().equals("mod")){
                mark.setText("%");
            }
            if(mark.getText().equals("sqrt")){
                mark.setText("√");
            }
        }
        inputField.setText("");
        operator="";
    }

    @FXML
    private void handleEqualAction(ActionEvent event) {
        if((mark.getText().equals("mod")||mark.getText().equals("%"))&&inputField.getText().equals("0")){
            result = "Nie dzielimy przez zero";
            outputField.setText(result);
            inputField.setText("");
            inputField2.setText("");
            mark.setText("");
            result="";
            return;
        }
        if(inputField.getText().equals("0.")){
            return;
        }
        if(mark.getText().equals("√")&&inputField2.getText().equals("")&&inputField.getText().equals("")){
            return;
        }
        if(operator.equals("-")&&num1==0){
            if(inputField.getText().equals("-")){
                return;
            }
            if(inputField.getText().charAt(0)!='-') {
                result = "-" + inputField.getText();
            }else{
                result = inputField.getText();
            }
            validate();
            outputField.setText(result);
            mark.setText("");
            inputField2.setText("");
            return;
        }
        if(inputField.getText().isEmpty()){
            return;
        }else {
            if(inputField.getText().equals("-")&&inputField2.getText().equals("")&&(!mark.getText().equals(""))){
                mark.setText("");
                inputField.setText("");
                return;
            }
            num2 = Double.parseDouble(inputField.getText());
        }
        if(num2!=0&&num1==0&&(mark.getText().equals(""))) {
            result = String.valueOf(num2);
        }
        if(mark.getText().equals("%")){
            mark.setText("mod");
        }
        if(mark.getText().equals("√")){
            mark.setText("sqrt");
        }
        switch (mark.getText()) {
            case "+":
                result = String.valueOf(add(num1, num2));
                validate();
                break;
            case "-":
                result = String.valueOf(subtract(num1, num2));
                validate();
                break;
            case "*":
                result = String.valueOf(multiply(num1, num2));
                validate();
                break;
            case "/":
                if (num2 != 0) {
                    result = String.valueOf(divide(num1, num2));
                } else {
                    result = "Nie dzielimy przez zero";
                }
                validate();
                break;
            case "^":
                result = String.valueOf(power(num1, num2));
                validate();
                break;
            case "sqrt":
                if(inputField2.getText().equals("")&&num1==0){
                    num1=1;
                }
                result = String.valueOf(root(num1, num2));
                validate();
                num1 = 0;
                num2 = 0;
                break;
            case "mod":
                result = String.valueOf(modulo(num1, num2));
                validate();
                break;
            default:
                validate();
                break;
        }
        inputField.setText("");
        inputField2.setText("");
        mark.setText("");
        operator = "";
        num1 = 0;
        num2 = 0;
        negative=false;
        temporary="";
    }
    private void validate(){
        if(inputField2.getText().equals("0.")){
            inputField2.setText("0.0");
        }
        if(result.equals("")){
            if(inputField.getText().equals("0")||inputField.getText().equals("0.0")){
                result="0";
            }else {
                return;
            }
        }
        if (result.equals("-0.0")){
            result="0.0";
        }
        if(result.equals("-0")){
            result="0";
        }
        if(result.length()>1){
            if(result.substring((result.length()-2)).equals(".0")) {
                outputField.setText(result.substring(0, result.length()-2));
                inputField.setText("");
                mark.setText("");
                inputField2.setText("");
                result="";
                return;
            }
        }
            outputField.setText(result);
            inputField.setText("");
            mark.setText("");
            inputField2.setText("");
            result="";

    }
    private double add(double num1, double num2) {
        operator = "";
        return num1 + num2;
    }

    private double subtract(double num1, double num2) {
        operator = "";
        return num1 - num2;
    }

    private double multiply(double num1, double num2) {
        operator = "";
        return num1 * num2;
    }

    private double divide(double num1, double num2) {
        operator = "";
        return num1 / num2;
    }

    private double power(double num1, double num2) {
        operator = "";
        return Math.pow(num1, num2);
    }

    private double root(double num1, double num2) {
        double temporary;
        temporary = (Math.sqrt(num2))*(num1);
        operator = "";
        return (temporary);
    }

    private double modulo(double num1, double num2) {
        operator = "";
        return (num1 % num2);
    }
    @FXML
    private void CE(ActionEvent event){
        inputField.setText("");
        inputField2.setText("");
        mark.setText("");
        outputField.setText("");
        operator = "";
        num1 = 0;
        num2 = 0;
        negative=false;
        temporary="";
    }

    @FXML
    private void delete(ActionEvent event){
        if(!inputField.getText().equals("")) {
            if(inputField.getText().charAt(inputField.getText().length()-1)=='-'){
                negative=false;
                temporary="";
                operator="";
            }
            inputField.setText(inputField.getText(0, (inputField.getLength() - 1)));
        }
    }
}