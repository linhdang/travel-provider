package ucd.travelprovider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TravelBrokerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelBrokerService.class);

    @Value("${agent.name}")
    private String agentName;

    @Value("${provider.type}")
    private String providerType;

    @Value("${travelBroker.url}")
    private String travelBrokerUrl;

    public void register() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> checkIfAgentRegistered
                    = restTemplate.getForEntity(travelBrokerUrl + "/travel-broker/providers/" + agentName, String.class);
            if (checkIfAgentRegistered.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("Agent {} is registered", agentName);
                return; //
            }
        } catch (HttpClientErrorException.NotFound e) {

            HttpEntity<String> request = new HttpEntity<String>(
                    "{\n" +
                            "\"type\":\"" + providerType.toUpperCase() + "\",\n" +
                            "\"name\": \"" + agentName + "\"\n" +
                            "}");
            ResponseEntity<String> response
                    = restTemplate.postForEntity(travelBrokerUrl + "/travel-broker/providers", request, String.class);
        }
    }

    public void registerNewPlan(String departure, String arrival, long from, long to, long price, long capacity) throws InterruptedException {
        LOGGER.info("request plan for departure [{}], arrival [{}], from [{}], to [{}]", departure, arrival, from, to);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(providerType.toUpperCase().equals("FLIGHT") ? flightJson(departure, arrival, from, to, price, capacity) :
                accommodationJson(arrival, from, price, capacity));
        ResponseEntity<String> response
                = restTemplate.postForEntity(travelBrokerUrl + "/" + agentName +"/plans", request, String.class);

    }

    private String flightJson(String departure, String arrival, long from, long to, long price, long capacity) {
        return "{\n" +
                "\"from\": " + from + ", \n" +
                "\"to\": " + to + ", \n" +
                "\"departure\": \"" + departure + "\",\n" +
                "\"arrival\": \"" + arrival + "\",\n" +
                "\"capacity\": "+capacity+",\n" +
                "\"price\": "+price+",\n" +
                "\"name\": \"" + agentName + "_" + System.currentTimeMillis() + "\"\n" +
                "}";
    }

    private String accommodationJson(String arrival, long from,long price, long capacity) {
        return "{\n" +
                "\"from\": " + from + ", \n" +
                "\"to\": " + 0 + ", \n" +
                "\"arrival\": \"" + arrival + "\",\n" +
                "\"capacity\": "+capacity+",\n" +
                "\"accommodationType\": \"ONE_BED\"," +
                "\"price\": "+price+",\n" +
                "\"name\": \"" + agentName + "_" + System.currentTimeMillis() + "\"\n" +
                "}";
    }

    @EventListener
    public void registerAgentName(ContextRefreshedEvent event) {
        register();
    }

}
