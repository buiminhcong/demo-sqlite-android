package com.bmcong2k.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.bmcong2k.sqlite.dal.SQliteHelper;
import com.bmcong2k.sqlite.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btnUpdate, btnBack, btnRemove;
    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();

        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());
        int p = 0;
        for(int i=0; i<sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p=i;
                break;
            }
        }
        sp.setSelection(p);
    }
    private void initView() {

        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnRemove = findViewById(R.id.btnDelete);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spriner,
                getResources().getStringArray(R.array.category)));

    }

    @Override
    public void onClick(View v) {
        SQliteHelper db = new SQliteHelper(this);
        if(v == eDate){
            final Calendar c  = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        if(v == btnBack){
            finish();
        }

        if(v == btnUpdate){
            String  t = eTitle.getText().toString();
            String  p = ePrice.getText().toString();
            String  c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();

            if(!t.isEmpty() && p.matches("\\d+")){
                int id = item.getId();
                Item i = new Item(id,t, c, p, d);
                db.update(i);
                finish();
            }
        }

        if(v == btnRemove){
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong Bao Xoa");
            builder.setMessage("Ban Co Chac Muon Xoa " + item.getTitle() + " khong?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(id);
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}