package com.quoc.Movie_Ticket_Booking.service;

import com.quoc.Movie_Ticket_Booking.dto.request.DatVeChatFlowRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.DatVeRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ChatResponse;

public interface ChatFlowService {
    ChatResponse hienThiPhim();
    ChatResponse hienThiSuatChieu(Long idPhim);
    ChatResponse hienThiDanhSachGhe(Long idSuatChieu, String jwt);
    ChatResponse datVe(DatVeChatFlowRequestDto dto, Long idUser);
    public ChatResponse xemSoDoGhe(Long idSuatChieu);
}
