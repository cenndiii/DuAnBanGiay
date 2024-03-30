package view;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.DanhMucSp;
import model.GioHang;
import model.Hang;
import model.HoaDonCho;
import model.KhachHang;
import model.MauSac;
import model.NhanVien;
import model.SanPham;
import model.Size;
import model.ThuocTinh;
import model.XuatXu;
import service.ChatLieu_Service;
import service.DanhMuc_Service;
import service.GioHang_Service;
import service.HangService;
import service.HoaDonCho_Service;
import service.KhachHangService;
import service.MauSacService;
import service.SanPham_Service;
import service.SizeService;
import service.ThuocTinh_Service;
import service.XuatXuService;

public class TrangChu extends javax.swing.JFrame {

    /**
     * Creates new form TrangChu
     */
    public TrangChu() throws SQLException {
        initComponents();
        setTitle("Ứng Dụng");
        setLocationRelativeTo(null);
        fillTableQuanLySp(SPDao.getAll());
        fillTableBhSanPham(SPDao.getAll());
        fillHang();
        fillMauSac();
        fillSize();
        fillXuatXu();
        fillChatLieu();
        fillDanhMuc();
        fillKhachHang(listKhachHang);
        fillTableHoaDonCho(hdcService.getAll());
        fillGiohang();
        fillTableHoaDonChiTiet();
    }

    MauSacService mauSacDao = new MauSacService();
    SizeService sizeDao = new SizeService();
    XuatXuService xuatXuDao = new XuatXuService();
    HangService hangService = new HangService();
    ChatLieu_Service chatLieuSevice = new ChatLieu_Service();
    DanhMuc_Service danhMucService = new DanhMuc_Service();
    ThuocTinh_Service tt = new ThuocTinh_Service();
    KhachHangService khachHangService = new KhachHangService();
    GioHang_Service gioHang_Service = new GioHang_Service();
    private final SanPham_Service SPDao = new SanPham_Service();
    HoaDonCho_Service hdcService = new HoaDonCho_Service();

    private List<KhachHang> listKhachHang = khachHangService.getAllKhachHang();
    private ArrayList<GioHang> listGioHang;
    private final List<SanPham> listSanPham = SPDao.getAll();

    private DefaultTableModel model_tblSanPham = new DefaultTableModel();
    private DefaultTableModel model_tblBhSanPham = new DefaultTableModel();
    private DefaultTableModel modelKhachHang = new DefaultTableModel();
    private DefaultTableModel dtm;
    private DefaultTableModel model_GioHang;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String bang, loai, hienThiLoai, loaiTimKiem, tenKh;
    int idHoadon = -1, index = -1, id = -1, indexBhSanPham = -1, row = -1, soLuong, idNvHDCho, idGhSanPham = -1, indexGioHang, soLuongGioHang = -1;
    public String a1, a2, a3, a4, a5, a6;
    double tongTien = 0;

    public void login(NhanVien nv) {
        lblRole.setText(nv.getVaiTro() != 0 ? "Quản Lý" : " Nhân Viên");
        idNvHDCho = nv.getID();
        if (nv.getVaiTro() == 0) {
            btnThuocTinh.setEnabled(false);
            btnNhanVien.setEnabled(false);
        }
    }

    public HoaDonCho saveBill2() {
        String ma = randomMaHoaDon();
        return new HoaDonCho(ma, idNvHDCho);
    }

    public String randomMaHoaDon() {
        Set<String> generatedCodes = new HashSet<>();

        Random random = new Random();
        String code;

        do {
            String s1 = "H";
            String s2 = "D";
            String so = "1234567890";

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 1; i++) {
                int randomIndex = random.nextInt(s1.length());
                sb.append(s1.charAt(randomIndex));
            }

            for (int i = 0; i < 1; i++) {
                int randomIndex = random.nextInt(s2.length());
                sb.append(s2.charAt(randomIndex));
            }

            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(so.length());
                sb.append(so.charAt(randomIndex));
            }

