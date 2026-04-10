package com.quoc.Movie_Ticket_Booking.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserRequestDto {
    @NotNull
    @NotBlank(message = "Tên người dùng không được để trống")
    @NotEmpty // Validation
    private String hoVaTen;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email(message = "Email không hợp lệ") // Validation
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 10,message = "Số điện thoại bắt buộc 10 số") // Validation
    private String soDienThoai;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 8, message = "Mật khẩu phải ít nhất 8 ký tự")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Mật khẩu phải có ít nhất 1 chữ hoa, 1 số và 1 ký tự đặc biệt "
    )
    private String password;

    private String cccd;

    private LocalDate ngaySinh;


    private String hashReset; // ma dat lai mat khau


    private String hashActive; //ma kich hoat  sau khi dang ki


    private Integer isActive; //'0: chưa kích hoạt, 1: đã kích hoạt'


    private Integer isBlock; //'0: chưa bị khóa, 1: đã bị khóa'
}
