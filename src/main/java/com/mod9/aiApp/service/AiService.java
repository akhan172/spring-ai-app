package com.mod9.aiApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;

    public String askAi(int userId, String topic) {

        return chatClient.prompt()
                .system("Provide only context specific info and answer in a short but explained manner")
                .user(topic)
                .advisors(
                        //new SafeGuardAdvisor(List.of("Politics", "Gaming")),

                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build(),

                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .defaultTopK(4)
                                .build()
                )
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();
    }

}
