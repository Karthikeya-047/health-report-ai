package com.mediexplain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Conversation conversation;
    @Column(nullable=false,length=10000) private String question;
    @Column(nullable=false,length=20000) private String answer;
    @Column(nullable=false,unique=true) private String submissionKey;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    protected Message(){}
    public Message(Conversation c,String q,String a,String submissionKey){conversation=c;question=q;answer=a;this.submissionKey=submissionKey;}
    public Long getId(){return id;} public String getQuestion(){return question;} public String getAnswer(){return answer;} public LocalDateTime getCreatedAt(){return createdAt;}
}
