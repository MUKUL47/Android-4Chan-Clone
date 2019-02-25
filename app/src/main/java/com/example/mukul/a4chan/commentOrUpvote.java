package com.example.mukul.a4chan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class commentOrUpvote extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> showInList;
    ArrayList<Data> DATA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuppostmenu);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        getWindow().setLayout((int)(w*.85),(int)(h*.56));
        ((Button)findViewById(R.id.comment)).setOnClickListener(this);

    }

    private void getThis() {
        String callingActivity = getIntent().getStringExtra("A");
        if (callingActivity != null && callingActivity.equals("AA")) {
            showInList = (ArrayList<String>) getIntent().getBundleExtra("showlist1").getSerializable("showlist");
            DATA = (ArrayList<Data>) getIntent().getBundleExtra("data1").getSerializable("data");
        }
        Data D = (Data)getIntent().getSerializableExtra("dataClass");

        for(int i = 0; i < DATA.size(); i++){
            if(DATA.get(i) == D) {
                DATA.get(i).addComments(((EditText)findViewById(R.id.eC)).getText()+"","someID");
                //Toast.makeText(getApplication(),i.getTitle(),Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        Intent I = new Intent(this,browsecontent.class);
        I.putExtra("comment",((EditText)findViewById(R.id.eC)).getText()+"");
        getThis();
        sendThis(I);
        startActivity(I);
    }
    private void sendThis(Intent I){
        Bundle args = new Bundle();
        args.putSerializable("showlist",(Serializable)showInList);
        I.putExtra("showlist1",args);
        args.putSerializable("data",(Serializable)DATA);
        I.putExtra("data1",args);
        I.putExtra("A","AA");
    }
}
