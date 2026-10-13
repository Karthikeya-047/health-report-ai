package com.mediexplain.repo;
import com.mediexplain.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface MessageRepository extends JpaRepository<Message,Long>{List<Message> findByConversationOrderByCreatedAtAsc(Conversation c); java.util.Optional<Message> findByConversationAndSubmissionKey(Conversation c,String submissionKey);}
