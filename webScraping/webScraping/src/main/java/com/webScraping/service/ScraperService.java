package com.webScraping.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {
    public List<String> scrapeChargingLocations(String url) throws IOException {
        List<String> chargingStations = new ArrayList<>();
        int page = 0;
        boolean hasMore = true;

        while (hasMore) {
            // Connect to the URL with pagination
            Document doc = Jsoup.connect(url + "?page=" + page).get();

            // Select all station items using the 'chargepoints-stations-item' class
            for (Element station : doc.select(".chargepoints-stations-item")) {
                // Extract station title (e.g., OP-02978671)
                String stationTitle = station.select(".chargepoints-stations-title h3").text();

                // Extract address (e.g., 717 Via Salaria 00138)
                String address = station.select(".chargepoints-stations-text .body-m").text();

                // Extract connector type and number (e.g., TYPE 2, x4 connectors)
                String connectorType = station.select(".charging-point-type").attr("title");
                String connectorCount = station.select(".char-type-1-num .body-s").text();

                // Format the extracted data
                chargingStations.add("Station: " + stationTitle + ", Address: " + address + ", Connector: " + connectorType + " (" + connectorCount + ")");
            }

            // Check if there is a "Load More" button or if new stations were found
            hasMore = doc.select(".load-more-button").size() > 0; // Adjust the selector based on actual HTML structure
            page++; // Increment page number
        }
        return chargingStations;
    }
}
