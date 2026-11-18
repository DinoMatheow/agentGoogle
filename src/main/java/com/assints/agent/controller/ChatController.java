package com.assints.agent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assints.agent.service.AssistantService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping ("/api/chat")
@RequiredArgsConstructor 
public class ChatController {


    private final AssistantService assistantService;
    

    @PostMapping
    public ResponseEntity<String> Chat(@RequestBody String prompt, @RequestParam(defaultValue = "gemini") String model ) {
        
        return ResponseEntity.ok(assistantService.chat(prompt, model));
    }

    @PostMapping(value = "/stream", produces = "text/event-stream; charset=UTF-8")
    public Flux<String> chatStream(@RequestBody String prompt, @RequestParam(defaultValue = "gemini")String model ) {
        
        return assistantService.chatStream(prompt, model);
    }
    

   
    
}
