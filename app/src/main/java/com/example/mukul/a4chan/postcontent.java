package com.example.mukul.a4chan;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class postcontent extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> showInList;

    DatabaseHelper DB;
    static final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private Random rng = new SecureRandom();
    private String ID = "", HCMUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcontent);
        DB = new DatabaseHelper(this);

        ((TextView)findViewById(R.id.answer2)).setHint("  "+((int)(Math.random() * ((10 - 1) + 1)))+" + "+((int)(Math.random() * ((10 - 1) + 1))));
        ((Button)findViewById(R.id.button3)).setOnClickListener(this);
        setAutoCompleteToCategory();
        updateMobileId();

    }

    private void setAutoCompleteToCategory() {
        ((AutoCompleteTextView)findViewById(R.id.category)).setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,DB.getOnlyCategory("")));
    }


    private boolean validatePost() {
        EditText T = ((EditText) findViewById(R.id.title)), C = ((EditText) findViewById(R.id.title));
        AutoCompleteTextView CC = (AutoCompleteTextView) findViewById(R.id.category);
        if (T.getText().toString().length() == 0 ||
            C.getText().toString().length() == 0 ||
           CC.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_LONG).show();
            return false;
        }
        int A = Integer.parseInt(((TextView) findViewById(R.id.answer2)).getHint().charAt(2) + "") +
                Integer.parseInt(((TextView) findViewById(R.id.answer2)).getHint().charAt(6) + "");

//        if( ((EditText)findViewById(R.id.answer2)).getText()+"" != A+"" ){
//            Toast.makeText(getApplicationContext(), "Enter correct security answer", Toast.LENGTH_LONG).show();
//            return false;
//        }

        return true;
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
    public void onClick(View v) {
        EditText T = ((EditText)findViewById(R.id.title)),C = ((EditText)findViewById(R.id.content));
        AutoCompleteTextView CC = (AutoCompleteTextView)findViewById(R.id.category);

        for(int i=0;i<16;i++){
            ID += ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
        }
        Toast.makeText(getApplicationContext(), ID+"", Toast.LENGTH_LONG).show();
        //

        if(validatePost()) {
            String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            DB.insertData(T.getText()+"",CC.getText()+"",C.getText()+"",ID,HCMUID,timeStamp);
            Intent I = new Intent(this,browsecontent.class);
            I.putExtra("mobileId",HCMUID);
            startActivity(I);
        }
        ID = "";
    }
//    private String getDeviceId() {
//        return Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
//    }
}
