package com.example.mukul.a4chan;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class browsecontent extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<String> showInList, Temp;
    //ArrayList<Data> DATA;
    DatabaseHelper DB;
    ArrayList<String> Title = new ArrayList<>();
    ArrayList<String> Category = new ArrayList<>();
    ArrayList<String> Content = new ArrayList<>();
    int positionFromFilter = -1;
    int sendFromFilter = -1;
    String HCMUID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsecontent);
        DB = new DatabaseHelper(this);
        positionFromFilter = -1;
        getSelectedFilter();
        displayList(-1);
        updateMobileId();
    }

    private void updateMobileId() {
            HCMUID = getIntent().getStringExtra("mobileId");
    }

    private void getSelectedFilter() {
        //PREPROCESS
        Title    = DB.getTitleFilter();
        Category = DB.getCategoryFilter();
        final String filter[]= {"Title","Category"};
        ArrayAdapter<String> Filter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,filter);
        ((Spinner)findViewById(R.id.filter)).setAdapter(Filter);
        ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setWidth(6);
        final ArrayAdapter<String> T = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Title);
        final ArrayAdapter<String> C = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Category);
        //SET HINT
        if(((Spinner)findViewById(R.id.filter)).getSelectedItem() == "Title"){
            ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setHint("Enter Title");
        }else{
            ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setHint("Enter Category");
        }
        //////////
        ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setAdapter(T);
        ((Spinner)findViewById(R.id.filter)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setAdapter(T);
                    ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setHint("Enter Title");
                }
                            else {
                    ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setAdapter(C);
                    ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setHint("Enter Category");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((AutoCompleteTextView)findViewById(R.id.titleSearch)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int newIndex;

                if(((AutoCompleteTextView)findViewById(R.id.titleSearch)).getAdapter() == T) newIndex = Title.indexOf(parent.getItemAtPosition(position));
                else newIndex = Category.indexOf(parent.getItemAtPosition(position));

                int i = showInList.get(newIndex).indexOf('(');
                Intent I = new Intent(browsecontent.this,showfullpost.class);
                I.putExtra("_Id",showInList.get(newIndex).substring(i+1,i+17)+"");
                I.putExtra("mobileId",HCMUID);
                startActivity(I);

            }
        });

    }

    private void displayList(int sendFromSelectedFilter) {
        showInList = DB.getCustomData();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
        R.layout.support_simple_spinner_dropdown_item, showInList);
        ListView listView = (ListView) findViewById(R.id.Items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent I = new Intent(this, frontpage.class);
        I.putExtra("mobileId",HCMUID);
        startActivity(I);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = showInList.get(position).indexOf('(');
        Intent I = new Intent(this,showfullpost.class);
        I.putExtra("_Id",showInList.get(position).substring(i+1,i+17)+"");
        I.putExtra("mobileId",HCMUID);
        startActivity(I);
    }
}
