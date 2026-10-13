package com.mediexplain.service;
import com.mediexplain.model.*; import com.mediexplain.repo.ReportRepository; import org.apache.pdfbox.Loader; import org.apache.pdfbox.pdmodel.PDDocument; import org.apache.pdfbox.text.PDFTextStripper; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.multipart.MultipartFile; import java.nio.file.*;
@Service public class PdfReportService {
 private final ReportRepository reports; @Value("${app.upload-dir}") private String uploadDir; private static final long LIMIT=10L*1024*1024;
 public PdfReportService(ReportRepository reports){this.reports=reports;}
 @Transactional public MedicalReport upload(AppUser owner,MultipartFile file){
  if(file==null||file.isEmpty())throw new IllegalArgumentException("Choose a PDF file to upload.");
  if(file.getSize()>LIMIT)throw new IllegalArgumentException("PDF must be 10 MB or smaller.");
  String original=file.getOriginalFilename()==null?"report.pdf":Path.of(file.getOriginalFilename()).getFileName().toString();
  if(!original.toLowerCase().endsWith(".pdf")||!"application/pdf".equalsIgnoreCase(file.getContentType()))throw new IllegalArgumentException("Only PDF files are supported.");
  Path path=null;
  try {byte[] bytes=file.getBytes();try(PDDocument doc=Loader.loadPDF(bytes)){String text=new PDFTextStripper().getText(doc).trim();Path dir=Paths.get(uploadDir).toAbsolutePath().normalize();Files.createDirectories(dir);path=Files.createTempFile(dir,"report-",".pdf");Files.write(path,bytes);MedicalReport r=new MedicalReport(owner,original,path.toString(),doc.getNumberOfPages(),text,text.isBlank()?"No selectable text found — this may be a scanned PDF.":"Text extracted");return reports.save(r);}}
  catch(IllegalArgumentException e){throw new IllegalArgumentException("This file is not a readable PDF.");}catch(Exception e){if(path!=null)try{Files.deleteIfExists(path);}catch(Exception ignored){}throw new IllegalArgumentException("We could not process this PDF. Please try another file.");}
 }
}
