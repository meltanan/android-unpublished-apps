package com.example.mohamed.hw1alcoholcalculator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Double r=0.68;
    Double weight=0.0;
    Double alcoholPercentage=0.0;
    Double bacLevel=0.0;
    Double finalbacLevel=0.0;
    int num=0;
    boolean flag=false;


    Double oz=1.0;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("demo","Hello");
        final EditText editTextWeight=findViewById(R.id.editTextWeight);
        final Switch gender=findViewById(R.id.switchGender);
        final Button buttonSave=findViewById(R.id.buttonSave);
        final Button buttonAddDrink=findViewById(R.id.buttonAddDrink);
        Button buttonReset=findViewById(R.id.buttonReset);
        RadioGroup radioGroup=findViewById(R.id.radioGroup);
        final SeekBar seekBar=findViewById(R.id.seekBarAlcohol);
        final TextView textViewAlcoholPercentage=findViewById(R.id.textViewAlcoholPercentage);
        final TextView textViewBACLevel=findViewById(R.id.textViewBACLevel);
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        final TextView textViewStatus=findViewById(R.id.textViewStatus);
        final TextView textViewGender=findViewById(R.id.textViewGender);
        final RadioButton radioButton1=findViewById(R.id.radioButton1oz);
        final RadioButton radioButton5=findViewById(R.id.radioButton5oz);
        final RadioButton radioButton12=findViewById(R.id.radioButton12oz);



       gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton,  boolean b) {

               if (b)
               {
                   textViewGender.setText("M");
                   r=0.68;
               }

               else
               {
                   textViewGender.setText("F");
                   r=0.55;
               }

           }
       });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (editTextWeight.getText().toString().isEmpty())

                    Toast.makeText(MainActivity.this,"You must enter the weight",500).show();
                else {

                    if (TextUtils.isDigitsOnly(editTextWeight.getText()) == true) {

                        if (Integer.parseInt(editTextWeight.getText().toString()) > 0)
                        {
                            weight = Double.valueOf(editTextWeight.getText().toString());
                            flag=true;

                        }
                        else
                            Toast.makeText(MainActivity.this, "Input must be greater than 0", 500).show();

                    } else
                        Toast.makeText(MainActivity.this, "Input must be a number and greater than 0", 500).show();
                }

                  Log.d("demo", String.valueOf(weight));
            }
        });

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               radioButton=findViewById(i);
               if (radioButton==radioButton1)
               {
                   oz=1.0;
                   Log.d("demo",String.valueOf(oz));
               }
               else if (radioButton==radioButton5)
               {
                   oz=5.0;
                   Log.d("demo",String.valueOf(oz));
               }

               else if (radioButton==radioButton12)
               {
                   oz=12.0;
                   Log.d("demo",String.valueOf(oz));
               }

           }
       });

       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               Log.d("demo", String.valueOf(i));
               textViewAlcoholPercentage.setText(String.valueOf(i)+" %");
               alcoholPercentage= (Double.parseDouble(String.valueOf(i)))/100;

               Log.d("demo",alcoholPercentage+" Yes");

           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });

       buttonAddDrink.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("WrongConstant")
           @Override
           public void onClick(View view) {
               if (flag == true) {


                   bacLevel = (((oz * alcoholPercentage) * 6.24) / (weight * r));
                   finalbacLevel = finalbacLevel + bacLevel;
                   finalbacLevel = Double.valueOf(String.format("%.3f", finalbacLevel));
                   Log.d("demo", String.valueOf(oz));

                   textViewBACLevel.setText("BAC Level: " + String.valueOf(finalbacLevel));


                   if (finalbacLevel >= 0.20) {
                       textViewStatus.setText("Over the limit!");
                       textViewStatus.setBackgroundColor(0xffff0000);
                       textViewStatus.setTextColor(0xffffffff);
                       progressBar.setProgress((int) Double.parseDouble(String.valueOf(finalbacLevel*100 )));
                       if (finalbacLevel > 0.25) {
                           Toast.makeText(MainActivity.this, "No more drinks for you! You are over the limit!", 1000).show();
                           buttonAddDrink.setEnabled(false);
                           buttonSave.setEnabled(false);
                           editTextWeight.setEnabled(false);
                           radioButton1.setEnabled(false);
                           radioButton5.setEnabled(false);
                           radioButton12.setEnabled(false);
                           gender.setEnabled(false);
                           seekBar.setEnabled(false);
                       }

                   } else if (finalbacLevel > 0.08 && finalbacLevel < 0.20) {
                       textViewStatus.setText("Be Careful!");
                       textViewStatus.setBackgroundColor(0xffffff00);
                       textViewStatus.setTextColor(0xffffffff);
                       progressBar.setProgress((int) Double.parseDouble(String.valueOf(finalbacLevel * 10)));
                   } else if (finalbacLevel <= 0.08) {
                       textViewStatus.setText("You are safe!");
                       textViewStatus.setBackgroundColor(0xff00ff00);
                       textViewStatus.setTextColor(0xffffffff);
                       progressBar.setProgress((int) Double.parseDouble(String.valueOf(finalbacLevel * 10)));
                   }
               }
               else
               {
                    Toast.makeText(MainActivity.this,"You must enter the weight then press save",500).show();
               }
           }
       });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewBACLevel.setText("BAC Leve:");
                editTextWeight.setText(null);
                radioButton1.setChecked(true);
                seekBar.setProgress(0);
                gender.setChecked(true);
                textViewStatus.setText(null);
                textViewStatus.setBackgroundColor(0xffffffff);
                progressBar.setProgress(0);
                finalbacLevel=0.0;
                buttonAddDrink.setEnabled(true);
                buttonSave.setEnabled(true);
                editTextWeight.setEnabled(true);
                radioButton1.setEnabled(true);
                radioButton5.setEnabled(true);
                radioButton12.setEnabled(true);
                gender.setEnabled(true);
                seekBar.setEnabled(true);
                flag=false;
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }


}
