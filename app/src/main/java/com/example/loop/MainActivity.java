package com.example.loop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText RecentDividend, FirstGrowthTime, FirstGrowthRate, SecondGrowthTime, SecondGrowthRate, FinalGrowthRate, DiscountRate;
    private EditText Dividend, DF,extraValue, PvOfDividend;
    private EditText PV;
    private LinearLayout set;
    private TableRow heading1;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecentDividend = findViewById(R.id.RecDiv);
        FirstGrowthRate = findViewById(R.id.frGrwRate);
        FirstGrowthTime = findViewById(R.id.FGrthTime);
        SecondGrowthRate = findViewById(R.id.SecGrwRt);
        SecondGrowthTime = findViewById(R.id.SGrthTime);
        FinalGrowthRate = findViewById(R.id.FinGrwRt);
        DiscountRate = findViewById(R.id.Dsrate);
        PV = findViewById(R.id.FinPV);

        set = findViewById(R.id.set);

        DF = findViewById(R.id.dff);
        Dividend = findViewById(R.id.Div);
        PvOfDividend = findViewById(R.id.PVDivi);
        extraValue = findViewById(R.id.Extr);



        heading1 = findViewById(R.id.table_heading1);
        tableLayout = findViewById(R.id.table);
    }

    public void calculateThree(View view) {

        heading1.setVisibility(View.GONE);
        set.setVisibility(View.GONE);
        tableLayout.removeAllViews();

        Dividend.getText().clear();
        DF.getText().clear();
        PvOfDividend.getText().clear();
        PV.getText().clear();

        if (!TextUtils.isEmpty(RecentDividend.getText()) &&
                !TextUtils.isEmpty(FirstGrowthTime.getText()) &&
                !TextUtils.isEmpty(FirstGrowthRate.getText()) &&
                !TextUtils.isEmpty(SecondGrowthTime.getText()) &&
                !TextUtils.isEmpty(SecondGrowthRate.getText()) &&
                !TextUtils.isEmpty(FinalGrowthRate.getText()) &&
                !TextUtils.isEmpty(DiscountRate.getText())) {

            int fgt;
            int sgt;

            double rd;
            double fgr;
            double sgr;
            double fGr;
            double dr;

            double dividnd = Double.parseDouble(RecentDividend.getText().toString());
            double de = 0;
            double def = 0;
            double dff = 0;
            double pv = 0;

            rd = Double.parseDouble(RecentDividend.getText().toString());
            fgr = Double.parseDouble(FirstGrowthRate.getText().toString());
            sgr = Double.parseDouble(SecondGrowthRate.getText().toString());
            fGr = Double.parseDouble(FinalGrowthRate.getText().toString());
            dr = Double.parseDouble(DiscountRate.getText().toString());

            DecimalFormat decimalFormat = new DecimalFormat("#.###");

            fgt = Integer.parseInt(FirstGrowthTime.getText().toString());
            sgt = Integer.parseInt(SecondGrowthTime.getText().toString());


            heading1.setVisibility(View.VISIBLE);
            set.setVisibility(View.VISIBLE);


            for (int i = 0; i < Integer.parseInt(FirstGrowthTime.getText().toString()) + Integer.parseInt(SecondGrowthTime.getText().toString()) +1; i++) {
                TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.subthree, null);
                tableLayout.addView(tableRow);


                EditText year = (EditText) tableRow.getChildAt(0);
                year.setText(String.valueOf(tableLayout.getChildCount()));
                EditText dividend = (EditText) tableRow.getChildAt(1);
                EditText extraValueET = (EditText) tableRow.getChildAt(2);
                if(i != fgt+sgt - 1){
                    extraValueET.setVisibility(View.INVISIBLE);
                }
                EditText df = (EditText) tableRow.getChildAt(3);
                EditText pvfdv = (EditText) tableRow.getChildAt(4);


                dff = Math.pow(1 / ((dr / 100) + 1), // Percent to decimal
                        Double.parseDouble(String.valueOf(tableLayout.getChildCount())));
                df.setText(String.valueOf(decimalFormat.format(dff)));

                if (i < fgt) {

                    dividnd += dividnd * (fgr / 100); // Percent to decimal
                    dividend.setText((String.valueOf(decimalFormat.format(dividnd))));

                    double pvT = dividnd * dff;
                    pv += pvT;
                    pvfdv.setText(String.valueOf(decimalFormat.format(pvT)));
                } else if (i >= fgt && i < fgt + sgt) {
                    dividnd += dividnd * sgr / 100;
                    dividend.setText((String.valueOf(decimalFormat.format(dividnd))));

//                    dff = Math.pow(1 / (dr + 1),
//                            Double.parseDouble(String.valueOf(tableLayout.getChildCount())));
//                    df.setText(String.valueOf(decimalFormat.format(dff)));


                    if (i == fgt + sgt - 1) {
                        de = dividnd;
                        def = dff;
                    } else {
                        double pvT = dividnd * dff;
                        pv += pvT;
                        pvfdv.setText(String.valueOf(decimalFormat.format(pvT)));
                    }
                }else{
                    dividnd += dividnd * (fGr / 100); // Percent to decimal
                    dividend.setText((String.valueOf(decimalFormat.format(dividnd))));

                    double extraValue = dividnd / ((dr - fGr)/ 100); // Percent to decimal
                    // Create extraValueEditText
                    TableRow tbr = (TableRow) tableLayout.getChildAt(i - 1);
                    EditText evt = (EditText) tbr.getChildAt(2);
                    evt.setText((String.valueOf(decimalFormat.format(extraValue))));

                    double pvT = (de + extraValue) * def;
                    pv += pvT;
                    EditText et = (EditText) tbr.getChildAt(4);
                    et.setText(String.valueOf(decimalFormat.format(pvT)));

                }
            }
            PV.setText(String.valueOf(decimalFormat.format(pv)));
        } else {
            Toast.makeText(this, "Fill Properly", Toast.LENGTH_SHORT).show();
        }
    }
}