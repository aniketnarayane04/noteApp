package com.example.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int s=3;
    private static final String TAG = "MainActivity";

    RecyclerView mrecyclerview;
    Adapter adapter;
    List<Model_database> notes = new ArrayList<>();


    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, create.class);
                startActivity(i);
                //MainActivity.this.finish();
            }
        });

        mrecyclerview = findViewById(R.id.myrecyclerview);

        Database db = new Database(this);
        db.sort(s);
        notes = db.getAllNotes();

        TextView no_note_tv = findViewById(R.id.no_note_tv);
        no_note_tv.setVisibility(View.GONE);


        if(!notes.isEmpty()){
            mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter(this, notes);
            mrecyclerview.setAdapter(adapter);
        }else{
            no_note_tv.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setQueryHint("Search Note");


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Database db = new Database(this);

       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: &&&&&&&&&&&&&&&&"+newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/

        if(id == R.id.sortAZ){
            s = 1;
            db.sort(s);
            notes = db.getAllNotes();


            if(!notes.isEmpty()){
                mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
                adapter = new Adapter(this, notes);
                mrecyclerview.setAdapter(adapter);
            }

        }

        if(id == R.id.sortZA){
            s = 2;
            db.sort(s);
            notes = db.getAllNotes();

            if(!notes.isEmpty()){
                mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
                adapter = new Adapter(this, notes);
                mrecyclerview.setAdapter(adapter);
            }
        }

        if(id == R.id.sortOld){
            s = 4;
            db.sort(s);
            notes = db.getAllNotes();

            if(!notes.isEmpty()){
                mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
                adapter = new Adapter(this, notes);
                mrecyclerview.setAdapter(adapter);
            }
        }

        if(id == R.id.sortNew){

            s = 3;
            db.sort(s);
            notes = db.getAllNotes();

            if(!notes.isEmpty()){
                mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
                adapter = new Adapter(this, notes);
                mrecyclerview.setAdapter(adapter);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}