package com.mediexplain.repo;
import com.mediexplain.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ConversationRepository extends JpaRepository<Conversation,Long>{List<Conversation> findByOwnerOrderByCreatedAtDesc(AppUser owner); Optional<Conversation> findByIdAndOwner(Long id,AppUser owner);}
