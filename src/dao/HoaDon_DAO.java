package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class HoaDon_DAO {
//Đếm sl mã sản phẩm khác nhau	
	public int getProductCodeCountByDateRange(Date fromDate, Date toDate) {
		int productCodeCount = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
//xử lý bên DAO qua bên GUI
		try {
			String sql = "SELECT COUNT(DISTINCT SanPham.maSP) AS SoLuongmaSP\n" + "FROM ChiTietHoaDon\n"
					+ "JOIN SanPham ON ChiTietHoaDon.maSP = SanPham.maSP\n"
					+ "JOIN HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHD\n" + "WHERE HoaDon.ngayMua BETWEEN ? AND ?";
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				productCodeCount = rs.getInt("SoLuongmaSP");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCodeCount;
	}
//Đếm tổng số lượng hóa đơn
	public int getInvoiceCountByDateRange(Date fromDate, Date toDate) {
		int invoiceCount = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT COUNT(*) AS InvoiceCount FROM HoaDon WHERE ngayMua BETWEEN ? AND ?";
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				invoiceCount = rs.getInt("InvoiceCount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoiceCount;
	}
//Tính tổng doanh thu
	public double getToTalByDateRange(Date fromDate, Date toDate) {
		double total = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT SUM(tongTien) AS TongDoanhThu FROM HoaDon WHERE ngayMua BETWEEN ? AND ?";
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt("TongDoanhThu");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
//Đếm mã sp chưa bán 
	public int getProductNotCountByDateRange(Date fromDate, Date toDate) {
		int productCodeCount = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT\r\n" + "    COUNT(DISTINCT SanPham.maSP) AS SoLuongmaSPChuaBan\r\n" + "FROM\r\n"
					+ "    SanPham\r\n" + "WHERE\r\n" + "    SanPham.maSP NOT IN (\r\n"
					+ "        SELECT DISTINCT ChiTietHoaDon.maSP\r\n" + "        FROM ChiTietHoaDon\r\n"
					+ "        JOIN HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHD\r\n"
					+ "        WHERE HoaDon.ngayMua BETWEEN ? AND ?)";
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				productCodeCount = rs.getInt("SoLuongmaSPChuaBan");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCodeCount;
	}
//Lấy tổng tiền bán của mỗi nhân viên
	public List<Object[]> getTongTienDaBanByNhanVienAndDateRange(Date fromDate, Date toDate) {
		List<Object[]> resultList = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT NhanVien.maNV, NhanVien.tenNV, SUM(HoaDon.tongTien) AS TongTienDaBan "
					+ "FROM NhanVien " + "JOIN HoaDon ON NhanVien.maNV = HoaDon.maNV "
					+ "WHERE HoaDon.ngayMua BETWEEN ? AND ? " + "GROUP BY NhanVien.maNV, NhanVien.tenNV";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setDate(1, fromDate);
				preparedStatement.setDate(2, toDate);

				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					String maNV = rs.getString("maNV");
					String tenNV = rs.getString("tenNV");
					float tongTienDaBan = rs.getFloat("TongTienDaBan");

					Object[] row = { maNV, tenNV, tongTienDaBan };
					resultList.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}
//Tổng tiền vs stt nhân viên
	public List<Object[]> getTongTien() {
		List<Object[]> resultList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT\r\n" + "  ROW_NUMBER() OVER (ORDER BY NhanVien.maNV) AS STT,\r\n"
					+ "  NhanVien.maNV,\r\n" + "  NhanVien.tenNV,\r\n" + "  SUM(HoaDon.tongTien) AS TongTien\r\n"
					+ "FROM\r\n" + "  NhanVien\r\n" + "LEFT JOIN\r\n" + "  HoaDon ON NhanVien.maNV = HoaDon.maNV\r\n"
					+ "GROUP BY\r\n" + "  NhanVien.maNV, NhanVien.tenNV;";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int stt = rs.getInt("STT"); // Retrieve the sequential number
				String maNV = rs.getString("maNV");
				String tenNV = rs.getString("tenNV");
				double tongTien = rs.getDouble("TongTien");
				NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
				Object[] row = { stt, maNV, tenNV, currencyFormatter.format(tongTien) };
				resultList.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
//lấy thông tin hóa đơn theo mã
	public HoaDon getHoaDonByMaHD(String maHD) {
		HoaDon hoaDon = null;

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM HoaDon WHERE maHD = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Set the parameter for the prepared statement
			preparedStatement.setString(1, maHD);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				// Extract data from the result set
				NhanVien nv = new NhanVien(rs.getString("maNV"));
				String maKH = rs.getString("maKH");
				KhachHang kh = new KhachHang(maKH);
				Date ngayMua = rs.getDate("ngayMua");
				double thue = rs.getDouble("thue");
				double tongTien = rs.getDouble("tongTien");

				// Create a new HoaDon object
				hoaDon = new HoaDon(maHD, nv, kh, ngayMua.toLocalDate(), thue, tongTien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hoaDon;
	}
//Đếm số lượng hóa đơn
	public int demSoLuongHoaDon() {
		int soLuong = 0;

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT COUNT(*) AS soLuong FROM HoaDon";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				soLuong = rs.getInt("soLuong");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return soLuong;
	}

	public boolean themHoaDon(HoaDon hoaDon) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Thiết lập các giá trị cho câu lệnh SQL
			preparedStatement.setString(1, hoaDon.getMaHD());
			preparedStatement.setString(2, hoaDon.getNv().getMaNV());
			preparedStatement.setString(3, hoaDon.getKh().getSoDienThoai());
			preparedStatement.setDate(4, Date.valueOf(hoaDon.getNgayMua()));
			preparedStatement.setDouble(5, hoaDon.getThue());
			preparedStatement.setDouble(6, hoaDon.getTongTien());

			// Thực hiện câu lệnh SQL để thêm dữ liệu
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Hóa đơn đã được thêm thành công.");
				return true;
			} else {
				System.out.println("Không thể thêm hóa đơn.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateTongTien(String maHD, double additionalAmount) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE HoaDon SET tongTien = ? WHERE maHD = ?");
			stmt.setDouble(1, additionalAmount);
			stmt.setString(2, maHD);

			int n = stmt.executeUpdate();

			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean kiemTraTonTaiHoaDon(String maHD) {
		try (Connection con = ConnectDB.getConnection()) {
			String sql = "SELECT COUNT(*) AS count FROM HoaDon WHERE maHD = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, maHD);

				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						int count = rs.getInt("count");
						return count > 0; // If count is greater than 0, the bill exists
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false; // Error occurred or the bill does not exist
	}

	public double getTongTienByMaHD(String maHD) {
		double tongTien = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("Select * from HoaDon where maHD = ?");
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tongTien = rs.getDouble("tongTien");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tongTien;
	}

	public ArrayList<HoaDon> getAllHoaDon() {
		ArrayList<HoaDon> hoaDonList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT * FROM HoaDon";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maHD = rs.getString("maHD");
				NhanVien nv = new NhanVien(rs.getString("maNV"));
				String maKH = rs.getString("maKH");
				KhachHang kh = new KhachHang(maKH);
				Date ngayMua = rs.getDate("ngayMua");
				double thue = rs.getDouble("thue");
				double tongTien = rs.getDouble("tongTien");

				HoaDon hoaDon = new HoaDon(maHD, nv, kh, ngayMua.toLocalDate(), thue, tongTien);
				hoaDonList.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDonList;
	}

	public ArrayList<HoaDon> getAllHoaDonByDateRange(Date fromDate, Date toDate) {
		ArrayList<HoaDon> hoaDonList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT * FROM HoaDon WHERE ngayMua BETWEEN ? AND ?";
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maHD = rs.getString("maHD");
				NhanVien nv = new NhanVien(rs.getString("maNV"));
				String maKH = rs.getString("maKH");
				KhachHang kh = new KhachHang(maKH);
				Date ngayMua = rs.getDate("ngayMua");
				double thue = rs.getDouble("thue");
				double tongTien = rs.getDouble("tongTien");

				HoaDon hoaDon = new HoaDon(maHD, nv, kh, ngayMua.toLocalDate(), thue, tongTien);
				hoaDonList.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDonList;
	}

	public ArrayList<HoaDon> getHoaDonByMaHDLike(String maHD) {
		ArrayList<HoaDon> hoaDonList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT * FROM HoaDon WHERE maHD LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + maHD + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maHDResult = rs.getString("maHD");
				NhanVien nv = new NhanVien(rs.getString("maNV"));
				String maKH = rs.getString("maKH");
				KhachHang kh = new KhachHang(maKH);
				Date ngayMua = rs.getDate("ngayMua");
				double thue = rs.getDouble("thue");
				double tongTien = rs.getDouble("tongTien");

				HoaDon hoaDon = new HoaDon(maHDResult, nv, kh, ngayMua.toLocalDate(), thue, tongTien);
				hoaDonList.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDonList;
	}

	public ArrayList<HoaDon> getHoaDonBySoDienThoai(String soDienThoai) {
		ArrayList<HoaDon> hoaDonList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT * FROM HoaDon WHERE maKH IN (SELECT maKH FROM KhachHang WHERE soDienThoai LIKE ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + soDienThoai + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maHD = rs.getString("maHD");
				NhanVien nv = new NhanVien(rs.getString("maNV"));
				String maKH = rs.getString("maKH");
				KhachHang kh = new KhachHang(maKH);
				Date ngayMua = rs.getDate("ngayMua");
				double thue = rs.getDouble("thue");
				double tongTien = rs.getDouble("tongTien");

				HoaDon hoaDon = new HoaDon(maHD, nv, kh, ngayMua.toLocalDate(), thue, tongTien);
				hoaDonList.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDonList;
	}

}