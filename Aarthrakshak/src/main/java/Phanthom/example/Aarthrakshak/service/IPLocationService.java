package Phanthom.example.Aarthrakshak.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class IPLocationService {

    public String getCountry(String ipAddress) {
        try {
            String url = "http://ip-api.com/json/" + ipAddress;
            RestTemplate rest = new RestTemplate();
            Map response = rest.getForObject(url, Map.class);
            if (response != null && "success".equals(response.get("status"))) {
                return response.get("country").toString();
            }
        } catch (Exception e) {
            System.out.println("IP lookup failed: " + e.getMessage());
        }
        return "Unknown";
    }
}