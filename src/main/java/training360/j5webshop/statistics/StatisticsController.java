package training360.j5webshop.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Statistics getStatistics(){
        return statisticsService.getStatistics();
    }
}
