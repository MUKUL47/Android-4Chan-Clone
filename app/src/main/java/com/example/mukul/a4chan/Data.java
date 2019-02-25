package com.example.mukul.a4chan;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    String title, Category, content, uniquieId;
    ArrayList<comments> comments;
    ArrayList<String> upVotes;

    public Data(String title, String category, String content, String uniquieId) {
        this.title = title;
        Category = category;
        this.content = content;
        comments = new ArrayList<>();
        upVotes = new ArrayList<>();
        this.uniquieId = uniquieId;
    }
    public void addComments(String comment, String uniquieId){
        comments.add(new comments(comment,uniquieId));
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return Category;
    }

    public String getContent() {
        return content;
    }

    public String getUniquieId() {
        return uniquieId;
    }

    public ArrayList<com.example.mukul.a4chan.comments> getComments() {
        return comments;
    }

    public ArrayList<String> getUpVotes() {
        return upVotes;
    }
}
class comments{
String uniquieId, comment;

    public comments(String uniquieId, String comment) {
        this.uniquieId = uniquieId;
        this.comment = comment;
    }
}
class upVotes{
String uniquieId;

    public upVotes(String uniquieId) {
        this.uniquieId = uniquieId;
    }
}
