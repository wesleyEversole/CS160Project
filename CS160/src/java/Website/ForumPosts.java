package Website;

import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Formatter;

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
    private final int topic;
    private final String title;
    private final String author;
    private final Blob content;    

    public ForumPosts(int id, Date date, int topic,String title, Blob content,String author) {
        this.id = id;
        this.date = date;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    public String getStringDate(){    
        return date.toString();
    }
    public int getTopic() {
        return topic;
    }

    public Blob getContent() {
        return content;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getStringContent() throws SQLException{
        byte[] bufferbyte= content.getBytes(1, (int) content.length());
        Formatter fmat = new Formatter();
        for(int i=0; i <bufferbyte.length;i++){
            fmat.format("c", bufferbyte[i]);
        }
        return fmat.toString();
    }
    public String getAuthor() {
        return author;
    }




    
}

