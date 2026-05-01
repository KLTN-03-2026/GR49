package com.quoc.Movie_Ticket_Booking.service;

import java.util.Map;

public interface DashboardService {

    public Map<String, Object> getTongDoanhThu();

    public Map<String, Object> getTongPhim();

    public Map<String, Object> getTongVeDaBan();

    public Map<String, Object> getTongPhongChieu();

    public Map<String, Object> getDatVeGanDay();

    public Map<String, Object> getPhimPhoBien();

    public Map<String, Object> getThongKeLoaiVe();
}
