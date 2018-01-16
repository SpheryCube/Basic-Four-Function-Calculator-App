package org.mssm.httpwww.calculator_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Constants extends AppCompatActivity {


    public double chosen_const_value = 0;
    public String chosen_const_symbol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifecycle", "Constants Activity on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constants);
    }


    public void returnResult()
    {
        Log.d("lifecycle", "returning constant result");
        Intent returnIntent = new Intent();
        returnIntent.putExtra("constant_val", chosen_const_value);
        returnIntent.putExtra("constant_symbol", chosen_const_symbol);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public double euler_const = 2.718281828;                //unitless
    public double pi = 3.14159265359;                       //unitless
    public double permeability_of_vacuum = 4 * pi * 10000000;
    public double c = 299792458;                            //Meters/sec
    public double permittivity_of_vacuum = 1/(c * c * permeability_of_vacuum);
    public double universal_gravitational_constant = 6.67408 * Math.pow(10, -11);            //Units in m3 kg-1 s-2
    public double molar_gas_constant= 83144621;

    public void on_e_btn_click(View v)
    {
        Log.d("lifecycle", "e button clicked");
        chosen_const_value =  euler_const;
        chosen_const_symbol = "e";
        returnResult();
    }
    public void on_pi_btn_click(View v)
    {
        chosen_const_value = pi;
        chosen_const_symbol = "π";
        returnResult();
    }
    public void on_permeability_of_vacuum_click(View v)
    {
        chosen_const_value = permeability_of_vacuum;
        chosen_const_symbol = "μ0";
        returnResult();
    }
    public void on_permittivity_of_vacuum_btn_click(View v)
    {
        chosen_const_value = permittivity_of_vacuum;
        chosen_const_symbol = "ε0";
        returnResult();
    }
    public void on_universal_gravitational_constant_click(View v)
    {
        chosen_const_value = universal_gravitational_constant;
        chosen_const_symbol = "G";
        returnResult();
    }
    public void on_molar_gas_constant_click(View v)
    {
        chosen_const_value = molar_gas_constant;
        chosen_const_symbol = "R";
        returnResult();
    }
}






