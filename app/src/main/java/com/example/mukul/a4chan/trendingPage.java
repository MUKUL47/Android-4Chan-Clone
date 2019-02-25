package com.example.mukul.a4chan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class trendingPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    DatabaseHelper DB;
    String HCMUID;
    String trendFilter[] = {"Up Votes","Down Votes","Most Comments","Less Comments","Recent","Oldest"};
    ArrayList<String> dupList = new ArrayList<>(), DD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_trending_page);
        DB = new DatabaseHelper(this);
        updateMobileId();
        updateSpinner();
        ((Spinner)findViewById(R.id.trendFilter)).setOnItemSelectedListener(this);
        ((ListView)findViewById(R.id.trendList)).setOnItemClickListener(this);
    }

    private void setTrendList(int pos) {

        DD = new ArrayList<>();

        if( trendFilter[pos] == "Up Votes" ){ dupList = DB.getUpVotesPost(); }
   else if( trendFilter[pos] == "Down Votes" ){ dupList = DB.getDownVotesPost(); }


        for(int i=0;i<DB.getUpVotesPost().size();i++){
            DD.add(i,dupList.get(i).substring(0,dupList.get(i).indexOf('(')));
        }
        ((ListView)findViewById(R.id.trendList)).setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,DD));
    }

    private void updateSpinner() {
        ((Spinner)findViewById(R.id.trendFilter)).setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,trendFilter));

    }

    private void updateMobileId() {
        HCMUID = getIntent().getStringExtra("mobileId");
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent I = new Intent(this, frontpage.class);
        I.putExtra("mobileId",HCMUID);
        startActivity(I);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { setTrendList(position); }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),dupList+"",Toast.LENGTH_LONG).show();
        int i = dupList.get(position).indexOf('(');
        Intent I = new Intent(this,showfullpost.class);
        I.putExtra("_Id",dupList.get(position).substring(i+1,i+17)+"");
        I.putExtra("mobileId",HCMUID);
        startActivity(I);
    }
}
