package com.assints.agent.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssitantConfig {
    
    @Bean("geminiClient") 
    ChatClient geminClient(GoogleGenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    @Bean("openAiClient") 
    ChatClient openAiClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }


}
