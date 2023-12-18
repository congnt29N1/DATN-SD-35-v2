package com.example.datn.controller.madinhdanh;

import com.example.datn.entity.MaDinhDanhCTSP;
import com.example.datn.service.ChiTietSanPhamService;
import com.example.datn.service.MaDinhDanhService;
import com.example.datn.utils.TrangThaiMaDinhDanh;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/ma-dinh-danh")
public class MaDinhDanhController {

    private final String UPLOAD_DIR = "./uploads/";
    private final Integer ITEM_PER_PAGE = 20;
    @Autowired
    MaDinhDanhService maDinhDanhService;
    @Autowired
    ChiTietSanPhamService ctspService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("")
    public String init(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        List<MaDinhDanhCTSP> listMaDinhDanh = maDinhDanhService.getALl();
        model.addAttribute("listMaDinhDanh", listMaDinhDanh);
        return "admin/madinhdanh/madinhdanh";
    }
//
//    @GetMapping("/page/{pageNum}")
//    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
//                             @Param("keyword") String keyword) {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("admin") == null) {
//            return "redirect:/login-admin";
//        }
//        Page<MaDinhDanhCTSP> page = maDinhDanhService.searchMDD(pageNum, ITEM_PER_PAGE, keyword);
////        List<MaDinhDanhCTSP> listMaDinhDanh = page.getContent();
//        List<MaDinhDanhCTSP> listMaDinhDanh = maDinhDanhService.getALl();
//        long startCount = pageNum * ITEM_PER_PAGE + 1;
//        long endCount = startCount + ITEM_PER_PAGE - 1;
//        if (endCount > page.getTotalElements()) {
//            endCount = page.getTotalElements();
//        }
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("startCount", startCount);
//        model.addAttribute("endCount", endCount);
//        model.addAttribute("totalItem", page.getTotalElements());
//        model.addAttribute("listMaDinhDanh", listMaDinhDanh);
//        model.addAttribute("keyword", keyword);
//        System.out.println(listMaDinhDanh.size());
//        return "admin/madinhdanh/madinhdanh";
//    }

    @GetMapping("/new")
    public String newMaDinhDanh(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        model.addAttribute("maDinhDanhCTSP", new MaDinhDanhCTSP());
        model.addAttribute("pageTitle", "Tạo Mới Seri");
        return "admin/madinhdanh/madinhdanh_form";
    }

    @GetMapping("/edit/{id}")
    public String editMaDinhDanh(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            model.addAttribute("edit", true);
            MaDinhDanhCTSP maDinhDanhCTSP = maDinhDanhService.get(id);
            if (maDinhDanhCTSP == null) {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy mã định danh của chi tiết sản phẩm");
                return "redirect:/admin/ma-dinh-danh";
            }
            model.addAttribute("maDinhDanhCTSP", maDinhDanhCTSP);
            model.addAttribute("pageTitle", "Update mã định danh chi tiết sản phẩm (ID : " + id + ")");
            return "admin/madinhdanh/madinhdanh_form";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @PostMapping("/save")
    public String upTrangThaiMaDinhDanh(MaDinhDanhCTSP maDinhDanhCTSP, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        MaDinhDanhCTSP updateMaDinhDanhCTSP = maDinhDanhService.get(maDinhDanhCTSP.getId());
        updateMaDinhDanhCTSP.setTrangThai(maDinhDanhCTSP.getTrangThai());
        updateMaDinhDanhCTSP.setMaDinhDanh(maDinhDanhCTSP.getMaDinhDanh());
        maDinhDanhService.save(updateMaDinhDanhCTSP);
        redirectAttributes.addFlashAttribute("message", "cập nhập trạng thái thành công ");
        return "redirect:/admin/ma-dinh-danh";
    }

    @PostMapping("/importExcept")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             MaDinhDanhCTSP maDinhDanhCTSP
            , RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        if (maDinhDanhCTSP.getChiTietSanPham() == null || ctspService.getChiTietSanPhamByMa(maDinhDanhCTSP.getChiTietSanPham().getMaChiTietSanPham()) == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn đúng chi tiết sản phẩm cần định danh");
            return "redirect:/admin/ma-dinh-danh";
        }
        if (file.isEmpty() && maDinhDanhCTSP.getMaDinhDanh().isBlank()) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn 1 trong 2 cách thức nhập mã định danh");
            return "redirect:/admin/ma-dinh-danh";
        } else if (!file.isEmpty()) {
            try {
                List<MaDinhDanhCTSP> listSave = new ArrayList<>();
                // Lưu file tạm thời
                File tempFile = File.createTempFile("temp", ".xlsx");
                file.transferTo(tempFile);

                // Đọc nội dung của file Excel
                Workbook workbook = new XSSFWorkbook(tempFile);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        MaDinhDanhCTSP result = MaDinhDanhCTSP.builder()
                                .chiTietSanPham(ctspService.getChiTietSanPhamByMa(maDinhDanhCTSP.getChiTietSanPham().getMaChiTietSanPham()))
                                .ngayNhap(new Timestamp(System.currentTimeMillis()))
                                .ngayBan(null)
                                .trangThai(TrangThaiMaDinhDanh.CHUA_BAN)
                                .build();
                        if (cell.getCellType() == CellType.STRING) {
                            String value = cell.getStringCellValue();
                            result.setMaDinhDanh(value);
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date dateValue = cell.getDateCellValue();
                                result.setMaDinhDanh(dateValue.toString());
                            } else {
                                double numericValue = cell.getNumericCellValue();
                                result.setMaDinhDanh(String.valueOf(numericValue));
                            }
                        }
                        if (result.getMaDinhDanh() != null) {
                            listSave.add(result);
                        }
                    }
                }
                maDinhDanhService.saveMany(listSave);
                workbook.close();
                redirectAttributes.addFlashAttribute("message", "Thêm list Mã định danh cho chi tiết sản phẩm thành công");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        } else {
            MaDinhDanhCTSP result = MaDinhDanhCTSP.builder()
                    .maDinhDanh(maDinhDanhCTSP.getMaDinhDanh())
                    .chiTietSanPham(ctspService.getChiTietSanPhamByMa(maDinhDanhCTSP.getChiTietSanPham().getMaChiTietSanPham()))
                    .ngayNhap(new Timestamp(System.currentTimeMillis()))
                    .ngayBan(null)
                    .trangThai(TrangThaiMaDinhDanh.CHUA_BAN)
                    .build();
            maDinhDanhService.save(result);
            redirectAttributes.addFlashAttribute("message", "Thêm Mã định danh cho chi tiết sản phẩm thành công");
        }

        return "redirect:/admin/ma-dinh-danh";
    }
}
