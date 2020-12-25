package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class create extends AppCompatActivity {

    EditText ctitle, ccontent;
    Calendar c;
    String todaysdate, currenttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Toolbar ctoolbar = findViewById(R.id.ctoolbar);
        //setSupportActionBar(ctoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ctitle = findViewById(R.id.create_title);
       ccontent = findViewById(R.id.create_content);

       /*ctitle.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.length() != 0){
                   getSupportActionBar().setTitle(s);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });*/

       c = Calendar.getInstance();
       todaysdate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(c.getTime());
       int am_pm = c.get(Calendar.AM_PM);
        currenttime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE))+" "+((am_pm == Calendar.AM)?"am":"pm");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.csave) {
            Model_database md = new Model_database(ctitle.getText().toString(),ccontent.getText().toString(),todaysdate,currenttime);
            Database db = new Database(this);
            long rid = db.addNote(md);

            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            create.this.finish();
        }

        if (id == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String pad(int i){
        if(i<10){
            return "0"+i;
        }else {
            return String.valueOf(i);
        }
    }



}