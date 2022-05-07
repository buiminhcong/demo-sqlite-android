package com.bmcong2k.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bmcong2k.sqlite.dal.SQliteHelper;
import com.bmcong2k.sqlite.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btnUpdate, btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();

        btnUpdate.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        eDate.setOnClickListener(this);


    }

    private void initView() {

        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancle = findViewById(R.id.btnCancle);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spriner,
                getResources().getStringArray(R.array.category)));

    }

    @Override
    public void onClick(View v) {
        if(v == eDate){
            final Calendar c  = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    if(month>8 && dayOfMonth >8){
                        date= dayOfMonth+"/" + (month+1) + "/" +year;
                    }else  if(month>8 && dayOfMonth <=8){
                        date=  "0"+dayOfMonth+"/" + (month+1) + "/" +year;
                    }
                    else if(month<=8 && dayOfMonth >8){
                        date= dayOfMonth+"/0" + (month+1) + "/" +year;
                    }else {
                        date= "0"+ dayOfMonth+"/0" + (month+1) + "/" +year;
                    }
                    date.trim();
                    eDate.setText(date);
                }
            },year, month, day);
            dialog.show();
        }

        if (v == btnCancle){
            finish();
        }

        if (v == btnUpdate){
            String  t = eTitle.getText().toString();
            String  p = ePrice.getText().toString();
            String  c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();

            if(!t.isEmpty() && p.matches("\\d+")){
                Item i = new Item(t, c, p, d);
                SQliteHelper db = new SQliteHelper(this);
                db.addItem(i);
                finish();
            }
        }

    }
}