/**
 * Created by daniel14 on 9/6/16.
 */
public class Calculator {   //This is our singleton class

    public static Calculator getInstance()
    {
        Calculator mInstance = null;

        if (mInstance == null)
        {
            mInstance = new Calculator();
        }
        return mInstance;
    }


    //A bunch of variables

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






}
