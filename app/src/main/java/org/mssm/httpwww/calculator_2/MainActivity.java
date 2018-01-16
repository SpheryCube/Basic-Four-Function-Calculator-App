package org.mssm.httpwww.calculator_2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "Main Activity onCreate");
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                double default_constant_val = 0;
                chosen_const_value = data.getDoubleExtra("constant_val", default_constant_val);
                chosen_constant_symbol = data.getStringExtra("constant_symbol");

                if (my_state == states_enum.appending_to_num1)  //ONly for debugging purposes.
                {
                    Log.d("lifecycle", "my_state is appending_to_num1");
                }
                if (my_state == states_enum.num1_blank) {
                    is_number1_a_constant_symbol = true;
                    chosen_const_value_num1 = chosen_const_value;
                    chosen_constant_symbol_num1 = chosen_constant_symbol;
                    my_state = states_enum.appending_to_num1;
                    Log.d("lifecycle", "I'm in onActivityResult");
                }
                else  //We must  be in state num2_blank since num2_blank and num1_blank were the only two conditions that allowed us to get to the constants menu in the first place.
                {
                    Log.d("lifecycle", "my_state is not num1_blank"); //For debugging purposes
                    is_number2_a_constant_symbol = true;
                    chosen_const_value_num2 = chosen_const_value;
                    chosen_constant_symbol_num2 = chosen_constant_symbol;
                    my_state = states_enum.appending_to_num2;
                }
                update_display();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }




    //Variables used to work with symbols.
    public double chosen_const_value = 0;
    public String chosen_constant_symbol = "";

    public double chosen_const_value_num1 = 0;
    public String chosen_constant_symbol_num1 = "";


    public double chosen_const_value_num2 = 0;
    public String chosen_constant_symbol_num2 = "";


    //Variables used for displaying expressions.
    private String current_btn_char = "";
    private String number1 = "";
    private String number2 = "";

    private boolean is_number1_a_constant_symbol = false;
    private boolean is_number2_a_constant_symbol = false;
    private String my_operator = "";
    public boolean is_current_btn_num = false;

    //Used to determine machine's state in addition to the contents of "number1" and "number2"
    private boolean operator_inputted = false; //Stays false until an operator has been pressed by the user

    private String my_expression = "";


    private boolean have_decimal = false; //Used to make sure that each number doesn't have more than one decimal in it. If each number does get more than one decimal in it, don't do anything.


    //Fields below this comment should not be cleared after press "clear"


    //Fields dealing with the history feature. Only clear these fields if history view clear" is pressed.
    private String historic_exp = ""; //Contents of the previous entry. "historic expression"
    private int expressions_inputed = 0; //Counts the number of expressions the user has inputted so far. Used for keep track of history.

    public enum states_enum {
        num1_blank, appending_to_num1, num2_blank, appending_to_num2, equals_state
    }

    public states_enum my_state = states_enum.num1_blank;

