package org.yourcompany.yourproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

class BillingService {
    public static String getPlanAndPricing(String userId) {
        return "[Backend] User " + userId + " is on PRO Plan ($29/mo). Status: Active.";
    }

    public static String openRefundCase(String userId, String reason) {
        String caseId = "REF-" + (int)(Math.random() * 10000);
        return "[Backend] Refund case " + caseId + " opened for " + userId + ". Reason: " + reason;
    }

    public static String getBillingHistory(String userId) {
        return "[Backend] Recent transactions for " + userId + ": Mar 1 ($29), Feb 1 ($29).";
    }

    public static String sendRefundForm(String userId) {
        return "[Backend] Refund request form has been sent to the user's registered email.";
    }
}

public class AiAgentsTask {

    private static final String API_KEY = "YOUR_API_KEY_HERE"; 
    private static final Client client = Client.builder().apiKey(API_KEY).build();
    private static final String MODEL_NAME = "gemini-3.1-flash-lite-preview"; 

    private static String chatHistory = "";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("System Message: Multi-Agent Support System Online.");
        System.out.println("Type 'exit' to quit or 'reset' to clear chat memory.");

        while (true) {
            System.out.print("\nYou: ");
            String userInput = scanner.nextLine();
            
            if (userInput.equalsIgnoreCase("exit")) break;
            
            if (userInput.equalsIgnoreCase("reset")) {
                chatHistory = "";
                System.out.println("System: Chat memory cleared.");
                continue;
            }

            try {
                String routingPrompt = """
                    Classify the user request into ONLY ONE category: TECHNICAL, BILLING, or IRRELEVANT.
                    - TECHNICAL: Setup, errors, JDK, Maven, API config.
                    - BILLING: Prices, refunds, payments, plans.
                    - IRRELEVANT: Anything not related to the project documentation.
                    Return ONLY the category name.
                    Request: """ + userInput;

                GenerateContentResponse response = client.models.generateContent(MODEL_NAME, routingPrompt, null);
                String category = response.text().toUpperCase().trim();

                if (category.contains("TECHNICAL")) {
                    handleTechnical(userInput);
                } else if (category.contains("BILLING")) {
                    handleBilling(userInput); 
                } else {
                    System.out.println("Support: I can only assist with technical or billing questions.");
                }

                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("System Error: " + e.getMessage());
            }
        }
    }

    private static void handleBilling(String query) throws Exception {
        String context = readDocs();
        String prompt = "You are a Billing Specialist. Tools: CALL_PLAN, CALL_REFUND, CALL_HISTORY, SEND_FORM. \nContext: " + context + "\nHistory: " + chatHistory + "\nQuery: " + query;

        GenerateContentResponse response = client.models.generateContent(MODEL_NAME, prompt, null);
        String aiText = response.text();
        System.out.println("Billing Agent: " + aiText);
        
        chatHistory += "User: " + query + "\nAgent: " + aiText + "\n";

        if (aiText.contains("CALL_REFUND")) System.out.println(BillingService.openRefundCase("User_777", query));
        if (aiText.contains("CALL_PLAN")) System.out.println(BillingService.getPlanAndPricing("User_777"));
        if (aiText.contains("CALL_HISTORY")) System.out.println(BillingService.getBillingHistory("User_777"));
        if (aiText.contains("SEND_FORM")) System.out.println(BillingService.sendRefundForm("User_777"));
    }

    private static void handleTechnical(String query) throws Exception {
        String context = readDocs();
        String prompt = "You are a Technical Specialist. Context: " + context + "\nHistory: " + chatHistory + "\nQuestion: " + query;

        GenerateContentResponse response = client.models.generateContent(MODEL_NAME, prompt, null);
        String aiText = response.text();
        System.out.println("Tech Specialist: " + aiText);
        chatHistory += "User: " + query + "\nAgent: " + aiText + "\n";
    }

    private static String readDocs() throws IOException {
        Path docsPath = Paths.get("src/main/resources/docs");
        if (!Files.exists(docsPath)) return "No documentation found.";
        try (var stream = Files.walk(docsPath)) {
            return stream.filter(Files::isRegularFile)
                    .map(path -> {
                        try { return Files.readString(path); } catch (IOException e) { return ""; }
                    }).collect(Collectors.joining("\n"));
        }
    }
}