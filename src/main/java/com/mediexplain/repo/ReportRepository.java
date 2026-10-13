package com.mediexplain.repo;
import com.mediexplain.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ReportRepository extends JpaRepository<MedicalReport,Long>{List<MedicalReport> findByOwnerOrderByUploadedAtDesc(AppUser owner); Optional<MedicalReport> findByIdAndOwner(Long id,AppUser owner);}
