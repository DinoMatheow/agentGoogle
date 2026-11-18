package com.assints.agent.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
    @NotBlank(message = "The prompt cannot be empty.")
    String prompt, 
    String model) {
    



}
