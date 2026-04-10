package com.quoc.Movie_Ticket_Booking.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GhePhanTrangResponseDto {
    private List<GheResponseDto> ghes;

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalElements;

    private Integer totalPages;

    private Boolean isFirst;

    private Boolean isLast;
}
