package com.assints.agent.dto;

import jakarta.validation.constraints.NotBlank;

public record RouteRequest(
    @NotBlank(message = "Origin is required")
    String origin,

    @NotBlank(message = "Destination is required")
    String destination,

    String model
) {

   

}
