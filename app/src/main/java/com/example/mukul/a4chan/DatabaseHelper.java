package com.example.mukul.a4chan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION  = 1;
    private static final String DATABASE_NAME  = "chanDb";
    private static final String TABLE_CONTACTS = "chanTable";
    private static final String title          = "title";
    private static final String category       = "category";
    private static final String content        = "content";
    private static final String Date           = "Date";
    private static final String uniqueId       = "uniqueId";

    private static final String upvotesId      = "upvotesId";
    private static final String upvotes        = "upvotes";
    private static final String downvotesId    = "downvotesId";
    private static final String downvotes      = "downvotes";

    private static final String _Id            = "_Id";
    private static final String commentSection = "commentSection";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table chanTable " +
                 "(title text primary key," +
                        " category       text," +
                        " content        text," +
                        " Date           text," +
                        " uniqueId       text," +
                        " _Id            text," +
                        " commentSection text," +
                        " upvotesId      text," +
                        " upvotes        integer," +
                        " downvotesId    text," +
                        " downvotes      integer )"
        );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void insertData(String title, String category, String content, String postId, String mobileId, String Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("category", category);
        contentValues.put("content", content);
        contentValues.put("_Id", postId);
        contentValues.put("uniqueId", mobileId);
        contentValues.put("Date", Date);
        contentValues.put("commentSection", "");

        contentValues.put("upvotesId", "");
        contentValues.put("downvotesId", "");
        contentValues.put("upvotes", 0);
        contentValues.put("downvotes", 0);

        db.insert("chanTable", null, contentValues);
    }
    public ArrayList<String> getCustomData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title)).toUpperCase() +"\n"+
                    "\n("+res.getString(res.getColumnIndex(_Id))+")"+"  "+res.getString(res.getColumnIndex(Date)) );
            res.moveToNext();
        }
        return array_list;
            }


    public ArrayList<String> getFullData() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title)));
            array_list.add(res.getString(res.getColumnIndex(category)));
            array_list.add(res.getString(res.getColumnIndex(content)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllTitle() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable", null );

        if(res.getCount() == 0) return array_list;

        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable", null );

        if(res.getCount() == 0) return array_list;

        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(category)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getOnlyTitles(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(title)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList getOnlyCategory(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(category)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList getOnlyContent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(content)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList getOnlyId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(_Id)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList getOnlyPostId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(_Id)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList getOnlyDate(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> A = new ArrayList<>();
        while(!res.isAfterLast()){
            A.add(res.getString(res.getColumnIndex(Date)));
            res.moveToNext();
        }
        return A;
    }
    public ArrayList<String> getOnlyComments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        ArrayList<String> comments = new ArrayList<>();
        if(res.getCount() == 0) return comments;
        while(!res.isAfterLast()){
            comments.add(res.getString(res.getColumnIndex(commentSection)));
            res.moveToNext();
        }
        return comments;
    }
    public boolean updateCommentSection (String _Id, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(commentSection,comment+" ");
        db.update("chanTable", contentValues, "_Id = ?",new String[] { _Id });
        return true;
    }

    public ArrayList<String> getTitleFilter() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title)).toUpperCase() +"\n"+
                    "\n("+res.getString(res.getColumnIndex(_Id)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getCategoryFilter() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(category)).toUpperCase() +"\n"+
                    "\n("+res.getString(res.getColumnIndex(_Id)));
            res.moveToNext();
        }
        return array_list;
    }

    public void setUpVote(String hcmuid, String id, int upVotes, boolean exist) {
        if( !exist ){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(upvotesId,hcmuid);
            contentValues.put(upvotes,  upVotes+1);
            db.update("chanTable", contentValues, "_Id = ?",new String[] { id });
        }
    }

    public void setDownVote(String hcmuid, String id, int downVotes, boolean exist) {
        if( !exist ) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(downvotesId, hcmuid);
            contentValues.put(downvotes, downVotes+1);
            db.update("chanTable", contentValues, "_Id = ?", new String[]{id});
        }
    }

    public int getUpVotes(String postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //ArrayList<String> array_list = new ArrayList<String>();
        int array_list = 0;
        Cursor res =  db.rawQuery( "select * from chanTable where _Id = ?", new String[] { postId }  );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list = Integer.parseInt(res.getString(res.getColumnIndex(upvotes)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> checkIfUpvoteIdExist(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable where _Id = ?", new String[] { id } );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(upvotesId)));
            res.moveToNext();
        }
        return array_list;
    }

    public int getDownVotes(String postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //ArrayList<String> array_list = new ArrayList<String>();
        int array_list = 0;
        Cursor res =  db.rawQuery( "select * from chanTable where _Id = ?", new String[] { postId }  );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list = Integer.parseInt(res.getString(res.getColumnIndex(downvotes)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> checkIfDownvoteIdExist(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable where _Id = ?", new String[] { id } );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(downvotesId)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getUpVotesPost() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable order by upvotes DESC ", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title))+"  [ Up Votes : "+res.getString(res.getColumnIndex(upvotes))+" ] " +
                    " ("+res.getString(res.getColumnIndex(_Id))+")");
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getDownVotesPost() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  db.rawQuery( "select * from chanTable order by downvotes DESC ", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(title))+"  [ Down Votes : "+res.getString(res.getColumnIndex(downvotes))+" ] " +
                    " ("+res.getString(res.getColumnIndex(_Id))+")");
            res.moveToNext();
        }
        return array_list;
    }
}
