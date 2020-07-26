package ucd.travelprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ucd.travelprovider.service.TravelBrokerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TravelProviderController {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TravelBrokerService travelBrokerService;

    @Autowired
    public TravelProviderController(TravelBrokerService travelBrokerService) {
        this.travelBrokerService = travelBrokerService;
    }

    @RequestMapping("/create-plan")
    public void createPlan(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival,
                                                 @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("price") Long price, @RequestParam("capacity") Long capacity) throws InterruptedException, ParseException {
        travelBrokerService.registerNewPlan(departure, arrival, dateFormat.parse(from).getTime(),
                dateFormat.parse(to).getTime(), price, capacity);
    }

}
