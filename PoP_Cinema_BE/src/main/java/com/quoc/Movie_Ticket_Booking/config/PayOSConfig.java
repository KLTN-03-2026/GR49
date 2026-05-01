package com.quoc.Movie_Ticket_Booking.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Value("${PAYOS_CLIENT_ID}")
    private String clientId;

    @Value("${PAYOS_API_KEY}")
    private String apiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String checksumKey;

    @Bean
    public PayOS payOS() {
//        System.out.println("clientId=" + clientId);
//        System.out.println("apiKey=" + apiKey);
//        System.out.println("checksumKey=" + checksumKey);

        return new PayOS(clientId, apiKey, checksumKey);
    }
}
