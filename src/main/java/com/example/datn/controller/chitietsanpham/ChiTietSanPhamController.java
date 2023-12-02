package com.example.datn.controller.chitietsanpham;

import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.entity.ChatLieu;
import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.HoaTiet;
import com.example.datn.entity.KhuyenMai;
import com.example.datn.entity.KichCo;
import com.example.datn.entity.KieuDet;
import com.example.datn.entity.KieuTui;
import com.example.datn.entity.LopLot;
import com.example.datn.entity.MauSac;
import com.example.datn.entity.SanPham;
import com.example.datn.entity.VeAo;
import com.example.datn.entity.XeTa;
import com.example.datn.exception.ChiTietSanPhamNotFountException;
import com.example.datn.service.CauTrucKhuyService;
import com.example.datn.service.ChatLieuService;
import com.example.datn.service.ChiTietSanPhamService;
import com.example.datn.service.HoaTietService;
import com.example.datn.service.KhuyenMaiService;
import com.example.datn.service.KichCoService;
import com.example.datn.service.KieuDetService;
import com.example.datn.service.KieuTuiService;
import com.example.datn.service.LopLotService;
import com.example.datn.service.MauSacService;
import com.example.datn.service.SanPhamService;
import com.example.datn.service.VeAoService;
import com.example.datn.service.XeTaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ChiTietSanPhamController {
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private KhuyenMaiService khuyenMaiService;
    @Autowired
    private KichCoService kichCoService;
    @Autowired
    private CauTrucKhuyService cauTrucKhuyService;
    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    private HoaTietService hoaTietService;
    @Autowired
    private KieuDetService kieuDetService;
    @Autowired
    private KieuTuiService kieuTuiService;
    @Autowired
    private VeAoService veAoService;
    @Autowired
    private LopLotService lopLotService;
    @Autowired
    private XeTaService xeTaService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/productDetails")
    public String listFirstPage(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        return listByPage(1, model, "maChiTietSanPham", "asc", null, null);
    }

    @GetMapping("/admin/productDetails/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword,
                              @Param("productName") String productName) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }

        Page<ChiTietSanPham> page = chiTietSanPhamService.listByPageAndProductName(pageNum, sortField, sortDir, keyword, productName);
        List<ChiTietSanPham> listChiTietSanPham = page.getContent();

        long startCount = (pageNum - 1) * ChiTietSanPhamService.PRODUCT_DETAIL_PER_PAGE + 1;
        long endCount = startCount + ChiTietSanPhamService.PRODUCT_DETAIL_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listChiTietSanPham", listChiTietSanPham);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("productName", productName);

        return "admin/chitietsanpham/product_detail";
    }

    @GetMapping("/admin/productDetails/new")
    public String newProduct(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        List<SanPham> listSanPham = sanPhamService.listAll();
        List<KhuyenMai> listKhuyenMai = khuyenMaiService.listAll();
        List<MauSac> listMauSac = mauSacService.getAllMauSac();
        List<KichCo> listKichCo = kichCoService.getAllKichCo();
        List<CauTrucKhuy> cauTrucKhuyList = cauTrucKhuyService.getAllCauTrucKhuy();
        List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
        List<HoaTiet> hoaTietList = hoaTietService.getAllHoaTiet();
        List<KieuDet> kieuDetList = kieuDetService.getAllKieuDet();
        List<KieuTui> kieuTuiList = kieuTuiService.getAllKieuTui();
        List<VeAo> veAoList = veAoService.getAllVeAo();
        List<LopLot> lopLotList = lopLotService.getAllLopLot();
        List<XeTa> xeTaList = xeTaService.getAllXeTa();
        ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
        chiTietSanPham.setTrangThai(1);
        model.addAttribute("chiTietSanPham", chiTietSanPham);
        model.addAttribute("listKhuyenMai", listKhuyenMai);
        model.addAttribute("listSanPham", listSanPham);
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("listKichCo", listKichCo);
        model.addAttribute("cauTrucKhuyList", cauTrucKhuyList);
        model.addAttribute("chatLieuList", chatLieuList);
        model.addAttribute("hoaTietList", hoaTietList);
        model.addAttribute("kieuDetList", kieuDetList);
        model.addAttribute("kieuTuiList", kieuTuiList);
        model.addAttribute("veAoList", veAoList);
        model.addAttribute("lopLotList", lopLotList);
        model.addAttribute("xeTaList", xeTaList);
        return "admin/chitietsanpham/product_detail_create";
    }

    @PostMapping("/admin/productDetails/save")
    public String saveProductDetails(ChiTietSanPham chiTietSanPham, RedirectAttributes ra) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        List<ChiTietSanPham> list = chiTietSanPhamService.findByIdSp(chiTietSanPham.getSanPham().getIdSanPham());
        Integer count = 0;
        for (ChiTietSanPham ctsp : list
        ) {
            if (ctsp.getMauSac() == chiTietSanPham.getMauSac() &&
                    ctsp.getKhuyenMai() == chiTietSanPham.getKhuyenMai() &&
                    ctsp.getCauTrucKhuy() == chiTietSanPham.getCauTrucKhuy() &&
                    ctsp.getChatLieu() == chiTietSanPham.getChatLieu() &&
                    ctsp.getKichCo() == chiTietSanPham.getKichCo() &&
                    ctsp.getHoaTiet() == chiTietSanPham.getHoaTiet() &&
                    ctsp.getKieuTui() == chiTietSanPham.getKieuTui() &&
                    ctsp.getKieuDet() == chiTietSanPham.getKieuDet() &&
                    ctsp.getVeAo() == chiTietSanPham.getVeAo() &&
                    ctsp.getLopLot() == chiTietSanPham.getLopLot() &&
                    ctsp.getXeTa() == chiTietSanPham.getXeTa()
            ) {
                if (ctsp.getIdChiTietSanPham() == chiTietSanPham.getIdChiTietSanPham()) {
                    count = 0;
                } else {
                    count++;
                }
            }

        }

        if (count > 0) {
            ra.addFlashAttribute("thongbaoTrung", "Sản phẩm đã tồn tại");
            return "redirect:/admin/productDetails/edit/" + chiTietSanPham.getIdChiTietSanPham();
        }
        chiTietSanPhamService.save(chiTietSanPham);
        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
        return "redirect:/admin/productDetails";
    }

    @GetMapping("/admin/productDetails/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model,
                              RedirectAttributes ra) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.get(id);
            List<SanPham> listSanPham = sanPhamService.listAll();
            List<KhuyenMai> listKhuyenMai = khuyenMaiService.listAll();
            List<MauSac> listMauSac = mauSacService.getAllMauSac();
            List<KichCo> listKichCo = kichCoService.getAllKichCo();
            List<CauTrucKhuy> cauTrucKhuyList = cauTrucKhuyService.getAllCauTrucKhuy();
            List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
            List<HoaTiet> hoaTietList = hoaTietService.getAllHoaTiet();
            List<KieuDet> kieuDetList = kieuDetService.getAllKieuDet();
            List<KieuTui> kieuTuiList = kieuTuiService.getAllKieuTui();
            List<VeAo> veAoList = veAoService.getAllVeAo();
            List<LopLot> lopLotList = lopLotService.getAllLopLot();
            List<XeTa> xeTaList = xeTaService.getAllXeTa();
            model.addAttribute("chiTietSanPham", chiTietSanPham);
            model.addAttribute("listKhuyenMai", listKhuyenMai);
            model.addAttribute("listSanPham", listSanPham);
            model.addAttribute("listMauSac", listMauSac);
            model.addAttribute("listKichCo", listKichCo);
            model.addAttribute("cauTrucKhuyList", cauTrucKhuyList);
            model.addAttribute("chatLieuList", chatLieuList);
            model.addAttribute("hoaTietList", hoaTietList);
            model.addAttribute("kieuDetList", kieuDetList);
            model.addAttribute("kieuTuiList", kieuTuiList);
            model.addAttribute("veAoList", veAoList);
            model.addAttribute("lopLotList", lopLotList);
            model.addAttribute("xeTaList", xeTaList);

            return "admin/chitietsanpham/product_detail_edit";
        } catch (ChiTietSanPhamNotFountException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/productDetails";
        }
    }

    @PostMapping("/admin/productDetails/update")
    public String updateProductDetails(ChiTietSanPham chiTietSanPham, RedirectAttributes ra) throws IOException, ChiTietSanPhamNotFountException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        List<ChiTietSanPham> list = chiTietSanPhamService.findByIdSp(chiTietSanPham.getSanPham().getIdSanPham());
        Integer count = 0;
        for (ChiTietSanPham ctsp : list
        ) {
            if (ctsp.getMauSac() == chiTietSanPham.getMauSac() &&
                    ctsp.getKhuyenMai() == chiTietSanPham.getKhuyenMai() &&
                    ctsp.getCauTrucKhuy() == chiTietSanPham.getCauTrucKhuy() &&
                    ctsp.getChatLieu() == chiTietSanPham.getChatLieu() &&
                    ctsp.getKichCo() == chiTietSanPham.getKichCo() &&
                    ctsp.getHoaTiet() == chiTietSanPham.getHoaTiet() &&
                    ctsp.getKieuTui() == chiTietSanPham.getKieuTui() &&
                    ctsp.getKieuDet() == chiTietSanPham.getKieuDet() &&
                    ctsp.getVeAo() == chiTietSanPham.getVeAo() &&
                    ctsp.getLopLot() == chiTietSanPham.getLopLot() &&
                    ctsp.getXeTa() == chiTietSanPham.getXeTa()
            ) {
                if (ctsp.getIdChiTietSanPham() == chiTietSanPham.getIdChiTietSanPham()) {
                    count = 0;
                } else {
                    count++;
                }
            }

        }

        if (count > 0) {
            ra.addFlashAttribute("thongbaoTrung", "Sản phẩm đã tồn tại");
            return "redirect:/admin/productDetails/edit/" + chiTietSanPham.getIdChiTietSanPham();
        }
        chiTietSanPhamService.save(chiTietSanPham);
        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
        return "redirect:/admin/productDetails";
    }

}
