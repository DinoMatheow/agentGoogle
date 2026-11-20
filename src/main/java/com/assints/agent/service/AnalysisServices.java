package com.assints.agent.service;

import com.assints.agent.dto.analysis.RouteSummary;

public interface AnalysisServices {
    RouteSummary summarizeRoute(String route, String model);
    
}
