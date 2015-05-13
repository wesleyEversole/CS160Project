/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Website;

/**
 *
 * @author wesley
 */
public class ForumPosts {
    private final int id;
    private final String date;
    private final String topic;
    private final String content;
    private final String op;
    private final int numberOfReply;

    public ForumPosts(int id, String date, String topic, String content, String op, int numberOfReply) {
        this.id = id;
        this.date = date;
        this.topic = topic;
        this.content = content;
        this.op = op;
        this.numberOfReply = numberOfReply;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public String getOp() {
        return op;
    }

    public int getNumberOfReply() {
        return numberOfReply;
    }
    
}