//Display Methods

    public void clear_history_onClick(View v)
    {
        TextView history_view_1_tv = (TextView) findViewById(R.id.history_view_1);
        TextView history_view_2_tv = (TextView) findViewById(R.id.history_view_2);
        TextView history_view_3_tv = (TextView) findViewById(R.id.history_view_3);
        TextView history_view_4_tv = (TextView) findViewById(R.id.history_view_4);
        TextView history_view_5_tv = (TextView) findViewById(R.id.history_view_5);

        history_view_1_tv.setText("");
        history_view_2_tv.setText("");
        history_view_3_tv.setText("");
        history_view_4_tv.setText("");
        history_view_5_tv.setText("");

        //Reset variables that keep track of history to their default states.
        historic_exp = ""; //Contents of the previous entry. "historic expression"
        expressions_inputed = 0; //Counts the number of expressions the user has inputted so far. Used for keep track of history.
    }


    public void add_to_history(String historic_exp)
    {
        TextView history_view_1_tv = (TextView) findViewById(R.id.history_view_1);
        TextView history_view_2_tv = (TextView) findViewById(R.id.history_view_2);
        TextView history_view_3_tv = (TextView) findViewById(R.id.history_view_3);
        TextView history_view_4_tv = (TextView) findViewById(R.id.history_view_4);
        TextView history_view_5_tv = (TextView) findViewById(R.id.history_view_5);

        if (expressions_inputed == 1)
        {
            history_view_1_tv.setText(historic_exp);
        }
        else if (expressions_inputed == 2)
        {
            history_view_2_tv.setText(historic_exp);
        }
        else if (expressions_inputed == 3)
        {
            history_view_3_tv.setText(historic_exp);
        }
        else if (expressions_inputed == 4)
        {
            history_view_4_tv.setText(historic_exp);
        }
        else if (expressions_inputed == 5)
        {
            history_view_5_tv.setText(historic_exp);
        }
        else if (expressions_inputed > 5)
        {
            //Move up the text from each of the history text views to the text view above it to make room for putting the new expression in history view 5. The stuff in history view 1 will be deleted.
            history_view_1_tv.setText(history_view_2_tv.getText());
            history_view_2_tv.setText(history_view_3_tv.getText());
            history_view_3_tv.setText(history_view_4_tv.getText());
            history_view_4_tv.setText(history_view_5_tv.getText());
            history_view_5_tv.setText(historic_exp);
        }
    }


    public void update_display()
    {
        Log.d("lifecycle", "I'm in update_display");

        TextView current_input_view_tv = (TextView) findViewById(R.id.current_input_view);
        TextView current_output_view_tv = (TextView) findViewById(R.id.output_view);


        if (my_state == states_enum.appending_to_num1)  //If the operator hasn't been inputted... Keep appending text (digits) to the first number until the user clicks on an operator
        {
            if (is_number1_a_constant_symbol == true) //Note we could never have conventional digits and then have a symbol since we can only get to the symbol menu if the current number we are on is blank.
            {
                Log.d("lifecycle", "I'm in update_display's is_number1_a_constant_symbol == true condition");
                number1 = chosen_constant_symbol_num1;
            }
            else            //We must be appending conventional
            {
                number1 = number1 + current_btn_char;
            }
            current_input_view_tv.setText(number1);
        }
        else if  (my_state == states_enum.num2_blank)
        {
            String display = number1 + my_operator;
            current_input_view_tv.setText(display);
        }

        else if (my_state == states_enum.appending_to_num2)
        {
            if (is_number2_a_constant_symbol == true)
            {
                number2 = chosen_constant_symbol_num2;
            }
            else
            {
                Log.d("lifecycle", "Number2 is " + number2 + ", current_btn_char is " + current_btn_char);
                number2 = number2 + current_btn_char;
            }
            String display = number1 + my_operator + number2;
            current_input_view_tv.setText(display);
        }

        else if (my_state == states_enum.equals_state)
        {

            if (number1.equals(".") || number2.equals("."))       //Some error checking.
            {
                TextView warning_messages_tv = (TextView) findViewById(R.id.warning_view);
                warning_messages_tv.setText("Please hit clear and input a correct expression!");
            }
            else
            {
                expressions_inputed = expressions_inputed + 1;
                my_expression = number1 + my_operator + number2 + "=";        //Remember that my_expression is a string


                //Display expression in the "current input" section
                current_input_view_tv.setText(my_expression);


                double my_result = 0;
                double num1;
                double num2;

                if (is_number1_a_constant_symbol)
                {
                    num1 = chosen_const_value_num1;
                }
                else
                {
                    num1 = Double.parseDouble(number1);
                }
                if (is_number2_a_constant_symbol) { num2 = chosen_const_value_num2; }
                else
                {
                    num2 = Double.parseDouble(number2);
                }

                if (my_operator.equals("*")) {
                    my_result = num1 * num2;
                }
                if (my_operator.equals("/")) {
                    my_result = num1 / num2;
                }
                if (my_operator.equals("+")) {
                    my_result = num1 + num2;
                }
                if (my_operator.equals("-")) {
                    my_result = num1 - num2;
                }

                //Display the output
                String result = Double.toString(my_result);
                current_output_view_tv.setText(result);

                //We want to push the resulting strings to one of the history text views. What history text view we use depends on how many expressions the user has inputted (recorded by the int variable "expressions_inputted")
                historic_exp = my_expression + System.lineSeparator() +  "             " + result + System.lineSeparator();
                add_to_history(historic_exp);
            }
        }
    }

    public void ClearDisplay()
    {
        TextView current_input_view_tv = (TextView) findViewById(R.id.current_input_view);
        TextView current_output_view_tv = (TextView) findViewById(R.id.output_view);
        TextView warning_messages_tv = (TextView) findViewById(R.id.warning_view);

        current_input_view_tv.setText("");
        current_output_view_tv.setText("");
        warning_messages_tv.setText("");
    }



    public void onDigitClick(View v) {
        Log.d("lifecycle", "I'm in onDigitClick");

        switch (v.getId()) {
            case R.id.zero_btn:
                current_btn_char = "0";
                break;
            case R.id.one_btn:
                current_btn_char = "1";
                break;
            case R.id.two_btn:
                current_btn_char = "2";
                break;
            case R.id.three_btn:
                current_btn_char = "3";
                break;
            case R.id.four_btn:
                current_btn_char = "4";
                break;
            case R.id.five_btn:
                current_btn_char = "5";
                break;
            case R.id.six_btn:
                current_btn_char = "6";
                break;
            case R.id.seven_btn:
                current_btn_char = "7";
                break;
            case R.id.eight_btn:
                current_btn_char = "8";
                break;
            case R.id.nine_btn:
                current_btn_char = "9";
                break;
        }

        if (my_state == states_enum.num1_blank)
        {
            my_state = states_enum.appending_to_num1;
            Log.d("Lifecycle", "I just changed my_state to appending_to_num1");
        }
        else if (my_state == states_enum.num2_blank)
        {
            my_state = states_enum.appending_to_num2;
        }
        update_display();
    }

    public void onDecimalClick(View v)
    {
        if (!have_decimal)   //Only add a decimal if we our number doesn't currently have a decimal in it.
        {
            if(my_state == states_enum.num1_blank)
            {
                my_state = states_enum.appending_to_num1;
            }
            if(my_state == states_enum.num2_blank)
            {
                my_state = states_enum.appending_to_num2;
            }

            current_btn_char = ".";
            have_decimal = true;
        }
        else
        {
            current_btn_char = "";
        }
        update_display();
    }

    public void onOperatorClick(View v)
    {
        if (my_state == states_enum.appending_to_num1 && !(number1.equals(".")))        //If the user has inputted a correct number for number1 ...
        {            //We can only use an operator if we already have something in num1 (and when we don't have anything for the operator already or in num2)
            switch (v.getId())
            {
                case R.id.multiply_btn:
                    if (!operator_inputted) {
                        my_operator = "*";
                    }
                    break;
                case R.id.divide_btn:
                    if (!operator_inputted) {
                        my_operator = "/";
                    }
                    break;
                case R.id.plus_btn:
                    if (!operator_inputted) {
                        my_operator = "+";

                    }
                    break;
                case R.id.subtract_btn:
                        my_operator = "-";
                        break;
            }
            have_decimal = false;                  //Reset decimal count for the next digit
            my_state = states_enum.num2_blank;  //Now we are ready to start inputting digits (or a symbol) for number 2.
            update_display();
        }
    }


    public void onConstantsClick(View v)
    {
        if (my_state == states_enum.num1_blank || my_state == states_enum.num2_blank)
        {
            Intent startAct = new Intent(this, Constants.class);
            startActivityForResult(startAct, 1);
        }
    }

    public void onEqualsClick(View v)
    {
        if (my_state == states_enum.equals_state)
        {
            //Do nothing but copy the last correct expression into another slot of history.
        }
        else if (my_state == states_enum.appending_to_num2)       //The user inputted a correct expression. Display the result and put it into history.
        {
            my_state = states_enum.equals_state;
        }
        else
        {
            current_btn_char = "";
        }

        update_display();
    }




    public void onClearClick(View v)    //Reset variables and display to intial state.
    {
        chosen_const_value = 0;
        chosen_constant_symbol = "";

        chosen_const_value_num1 = 0;
        chosen_constant_symbol_num1 = "";


        chosen_const_value_num2 = 0;
        chosen_constant_symbol_num2 = "";


        //Variables used for displaying expressions.
        current_btn_char = "";
        number1 = "";
        number2 = "";


        is_number1_a_constant_symbol = false;
        is_number2_a_constant_symbol = false;
        my_operator = "";
        is_current_btn_num = false;

        //Used to determine machine's state in addition to the contents of "number1" and "number2"
        operator_inputted = false; //Stays false until an operator has been pressed by the user

        my_expression = "";

        have_decimal = false; //Used to make sure that each number doesn't have more than one decimal in it. If each number does get more than one decimal in it, don't do anything.


        ClearDisplay();

        my_state = states_enum.num1_blank;
    }



//Functions that have yet to be implemented.
    public void onParenthesisClick(View v)
    {
    }
    public void onDeleteClick(View v)       //Has to be fixed.
    {
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle", "Main Activity onStart");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://org.mssm.httpwww.calculator_2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("lifecycle", "Main Activity onStop");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://org.mssm.httpwww.calculator_2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