            code = sb.toString();
        } while (generatedCodes.contains(code));

        generatedCodes.add(code);

        return code;
    }

    private void hienThiBangThuocTinh(List<ThuocTinh> list) {
        dtm = (DefaultTableModel) tblThuocTinh.getModel();
        dtm.setRowCount(0);

        for (ThuocTinh Tt : list) {
            hienThiLoai = switch (Tt.getLoai()) {
                case "Kich Co" ->
                    "Kích cỡ";
                case "Noi San Xuat" ->
                    "Nơi Sản Xuất";
                case "Chat Lieu" ->
                    "Chất Liệu";
                case "Thuong Hieu" ->
                    "Thương Hiệu";
                case "Danh Muc Sp" ->
                    "Danh Mục Sản Phẩm";
                default ->
                    "Màu Sắc";
            };
            dtm.addRow(new Object[]{Tt.getId(), hienThiLoai, Tt.getChiTiet()});
        }
    }

    private void CheckLoai() {
        if (cbxThuocTinh.getSelectedItem().equals("Kích cỡ")) {
            bang = "KichCo";
            loai = "Kich Co";
        } else if (cbxThuocTinh.getSelectedItem().equals("Màu Sắc")) {
            bang = "MauSac";
            loai = "Mau Sac";
        } else if (cbxThuocTinh.getSelectedItem().equals("Thương Hiệu")) {
            bang = "ThuongHieu";
            loai = "Thuong Hieu";
        } else if (cbxThuocTinh.getSelectedItem().equals("Danh Mục Sản Phẩm")) {
            bang = "DanhMucSp";
            loai = "Danh Muc Sp";
        } else if (cbxThuocTinh.getSelectedItem().equals("Chất Liệu")) {
            bang = "ChatLieu";
            loai = "Chat Lieu";
        } else {
            bang = "NSX";
            loai = "Noi San Xuat";
        }
    }

    public void fillTableQuanLySp(List<SanPham> list) {
        model_tblSanPham.setRowCount(0);
        model_tblSanPham = (DefaultTableModel) tblQLSanPham.getModel();
        for (SanPham x : list) {
            model_tblSanPham.addRow(x.toDataRow());
        }
    }

    public void fillTableBhSanPham(List<SanPham> list) {
        model_tblBhSanPham.setRowCount(0);
        model_tblBhSanPham = (DefaultTableModel) tblBhSanPham.getModel();
        for (SanPham x : list) {
            model_tblBhSanPham.addRow(x.toDataBhSanPhamRow());
        }
    }

    public void fillMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMauSac.getModel();
        model.removeAllElements();
        List<MauSac> list = mauSacDao.getAll();
        for (MauSac x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public void fillSize() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSize.getModel();
        model.removeAllElements();
        List<Size> list = sizeDao.getAll();
        for (Size x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public void fillHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHang.getModel();
        model.removeAllElements();
        List<Hang> list = hangService.getAll();
        for (Hang x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public void fillXuatXu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboXuatSu.getModel();
        model.removeAllElements();
        List<XuatXu> list = xuatXuDao.getAll();
        for (XuatXu x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public void fillChatLieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxChatLieu.getModel();
        model.removeAllElements();
        List<ChatLieu> list = chatLieuSevice.getAll();
        for (ChatLieu x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public void fillDanhMuc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxDanhMuc.getModel();
        model.removeAllElements();
        List<DanhMucSp> list = danhMucService.getAll();
        for (DanhMucSp x : list) {
            model.addElement(x.getChiTiet());
        }
    }

    public SanPham readFrom() {
        String ten = txtTenSP.getText();
        double giaNhap = Double.parseDouble(txtGiaNhap.getText());
        double giaBan = Double.parseDouble(txtGiaBan.getText());
        int soluong = Integer.parseInt(txtSoLuong.getText());
        String moTa = txtMoTa.getText();

        index = cboHang.getSelectedIndex();
        List<Hang> list = hangService.getAll();
        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                a1 = String.valueOf(list.get(i).getId());
            }
        }

        index = cboXuatSu.getSelectedIndex();
        List<XuatXu> list2 = xuatXuDao.getAll();
        for (int i = 0; i < list2.size(); i++) {
            if (i == index) {
                a2 = String.valueOf(list2.get(i).getId());
            }
        }

        index = cboMauSac.getSelectedIndex();
        List<MauSac> list3 = mauSacDao.getAll();
        for (int i = 0; i < list3.size(); i++) {
            if (i == index) {
                a3 = String.valueOf(list3.get(i).getId());
            }
        }

        index = cboSize.getSelectedIndex();
        List<Size> list4 = sizeDao.getAll();
        for (int i = 0; i < list4.size(); i++) {
            if (i == index) {
                a4 = String.valueOf(list4.get(i).getId());
            }
        }

        index = cbxChatLieu.getSelectedIndex();
        List<ChatLieu> list5 = chatLieuSevice.getAll();
        for (int i = 0; i < list5.size(); i++) {
            if (i == index) {
                a5 = String.valueOf(list5.get(i).getId());
            }
        }

        index = cbxDanhMuc.getSelectedIndex();
        List<DanhMucSp> list6 = danhMucService.getAll();
        for (int i = 0; i < list6.size(); i++) {
            if (i == index) {
                a6 = String.valueOf(list6.get(i).getId());
            }
        }
        return new SanPham(id, ten, giaNhap, giaBan, soluong, a2, a1, a3, a4, a5, a6, moTa);
    }

    private void timKiemTheoLoai() {
        if (cbxTimTheoThuocTinh.getSelectedItem().equals("Tất Cả")) {
            hienThiBangThuocTinh(tt.getFullData());
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals(("Theo Màu Sắc"))) {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals(("Theo Kích cỡ"))) {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals(("Theo Thương Hiệu"))) {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals(("Theo Chất Liệu"))) {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals(("Theo Danh Mục Sản Phẩm"))) {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        } else {
            hienThiBangThuocTinh(tt.getDataFromEachTable(CheckBang()));
        }
    }

    private String CheckBang() {
        if (cbxTimTheoThuocTinh.getSelectedItem().equals("Theo Kích cỡ")) {
            return "KichCo";
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals("Theo Màu Sắc")) {
            return "MauSac";
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals("Theo Thương Hiệu")) {
            return "ThuongHieu";
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals("Theo Danh Mục Sản Phẩm")) {
            return "DanhMucSp";
        } else if (cbxTimTheoThuocTinh.getSelectedItem().equals("Theo Chất Liệu")) {
            return "ChatLieu";
        } else {
            return "NSX";
        }
    }

    public void clearSp() {
        txtTenSP.setText("");
        txtSoLuong.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtMoTa.setText("");
    }

    public void clearKh() {
        txtHo.setText("");
        txtTenDem.setText("");
        txtTen.setText("");
        txtmailKh.setText("");
        txtSDTKhachHang.setText("");
        txtNgaySinh.setDate(null);
    }

    public void clearBhKH(){
        txtIdGuest.setText("");
        txtNameGuest.setText("");
        txtGuestPhoneNumber.setText("");
    }
    private void fillKhachHang(List<KhachHang> khachhangs) {
        modelKhachHang = (DefaultTableModel) tblKhachHang.getModel();
        modelKhachHang.setRowCount(0);
        String ten = null;
        for (KhachHang kH : khachhangs) {
            ten = kH.getTen();
            if (kH.getHo().isBlank() && !kH.getTenDem().isBlank()) {
                ten = kH.getTenDem() + " " + kH.getTen();
            } else if (kH.getTenDem().isBlank() && !kH.getHo().isBlank()) {
                ten = kH.getHo() + " " + kH.getTen();
            } else if (!kH.getTenDem().isBlank() && !kH.getHo().isBlank() && !kH.getTen().isBlank()) {
                ten = kH.getHo() + " " + kH.getTenDem() + " " + kH.getTen();
            }
            modelKhachHang.addRow(new Object[]{kH.getID(), ten, kH.getGioiTinh() == true ? "Nam" : "Nữ", kH.getNgaySinh(), kH.getMail(), kH.getSDT()});
        }
    }

    private KhachHang getFormKhachHang() {
        if (row != -1) {
            KhachHang KH = new KhachHang();
            KH.setID((int) tblKhachHang.getValueAt(row, 0));
            KH.setHo(txtHo.getText());
            KH.setTenDem(txtTenDem.getText());
            KH.setTen(txtTen.getText());
            Boolean gioiTinh = rdNam.isSelected();
            if (gioiTinh == true) {
                KH.setGioiTinh(true);
            } else {
                KH.setGioiTinh(false);
            }
            KH.setNgaySinh(sdf.format(txtNgaySinh.getDate()));
            KH.setMail(txtmailKh.getText());
            KH.setSDT(txtSDTKhachHang.getText());
            return KH;
        }
        return null;
    }

    public void detail(int viTri) throws ParseException {
        KhachHang KH = listKhachHang.get(viTri);
        if (!KH.getHo().isBlank()) {
            txtHo.setText(KH.getHo());
        }
        if (!KH.getTenDem().isBlank()) {
            txtTenDem.setText(KH.getTenDem());
        }
        if (!KH.getMail().isBlank()) {
            txtmailKh.setText(KH.getMail());
        }
        txtNgaySinh.setDate(sdf.parse(KH.getNgaySinh()));
        txtTen.setText(KH.getTen());
        txtSDTKhachHang.setText(KH.getSDT());

        Boolean gioiTinh = KH.getGioiTinh();
        if (gioiTinh == true) {
            rdNam.setSelected(true);
        } else {
            rdNu.setSelected(true);
        }
    }

    public void fillGiohang() {
        model_GioHang = (DefaultTableModel) tblCart.getModel();
        model_GioHang.setRowCount(0);
        if (listGioHang != null) {
            for (GioHang gh : listGioHang) {
                model_GioHang.addRow(new Object[]{gh.getIdSP(), gh.getTenSp(), gh.getSoLuong(), gh.getGia(), gh.getTongTien()});
            }
        }
    }

    public boolean validateGioHang() {

        try {
            soLuong = Integer.parseInt(JOptionPane.showInputDialog(this, "Chon so luong mua"));
            for (SanPham sp : listSanPham) {
                if (sp.getIdSP() == (int) tblBhSanPham.getValueAt(indexBhSanPham, 0)) {
                    if (soLuong > sp.getSoLuong() || soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Mua quá số lượng cho phép");
                        return false;
                    }
                    sp.setSoLuong(sp.getSoLuong() - soLuong);
                }
            }
            fillTableBhSanPham(listSanPham);
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Yêu cầu nhập đúng số lượng cần mua");
            return false;
        }

        return true;
    }
    DefaultTableModel model_tblBill2 = new DefaultTableModel();

    public void fillTableHoaDonCho(List<HoaDonCho> list) {
        model_tblBill2 = (DefaultTableModel) tblBill2.getModel();
        model_tblBill2.setRowCount(0);
        for (HoaDonCho x : list) {
            model_tblBill2.addRow(new Object[]{x.getStt(), x.getMaHDC(), x.getNgayTao(), x.getTenNV(), x.getTrangThai()});
        }
    }

    public void tinhTongTien() {
        if (listGioHang != null) {
            if (!listGioHang.isEmpty()) {
                for (GioHang gioHang : listGioHang) {
                    tongTien += gioHang.getTongTien();
                    txtTotal3.setText(String.valueOf(tongTien));
                }
            } else {
                txtTotal3.setText("");
            }
        }
        tongTien = 0;
    }

    DefaultTableModel model_HoaDonChiTiet;

    public void fillTableHoaDonChiTiet() {
        model_HoaDonChiTiet = (DefaultTableModel) tblBillForm.getModel();
        model_HoaDonChiTiet.setRowCount(0);
        try {
            for (HoaDonCho hDC : hdcService.getAll()) {
                model_HoaDonChiTiet.addRow(new Object[]{hDC.getStt(),hDC.getMaHDC(),hDC.getNgayTao(),hDC.getTenNV(),hDC.getTrangThai()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnBanHang = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnKhuyenMai = new javax.swing.JButton();
        btnSanPham = new javax.swing.JButton();
        btnThuocTinh = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        TrangChu_Panel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        NhanVien_Panel = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        rdoQuanLy = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        rdoNhanVien = new javax.swing.JRadioButton();
        ID = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        rdoDangLam = new javax.swing.JRadioButton();
        btnSua = new javax.swing.JButton();
        rdoNghi = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        txtHoTen = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        KhachHang_Panel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnTimKiemKhachHang = new javax.swing.JButton();
        txtTimKiemKH = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtSDTKhachHang = new javax.swing.JTextField();
        txtHo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTenDem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtmailKh = new javax.swing.JTextField();
        btnSuaKhachHang = new javax.swing.JButton();
        btnThemKhachHang = new javax.swing.JButton();
        btnXoaKhachHang = new javax.swing.JButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        KhuyenMai_Panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnCreateVoucher = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtVoucherID = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        rdoVnd = new javax.swing.JRadioButton();
        txtVoucherValue = new javax.swing.JTextField();
        rdoPercent = new javax.swing.JRadioButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        btnResetForm = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        txtCondition = new javax.swing.JTextField();
        rdoTotalBill = new javax.swing.JRadioButton();
        rdoTotalProduct = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        btnSearhVoucher = new javax.swing.JButton();
        txtIdS = new javax.swing.JTextField();
        cbxTypeVoucher = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        SanPham_Panel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtTimKiem1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblQLSanPham = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cboHang = new javax.swing.JComboBox<>();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem2 = new javax.swing.JButton();
        btnXoa2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnSua2 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        cboXuatSu = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        cbxDanhMuc = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        cbxChatLieu = new javax.swing.JComboBox<>();
        HoaDon_Panel = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblBillForm = new javax.swing.JTable();
        rdoAll = new javax.swing.JRadioButton();
        rdoCancelled = new javax.swing.JRadioButton();
        rdoPaid = new javax.swing.JRadioButton();
        txtSearchBox = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        cbxType = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblDetailedInvoice = new javax.swing.JTable();
        BanHang_Panel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        btnRemoveProductInCart = new javax.swing.JButton();
        btnUpdateProductInCart = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblBhSanPham = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblBill2 = new javax.swing.JTable();
        btnCancelledBill2 = new javax.swing.JButton();
        btnCreateBill2 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        txtBillId3 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txtStaffName3 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtTotal3 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtMoneyPay3 = new javax.swing.JTextField();
        btnPay3 = new javax.swing.JButton();
        txtDateCreate3 = new javax.swing.JTextField();
        lblReduce3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtGuestPhoneNumber = new javax.swing.JTextField();
        txtIdGuest = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNameGuest = new javax.swing.JTextField();
        ThuocTinh_Panel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cbxThuocTinh = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtChiTietThuocTinh = new javax.swing.JTextField();
        btnThemThuocTinh = new javax.swing.JButton();
        btnSuaThuocTinh = new javax.swing.JButton();
        btnXoaThuocTinh = new javax.swing.JButton();
        txtTimKiemThuocTinh = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        cbxTimTheoThuocTinh = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        pnlAuth = new javax.swing.JPanel();
        lblRole = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new java.awt.GridLayout(9, 1));

        btnTrangChu.setBackground(new java.awt.Color(153, 153, 153));
        btnTrangChu.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        jPanel2.add(btnTrangChu);

        btnBanHang.setBackground(new java.awt.Color(153, 153, 153));
        btnBanHang.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnBanHang.setText("Bán Hàng");
        btnBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangActionPerformed(evt);
            }
        });
        jPanel2.add(btnBanHang);

        btnHoaDon.setBackground(new java.awt.Color(153, 153, 153));
        btnHoaDon.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });
        jPanel2.add(btnHoaDon);

        btnNhanVien.setBackground(new java.awt.Color(153, 153, 153));
        btnNhanVien.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        jPanel2.add(btnNhanVien);

        btnKhachHang.setBackground(new java.awt.Color(153, 153, 153));
        btnKhachHang.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setText("Khách Hàng");
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });
        jPanel2.add(btnKhachHang);

        btnKhuyenMai.setBackground(new java.awt.Color(153, 153, 153));
        btnKhuyenMai.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnKhuyenMai.setText("Khuyến Mãi");
        btnKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhuyenMaiActionPerformed(evt);
            }
        });
        jPanel2.add(btnKhuyenMai);

        btnSanPham.setBackground(new java.awt.Color(153, 153, 153));
        btnSanPham.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSanPham.setText("Sản Phẩm");
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });
        jPanel2.add(btnSanPham);

        btnThuocTinh.setBackground(new java.awt.Color(153, 153, 153));
        btnThuocTinh.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btnThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnThuocTinh.setText("Thuộc Tính");
        btnThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuocTinhActionPerformed(evt);
            }
        });
        jPanel2.add(btnThuocTinh);

        jButton8.setBackground(new java.awt.Color(153, 153, 153));
        jButton8.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Thoát");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setFont(new java.awt.Font("Sylfaen", 3, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("FOURSHOES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(294, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MainPanel.setLayout(new java.awt.CardLayout());

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/trangchu1.jpg"))); // NOI18N

        javax.swing.GroupLayout TrangChu_PanelLayout = new javax.swing.GroupLayout(TrangChu_Panel);
        TrangChu_Panel.setLayout(TrangChu_PanelLayout);
        TrangChu_PanelLayout.setHorizontalGroup(
            TrangChu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        TrangChu_PanelLayout.setVerticalGroup(
            TrangChu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MainPanel.add(TrangChu_Panel, "card2");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel7.setText("Tình trạng");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Họ Tên", "Email", "Tình Trạng ", "Vai Trò"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        jLabel10.setText("Mật khẩu");

        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel11.setText("Email");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        rdoQuanLy.setSelected(true);
        rdoQuanLy.setText("Quản Lý");

        jLabel12.setText("Họ và tên");

        jLabel15.setText("Tìm kiếm");

        rdoNhanVien.setText("Nhân Viên");

        ID.setText("ID");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        rdoDangLam.setSelected(true);
        rdoDangLam.setText("Đang làm");

        btnSua.setText("Sửa ");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        rdoNghi.setText("Đã Nghỉ");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtHoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoTenActionPerformed(evt);
            }
        });

        jLabel16.setText("Vai trò");

        javax.swing.GroupLayout NhanVien_PanelLayout = new javax.swing.GroupLayout(NhanVien_Panel);
        NhanVien_Panel.setLayout(NhanVien_PanelLayout);
        NhanVien_PanelLayout.setHorizontalGroup(
            NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                        .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                    .addComponent(txtHoTen)
                                    .addComponent(txtEmail))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel7))
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimKiem)
                            .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                                        .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rdoDangLam)
                                            .addComponent(rdoQuanLy))
                                        .addGap(18, 18, 18)
                                        .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rdoNghi))))
                                .addGap(52, 52, 52)
                                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSua)
                                    .addComponent(btnThem))))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        NhanVien_PanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSua, btnThem, btnXoa});

        NhanVien_PanelLayout.setVerticalGroup(
            NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhanVien_PanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem))
                .addGap(31, 31, 31)
                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(rdoDangLam)
                    .addComponent(rdoNghi)
                    .addComponent(btnSua))
                .addGap(32, 32, 32)
                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(rdoQuanLy)
                    .addComponent(rdoNhanVien)
                    .addComponent(btnXoa))
                .addGap(53, 53, 53)
                .addGroup(NhanVien_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        MainPanel.add(NhanVien_Panel, "card3");

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên", "Giới Tính", "Ngày Sinh", "Email", "Số Điện Thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang);

        btnTimKiemKhachHang.setText("Tìm Kiếm");
        btnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKhachHangActionPerformed(evt);
            }
        });

        txtTimKiemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiemKhachHang)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiemKhachHang)
                    .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jLabel19.setText("Số Điện thoại");

        jLabel20.setText("Họ ");

        jLabel1.setText("Tên Đệm");

        jLabel3.setText("Tên");

        jLabel4.setText("Giới Tính");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        jLabel5.setText("Ngày Sinh");

        jLabel6.setText("Email");

        btnSuaKhachHang.setText("Sửa ");
        btnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhachHangActionPerformed(evt);
            }
        });

        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        btnXoaKhachHang.setText("Xóa");
        btnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdNam)
                        .addGap(21, 21, 21)
                        .addComponent(rdNu))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenDem, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txtHo)
                            .addComponent(txtTen))
                        .addGap(162, 162, 162)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmailKh, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSDTKhachHang))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnThemKhachHang)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaKhachHang)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaKhachHang))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtHo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTenDem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtmailKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtSDTKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rdNam)
                            .addComponent(rdNu))))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout KhachHang_PanelLayout = new javax.swing.GroupLayout(KhachHang_Panel);
        KhachHang_Panel.setLayout(KhachHang_PanelLayout);
        KhachHang_PanelLayout.setHorizontalGroup(
            KhachHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhachHang_PanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(KhachHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        KhachHang_PanelLayout.setVerticalGroup(
            KhachHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhachHang_PanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        MainPanel.add(KhachHang_Panel, "card4");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnCreateVoucher.setText("Tạo Voucher");
        btnCreateVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateVoucherActionPerformed(evt);
            }
        });

        jLabel37.setText("Mã Voucher");

        jLabel38.setText("Tiêu đề");

        jLabel39.setText("Giảm theo");

        rdoVnd.setSelected(true);
        rdoVnd.setText("VND");

        rdoPercent.setText("%");

        jLabel40.setText("Từ ngày");

        jLabel41.setText("Đến ngày");

        btnResetForm.setText("Reset");

        jLabel42.setText("Điều kiện ");

        rdoTotalBill.setSelected(true);
        rdoTotalBill.setText("Tổng Hóa Đơn Trên ... Tiền ");

        rdoTotalProduct.setText("Tổng Sản Phẩm Đã Mua Trên ... Sản Phẩm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnCreateVoucher)
                        .addGap(18, 18, 18)
                        .addComponent(btnResetForm)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(rdoTotalBill)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoTotalProduct))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(rdoVnd)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoPercent))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtVoucherID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                        .addComponent(txtTitle, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtVoucherValue))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(30, 30, 30)
                                .addComponent(txtCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtVoucherID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtVoucherValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoVnd)
                    .addComponent(rdoPercent))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTotalBill)
                    .addComponent(rdoTotalProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addGap(16, 16, 16)
                .addComponent(jLabel41)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateVoucher)
                    .addComponent(btnResetForm))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSearhVoucher.setText("Search");

        cbxTypeVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã Voucher", "Tiêu Đề", " " }));

        jLabel22.setText("Tìm theo");

        jLabel43.setText("Từ ngày");

        jLabel23.setText("Đến ngày");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnSearhVoucher)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdS)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addComponent(cbxTypeVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(txtIdS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTypeVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addComponent(jLabel43)
                .addGap(16, 16, 16)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(btnSearhVoucher))
        );

        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Voucher", "Tiêu đề", "Giá Trị", "Điều Kiện", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblVoucher);

        javax.swing.GroupLayout KhuyenMai_PanelLayout = new javax.swing.GroupLayout(KhuyenMai_Panel);
        KhuyenMai_Panel.setLayout(KhuyenMai_PanelLayout);
        KhuyenMai_PanelLayout.setHorizontalGroup(
            KhuyenMai_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhuyenMai_PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(KhuyenMai_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        KhuyenMai_PanelLayout.setVerticalGroup(
            KhuyenMai_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhuyenMai_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(KhuyenMai_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        MainPanel.add(KhuyenMai_Panel, "card5");

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel25.setText("Tên Sản Phẩm");

        jButton1.setText("Tìm Kiếm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblQLSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sản Phẩm", "Tên SP", "Giá Nhập", "Giá Bán", "Số Lượng", "Xuất xứ", "Hãng", "Màu sắc", "Size", "Chất Liệu", "Danh Mục Sp", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblQLSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLSanPhamMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblQLSanPham);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel26.setText("Tên Sản Phẩm");

        jLabel27.setText("Thương Hiệu");

        jLabel28.setText("Xuất sứ");

        jLabel29.setText("Giá nhập");

        cboHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel32.setText("Màu Sắc");

        jLabel33.setText("Size");

        jLabel34.setText("Mô Tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane8.setViewportView(txtMoTa);

        btnThem2.setText("Thêm");
        btnThem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem2ActionPerformed(evt);
            }
        });

        btnXoa2.setText("Xóa");
        btnXoa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa2ActionPerformed(evt);
            }
        });

        jButton4.setText("Mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel36.setText("Số Lượng");

        btnSua2.setText("Sửa");
        btnSua2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua2ActionPerformed(evt);
            }
        });

        jLabel30.setText("Giá bán");

        jLabel35.setText("Danh Mục Sản Phẩm");

        jLabel44.setText("Chất Liệu");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenSP)
                            .addComponent(cboXuatSu, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtGiaBan)
                                    .addComponent(txtGiaNhap)))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel36)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(btnThem2)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua2)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cboHang, cboMauSac, cboSize, cboXuatSu, cbxChatLieu, cbxDanhMuc, jScrollPane8, txtGiaBan, txtGiaNhap, txtSoLuong, txtTenSP});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem2)
                            .addComponent(btnXoa2)
                            .addComponent(jButton4)
                            .addComponent(btnSua2))
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboXuatSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(12, 12, 12)
                        .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel33)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout SanPham_PanelLayout = new javax.swing.GroupLayout(SanPham_Panel);
        SanPham_Panel.setLayout(SanPham_PanelLayout);
        SanPham_PanelLayout.setHorizontalGroup(
            SanPham_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SanPham_PanelLayout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(SanPham_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        SanPham_PanelLayout.setVerticalGroup(
            SanPham_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPham_PanelLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(SanPham_Panel, "card6");

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tblBillForm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Tên Nhân Viên", "Tình Trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillFormMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblBillForm);

        rdoAll.setSelected(true);
        rdoAll.setText("Tất cả");
        rdoAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoAllMouseClicked(evt);
            }
        });

        rdoCancelled.setText("Đã hủy");
        rdoCancelled.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCancelledMouseClicked(evt);
            }
        });

        rdoPaid.setText("Đã thanh toán");
        rdoPaid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoPaidMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(rdoAll)
                        .addGap(60, 60, 60)
                        .addComponent(rdoCancelled)
                        .addGap(57, 57, 57)
                        .addComponent(rdoPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoAll)
                    .addComponent(rdoCancelled)
                    .addComponent(rdoPaid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchBoxKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchBoxKeyTyped(evt);
            }
        });

        jLabel65.setText("Tìm Theo:");

        cbxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã Hóa Đơn", "Tên", "Số Điện Thoại", "Tình Trạng" }));

        jLabel66.setText("Đến:");

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel67.setText("Từ Ngày:");

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Chi Tiết", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tblDetailedInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Năm Bán", "Trọng Lượng", "Mô Tả", "SL SP", "Giá Nhập", "Giá Bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(tblDetailedInvoice);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout HoaDon_PanelLayout = new javax.swing.GroupLayout(HoaDon_Panel);
        HoaDon_Panel.setLayout(HoaDon_PanelLayout);
        HoaDon_PanelLayout.setHorizontalGroup(
            HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDon_PanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(HoaDon_PanelLayout.createSequentialGroup()
                        .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(HoaDon_PanelLayout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addGap(18, 18, 18)
                                .addComponent(cbxType, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel66)
                            .addComponent(jLabel67))
                        .addGap(130, 130, 130)
                        .addComponent(jButton2))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        HoaDon_PanelLayout.setVerticalGroup(
            HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDon_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HoaDon_PanelLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(61, 61, 61))
                    .addGroup(HoaDon_PanelLayout.createSequentialGroup()
                        .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67))
                        .addGap(8, 8, 8)
                        .addGroup(HoaDon_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65)
                            .addComponent(jLabel66))
                        .addGap(20, 20, 20)))
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        MainPanel.add(HoaDon_Panel, "card7");

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCartMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblCart);
        if (tblCart.getColumnModel().getColumnCount() > 0) {
            tblCart.getColumnModel().getColumn(0).setResizable(false);
            tblCart.getColumnModel().getColumn(1).setResizable(false);
            tblCart.getColumnModel().getColumn(2).setResizable(false);
            tblCart.getColumnModel().getColumn(3).setResizable(false);
            tblCart.getColumnModel().getColumn(4).setResizable(false);
        }

        btnRemoveProductInCart.setText("Xóa");
        btnRemoveProductInCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveProductInCartActionPerformed(evt);
            }
        });

        btnUpdateProductInCart.setText("Sửa");
        btnUpdateProductInCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProductInCartActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setText("Giỏ Hàng");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(btnRemoveProductInCart)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateProductInCart))
                    .addComponent(jLabel56))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveProductInCart)
                    .addComponent(btnUpdateProductInCart))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setText("Sản Phẩm");

        tblBhSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sản Phẩm", "Tên SP", "Giá Bán", "Số Lượng", "Xuất xứ", "Hãng", "Màu sắc", "Size", "Chất Liệu", "Danh Mục Sp", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblBhSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBhSanPhamMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblBhSanPham);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblBill2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Ngày tạo", "Tên nhân viên", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBill2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBill2MouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblBill2);

        btnCancelledBill2.setText("Hủy hóa đơn");
        btnCancelledBill2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelledBill2ActionPerformed(evt);
            }
        });

        btnCreateBill2.setText("Tạo hóa đơn");
        btnCreateBill2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateBill2ActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel58.setText("Hóa Đơn");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(btnCreateBill2)
                                .addGap(30, 30, 30)
                                .addComponent(btnCancelledBill2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelledBill2)
                    .addComponent(btnCreateBill2))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel59.setText("Mã Hd");

        txtBillId3.setEnabled(false);

        jLabel60.setText("Tên Nv");

        jLabel61.setText("Ngày Tạo");

        txtStaffName3.setEnabled(false);

        jLabel62.setText("Tổng Tiền");

        txtTotal3.setEnabled(false);

        jLabel63.setText("Tiền Khách Đưa");

        txtMoneyPay3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoneyPay3KeyReleased(evt);
            }
        });

        btnPay3.setText("Thanh Toán");
        btnPay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPay3ActionPerformed(evt);
            }
        });

        txtDateCreate3.setEnabled(false);

        lblReduce3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblReduce3.setForeground(new java.awt.Color(204, 204, 204));

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setText("Tên Khách Hàng");

        txtGuestPhoneNumber.setEnabled(false);

        txtIdGuest.setEnabled(false);

        jButton3.setText(". . .");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Sdt");

        jLabel21.setText("ID");

        txtNameGuest.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGuestPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNameGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtIdGuest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNameGuest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtGuestPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(lblReduce3))
                            .addComponent(txtTotal3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMoneyPay3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStaffName3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDateCreate3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBillId3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPay3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 9, Short.MAX_VALUE))))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBillId3, txtDateCreate3, txtStaffName3, txtTotal3});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtBillId3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDateCreate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStaffName3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(txtTotal3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(txtMoneyPay3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblReduce3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPay3)
                .addContainerGap())
        );

        javax.swing.GroupLayout BanHang_PanelLayout = new javax.swing.GroupLayout(BanHang_Panel);
        BanHang_Panel.setLayout(BanHang_PanelLayout);
        BanHang_PanelLayout.setHorizontalGroup(
            BanHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BanHang_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BanHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BanHang_PanelLayout.createSequentialGroup()
                        .addGroup(BanHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BanHang_PanelLayout.setVerticalGroup(
            BanHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BanHang_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BanHang_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(BanHang_PanelLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(BanHang_Panel, "card8");

        jLabel18.setText("Loại:");

        cbxThuocTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kích cỡ", "Màu Sắc", "Thương Hiệu", "Danh Mục Sản Phẩm", "Chất Liệu", "Nơi Sản Xuất" }));

        jLabel8.setText("Chi tiết");

        btnThemThuocTinh.setText("Thêm");
        btnThemThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhActionPerformed(evt);
            }
        });

        btnSuaThuocTinh.setText("Sửa");
        btnSuaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThuocTinhActionPerformed(evt);
            }
        });

        btnXoaThuocTinh.setText("Xóa");
        btnXoaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaThuocTinhActionPerformed(evt);
            }
        });

        txtTimKiemThuocTinh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemThuocTinhKeyReleased(evt);
            }
        });

        jLabel9.setText("Tìm Kiếm");

        cbxTimTheoThuocTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Theo Kích cỡ", "Theo Màu Sắc", "Theo Thương Hiệu", "Theo Danh Mục Sản Phẩm", "Theo Chất Liệu", "Theo Nơi Sản Xuất" }));
        cbxTimTheoThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTimTheoThuocTinhActionPerformed(evt);
            }
        });

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Loại", "Chi Tiết"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblThuocTinh);
        if (tblThuocTinh.getColumnModel().getColumnCount() > 0) {
            tblThuocTinh.getColumnModel().getColumn(0).setHeaderValue("ID");
        }

        javax.swing.GroupLayout ThuocTinh_PanelLayout = new javax.swing.GroupLayout(ThuocTinh_Panel);
        ThuocTinh_Panel.setLayout(ThuocTinh_PanelLayout);
        ThuocTinh_PanelLayout.setHorizontalGroup(
            ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                                    .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel8))
                                    .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                                            .addGap(202, 202, 202)
                                            .addComponent(btnSuaThuocTinh)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                                            .addComponent(btnXoaThuocTinh))
                                        .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cbxThuocTinh, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtChiTietThuocTinh)))))
                                .addComponent(btnThemThuocTinh)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThuocTinh_PanelLayout.createSequentialGroup()
                                    .addComponent(txtTimKiemThuocTinh)
                                    .addGap(157, 157, 157)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThuocTinh_PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbxTimTheoThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(226, Short.MAX_VALUE))
        );
        ThuocTinh_PanelLayout.setVerticalGroup(
            ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThuocTinh_PanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbxThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtChiTietThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaThuocTinh)
                    .addComponent(btnThemThuocTinh)
                    .addComponent(btnXoaThuocTinh))
                .addGap(53, 53, 53)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThuocTinh_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTimTheoThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        MainPanel.add(ThuocTinh_Panel, "card9");

        pnlAuth.setBackground(new java.awt.Color(153, 153, 153));

        lblRole.setForeground(new java.awt.Color(255, 255, 255));
        lblRole.setText("jLabel6");

        javax.swing.GroupLayout pnlAuthLayout = new javax.swing.GroupLayout(pnlAuth);
        pnlAuth.setLayout(pnlAuthLayout);
        pnlAuthLayout.setHorizontalGroup(
            pnlAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAuthLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(lblRole)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        pnlAuthLayout.setVerticalGroup(
            pnlAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAuthLayout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addComponent(lblRole)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAuth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlAuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(true);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(true);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(true);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhuyenMaiActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(true);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnKhuyenMaiActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(true);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangActionPerformed
        // TODO add your handling code here:
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(true);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnBanHangActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        TrangChu_Panel.setVisible(true);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(false);
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuocTinhActionPerformed
        TrangChu_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        KhuyenMai_Panel.setVisible(false);
        SanPham_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        ThuocTinh_Panel.setVisible(true);
    }//GEN-LAST:event_btnThuocTinhActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked

    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

    }//GEN-LAST:event_btnThemActionPerformed

    private void txtHoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoTenActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblQLSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLSanPhamMouseClicked
        index = tblQLSanPham.getSelectedRow();
        if (index > -1) {
            id = (int) tblQLSanPham.getValueAt(index, 0);
            txtTenSP.setText(tblQLSanPham.getValueAt(index, 1).toString());
            cboHang.setSelectedItem(tblQLSanPham.getValueAt(index, 6).toString());
            cboXuatSu.setSelectedItem(tblQLSanPham.getValueAt(index, 5).toString());
            txtGiaNhap.setText(tblQLSanPham.getValueAt(index, 2).toString());
            txtGiaBan.setText(tblQLSanPham.getValueAt(index, 3).toString());
            cboMauSac.setSelectedItem(tblQLSanPham.getValueAt(index, 7).toString());
            cboSize.setSelectedItem(tblQLSanPham.getValueAt(index, 8).toString());
            txtSoLuong.setText(tblQLSanPham.getValueAt(index, 4).toString());
            txtMoTa.setText(tblQLSanPham.getValueAt(index, 11).toString());
        }
    }//GEN-LAST:event_tblQLSanPhamMouseClicked

    private void btnThem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem2ActionPerformed
        try {
            SanPham sp = readFrom();
            if (SPDao.insert(sp) != null) {
                fillTableQuanLySp(SPDao.getAll());
                fillTableBhSanPham(SPDao.getAll());
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                clearSp();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThem2ActionPerformed

    private void btnXoa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa2ActionPerformed
        try {
            SanPham sp = readFrom();
            if (SPDao.delete(sp.getIdSP()) != null) {
                fillTableQuanLySp(SPDao.getAll());
                fillTableBhSanPham(SPDao.getAll());
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                clearSp();

            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnXoa2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearSp();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSua2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua2ActionPerformed
        try {
            SanPham sp = readFrom();
            if (SPDao.update(sp) != null) {
                fillTableQuanLySp(SPDao.getAll());
                fillTableBhSanPham(SPDao.getAll());
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                clearSp();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSua2ActionPerformed

    private void tblBillFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillFormMouseClicked

    }//GEN-LAST:event_tblBillFormMouseClicked

    private void rdoAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoAllMouseClicked

    }//GEN-LAST:event_rdoAllMouseClicked

    private void rdoCancelledMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCancelledMouseClicked

    }//GEN-LAST:event_rdoCancelledMouseClicked

    private void rdoPaidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoPaidMouseClicked

    }//GEN-LAST:event_rdoPaidMouseClicked

    private void txtSearchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBoxKeyReleased

    }//GEN-LAST:event_txtSearchBoxKeyReleased

    private void txtSearchBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBoxKeyTyped

    }//GEN-LAST:event_txtSearchBoxKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCreateVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateVoucherActionPerformed

    }//GEN-LAST:event_btnCreateVoucherActionPerformed

    private void btnThemThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocTinhActionPerformed
        CheckLoai();
        tt.themThuocTinh(bang, loai, txtChiTietThuocTinh.getText());
        timKiemTheoLoai();
        fillHang();
        fillMauSac();
        fillSize();
        fillXuatXu();
        fillChatLieu();
        fillDanhMuc();
    }//GEN-LAST:event_btnThemThuocTinhActionPerformed

    private void btnSuaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThuocTinhActionPerformed
