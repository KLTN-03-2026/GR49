package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatVeChatFlowRequestDto {
    private List<Long> listVeIds;
}
