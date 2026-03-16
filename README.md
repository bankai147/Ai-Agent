# Ai-Agent
Markdown
# Multi-Agent Support System (Gemini 3.1 Flash Lite)

An intelligent customer support infrastructure built with Java and the Google Gemini API. This system utilizes a multi-agent architecture including a Router, Technical Specialist, and Billing Specialist to process user requests using local documentation (RAG).

## Features
- **Router Agent**: Automatically classifies user requests into TECHNICAL, BILLING, or IRRELEVANT categories.
- **Technical Specialist**: Provides answers based on local text files stored in the `docs` directory.
- **Billing Specialist**: Handles financial inquiries and simulates tool calling for plan verification and refund processing.
- **Context Memory**: Maintains chat history to provide context-aware responses during a session.
- **RAG (Retrieval-Augmented Generation)**: Dynamically injects local documentation into the model's prompt for factual accuracy.

## Technical Stack
- **Java 17+**
- **Maven**: Dependency management and build automation.
- **Google GenAI Java SDK**
- **Model**: `gemini-3.1-flash-lite-preview`

## Prerequisites
1. **JDK 17** or higher installed.
2. **Apache Maven** installed.
3. **API Key** obtained from Google AI Studio.

## Installation and Setup

1. **Documentation Setup**:
   Create a directory at `src/main/resources/docs` and populate it with `.txt` files containing your project information.

2. **API Key Configuration**:
   Open `AiAgentsTask.java` and assign your key to the variable:
   ```java
   private static final String API_KEY = "YOUR_API_KEY_HERE";
Build the Project:

Bash
mvn clean compile
Run the Application:

Bash
mvn exec:java -Dexec.mainClass="org.yourcompany.yourproject.AiAgentsTask"
Project Structure
src/main/java/.../AiAgentsTask.java: Main logic and agent definitions.

src/main/resources/docs/: Directory for the knowledge base.

pom.xml: Maven configuration and dependency list.

Security Note
The .class files and the target/ directory are included in the .gitignore file to prevent API key exposure. Do not commit your API key to public repositories.

Runtime Commands
reset: Clears the current chat history.

exit: Terminates the application.


---



This documentation provides a clear guide for any developer to understand, install, and run your project. 

Would you like me to help you draft the `docs/technical_manual.txt` file so the agents have actual content to read?
