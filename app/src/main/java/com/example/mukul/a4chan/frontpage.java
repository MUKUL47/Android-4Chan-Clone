package com.example.mukul.a4chan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class frontpage extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> showInList;
    DatabaseHelper DB;
    String hardCodedMobileUniqueId[] = {"iz4bW2qC6BkVBD90tXQk","2e1UlKRUOVbWN6yKT1Ju","rWqvQTYty1ZCp0woa5EW","vErEopJrQrYMSG1YxsGi",
                                        "yT8JmGZ547jXTQG0Zabo","l1iTty0WwWOkQETGLOl6","glbqyTc4mtpAxDsceQ84","t6RgtevJgcdMpNUTqnVy",
                                        "5ntwdamL1QeeKhsWHm15","Hgxlvp3kGsfI1FuvP2RF"};
    String HCMUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
        DB = new DatabaseHelper(this);
        ((Button)findViewById(R.id.button)).setOnClickListener(this);
        ((Button)findViewById(R.id.button2)).setOnClickListener(this);
        ((Button)findViewById(R.id.trend)).setOnClickListener(this);
        HCMUID = hardCodedMobileUniqueId[new Random().nextInt(10)];
        updateMobileId();
        ((TextView)findViewById(R.id.mId)).setText("Your Unique ID : "+HCMUID);

    }

    private void updateMobileId() {
        HCMUID = getIntent().getStringExtra("mobileId");
        if(HCMUID == null) { HCMUID = hardCodedMobileUniqueId[new Random().nextInt(10)];
            //HCMUID = hardCodedMobileUniqueId[2];
            }
    }

    @Override
    public void onClick(View v) {
        Intent I = null;
        if(v.getId() == R.id.button)  I = new Intent(this, postcontent.class);

        if(v.getId() == R.id.button2) I = new Intent(this, browsecontent.class);

        if(v.getId() == R.id.trend)   I = new Intent(this, trendingPage.class);

        I.putExtra("mobileId",HCMUID);

        startActivity(I);

    }
}
