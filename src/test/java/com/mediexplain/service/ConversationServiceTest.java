package com.mediexplain.service;

import com.mediexplain.model.AppUser;
import com.mediexplain.repo.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversationServiceTest {
    @Test void refusesConversationOwnedByAnotherUser() {
        ConversationRepository conversations=mock(ConversationRepository.class);
        GeminiService gemini=mock(GeminiService.class);
        ConversationService service=new ConversationService(conversations,mock(MessageRepository.class),mock(ReportRepository.class),gemini);
        when(conversations.findByIdAndOwner(eq(7L),any(AppUser.class))).thenReturn(Optional.empty());
        assertThrows(java.util.NoSuchElementException.class,()->service.ask(new AppUser("A","a@x.test","hash"),7L,"What does this mean?","d1f4c18c-416e-40f4-a513-d5598cfe5569"));
        verifyNoInteractions(gemini);
    }
}
