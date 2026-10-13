package com.mediexplain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Conversation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private AppUser owner;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private MedicalReport report;
    @Column(nullable=false) private String title;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    protected Conversation(){}
    public Conversation(AppUser owner,MedicalReport report,String title){this.owner=owner;this.report=report;this.title=title;}
    public Long getId(){return id;} public AppUser getOwner(){return owner;} public MedicalReport getReport(){return report;} public String getTitle(){return title;} public LocalDateTime getCreatedAt(){return createdAt;}
}
