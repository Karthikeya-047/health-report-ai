# MediExplain AI

A locally runnable Java 21 and Spring Boot application for uploading text-based medical report PDFs, viewing extracted text, and asking Gemini questions grounded in the report. It includes private user accounts, persisted report conversations, and a responsive Thymeleaf interface.

## Requirements

- Java 21 (JDK)
- Apache Maven 3.9+
- Internet access at runtime only for Bootstrap/Google Fonts CDN assets and Gemini Q&A (the application itself and H2 run locally)

Install a JDK 21 distribution (for example Eclipse Temurin) and Maven, then confirm `java -version` and `mvn -version` in PowerShell.

## Configure Gemini (optional until you use Q&A)

Create a Gemini API key in Google AI Studio. The application reads `GEMINI_API_KEY` on the server. It is never sent to the browser. If unset, account, upload, report, and history features still work, and chat displays a clear configuration message without inventing an answer.

In Windows PowerShell, set it for the current terminal session before starting:

```powershell
$env:GEMINI_API_KEY = "your_actual_key"
mvn spring-boot:run
```

For a persistent user environment variable, run `setx GEMINI_API_KEY "your_actual_key"`, then close and reopen PowerShell before launching the app. `.env.example` documents the placeholder format; Spring Boot does not load `.env` automatically. Never commit a real key.

## Run

From the project directory:

```powershell
mvn spring-boot:run
```

Open <http://localhost:8080>. Register an account, upload a text-based PDF (maximum 10 MB), inspect the extraction, and start a report conversation. Scanned PDFs with no selectable text are identified; OCR is not included.

The H2 database persists at `./data/mediexplain.mv.db`. Uploaded PDFs are stored under `./data/uploads/`, outside public resources. Both paths are excluded from version control and project ZIP delivery.

## Verify

```powershell
mvn test
mvn package
```

## Privacy and scope

BCrypt hashes passwords. Report and conversation lookups are scoped to the authenticated owner. Gemini requests include extracted report text (up to 24,000 characters), recent chat turns, and the question. Do not upload documents unless you are comfortable processing them with your configured AI provider. Answers are educational, not diagnosis or treatment; discuss results with a qualified clinician. Local H2 storage is intended for development/demo use, not production deployment.
