package se.ai.aireviewgenerator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CatFactService {

    private static final String CAT_FACT_URL = "https://catfact.ninja/fact";

    private final RestTemplate restTemplate;


    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCatFact() {
        try {
            Map<String, Object> response =
                    restTemplate.getForObject(CAT_FACT_URL, Map.class);

            if (response == null || !response.containsKey("fact")) {
                return "No cat fact available.";
            }

            return response.get("fact").toString();

        } catch (Exception e) {
            return "Could not fetch cat fact.";
        }
    }
}