package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class edit extends AppCompatActivity {

    EditText etitle, econtent;
    int sid;
    Calendar c;
    String todaysdate, currenttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Toolbar etoolbar = findViewById(R.id.etoolbar);
        //setSupportActionBar(etoolbar);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        etitle = findViewById(R.id.edit_title);
        econtent = findViewById(R.id.edit_content);

        Intent i = getIntent();
        sid = i.getIntExtra("ID",0);

        Database db = new Database(this);
        Model_database md = db.getNote(sid);

        etitle.setText(md.getTitle());
        econtent.setText(md.getContent());

        c = Calendar.getInstance();
        todaysdate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(c.getTime());
        int am_pm = c.get(Calendar.AM_PM);
        currenttime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE))+" "+((am_pm == Calendar.AM)?"am":"pm");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.esave) {
            Model_database md = new Model_database(sid,etitle.getText().toString(),econtent.getText().toString(),todaysdate,currenttime);
            Database db = new Database(this);
            long rid = db.editNote(md);

            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            edit.this.finish();
        }

        if(id == R.id.edit_delete){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete Note?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Database db = new Database(edit.this);
                    db.deleteNote(sid);
                    Intent i = new Intent(edit.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    edit.this.finish();
                }
            });
            builder.show();
        }

        if(id == R.id.edit_info){
            Database db = new Database(this);
            Model_database md = db.getNote(sid);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Info.")
                    .setMessage("Title : "+md.getTitle()+"\nCreated on : "+md.getDate()+"  "+md.getTime());

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }

        if (id == android.R.id.home) {
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