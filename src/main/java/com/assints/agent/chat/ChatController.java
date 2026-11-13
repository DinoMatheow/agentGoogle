package com.assints.agent.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    
}
