package Website;

import java.sql.Blob;
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Website;

/**
 *
 * @author wesley
 */
public class ForumPosts {
    private final int id;
    private final Date date;
    private final String topic;
    private final String title;
    private final Blob content;
    private final int numberOfReply;

    public ForumPosts(int id, Date date, String topic,String title, Blob content,int numberOfReply) {
        this.id = id;
        this.date = date;
        this.topic = topic;
        this.content = content;
        this.numberOfReply = numberOfReply;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public Blob getContent() {
        return content;
    }

    public int getNumberOfReply() {
        return numberOfReply;
    }




    
}
