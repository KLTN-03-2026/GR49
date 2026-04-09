package com.quoc.Movie_Ticket_Booking.util;

import com.quoc.Movie_Ticket_Booking.dto.request.EmailRequest;
import com.quoc.Movie_Ticket_Booking.dto.request.UserRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Users;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private DonHangRepository donHangRepository;
    String msg=null;

    private void sendEmail(EmailRequest emailRequest) throws Exception{

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(mailFrom,emailRequest.getTitle());
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getMessage(), true);
        mailSender.send(mimeMessage);

    }

    //dùng cho đăng kí
    @Async
    public void emailSend(Users users) throws Exception {
        String message="Hi,<b>[[username]]</b> "
                + "<br> Tài khoản bạn đã đăng kí thành công.<br>"
                +"<br> Nhấp vào liên kết bên dưới để xác minh và kích hoạt tài khoản của bạn <br>"
                +"<a href='[[url]]' >Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", users.getHoVaTen());
        message = message.replace("[[url]]",
                "http://localhost:5173/client/kich-hoat/"+users.getHashActive());

        EmailRequest emailRequest = EmailRequest.builder()
                .to(users.getEmail())
                .title("PoP Cinema Service")
                .subject("Kích hoạt tài khoản")
                .message(message)
                .build();

        sendEmail(emailRequest);

    }


//dùng cho quên mật khẩu
    @Async
    public void emailSendToResetPass(Users users) throws Exception {
        String message=
                "<br> Để đặt lại mật khẩu cho tài khoản [[email]]của bạn. Vui lòng nhấn vào liên kết dưới đây:<br>"
                        +"<br> Nhấp vào liên kết bên dưới để xác minh và kích hoạt tài khoản của bạn <br>"
                        +"<a href='[[url]]' >Đặt lại mật khẩu</a> <br><br>"
                        +"Thanks,<br>Enotes.com";

        message = message.replace("[[email]]", users.getEmail());
        message = message.replace("[[url]]",
                "http://localhost:5173/client/dat-lai-mat-khau/"+users.getHashReset());

        EmailRequest emailRequest = EmailRequest.builder()
                .to(users.getEmail())
                .title("PoP Cinema Service")
                .subject("PoP Lấy lại mật khẩu")
                .message(message)
                .build();

        sendEmail(emailRequest);

    }




}
