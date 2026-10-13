package com.mediexplain.service;

import com.mediexplain.repo.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PdfReportServiceTest {
    private final PdfReportService service = new PdfReportService(mock(ReportRepository.class));
    @Test void rejectsNonPdfBeforeParsing() {
        var file = new MockMultipartFile("file", "notes.txt", "text/plain", "hello".getBytes());
        assertEquals("Only PDF files are supported.", assertThrows(IllegalArgumentException.class, () -> service.upload(null, file)).getMessage());
    }
    @Test void rejectsOversizedPdf() {
        var file = new MockMultipartFile("file", "large.pdf", "application/pdf", new byte[10 * 1024 * 1024 + 1]);
        assertEquals("PDF must be 10 MB or smaller.", assertThrows(IllegalArgumentException.class, () -> service.upload(null, file)).getMessage());
    }
}
