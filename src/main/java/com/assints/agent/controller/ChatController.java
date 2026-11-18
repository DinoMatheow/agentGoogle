package com.assints.agent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assints.agent.dto.ChatRequest;
import com.assints.agent.service.AssistantService;

import jakarta.validation.Valid;
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
    public ResponseEntity<String> Chat(@RequestBody ChatRequest request) {
        
        return ResponseEntity.ok(assistantService.chat(request.prompt(), request.model()));
    }

    @PostMapping(value = "/stream", produces = "text/event-stream; charset=UTF-8")
    public Flux<String> chatStream(@RequestBody ChatRequest request ) {
        
        return assistantService.chatStream(request.prompt(), request.model());
    }

    @PostMapping("/explain")
    private ResponseEntity<String> explainCondition(@Valid @RequestBody ChatRequest request){

        return  ResponseEntity.ok(assistantService.explainCondition(request.prompt(), request.model()));        
    }
    

   
    
}
