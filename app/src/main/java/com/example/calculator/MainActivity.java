package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum OP{
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        EQUAL,
        NONE
    }

    TextView tv;
    Double storedNumber;
    OP storedOperator = OP.NONE;
    boolean editStart =true;



    public static String ACTIVITY_NAME="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);


    }


    public void buttonsClickHandler(View v){
        Button buttonClicked=(Button)v;
        Log.i(ACTIVITY_NAME,"btnText : "+buttonClicked.getText().toString());
        //check if it is c clicked then clear textfield
        if(buttonClicked.getText().toString().equals("C")){
            clear();
            return;
        }

        if(editStart){
            tv.setText("");
            editStart=false;
        }
        //if it is no clicked then set num to that number
        int num=-1;
        try {
            num=Integer.parseInt(buttonClicked.getText().toString());
            if(tv.getText().toString().length()<=10) tv.setText(tv.getText()+String.valueOf(num));
            return;
        }catch (NumberFormatException e){
            //if it isnt number and is a dot
            Log.i(ACTIVITY_NAME,"isnt a number");
            if(buttonClicked.getText().toString().equals(".")) {
                if(tv.getText().toString().length()<=10 && !tv.getText().toString().contains(".")){
                    tv.setText(tv.getText() + buttonClicked.getText().toString());
                }
                return;
            }else{
                operatorClicked(operatify(buttonClicked.getText().toString()));
            }


        }catch (Exception e){
            return;
        }



    }


    private void operatorClicked(OP op){
        Log.i(ACTIVITY_NAME,op.name());
        if(storedOperator==OP.NONE){
            if(!(op==OP.EQUAL)){
                updateStoredNum();
                storedOperator=op;
            }
            return;
        }
        double num=Double.valueOf(tv.getText().toString());
        switch (storedOperator){
            case PLUS:
                storedNumber+=num;
                break;
            case MINUS:
                storedNumber-=num;
                break;
            case MULTIPLY:
                storedNumber*=num;
                break;
            case DIVIDE:
                storedNumber/=num;
                break;
        }

        if(op!=OP.EQUAL){
            storedOperator=op;
        }else{
            storedOperator=OP.NONE;
        }

        updateTextViewFromStoredNum();
    }




    private OP operatify(String op){
        switch(op){
            case "+":
                return OP.PLUS;
            case "-":
                return OP.MINUS;
            case "*":
                return OP.MULTIPLY;
            case "/":
                return OP.DIVIDE;
            case "=":
                return OP.EQUAL;
            default:
                return OP.NONE;
        }
    }

    private void updateStoredNum(){
        try {
            storedNumber=Double.valueOf(tv.getText().toString());
            storedNumber=Double.valueOf(String.format("%.2f",storedNumber));
            tv.setText("0");
            editStart=true;
        }catch (NumberFormatException e){
            storedNumber=0.0;
            tv.setText("0");
        }

    }

    private void updateTextViewFromStoredNum(){
        if(String.format("%.2f",storedNumber).length()>11){
            Log.i(ACTIVITY_NAME,"Answer too long");
            Toast.makeText(getApplicationContext(),"No too large",Toast.LENGTH_LONG).show();
            clear();
        }else{
            tv.setText(String.format("%.2f",storedNumber));
        }

    }

    private void clear(){
        tv.setText("0");
        storedNumber=0.0;
        storedOperator=OP.NONE;
        editStart =true;
    }

}
