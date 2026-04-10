package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VePhanTrangResponseDto {
    private List<VeResponseDto> ves;

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalElements;

    private Integer totalPages;

    private Boolean isFirst;

    private Boolean isLast;
}
