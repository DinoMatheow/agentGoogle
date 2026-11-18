package com.assints.agent.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


@Service 
@Slf4j 
public class AssistantServiceImple implements AssistantService{

    private  final ChatClient geminiClient;
    private  final ChatClient openAiClient;

    public AssistantServiceImple(
    @Qualifier("geminiClient") ChatClient geminiClient,
    @Qualifier("openAiClient") ChatClient openAiClient
        ){
            this.geminiClient = geminiClient;
            this.openAiClient = openAiClient;
        }

    @Override
    public String chat(String prompt, String model) {
        log.info("Chat request - model: {}", model);

        return resolveClient(model).prompt(prompt).call().content();
    }

    @Override
    public Flux<String> chatStream(String prompt, String model) {
        log.info("ChatStream request - model: {}", model);

        return resolveClient(model).prompt(prompt).stream().content();
    }

    private ChatClient resolveClient(String  model){
           return "gemini".equalsIgnoreCase(model)
            ? geminiClient
            : openAiClient;
    }

    @Override
    public String explainCondition(String condition, String model) {
        log.info("Explain request - condition: {}, modelo:{}", condition, model);
        
        return resolveClient(model)
                .prompt()
                .user(u -> u.text("Explain to me what it is {condicion} in simple terms for a patient")
                            .param("condition",condition))
                .call()
                .content();
    }


    
}
