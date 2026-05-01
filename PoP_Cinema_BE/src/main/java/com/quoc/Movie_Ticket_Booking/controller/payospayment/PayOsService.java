package com.quoc.Movie_Ticket_Booking.controller.payospayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.quoc.Movie_Ticket_Booking.dto.response.DonHangDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.VeDetailsResponseDto;
import com.quoc.Movie_Ticket_Booking.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class PayOsService {
    @Autowired
    private PayOS payOS;

    @Autowired
    private DonHangRepository donHangRepository;

    public ObjectNode createPayOsPayment(DonHangDetailsResponseDto responseDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            // Lấy danh sách vé
            List<VeDetailsResponseDto> dsVe =
                    donHangRepository.getVeByMaDonHangForUser(responseDto.getMaDonHang());

            int quantity = dsVe.size();
            String productName = responseDto.getTenPhim();
            if (productName == null || productName.isBlank()) {
                productName = "Dat ve xem phim";
            }
            String description = responseDto.getMaDonHang();
            int price = responseDto.getTienThucNhan();

            // orderCode phải UNIQUE
            long orderCode = System.currentTimeMillis();

            // redirect về FE
            String returnUrl = "http://localhost:8080/api/payment/success"
                    + "?maDonHang=" + URLEncoder.encode(description, StandardCharsets.UTF_8)
                    + "&maGiaoDich=" + orderCode;
            String cancelUrl = "http://localhost:8080/api/payment/cancel" + "?maDonHang=" + URLEncoder.encode(description, StandardCharsets.UTF_8);

            PaymentLinkItem item =
                    PaymentLinkItem.builder().name(productName).quantity(quantity).price((long) price).build();

            CreatePaymentLinkRequest paymentData =
                    CreatePaymentLinkRequest.builder()
                            .orderCode(orderCode)
                            .description(description)
                            .amount((long) price)
                            .item(item)
                            .returnUrl(returnUrl)
                            .cancelUrl(cancelUrl)
                            .build();

            CreatePaymentLinkResponse data = payOS.paymentRequests().create(paymentData);
            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }
}
