package com.quoc.Movie_Ticket_Booking.vnpaypayment;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponse {
    public String code;
    public String message;
    public String paymentUrl;
}