//        CheckLoai();
        tt.suaThuocTinh(bang, txtChiTietThuocTinh.getText(), tt.getFullData().get(index).getId());
        timKiemTheoLoai();
        fillHang();
        fillMauSac();
        fillSize();
        fillXuatXu();
        fillChatLieu();
        fillDanhMuc();
    }//GEN-LAST:event_btnSuaThuocTinhActionPerformed

    private void btnXoaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaThuocTinhActionPerformed
        CheckLoai();
        tt.xoaThuocTinh(bang, tt.getFullData().get(index).getId());
        timKiemTheoLoai();
        fillHang();
        fillMauSac();
        fillSize();
        fillXuatXu();
        fillChatLieu();
        fillDanhMuc();
    }//GEN-LAST:event_btnXoaThuocTinhActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        hienThiBangThuocTinh(tt.getFullData());
    }//GEN-LAST:event_formWindowOpened

    private void txtTimKiemThuocTinhKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemThuocTinhKeyReleased
        if (txtTimKiemThuocTinh.getText().equalsIgnoreCase("") && cbxTimTheoThuocTinh.getSelectedItem().equals("Tất Cả")) {
            hienThiBangThuocTinh(tt.getFullData());
        } else {
            loaiTimKiem = tt.checkLoaiTimKiem(String.valueOf(cbxTimTheoThuocTinh.getSelectedItem()));
            hienThiBangThuocTinh(tt.timTheoThuocTinh(loaiTimKiem, txtTimKiemThuocTinh.getText()));
        }

    }//GEN-LAST:event_txtTimKiemThuocTinhKeyReleased

    private void cbxTimTheoThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTimTheoThuocTinhActionPerformed

        timKiemTheoLoai();

    }//GEN-LAST:event_cbxTimTheoThuocTinhActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        index = tblThuocTinh.getSelectedRow();
        txtChiTietThuocTinh.setText(tt.getFullData().get(index).getChiTiet());
        cbxThuocTinh.setSelectedItem(tblThuocTinh.getValueAt(index, 1));
        CheckLoai();
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        row = tblKhachHang.getSelectedRow();
        try {
            detail(row);

        } catch (ParseException ex) {
            Logger.getLogger(TrangChu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKhachHangActionPerformed
//        timKiemKH();
    }//GEN-LAST:event_btnTimKiemKhachHangActionPerformed

    private void txtTimKiemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKHActionPerformed

    private void btnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhachHangActionPerformed

        khachHangService.updateKhachHang(getFormKhachHang());
        listKhachHang = khachHangService.getAllKhachHang();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        fillKhachHang(listKhachHang);
        clearKh();
    }//GEN-LAST:event_btnSuaKhachHangActionPerformed

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        Boolean ThemKH = khachHangService.addKhachHang(getFormKhachHang());
        listKhachHang = khachHangService.getAllKhachHang();
        fillKhachHang(listKhachHang);
        if (ThemKH) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
        clearKh();
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhachHangActionPerformed
        int Vitri = tblKhachHang.getSelectedRow();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        boolean Xoa = khachHangService.delete(listKhachHang.get(Vitri).getID());
        listKhachHang = khachHangService.getAllKhachHang();
        fillKhachHang(listKhachHang);
        if (Xoa) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }

        clearKh();
    }//GEN-LAST:event_btnXoaKhachHangActionPerformed

    private void btnPay3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPay3ActionPerformed
        try {
            double tong = Double.parseDouble(txtTotal3.getText());
            double tienKhachDua = Double.parseDouble(txtMoneyPay3.getText());
            double tienThua = tienKhachDua - tong;
            String idKh = txtIdGuest.getText();
            
            if (tienThua == 0 || tienThua > 0) {         
                hdcService.Pay(Double.parseDouble(txtTotal3.getText()), (int) tblBill2.getValueAt(idHoadon, 0),idKh);
                clearBhKH();
                fillTableHoaDonCho(hdcService.getAll());
                clearBill();
                listGioHang.clear();
                fillGiohang();
                for (SanPham sanPham : listSanPham) {
                    hdcService.updateQuantityProduct(sanPham.getSoLuong(), sanPham.getIdSP());
                }
                fillTableBhSanPham(listSanPham);
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Khách chưa mua gì");
        }
    }//GEN-LAST:event_btnPay3ActionPerformed

    public void clearBill() {
        txtStaffName3.setText("");
        txtBillId3.setText("");
        txtDateCreate3.setText("");
        txtTotal3.setText("");
        txtMoneyPay3.setText("");
    }

    private void txtMoneyPay3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoneyPay3KeyReleased

    }//GEN-LAST:event_txtMoneyPay3KeyReleased

    private void btnCreateBill2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateBill2ActionPerformed
        try {
            HoaDonCho hd = saveBill2();
            if (hdcService.insert(hd) != null) {
                clearCart();
                clearBill();
                fillTableHoaDonCho(hdcService.getAll());
                idHoadon = -1;
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn chờ thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn chờ thất bại!");
            }
        } catch (HeadlessException | SQLException e) {
        }
    }//GEN-LAST:event_btnCreateBill2ActionPerformed

    private void btnCancelledBill2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelledBill2ActionPerformed
        try {
            hdcService.CancelBill((int) tblBill2.getValueAt(idHoadon, 0));
            clearCart();
            clearBill();
            idHoadon = -1;
            fillTableHoaDonCho(hdcService.getAll());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCancelledBill2ActionPerformed

    private void tblBill2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBill2MouseClicked
        idHoadon = tblBill2.getSelectedRow();
        clearCart();
        txtTotal3.setText("");
        String maHd = (String) tblBill2.getValueAt(idHoadon, 1);
        String ngayTao = (String) tblBill2.getValueAt(idHoadon, 2);
        String tenNv = (String) tblBill2.getValueAt(idHoadon, 3);
        txtBillId3.setText(maHd);
        txtDateCreate3.setText(ngayTao);
        txtStaffName3.setText(tenNv);
    }//GEN-LAST:event_tblBill2MouseClicked

    public void clearCart() {
        if (listGioHang != null) {
            for (GioHang gioHang : listGioHang) {
                for (SanPham sanPham : listSanPham) {
                    if (gioHang.getIdSP() == sanPham.getIdSP()) {
                        sanPham.setSoLuong(sanPham.getSoLuong() + gioHang.getSoLuong());
                    }
                }
            }
            listGioHang.clear();
        }
        fillGiohang();
        fillTableBhSanPham(listSanPham);
    }

    private void tblBhSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBhSanPhamMouseClicked
        indexBhSanPham = tblBhSanPham.getSelectedRow();
        if (idHoadon != -1) {
            if (validateGioHang()) {
                int idBhSp = (int) tblBhSanPham.getValueAt(indexBhSanPham, 0);
                String TEN = tblBhSanPham.getValueAt(indexBhSanPham, 1).toString();
                double gia = (double) tblBhSanPham.getValueAt(indexBhSanPham, 2);
                GioHang gh = new GioHang(idBhSp, soLuong, TEN, gia, 0);
                listGioHang = gioHang_Service.getProduct(gh);
                fillGiohang();
            }
            tinhTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn");
            tblBhSanPham.clearSelection();
        }
    }//GEN-LAST:event_tblBhSanPhamMouseClicked

    private void btnUpdateProductInCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProductInCartActionPerformed

        try {
            int idGhSp = (int) tblCart.getValueAt(indexGioHang, 0);
            soLuongGioHang = Integer.parseInt(JOptionPane.showInputDialog(this, "Chọn số lượng cần mua"));
            boolean check = false;
            int soLuongTon = 0;
            List<SanPham> listCheckSoLuong = SPDao.getAll();
            for (SanPham sanPham : listCheckSoLuong) {
                if (idGhSp == sanPham.getIdSP()) {
                    soLuongTon = sanPham.getSoLuong();
                }
            }
            if (soLuongGioHang > 0) {
                for (SanPham sanPham : listSanPham) {
                    if (sanPham.getIdSP() == idGhSp) {
                        for (GioHang gioHang : listGioHang) {
                            if (gioHang.getIdSP() == idGhSp) {
                                if (soLuongGioHang <= soLuongTon) {
                                    if (sanPham.getSoLuong() >= 0) {
                                        int sl = gioHang.getSoLuong() - soLuongGioHang;
                                        gioHang.setSoLuong(gioHang.getSoLuong() - sl);
                                        gioHang.setTongTien(gioHang.getSoLuong() * gioHang.getGia());
                                        sanPham.setSoLuong(sanPham.getSoLuong() + sl);
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Hết hàng");
                                    }
                                    check = true;
                                }
                            }
                        }
                    }
                }
                fillTableBhSanPham(listSanPham);
                tinhTongTien();
                fillGiohang();
                if (!check) {
                    JOptionPane.showMessageDialog(this, "Nhập quá số lượng tồn");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Số lượng sửa không được nhỏ hơn 1");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnUpdateProductInCartActionPerformed

    private void btnRemoveProductInCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductInCartActionPerformed
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getIdSP() == (int) tblCart.getValueAt(indexGioHang, 0)) {
                sanPham.setSoLuong(sanPham.getSoLuong() + (int) tblCart.getValueAt(indexGioHang, 2));
            }
        }
        fillTableBhSanPham(listSanPham);
        listGioHang.remove(indexGioHang);
        tinhTongTien();
        fillGiohang();
    }//GEN-LAST:event_btnRemoveProductInCartActionPerformed

    private void tblCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCartMouseClicked
        indexGioHang = tblCart.getSelectedRow();
    }//GEN-LAST:event_tblCartMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ChonKhachHang_view ckh = new ChonKhachHang_view(this.txtNameGuest, this.txtGuestPhoneNumber,this.txtIdGuest);
        ckh.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TrangChu().setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(TrangChu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BanHang_Panel;
    private javax.swing.JPanel HoaDon_Panel;
    private javax.swing.JLabel ID;
    private javax.swing.JPanel KhachHang_Panel;
    private javax.swing.JPanel KhuyenMai_Panel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel NhanVien_Panel;
    private javax.swing.JPanel SanPham_Panel;
    private javax.swing.JPanel ThuocTinh_Panel;
    private javax.swing.JPanel TrangChu_Panel;
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnCancelledBill2;
    private javax.swing.JButton btnCreateBill2;
    private javax.swing.JButton btnCreateVoucher;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnKhuyenMai;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPay3;
    private javax.swing.JButton btnRemoveProductInCart;
    private javax.swing.JButton btnResetForm;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnSearhVoucher;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua2;
    private javax.swing.JButton btnSuaKhachHang;
    private javax.swing.JButton btnSuaThuocTinh;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem2;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnThemThuocTinh;
    private javax.swing.JButton btnThuocTinh;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimKiemKhachHang;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnUpdateProductInCart;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa2;
    private javax.swing.JButton btnXoaKhachHang;
    private javax.swing.JButton btnXoaThuocTinh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboXuatSu;
    private javax.swing.JComboBox<String> cbxChatLieu;
    private javax.swing.JComboBox<String> cbxDanhMuc;
    private javax.swing.JComboBox<String> cbxThuocTinh;
    private javax.swing.JComboBox<String> cbxTimTheoThuocTinh;
    private javax.swing.JComboBox<String> cbxType;
    private javax.swing.JComboBox<String> cbxTypeVoucher;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblReduce3;
    private javax.swing.JLabel lblRole;
    private javax.swing.JPanel pnlAuth;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoCancelled;
    private javax.swing.JRadioButton rdoDangLam;
    private javax.swing.JRadioButton rdoNghi;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoPaid;
    private javax.swing.JRadioButton rdoPercent;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JRadioButton rdoTotalBill;
    private javax.swing.JRadioButton rdoTotalProduct;
    private javax.swing.JRadioButton rdoVnd;
    private javax.swing.JTable tblBhSanPham;
    private javax.swing.JTable tblBill2;
    private javax.swing.JTable tblBillForm;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblDetailedInvoice;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblQLSanPham;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTable tblVoucher;
    private javax.swing.JTextField txtBillId3;
    private javax.swing.JTextField txtChiTietThuocTinh;
    private javax.swing.JTextField txtCondition;
    private javax.swing.JTextField txtDateCreate3;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtGuestPhoneNumber;
    private javax.swing.JTextField txtHo;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIdGuest;
    private javax.swing.JTextField txtIdS;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtMoneyPay3;
    private javax.swing.JTextField txtNameGuest;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDTKhachHang;
    private javax.swing.JTextField txtSearchBox;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtStaffName3;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenDem;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiem1;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemThuocTinh;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtTotal3;
    private javax.swing.JTextField txtVoucherID;
    private javax.swing.JTextField txtVoucherValue;
    private javax.swing.JTextField txtmailKh;
    // End of variables declaration//GEN-END:variables
}
