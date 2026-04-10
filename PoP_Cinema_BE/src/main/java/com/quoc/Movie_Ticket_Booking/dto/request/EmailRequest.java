package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {


    private String to;

    private String subject;

    private String title;

    private String message;
}
