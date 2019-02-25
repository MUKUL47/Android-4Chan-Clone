package com.example.mukul.a4chan;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showfullpost extends AppCompatActivity implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    String comments[];
    String _Id = "", HCMUID, secureSplit = "___8GywshqHTJyxVhPxN4CPvPwUUUN4SXw4sWgL2KPM6L82ECzBbAe7jGpZrp6tAZJejGYTQLLn___";
    DatabaseHelper DB;
    boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_custom_layout);
        DB = new DatabaseHelper(this);
        if(getIntent().getStringExtra("_Id") != null){
            _Id = getIntent().getStringExtra("_Id");
        }
        setContentView(R.layout.activity_showfullpost);
        showSelectedItem();
        preLoad();
        restoreComments();
        updateMobileId();
            }

    private void preLoad() {
        ((Button)findViewById(R.id.button5)).setOnClickListener(this);
        ((Button)findViewById(R.id.up)).setOnClickListener(this);
        ((Button)findViewById(R.id.down)).setOnClickListener(this);
        ((TextView)findViewById(R.id.upScore)).setText(DB.getUpVotes(_Id)+"");
        ((TextView)findViewById(R.id.downScore)).setText(DB.getDownVotes(_Id)+"");
    }

    private void updateMobileId() {
        HCMUID = getIntent().getStringExtra("mobileId");
    }

    private void restoreComments() {
        ArrayList getId = DB.getOnlyId(_Id);
        int idIndex = getId.indexOf(_Id);
        comments = DB.getOnlyComments().get(idIndex).split(secureSplit);
        ArrayAdapter<String> cmts = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,comments);
        ((ListView)findViewById(R.id.commentList)).setAdapter(cmts);
    }


    private void showSelectedItem() {
        ArrayList getId = DB.getOnlyId(_Id);
        int idIndex = getId.indexOf(_Id);

        ((TextView)findViewById(R.id.t)).setText(DB.getOnlyTitles("S").get(idIndex)+"");
        ((TextView)findViewById(R.id.t2)).setText(DB.getOnlyCategory("S").get(idIndex)+"");
        ((TextView)findViewById(R.id.t3)).setText(DB.getOnlyContent("S").get(idIndex)+"");
        ((TextView)findViewById(R.id.date)).setText("Post ID : "+DB.getOnlyPostId("S").get(idIndex)+"");
        ((TextView)findViewById(R.id.postId)).setText("Created On : "+DB.getOnlyDate("S").get(idIndex)+"");

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button5 && ((EditText)findViewById(R.id.comment)).getText()+"" != ""){
            comments = new String[]{};
            ArrayList getId = DB.getOnlyId(_Id);
            int idIndex = getId.indexOf(_Id);
            comments = DB.getOnlyComments().get(idIndex).split(secureSplit);
            DB.updateCommentSection(_Id,(DB.getOnlyComments().get(idIndex)+secureSplit+((EditText)findViewById(R.id.comment)).getText()+""));
            comments = DB.getOnlyComments().get(idIndex).split(secureSplit);
            ArrayAdapter<String> cmts = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,comments);
            ((ListView)findViewById(R.id.commentList)).setAdapter(cmts);
            ((EditText)findViewById(R.id.comment)).setText("");
        }

        if(v.getId() == R.id.up){
                upVote();
            }

        if(v.getId() == R.id.down){
                downVote();
            }
        }

    private void downVote() {
        ArrayList downVotes = DB.checkIfDownvoteIdExist(_Id);
        int getDownVotes = DB.getDownVotes(_Id);
        String downVoteId = "";
        for(int i=0;i<downVotes.size();i++){ downVoteId += downVotes.get(i); }
        String checkDupId[] = downVoteId.split(secureSplit);
        exist = false;
        exist = Arrays.asList(checkDupId).indexOf(HCMUID) >= 0;

        if( downVoteId == "" ){ DB.setDownVote(HCMUID,_Id,getDownVotes,false);  }
        else{ DB.setDownVote(downVoteId+secureSplit+HCMUID,_Id,getDownVotes,exist);

            /////////check and remove if upvote HCMUID exist//////////////////////
            String getIfUpVote ="";
            for(int i=0;i<DB.checkIfUpvoteIdExist(_Id).size();i++){ getIfUpVote += DB.checkIfUpvoteIdExist(_Id).get(i); }
            List<String> SS =  Arrays.asList(getIfUpVote.split(secureSplit));
            if( SS.contains(HCMUID)) removeIfUpVoted(SS);
            //////////////////////////////////////////////////////////////////////

        }
        if( exist ){  Toast.makeText(getApplicationContext(),"You already DownVoted this post",Toast.LENGTH_LONG).show();}
        else Toast.makeText(getApplicationContext(),"DownVoted",Toast.LENGTH_LONG).show();
    }

    private void upVote() {
        ArrayList upVotes = DB.checkIfUpvoteIdExist(_Id);
        int getUpVotes = DB.getUpVotes(_Id);
        String upVoteId = "";
        for(int i=0;i<upVotes.size();i++){ upVoteId += upVotes.get(i); }
        String checkDupId[] = upVoteId.split(secureSplit);
        exist = false;
        exist = Arrays.asList(checkDupId).indexOf(HCMUID) >= 0;

        if( upVoteId == "" ) { DB.setUpVote(HCMUID,_Id,getUpVotes,false); }
        else { DB.setUpVote(upVoteId+secureSplit+HCMUID,_Id,getUpVotes,exist);

            /////////check and remove if downvote HCMUID exist//////////////////////
            String getIfDownVote ="";
            for(int i=0;i<DB.checkIfDownvoteIdExist(_Id).size();i++){ getIfDownVote += DB.checkIfDownvoteIdExist(_Id).get(i); }
            List<String> SS =  Arrays.asList(getIfDownVote.split(secureSplit));
            if( SS.contains(HCMUID)) removeIfDownVoted(SS);
            //////////////////////////////////////////////////////////////////////

        }
        if( exist ) Toast.makeText(getApplicationContext(),"You already UpVoted this post",Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(),"UpVoted",Toast.LENGTH_LONG).show();
    }

    private void removeIfDownVoted(List<String> checkDupId) {
        String SS = "";
        for(int i =0;i<checkDupId.size();i++){
            if(checkDupId.indexOf(HCMUID) != i){
                if(SS == "") SS+=checkDupId.get(i);
                else SS += secureSplit+checkDupId.get(i);
            }
        }
        DB.setDownVote(SS,_Id,DB.getDownVotes(_Id)-2,false);
    }

    private void removeIfUpVoted(List<String> checkDupId) {
        String SS = "";
        for(int i =0;i<checkDupId.size();i++){
            if(checkDupId.indexOf(HCMUID) != i){
                if(SS == "") SS+=checkDupId.get(i);
                else SS += secureSplit+checkDupId.get(i);
            }
        }
        DB.setUpVote(SS,_Id,DB.getUpVotes(_Id)-2,false);

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent I = new Intent(this, browsecontent.class);
        I.putExtra("mobileId",HCMUID);
        startActivity(I);
    }


}