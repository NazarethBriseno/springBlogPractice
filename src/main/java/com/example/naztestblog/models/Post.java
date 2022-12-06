package com.example.naztestblog.models;

import javax.persistence.*;

@Entity
@Table(name="Posts")
public class Post {

    //Instance variables
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40)
    private String title;
    @Column(nullable = false)
    private String body;

    public Post(String title, String body){
        this.title = title;
        this.body = body;
    }
    public Post(Long id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
    }
    public Post(){}
    //Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    //End of Getters and Setters
}
