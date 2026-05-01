package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThayDoiMatKhauRequestDto {

    @NotBlank(message = "Vui lòng nhập mật khẩu cũ")
    private String matKhauCu;

    @NotBlank(message = "Vui lòng nhập mật khẩu mới")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm chữ hoa, số và ký tự đặc biệt (@, $, !, %, *, ?, &)"
    )
    private String matkhauMoi;

    @NotBlank(message = "Vui lòng xác nhận mật khẩu")
    private String xacNhanMatKhau;
}
