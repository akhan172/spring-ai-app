package com.mod9.aiApp.service;

import com.mod9.aiApp.records.TicketAnalysis;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class TicketAnalyser {

    private ChatClient chatclient;

    public TicketAnalysis analysis(String dept, String userMessage) {
        Prompt prompt = createAnalyzerPrompt(dept, userMessage);
        var response = chatclient.prompt(prompt)
                .options(ChatOptions.builder()
                        .temperature(0.1))
                .call()
                .responseEntity(TicketAnalysis.class);
        TicketAnalysis ticketAnalysis = response.entity();
        ChatResponse resp = response.getResponse();
        var usage = resp.getMetadata().getUsage();

        if (usage != null) {
            System.out.println("Input tokens: " + usage.getPromptTokens());
            System.out.println("Output tokens: " + usage.getCompletionTokens());
            System.out.println("Total tokens: " + usage.getTotalTokens());
        }
        return ticketAnalysis;
    }
    private Prompt createAnalyzerPrompt(String segment, String userMessage){
        return analyserPrompt.create(Map.of(
                "segment", segment,
                "userMessage", userMessage
        ));
    }
    private final PromptTemplate analyserPrompt = new PromptTemplate("""
            You are an expert customer support analyser for a company which deals in {segment}.
            You have to read the user data and classify the ticket.
            classify the ticket on its Category, the priority and the suggested action only.
            the user message is {userMessage}.
            """);

}
