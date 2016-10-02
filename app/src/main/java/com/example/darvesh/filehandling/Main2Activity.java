package com.example.darvesh.filehandling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button delete, update, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        String rollNumber="";
        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.darvesh.filehandling", Context.MODE_PRIVATE);


        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        logout = (Button) findViewById(R.id.log_out);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rollNumber = extras.getString("rollNum");
        }

        final String r = rollNumber;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("roll_num", "-1").apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    final SQLiteDatabase database = openOrCreateDatabase("students", MODE_PRIVATE, null);
                    database.execSQL("DELETE from users where roll_no = '"+r+"'");
                    sharedPreferences.edit().putString("roll_num", "-1").apply();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }
}
