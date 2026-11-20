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

    @Value("classpath:prompts/route-assistant.st")
    private Resource consultRoutePrompt; 
    @Value("classpath:prompts/explain-incident.st")
    private Resource explainIncidentPrompt;
    @Value("classpath:prompts/analyze-incident.st")
    private Resource generateIncidentReportPrompt;
    @Value("classpath:prompts/consultation.st")
    private Resource consultationPrompt;   

    private PromptTemplate consultRouteTemplate;
    private PromptTemplate explainIncidentTemplate;
    private PromptTemplate generateIncidentReportTemplate;
    private PromptTemplate consultationTemplate;


    @PostConstruct
    void init(){
        consultRouteTemplate = new PromptTemplate(consultRoutePrompt);
        explainIncidentTemplate = new PromptTemplate(explainIncidentPrompt);
        generateIncidentReportTemplate = new PromptTemplate(generateIncidentReportPrompt);
        consultationTemplate = new PromptTemplate(consultationPrompt);

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
    public String consultRoute(String origin, String destination, String model) {
        log.info("Consult request - origin: {}, destination:{}, modelo:{}", origin, destination, model);
        
        String message = consultRouteTemplate.render(Map.of("origin",origin,  "destination", destination));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }

    @Override
    public String explainIncident(String incident, String model) {
        log.info("Explain  Incident - incident: {}, modelo:{}", incident , model);
        
        String message = explainIncidentTemplate.render(Map.of("incident", incident));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }



    @Override
    public String generateIncidentReport(String incident, String model) {
        log.info("Analyze  Incident - incident: {}, modelo:{}", incident , model);
        
        String message = generateIncidentReportTemplate.render(Map.of("incident", incident));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();
    }



    @Override
    public String consultation(String  incident, String model) {
         log.info("Consultion : consultion {}, modelo:{}", incident , model);
        
        String message = consultationTemplate.render(Map.of("incident", incident));
        
        return resolveClient(model)
                .prompt(message)
                .call()
                .content();

    }


    
}
