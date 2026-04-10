package com.quoc.Movie_Ticket_Booking.controller.admin;

import com.quoc.Movie_Ticket_Booking.dto.request.ChangStatusDto;
import com.quoc.Movie_Ticket_Booking.dto.request.GheRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhimRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.request.PhongChieuRequestDto;
import com.quoc.Movie_Ticket_Booking.dto.response.ApiResponse;
import com.quoc.Movie_Ticket_Booking.dto.response.GhePhanTrangResponseDto;
import com.quoc.Movie_Ticket_Booking.dto.response.GheResponseDto;
import com.quoc.Movie_Ticket_Booking.model.Ghe;
import com.quoc.Movie_Ticket_Booking.model.NhanVien;
import com.quoc.Movie_Ticket_Booking.model.Phim;
import com.quoc.Movie_Ticket_Booking.model.PhongChieu;
import com.quoc.Movie_Ticket_Booking.service.GheService;
import com.quoc.Movie_Ticket_Booking.service.NhanVienService;
import com.quoc.Movie_Ticket_Booking.service.PhongChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ghe")
public class GheController {

    @Autowired
    private GheService ghesService;

    @Autowired
    private NhanVienService nhanVienService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createGhe(@RequestBody GheRequestDto req, @RequestHeader("Authorization") String jwt) {
        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveGhe = ghesService.createGhe(req,nhanVienId);
        return new ResponseEntity<>(saveGhe, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateGhe(@RequestBody GheRequestDto req,@RequestHeader("Authorization") String jwt) throws Exception {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> saveGhe = ghesService.updateGhe(req.getId(), req,nhanVienId);
        return new ResponseEntity<>(saveGhe, HttpStatus.OK);
    }


    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse<?>> updateStatusGhe(@RequestBody ChangStatusDto req,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> updateStatus = ghesService.updateStatus(req.getId(),nhanVienId);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteGhe(@PathVariable Long id,@RequestHeader("Authorization") String jwt)  {

        NhanVien nhanVienId = nhanVienService.findUserByJwtToken(jwt);
        ApiResponse<?> deleteGhe = ghesService.deleteGhe(id, nhanVienId);
        return new ResponseEntity<>(deleteGhe, HttpStatus.OK);
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getAllGhe()  {

        List<GheResponseDto> allGhe = ghesService.getAllGhe();
        ApiResponse<?> success = ApiResponse.success("Hiển thị danh sách phòng chiếu thành công!", allGhe);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/get-all-ghe")
    public ResponseEntity<?> getAllGhePhanTrang(  @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "16") Integer pageSize)  {

        GhePhanTrangResponseDto allGhePhanTrang = ghesService.getAllGhePhanTrang(pageNo, pageSize);
        return new ResponseEntity<>(allGhePhanTrang, HttpStatus.OK);
    }

    @GetMapping("/get-ghe-by-phong")
    public ResponseEntity<?> getAllGheByPhongChieu( @RequestParam Long phongChieuId,
                                                     @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "16") Integer pageSize)  {

        GhePhanTrangResponseDto allGhePhanTrang = ghesService.getGheByPhongChieuId(phongChieuId, pageNo, pageSize);
        return new ResponseEntity<>(allGhePhanTrang, HttpStatus.OK);
    }

    @GetMapping("/get-map-ghe/{phongChieuId}")
    public ResponseEntity<?> getMapGheByPhongChieu( @PathVariable Long phongChieuId)  {

        Map<String, Object> mapGheByPhongChieuId = ghesService.getMapGheByPhongChieuId(phongChieuId);
        return new ResponseEntity<>(mapGheByPhongChieuId, HttpStatus.OK);
    }
}
