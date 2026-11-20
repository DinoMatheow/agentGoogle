package com.assints.agent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assints.agent.dto.ChatRequest;
import com.assints.agent.dto.analysis.RouteSummary;
import com.assints.agent.service.AnalysisServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/analysis")
@RequiredArgsConstructor 
public class AnalysisController {
    private final AnalysisServices analysisServices;

    @PostMapping("/route")
    public  ResponseEntity<RouteSummary> analyzeRoute(@Valid @RequestBody ChatRequest request ){
        return ResponseEntity.ok(analysisServices.summarizeRoute(request.prompt(), request.model()));
    }    
}
