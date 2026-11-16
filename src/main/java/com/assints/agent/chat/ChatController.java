package com.assints.agent.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping ("/api/chat")
public class ChatController {


    private final ChatClient geminiClient;
    private final ChatClient openAiClient;

    public ChatController (@Qualifier("geminiClient") ChatClient geminiClient, 
                           @Qualifier("openAiClient")ChatClient openAiClient){
        this.geminiClient = geminiClient;
        this.openAiClient = openAiClient;
    }

    

    @PostMapping
    public String Chat(@RequestBody String prompt, @RequestParam(defaultValue = "gemini") String model ) {
        
        return resolveClient(model).prompt(prompt).call().content();
    }

    @PostMapping(value = "/stream", produces = "text/event-stream; charset=UTF-8")
    public Flux<String> chatStream(@RequestBody String prompt, @RequestParam(defaultValue = "gemini")String model ) {
        
        return resolveClient(model).prompt(prompt).stream().content();
    }
    

    private ChatClient resolveClient(String  model){
           return "gemini".equalsIgnoreCase(model)
            ? geminiClient
            : openAiClient;
    }
    
}
