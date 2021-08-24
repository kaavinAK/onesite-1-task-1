package com.example.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottomcalculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottomcalculator extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private itemviewmodel shareddata;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootview;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button plus;
    private Button minus;
    private  Button equal;
    private Button multiply;
    private Button divide;
    private Button clear;
   private String expression="";


    public bottomcalculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bottomcalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static bottomcalculator newInstance(String param1, String param2) {
        bottomcalculator fragment = new bottomcalculator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_bottomcalculator, container, false);
plus=rootview.findViewById(R.id.plus);
multiply=rootview.findViewById(R.id.multiply);
multiply.setOnClickListener(this);
divide = rootview.findViewById(R.id.divide);
divide.setOnClickListener(this);
clear=rootview.findViewById(R.id.clear);
clear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        shareddata.setdata("");
        expression="";

    }
});
        plus.setOnClickListener(this);
minus=rootview.findViewById(R.id.minus);
        minus.setOnClickListener(this);
equal = rootview.findViewById(R.id.equal);

        button1=rootview.findViewById(R.id.button1);
       button1.setOnClickListener(this);
        button2=rootview.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3=rootview.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4=rootview.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5=rootview.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6=rootview.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7=rootview.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8=rootview.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9=rootview.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        shareddata=new ViewModelProvider(getActivity()).get(itemviewmodel.class);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("expression", "onClick: "+expression);
                 double resultdouble = eval(expression);
                 if(resultdouble!=-1)
                 {
                     String result = expression+" = "+String.valueOf(resultdouble);
                     expression="";
                     shareddata.setdata(result);
                 }
                 else
                 {
                    Toast toast = Toast.makeText(getActivity(),"invalid input",Toast.LENGTH_SHORT);
                    toast.show();
                     expression="";
                     shareddata.setdata(expression);
                 }



            }
        });
        return rootview;
    }
    public static double eval(final String str) {
        try{
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                    return x;
                }


                double parseExpression() {
                    double x = parseTerm();
                    for (;;) {
                        if      (eat('+')) x += parseTerm(); // addition
                        else if (eat('-')) x -= parseTerm(); // subtraction
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (;;) {
                        if      (eat('*')) x *= parseFactor(); // multiplication
                        else if (eat('/')) x /= parseFactor(); // division
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor(); // unary plus
                    if (eat('-')) return -parseFactor(); // unary minus

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) { // parentheses
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    } else if (ch >= 'a' && ch <= 'z') { // functions
                        while (ch >= 'a' && ch <= 'z') nextChar();
                        String func = str.substring(startPos, this.pos);
                        x = parseFactor();
                        if (func.equals("sqrt")) x = Math.sqrt(x);
                        else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                        else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                        else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                        else throw new RuntimeException("Unknown function: " + func);
                    } else {
                        throw new RuntimeException("Unexpected: " + (char)ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                    // Log.d("before good", "parseFactor: "+String.valueOf(x));
                    return x;
                }
            }.parse();
        }
        catch (Exception e)
        {
          return -1;
        }

    }



    @Override
    public void onClick(View v) {
        Button value=rootview.findViewById(v.getId());
        String number = value.getText().toString();
        expression=expression+number;
        shareddata.setdata(expression);
    }
}