package com.example.darvesh.filehandling;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button sign_in, register;
    EditText roll_number, pass, name;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_in = (Button) findViewById(R.id.sign_in);
        register = (Button) findViewById(R.id.register);
        roll_number = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        name = (EditText) findViewById(R.id.name);
        tv = (TextView) findViewById(R.id.textView);

        try{

            final SQLiteDatabase database = openOrCreateDatabase("students", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, roll_no VARCHAR, password VARCHAR)");

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a=roll_number.getText().toString(),
                            b=pass.getText().toString(),
                            c=name.getText().toString();

                    int exists = 0;


                    Cursor crs = database.rawQuery("SELECT * FROM users", null);
                    int roll_n_index = crs.getColumnIndex("roll_no");

                    if(crs.moveToFirst()){
                        do{
                            if(crs.getString(roll_n_index).equals(a)){
                                exists = 1;
                            }
                        }while(crs.moveToNext());
                    }

                    if(exists==0){
                        database.execSQL("INSERT INTO `users` (name, roll_no, password) VALUES ('"+c+"', '"+a+"', '"+b+"')");
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Roll Number Already Exists!!", Toast.LENGTH_SHORT).show();
                    }

                    crs.close();

                }
            });


            sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String a=roll_number.getText().toString(),
                            b=pass.getText().toString();

                    int exists = 0;

                    Cursor crs = database.rawQuery("SELECT * FROM users", null);

                    int roll_n_index = crs.getColumnIndex("roll_no"),
                            pass_index = crs.getColumnIndex("password");

                    if(crs.moveToFirst()){
                        do{
                            if(crs.getString(roll_n_index).equals(a) && crs.getString(pass_index).equals(b)){
                                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                startActivity(i);
                                exists = 1;
                                break;
                            }
                        }while(crs.moveToNext());
                    }
                    if(exists==0){
                        Toast.makeText(getApplicationContext(), "No User Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){


        }
    }
}
