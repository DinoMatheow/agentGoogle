package com.assints.agent.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping ("/api/chat")
@RequiredArgsConstructor 
public class ChatController {


    private final ChatClient chatClient;
    

    @PostMapping
    public String Chat(@RequestBody String prompt) {
        
        return chatClient.prompt(prompt).call().content();
    }

    @PostMapping(value = "/stream", produces = "text/event-stream; charset=UTF-8")
    public Flux<String> chatStream(@RequestBody String prompt) {
        
        return chatClient.prompt(prompt).stream().content();
    }
    
    
}
