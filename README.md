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

## Interaction Examples

### 1. Routing and Tool Simulation
The system identifies the intent and simulates backend calls for billing tasks.

**Conversation:**
- **User:** "What plan do I have?"
- **Billing Agent:** "I can check your current plan for you. CALL_PLAN"
- **System Output:** `[Backend] User User_777 is on PRO Plan ($29/mo). Status: Active.`

**Description:** Demonstrates the Router correctly identifying a billing query and the Billing Agent triggering the `CALL_PLAN` logic to retrieve user data.

### 2. Contextual Memory (RAG)
The agent uses local documentation to answer specific technical questions.

**Conversation:**
- **User:** "What are the key features of this project?"
- **Tech Specialist:** "Based on the documentation, the key features are Multi-agent support, Context memory, and RAG implementation."

**Description:** Shows the Technical Specialist accessing the `docs/` folder to provide accurate information not present in its original training data.

### 3. Refund Case Processing
Handling multi-step logic and capturing user input for backend services.

**Conversation:**
- **User:** "I want a refund because the app is crashing."
- **Billing Agent:** "I am sorry to hear that. I will open a refund case for you. CALL_REFUND"
- **System Output:** `[Backend] Refund case REF-4829 opened for User_777. Reason: I want a refund because the app is crashing.`

**Description:** Highlights the system's ability to pass the user's specific reason directly to the simulated backend service.

### 4. Handling Irrelevant Queries
The Router protects the system from out-of-scope requests.

**Conversation:**
- **User:** "What is the capital of France?"
- **Support:** "I can only assist with technical or billing questions related to this project."

**Description:** Demonstrates the IRRELEVANT category filter, ensuring the agents stay focused on the project's specific domain.
