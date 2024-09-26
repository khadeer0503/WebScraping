package com.webScraping.controller;

import com.webScraping.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ScrapingController {
    @Autowired
    private ScraperService scraperService;

    @GetMapping("/scrape")
    public List<String> scrape(@RequestParam String url) throws IOException {
        return scraperService.scrapeChargingLocations(url);
    }
}
