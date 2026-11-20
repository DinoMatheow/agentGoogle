package com.assints.agent.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.assints.agent.dto.analysis.RouteSummary;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j  
public class AnalysisServiceImple implements AnalysisServices {

    private  final ChatClient geminiClient;
    private  final ChatClient openAiClient;

    public AnalysisServiceImple(
    @Qualifier("geminiClient") ChatClient geminiClient,
    @Qualifier("openAiClient") ChatClient openAiClient
        ){
            this.geminiClient = geminiClient;
            this.openAiClient = openAiClient;
        }

    
@Override
public RouteSummary summarizeRoute(String route, String model) {

    var converter = new BeanOutputConverter<>(RouteSummary.class);

    String format = converter.getFormat();

    String prompt = """
            Eres un asistente especializado en transporte
            terrestre interprovincial en Perú.

            Analiza la siguiente consulta:
            %s

            Identifica únicamente:
            - Ciudad de origen.
            - Ciudad de destino.

            No inventes información.
            Si falta un dato, utiliza null.

            Devuelve la respuesta siguiendo este formato:
            %s
            """.formatted(route, format);

    log.info("Analizando ruta con modelo: {}", model);

    String jsonResponse = resolveClient(model)
            .prompt(prompt)
            .call()
            .content();

    log.info("Respuesta del modelo: {}", jsonResponse);

    return converter.convert(jsonResponse);
}



    
    private ChatClient resolveClient(String  model){
           return "gemini".equalsIgnoreCase(model)
            ? geminiClient
            : openAiClient;
    }
    
}
