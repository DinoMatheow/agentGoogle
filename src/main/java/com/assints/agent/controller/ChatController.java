package com.assints.agent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assints.agent.dto.ChatRequest;
import com.assints.agent.dto.RouteRequest;
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

    @PostMapping("/consult-route")
    private ResponseEntity<String> consultRoute(@Valid @RequestBody RouteRequest request){

        return  ResponseEntity.ok(assistantService.consultRoute(request.origin(), request.destination(), request.model()));        
    }

     @PostMapping("/explainIncident")
    private ResponseEntity<String> explainIncident(@Valid @RequestBody ChatRequest request){

        return  ResponseEntity.ok(assistantService.explainIncident(request.prompt(), request.model()));        
    }

    @PostMapping("/incidentReport")
    private ResponseEntity<String> analyzeIncident(@Valid @RequestBody ChatRequest request){

        return  ResponseEntity.ok(assistantService.generateIncidentReport(request.prompt(), request.model()));        
    }

     @PostMapping("/consultation")
    private ResponseEntity<String> consult(@Valid @RequestBody ChatRequest request){

        return  ResponseEntity.ok(assistantService.consultation(request.prompt(), request.model()));        
    }
    

   
    
}
