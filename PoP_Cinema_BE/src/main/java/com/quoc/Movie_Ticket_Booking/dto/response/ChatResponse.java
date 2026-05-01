package com.quoc.Movie_Ticket_Booking.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private String type;      // text | ui
    private String message;   // lời nhắc
    private String component;
    private List<Map<String, Object>> matrix;
    private List<Map<String, Object>> options;   // dữ liệu UI
    private Boolean requireLogin;
}
