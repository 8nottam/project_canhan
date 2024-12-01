package com.it4_k12.btl.Controller;

import static java.sql.DriverManager.getConnection;

import android.os.StrictMode;
import android.util.Log;

import com.it4_k12.btl.Model.ChiTietDonHang;
import com.it4_k12.btl.Model.ChiTietHoaDon;
import com.it4_k12.btl.Model.DanhGiaSanPham;
import com.it4_k12.btl.Model.GioHang;
import com.it4_k12.btl.Model.HoaDon;
import com.it4_k12.btl.Model.SanPham;
import com.it4_k12.btl.Model.User;
import com.it4_k12.btl.Model.DonHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    private Connection conn;
    private String database = "nura";
    private String user = "tamkmhd12";
    private String password = "tamkmhd12";
    private String host = "192.168.43.28";

    public DAO(Connection conn) {
        this.conn = conn;
    }
    public DAO() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = getConnection("jdbc:mysql://" + host + ":3306/" + database + "?user=" + user + "&password=" + password);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Login
    public User login(String email, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND matKhau = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("maUser"),
                            rs.getString("hoTen"),
                            rs.getString("email"),
                            rs.getString("matKhau"),
                            rs.getString("soDienThoai"),
                            rs.getString("gioiTinh"),
                            rs.getString("ngaySinh"),
                            rs.getString("diaChi"),
                            rs.getString("anhDaiDien"),
                            rs.getString("vaiTro"),
                            rs.getString("ngayTao")
                    );
                }
            }
        } catch (SQLException e) {
            Log.e("DAO", "Login failed", e);
        }
        return user;
    }
    // ĐĂNG KÝ
    public boolean themTaiKhoan(User user) {
        String query = "INSERT INTO users (hoTen, email, matKhau, soDienThoai, gioiTinh, ngaySinh, diaChi, anhDaiDien, vaiTro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getHoTen());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getMatKhau());
            stmt.setString(4, user.getSoDienThoai());
            stmt.setString(5, user.getGioiTinh());
            stmt.setString(6, user.getNgaySinh());
            stmt.setString(7, user.getDiaChi());
            stmt.setString(8, user.getAnhDaiDien());
            stmt.setString(9, user.getVaiTro());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to update user details
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET hoTen = ?, email = ?, soDienThoai = ?, gioiTinh = ?, ngaySinh = ?, diaChi = ?, anhDaiDien = ? WHERE maUser = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getHoTen());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getSoDienThoai());
            pstmt.setString(4, user.getGioiTinh());
            pstmt.setString(5, user.getNgaySinh());
            pstmt.setString(6, user.getDiaChi());
            pstmt.setString(7, user.getAnhDaiDien());
            pstmt.setInt(8, user.getMaUser());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Return false if there was an error
        }
    }


    // Thêm phương thức tìm kiếm người dùng theo tên
    public List<User> searchUserByName(String name) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE hoTen LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setMaUser(rs.getInt("maUser"));
                user.setHoTen(rs.getString("hoTen"));
                user.setEmail(rs.getString("email"));
                user.setMatKhau(rs.getString("matKhau"));
                user.setSoDienThoai(rs.getString("soDienThoai"));
                user.setGioiTinh(rs.getString("gioiTinh"));
                user.setNgaySinh(rs.getString("ngaySinh"));
                user.setDiaChi(rs.getString("diaChi"));
                user.setAnhDaiDien(rs.getString("anhDaiDien"));
                user.setVaiTro(rs.getString("vaiTro"));
                user.setNgayTao(rs.getString("ngayTao"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Thêm phương thức xóa người dùng
    public boolean deleteUser(int maUser) {
        String sql = "DELETE FROM users WHERE maUser = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maUser);
            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }


    // Thêm phương thức cập nhật mật khẩu
    public boolean updatePassword(int maUser, String newPassword) {
        String sql = "UPDATE users SET matKhau = ? WHERE maUser = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setInt(2, maUser);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<SanPham> getAllSanPham() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String query = "SELECT * FROM san_pham";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SanPham sanPham = new SanPham(
                        rs.getInt("ma_san_pham"),
                        rs.getString("ten_san_pham"),
                        rs.getString("loai"),
                        rs.getString("anh_san_pham"),
                        rs.getDouble("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("ngay_tao")
                );
                sanPhamList.add(sanPham);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sanPhamList;
    }

    public List<SanPham> searchSanPhamByName(String name) {
        List<SanPham> sanPhams = new ArrayList<>();
        String query = "SELECT * FROM san_pham WHERE ten_san_pham LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham(
                            rs.getInt("ma_san_pham"),
                            rs.getString("ten_san_pham"),
                            rs.getString("loai"),
                            rs.getString("anh_san_pham"),
                            rs.getDouble("gia"),
                            rs.getInt("so_luong"),
                            rs.getString("mo_ta"),
                            rs.getString("ngay_tao")
                    );
                    sanPhams.add(sp);
                }
            }
        } catch (SQLException e) {
            Log.e("DAO", "Error searching products by name", e);
        }
        return sanPhams;
    }

    // Phương thức lấy danh sách sản phẩm theo loại
    public List<SanPham> layDanhSachSanPhamTheoLoai(String loai) {
        List<SanPham> danhSachSanPhamTheoLoai = new ArrayList<>();
        String query;

        if (loai.equalsIgnoreCase("Tất cả")) {
            // Nếu loại là "Tất cả", lấy tất cả sản phẩm
            query = "SELECT * FROM san_pham";
        } else {
            // Nếu không, lấy sản phẩm theo loại cụ thể
            query = "SELECT * FROM san_pham WHERE loai = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Chỉ set parameter nếu không phải "Tất cả"
            if (!loai.equalsIgnoreCase("Tất cả")) {
                stmt.setString(1, loai);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SanPham sanPham = new SanPham(
                            rs.getInt("ma_san_pham"),
                            rs.getString("ten_san_pham"),
                            rs.getString("loai"),
                            rs.getString("anh_san_pham"),
                            rs.getDouble("gia"),
                            rs.getInt("so_luong"),
                            rs.getString("mo_ta"),  // Chắc chắn thêm mo_ta vào đây
                            rs.getString("ngay_tao")
                    );
                    danhSachSanPhamTheoLoai.add(sanPham);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachSanPhamTheoLoai;
    }


    // Thêm sản phẩm mới
    public boolean addSanPham(SanPham sanPham) {
        boolean isAdded = false;
        try {
            // Câu truy vấn SQL để thêm sản phẩm
            String query = "INSERT INTO san_pham (ten_san_pham, loai, anh_san_pham, gia, so_luong, mo_ta) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sanPham.getTenSanPham());
            stmt.setString(2, sanPham.getLoai());
            stmt.setString(3, sanPham.getAnhSanPham());
            stmt.setDouble(4, sanPham.getGia());
            stmt.setInt(5, sanPham.getSoLuong());
            stmt.setString(6, sanPham.getMoTa());

            // Thực thi câu truy vấn
            int rowsAffected = stmt.executeUpdate();

            // Kiểm tra nếu sản phẩm được thêm thành công
            if (rowsAffected > 0) {
                isAdded = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi SQL
        }
        return isAdded;
    }



    // Cập nhật sản phẩm
    public boolean updateSanPham(SanPham sanPham) {
        boolean isUpdated = false;
        try {
            // Câu truy vấn SQL để cập nhật sản phẩm
            String query = "UPDATE san_pham SET ten_san_pham = ?, loai = ?, anh_san_pham = ?, gia = ?, so_luong = ?, mo_ta = ? WHERE ma_san_pham = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sanPham.getTenSanPham());
            stmt.setString(2, sanPham.getLoai());
            stmt.setString(3, sanPham.getAnhSanPham());
            stmt.setDouble(4, sanPham.getGia());
            stmt.setInt(5, sanPham.getSoLuong());
            stmt.setString(6, sanPham.getMoTa());
            stmt.setInt(7, sanPham.getMaSanPham()); // Cập nhật theo mã sản phẩm

            // Thực thi câu truy vấn
            int rowsAffected = stmt.executeUpdate();

            // Kiểm tra nếu sản phẩm được cập nhật thành công
            if (rowsAffected > 0) {
                isUpdated = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi SQL
        }
        return isUpdated;
    }


    public boolean deleteSanPham(int maSanPham) {
        boolean isDeleted = false;
        try {
            // Câu truy vấn SQL để xóa sản phẩm
            String query = "DELETE FROM san_pham WHERE ma_san_pham = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, maSanPham);
            int rowsAffected = stmt.executeUpdate();

            // Kiểm tra nếu có bản ghi bị xóa
            if (rowsAffected > 0) {
                isDeleted = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi nếu có vấn đề
        }
        return isDeleted;
    }











    // Lấy thông tin sản phẩm theo ID
    public SanPham getSanPhamById(int id) {
        SanPham sanPham = null;
        String query = "SELECT * FROM san_pham WHERE ma_san_pham = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sanPham = new SanPham();
                sanPham.setMaSanPham(rs.getInt("ma_san_pham"));
                sanPham.setTenSanPham(rs.getString("ten_san_pham"));
                sanPham.setGia(rs.getFloat("gia"));
                sanPham.setMoTa(rs.getString("mo_ta"));
                sanPham.setAnhSanPham(rs.getString("anh_san_pham"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPham;
    }

    public User getUserById(int maUser) {
        User user = null;
        String query = "SELECT * FROM users WHERE maUser = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setHoTen(rs.getString("hoTen"));
                user.setAnhDaiDien(rs.getString("anhDaiDien"));
                user.setEmail(rs.getString("email"));
                user.setDiaChi(rs.getString("diaChi"));
                user.setGioiTinh(rs.getString("gioiTinh"));
                user.setNgaySinh(rs.getString("ngaySinh"));
                user.setSoDienThoai(rs.getString("soDienThoai"));
                user.setNgayTao(rs.getString("ngayTao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    // Lấy danh sách đánh giá của sản phẩm theo ID
    public List<DanhGiaSanPham> getDanhGiaBySanPhamId(int maSanPham) {
        List<DanhGiaSanPham> danhGiaList = new ArrayList<>();
        String query = "SELECT * FROM danh_gia_san_pham WHERE ma_san_pham = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DanhGiaSanPham danhGia = new DanhGiaSanPham();
                danhGia.setMaDanhGia(rs.getInt("ma_danh_gia"));
                danhGia.setMaUser(rs.getInt("ma_user"));
                danhGia.setDiem(rs.getInt("diem"));
                danhGia.setBinhLuan(rs.getString("binh_luan"));
                danhGia.setNgayDanhGia(rs.getString("ngay_danh_gia"));

                // Thêm thông tin người dùng vào đánh giá
                User user = getUserById(danhGia.getMaUser());
                danhGia.setHoTenNguoiDanhGia(user != null ? user.getHoTen() : "Người dùng không xác định");

                danhGiaList.add(danhGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhGiaList;
    }
    public boolean themDanhGia(int maUser, int maSanPham, int diem, String binhLuan) {
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        // Câu truy vấn SQL để thêm đánh giá
        String query = "INSERT INTO danh_gia_san_pham (ma_user, ma_san_pham, diem, binh_luan) VALUES (?, ?, ?, ?)";

        try {
            // Chuẩn bị câu lệnh SQL
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, maUser); // Đặt giá trị mã người dùng
            pstmt.setInt(2, maSanPham); // Đặt giá trị mã sản phẩm
            pstmt.setInt(3, diem); // Đặt giá trị điểm đánh giá (từ 1 đến 5)
            pstmt.setString(4, binhLuan); // Đặt giá trị bình luận

            // Thực hiện câu lệnh và kiểm tra kết quả
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                isSuccess = true; // Đánh dấu thành công nếu có hàng được chèn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng PreparedStatement
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }








    //  Phương thức để thêm sản phẩm vào giỏ hàng


    // Kiểm tra sản phẩm trùng lặp trong giỏ hàng
    public boolean isProductInCart(int maSanPham, int maThuocTinh, int maUser) {
        String query = "SELECT * FROM gio_hang WHERE ma_san_pham = ? AND ma_thuoc_tinh = ? AND ma_user = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maSanPham);
            ps.setInt(2, maThuocTinh);
            ps.setInt(3, maUser);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy sản phẩm trong giỏ hàng
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console để kiểm tra
        }
        return false; // Trả về false nếu có lỗi hoặc không tìm thấy sản phẩm
    }


// giỏ hàng


    // Xóa sản phẩm khỏi giỏ hàng
    public boolean deleteFromCart(int maGioHang) {
        String query = "DELETE FROM gio_hang WHERE ma_gio_hang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maGioHang);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật số lượng trong giỏ hàng
    public boolean updateCartQuantity(int maGioHang, int soLuong) {
        String query = "UPDATE gio_hang SET so_luong = ? WHERE ma_gio_hang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, soLuong);
            ps.setInt(2, maGioHang);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getTongTienCart(List<GioHang> List){
        try {
            int tongTien = 0;
            for(GioHang gh: List){
                PreparedStatement stm = conn.prepareStatement("SELECT (sp.gia * gh.so_luong) FROM `gio_hang` gh " +
                        "INNER JOIN san_pham sp ON gh.ma_san_pham = sp.ma_san_pham WHERE gh.ma_gio_hang = ?");
                stm.setInt(1, gh.getMaGioHang());
                ResultSet rs = stm.executeQuery();
                rs.next();
                tongTien += rs.getInt(1);
            }
            return tongTien;
        }catch (SQLException e){
            return 0;
        }
    }
    // Phương thức lấy danh sách sản phẩm trong giỏ hàng theo userId
    public List<GioHang> getCartItemsByUser(int currentUserId) {
        List<GioHang> cartItems = new ArrayList<>();
        String query = "SELECT gh.ma_gio_hang, gh.ma_user, gh.ma_san_pham, gh.so_luong,sp.ten_san_pham, sp.anh_san_pham, sp.gia, ttsp.size, ttsp.mau_sac, ttsp.ma_thuoc_tinh " +
                "FROM gio_hang gh JOIN san_pham sp ON gh.ma_san_pham = sp.ma_san_pham JOIN thuoc_tinh_san_pham " +
                "ttsp ON ttsp.ma_thuoc_tinh = gh.ma_thuoc_tinh WHERE gh.ma_user = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int maGioHang = rs.getInt("ma_gio_hang");
                int maSanPham = rs.getInt("ma_san_pham");
                int soLuong = rs.getInt("so_luong");
                String tenSanPham = rs.getString("ten_san_pham");
                String anhSanPham = rs.getString("anh_san_pham");
                int maThuocTinh = rs.getInt("ma_thuoc_tinh");
                int gia = rs.getInt("gia");
                String size = rs.getString("size");
                String mauSac = rs.getString("mau_sac");

                // Khởi tạo đối tượng GioHang
                GioHang item = new GioHang();
                item.setMaGioHang(maGioHang);
                item.setMaSanPham(maSanPham);
                item.setSoLuong(soLuong);
                item.setTenSanPham(tenSanPham);
                item.setAnhSanPham(anhSanPham);
                item.setGia(gia);
                item.setMaThuocTinh(maThuocTinh);
                item.setSize(size);
                item.setMauSac(mauSac);
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public int getMaThuocTinh(int maSanPham, String size, String mauSac) {
        int maThuocTinh = 0; // Giá trị mặc định nếu không tìm thấy
        String query = "SELECT ma_thuoc_tinh FROM thuoc_tinh_san_pham WHERE ma_san_pham = ? AND size = ? AND mau_sac = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, maSanPham);
            statement.setString(2, size);
            statement.setString(3, mauSac);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maThuocTinh = resultSet.getInt("ma_thuoc_tinh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maThuocTinh; // Trả về -1 nếu không tìm thấy
    }

    public boolean addToCart(int maUser, int maSanPham, int maThuocTinh, int soLuong, String ngayThem) {
        // Kiểm tra các tham số đầu vào
        if (maUser <= 0 || maSanPham <= 0 || maThuocTinh <= 0 || soLuong <= 0 || ngayThem == null || ngayThem.isEmpty()) {
            System.out.println("Thông tin không hợp lệ. Không thể thêm sản phẩm vào giỏ hàng.");
            return false; // Trả về false nếu thông tin không hợp lệ
        }

        // Truy vấn thêm vào giỏ hàng
        String query = "INSERT INTO gio_hang(`ma_user`, `ma_san_pham`, `ma_thuoc_tinh`, `so_luong`, `ngay_them`) VALUES(?, ?,?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, maUser);
            statement.setInt(2, maSanPham);
            statement.setInt(3, maThuocTinh);
            statement.setInt(4, soLuong);
            statement.setString(5, ngayThem);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sản phẩm đã được thêm vào giỏ hàng");
                return true; // Trả về true nếu thêm thành công
            } else {
                System.out.println("Không thể thêm sản phẩm vào giỏ hàng. Không có hàng bị ảnh hưởng.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console để kiểm tra
        }
        return false; // Trả về false nếu không thêm thành công
    }

    public boolean addDonHang(int id_kh, List<GioHang> list){
            if(!list.isEmpty()){
                try{
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO `don_hang`(`ma_user`, `ngay_dat_hang`, `trang_thai`, `tong_tien`) VALUES (?,?,?,?)");
                    stm.setInt(1, id_kh);
                    stm.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    stm.setString(3, "cho_xu_ly");
                    stm.setInt(4, getTongTienCart(list));
                    boolean d = stm.executeUpdate() > 0;
                        stm = conn.prepareStatement("SELECT MAX(ma_don_hang) FROM `don_hang` WHERE ma_user = ?");
                        stm.setInt(1, id_kh);
                        ResultSet rs = stm.executeQuery();
                        rs.next();
                        int maDonHang = rs.getInt(1);
                        for(GioHang gh : list){
                            stm = conn.prepareStatement("INSERT INTO `chi_tiet_don_hang`(`ma_don_hang`, `ma_san_pham`, `ma_thuoc_tinh`, `so_luong`, `gia`) VALUES (?, ?,?,?,?)");
                            stm.setInt(1, maDonHang);
                            stm.setInt(2, gh.getMaSanPham());
                            stm.setInt(3, gh.getMaThuocTinh());
                            stm.setInt(4, gh.getSoLuong());
                            stm.setInt(5, gh.getGia());
                            stm.executeUpdate();
                        }
                        for(GioHang gh : list){
                            stm = conn.prepareStatement("DELETE FROM `gio_hang` WHERE ma_gio_hang = ?");
                            stm.setInt(1, gh.getMaGioHang());
                            stm.executeUpdate();
                        }

                        return true;
                }catch (SQLException e){
                    return false;
                }
            }
            return false;
    }
    public List<DonHang> getDonHangByUserId(int userId) {
        List<DonHang> donHangs = new ArrayList<>();
        String query = "SELECT `ma_don_hang`,`ngay_dat_hang`, `trang_thai`, `tong_tien` FROM `don_hang` WHERE ma_user = ? order by ma_don_hang desc";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                query = "SELECT COUNT(ma_don_hang) FROM `chi_tiet_don_hang` WHERE ma_don_hang = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_don_hang"));
                ResultSet rs2 = pstmt1.executeQuery();
                rs2.next();
                int soL = rs2.getInt(1);

                query = "SELECT sp.ten_san_pham, sp.anh_san_pham FROM `chi_tiet_don_hang` ct INNER JOIN san_pham sp on sp.ma_san_pham = ct.ma_san_pham WHERE ct.ma_don_hang = ?";
                pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_don_hang"));
                rs2 = pstmt1.executeQuery();
                rs2.next();
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(rs.getInt("ma_don_hang"));
                donHang.setMaUser(userId); // Mã người dùng
                donHang.setNgayDatHang(rs.getString("ngay_dat_hang")); // Ngày đặt hàng
                donHang.setTrangThai(rs.getString("trang_thai")); // Trạng thái đơn hàng
                donHang.setTongTien(rs.getInt("tong_tien")); // Tổng tiền
                donHang.setSoluong(soL); // Số lượng sản phẩm trong đơn hàng
                donHang.setImageUrl(rs2.getString(2)); // Lấy ảnh sản phẩm
                donHang.setTenSanPham(rs2.getString(1)); // Lấy tên sản phẩm
                donHangs.add(donHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donHangs;
    }

    public void deleteDonHang(int maDonHang) {
        String query = "DELETE FROM don_hang WHERE ma_don_hang = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maDonHang);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ChiTietDonHang> getChiTietDonHang(int maDonHang) {
        List<ChiTietDonHang> chiTietDonHangList = new ArrayList<>();
        String query = "SELECT sp.ten_san_pham, ct.so_luong, sp.gia, tt.size, tt.mau_sac, sp.anh_san_pham FROM `chi_tiet_don_hang` ct INNER JOIN san_pham sp " +
                "on ct.ma_san_pham = sp.ma_san_pham INNER JOIN thuoc_tinh_san_pham tt on " +
                "tt.ma_thuoc_tinh = ct.ma_thuoc_tinh WHERE ma_don_hang = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, maDonHang);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                query = "SELECT `ngay_dat_hang`,`tong_tien` FROM `don_hang` WHERE ma_don_hang = ?";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setInt(1, maDonHang);
                ResultSet rs = stm.executeQuery();
                rs.next();

                ChiTietDonHang chiTiet = new ChiTietDonHang();
                chiTiet.setTenSanPham(resultSet.getString(1));
                chiTiet.setSoLuong(resultSet.getInt(2));
                chiTiet.setGia(resultSet.getInt(3));
                chiTiet.setSize(resultSet.getString(4));
                chiTiet.setMauSac(resultSet.getString(5));
                chiTiet.setAnhSanPham(resultSet.getString(6));
                chiTiet.setNgayDatHang(rs.getString(1));
                chiTiet.setTongTien(rs.getInt(2));

                chiTietDonHangList.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietDonHangList;
    }
    public List<DonHang> getDonHangAdmin() {
        List<DonHang> donHangs = new ArrayList<>();
        String query = "SELECT `ma_don_hang`,`ngay_dat_hang`, `trang_thai`, `tong_tien` FROM `don_hang` order by ma_don_hang desc";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                query = "SELECT COUNT(ma_don_hang) FROM `chi_tiet_don_hang` WHERE ma_don_hang = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_don_hang"));
                ResultSet rs2 = pstmt1.executeQuery();
                rs2.next();
                int soL = rs2.getInt(1);

                query = "SELECT sp.ten_san_pham, sp.anh_san_pham FROM `chi_tiet_don_hang` ct INNER JOIN san_pham sp on sp.ma_san_pham = ct.ma_san_pham WHERE ct.ma_don_hang = ?";
                pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_don_hang"));
                rs2 = pstmt1.executeQuery();
                rs2.next();
                query = "SELECT u.hoTen, u.soDienThoai, u.diaChi FROM `don_hang` dh INNER JOIN users u ON dh.ma_user = u.maUser WHERE ma_don_hang =?";
                pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_don_hang"));
                ResultSet rs3 = pstmt1.executeQuery();
                rs3.next();
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(rs.getInt("ma_don_hang"));
                donHang.setNgayDatHang(rs.getString("ngay_dat_hang")); // Ngày đặt hàng
                donHang.setTrangThai(rs.getString("trang_thai")); // Trạng thái đơn hàng
                donHang.setTongTien(rs.getInt("tong_tien")); // Tổng tiền
                donHang.setSoluong(soL); // Số lượng sản phẩm trong đơn hàng
                donHang.setImageUrl(rs2.getString(2)); // Lấy ảnh sản phẩm
                donHang.setTenSanPham(rs2.getString(1)); // Lấy tên sản phẩm
                donHang.setTenKhachHang(rs3.getString(1));
                donHang.setSoDienThoai(rs3.getString(2));
                donHang.setDiaChi(rs3.getString(3));
                donHangs.add(donHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donHangs;
    }

    public boolean addHoaDon(int maDonHang, int ma_user){
            try{
                PreparedStatement stm = conn.prepareStatement("INSERT INTO `hoa_don`(`ma_user`, `ngay_lap`, `tong_tien`, `trang_thai`) VALUES (?,?,?,?)");
                stm.setInt(1, ma_user);
                stm.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                stm.setString(4, "da_thanh_toan");
                stm.setInt(3, getTongTienDonHang(maDonHang));
                boolean d = stm.executeUpdate() > 0;
                stm = conn.prepareStatement("SELECT MAX(ma_hoa_don) FROM `hoa_don` WHERE ma_user = ?");
                stm.setInt(1, ma_user);
                ResultSet rs = stm.executeQuery();
                rs.next();
                int maHoaDon = rs.getInt(1);
                    stm = conn.prepareStatement("SELECT * FROM `chi_tiet_don_hang` WHERE ma_don_hang = ?");
                    stm.setInt(1, maDonHang);
                    ResultSet rs2 = stm.executeQuery();
                    while (rs2.next()){
                        stm = conn.prepareStatement("INSERT INTO `chi_tiet_hoa_don`( `ma_hoa_don`, `ma_san_pham`, `ma_thuoc_tinh`, `so_luong`, `gia`) VALUES (?,?,?,?,?)");
                        stm.setInt(1, maHoaDon);
                        stm.setInt(2, rs2.getInt(4));
                        stm.setInt(3, rs2.getInt(3));
                        stm.setInt(4, rs2.getInt(5));
                        stm.setInt(5, rs2.getInt(6));
                        stm.executeUpdate();
                    }
                    deleteDonHang(maDonHang);
                return true;
            }catch (SQLException e){
                return false;
            }
    }
    public int getTongTienDonHang(int maDonHang){
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT `tong_tien` FROM `don_hang` WHERE ma_don_hang = ?");
            stm.setInt(1, maDonHang);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }



    public List<ChiTietHoaDon> getChiTietHoaDon(int maHoaDon) {
        List<ChiTietHoaDon> chiTietHoaDonList = new ArrayList<>();
        String query = "SELECT sp.ten_san_pham, ct.so_luong, sp.gia, tt.size, tt.mau_sac, sp.anh_san_pham FROM `chi_tiet_hoa_don` ct INNER JOIN san_pham sp " +
                "on ct.ma_san_pham = sp.ma_san_pham INNER JOIN thuoc_tinh_san_pham tt on " +
                "tt.ma_thuoc_tinh = ct.ma_thuoc_tinh WHERE ma_hoa_don = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, maHoaDon);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                query = "SELECT `ngay_lap`,`tong_tien` FROM `hoa_don` WHERE ma_hoa_don = ?";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setInt(1, maHoaDon);
                ResultSet rs = stm.executeQuery();
                rs.next();

                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setTenSanPham(resultSet.getString(1));
                chiTiet.setSoLuong(resultSet.getInt(2));
                chiTiet.setGia(resultSet.getInt(3));
                chiTiet.setSize(resultSet.getString(4));
                chiTiet.setMauSac(resultSet.getString(5));
                chiTiet.setAnhSanPham(resultSet.getString(6));
                chiTiet.setNgayDatHang(rs.getString(1));
                chiTiet.setTongTien(rs.getInt(2));

                chiTietHoaDonList.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietHoaDonList;
    }

    public List<HoaDon> getHoaDonByUserId(int userId) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String query = "SELECT `ma_hoa_don`,`ngay_lap`, `trang_thai`, `tong_tien` FROM `hoa_don` WHERE ma_user = ? order by ma_hoa_don desc";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                query = "SELECT COUNT(ma_hoa_don) FROM `chi_tiet_hoa_don` WHERE ma_hoa_don = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_hoa_don"));
                ResultSet rs2 = pstmt1.executeQuery();
                rs2.next();
                int soL = rs2.getInt(1);

                query = "SELECT sp.ten_san_pham, sp.anh_san_pham FROM `chi_tiet_hoa_don` ct INNER JOIN san_pham sp on sp.ma_san_pham = ct.ma_san_pham WHERE ct.ma_hoa_don = ?";
                pstmt1 = conn.prepareStatement(query);
                pstmt1.setInt(1, rs.getInt("ma_hoa_don"));
                rs2 = pstmt1.executeQuery();
                rs2.next();
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("ma_hoa_don"));
                hoaDon.setMaUser(userId); // Mã người dùng
                hoaDon.setNgayLap(rs.getString("ngay_lap")); // Ngày đặt hàng
                hoaDon.setTrangThai(rs.getString("trang_thai")); // Trạng thái đơn hàng
                hoaDon.setTongTien(rs.getInt("tong_tien")); // Tổng tiền
                hoaDon.setSoluong(soL); // Số lượng sản phẩm trong đơn hàng
                hoaDon.setImageUrl(rs2.getString(2)); // Lấy ảnh sản phẩm
                hoaDon.setTenSanPham(rs2.getString(1)); // Lấy tên sản phẩm
                hoaDons.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoaDons;
    }

}




