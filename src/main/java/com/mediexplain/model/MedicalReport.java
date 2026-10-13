package com.mediexplain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicalReport {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private AppUser owner;
    @Column(nullable=false) private String filename;
    @Column(nullable=false) private String storedPath;
    @Column(nullable=false) private LocalDateTime uploadedAt=LocalDateTime.now();
    private int pageCount;
    @Column(nullable=false,length=1000000) private String extractedText;
    @Column(nullable=false) private String extractionStatus;
    protected MedicalReport(){}
    public MedicalReport(AppUser owner,String filename,String storedPath,int pages,String text,String status){this.owner=owner;this.filename=filename;this.storedPath=storedPath;this.pageCount=pages;this.extractedText=text;this.extractionStatus=status;}
    public Long getId(){return id;} public AppUser getOwner(){return owner;} public String getFilename(){return filename;} public LocalDateTime getUploadedAt(){return uploadedAt;} public int getPageCount(){return pageCount;} public String getExtractedText(){return extractedText;} public String getExtractionStatus(){return extractionStatus;}
}
