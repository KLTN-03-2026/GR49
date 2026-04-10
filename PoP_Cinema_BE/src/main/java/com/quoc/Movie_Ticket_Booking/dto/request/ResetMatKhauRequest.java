package com.quoc.Movie_Ticket_Booking.dto.request;

import lombok.Data;

@Data
public class ResetMatKhauRequest {

  private String matKhauMoi;

  private String xacNhanMatKhauMoi;

    private String hashReset; // ma dat lai mat khau
}
