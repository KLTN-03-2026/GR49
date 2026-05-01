package com.quoc.Movie_Ticket_Booking.controller.client;

import com.quoc.Movie_Ticket_Booking.service.ChatService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;

    }

    @PostMapping("/stream")
    public Object chatWithStream(@RequestParam String message,@RequestHeader(value = "Authorization", required = false) String jwt) {

        return  chatService.chatWithStream(message,jwt);
    }
}
