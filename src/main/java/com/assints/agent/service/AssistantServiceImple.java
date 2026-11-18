package com.assints.agent.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


@Service 
@Slf4j 
public class AssistantServiceImple implements AssistantService{

    private  final ChatClient geminiClient;
    private  final ChatClient openAiClient;

    @Value("classpath:prompts/explain-condition.st")
    private Resource explainConditionPrompt; 
    @Value("classpath:prompts/symptom-analysis.st")
    private Resource symtomAnalysisPrompt; 

    private PromptTemplate explainConditionTemplate;
    private PromptTemplate symtomAnalysisTemplate;

    @PostConstruct
    void init(){
        symtomAnalysisTemplate = new PromptTemplate(symtomAnalysisPrompt);
        explainConditionTemplate = new PromptTemplate(explainConditionPrompt);
    }

    

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
        
        String message = explainConditionTemplate.render(Map.of("condition", condition));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }

    @Override
    public String analyzeSymptoms(String symptoms, String model) {
        log.info("Analyze of symptoms - symptoms: {}, modelo:{}", symptoms , model);
        
        String message = symtomAnalysisTemplate.render(Map.of("symptoms", symptoms));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }


    
}
