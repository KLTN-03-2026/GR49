package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatVeRequestDto {

    private List<ItemRequestDto> list_ben_phai;

    private String maCode;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemRequestDto{
        private Long idVe;

        private Integer giaVe;

        private String tenGhe;

        private Integer type;
    }
}
