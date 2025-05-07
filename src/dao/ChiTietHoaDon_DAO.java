package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;

public class ChiTietHoaDon_DAO {

	public boolean deleteChiTietHoaDonBymaSanPham(String maSanPham) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("DELETE FROM ChiTietHoaDon WHERE maSP = ?");
			stmt.setString(1, maSanPham);

			int n = stmt.executeUpdate();

			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Object[]> getThongKeSanPhamByDateRange(Date fromDate, Date toDate) {
		List<Object[]> resultList = new ArrayList<>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			 String sql = "SELECT SanPham.tenSP, SUM(ChiTietHoaDon.soLuong) AS TongSoLuong "
	                   + "FROM ChiTietHoaDon "
	                   + "JOIN SanPham ON ChiTietHoaDon.maSP = SanPham.maSP "
	                   + "JOIN HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHD "
	                   + "WHERE HoaDon.ngayMua BETWEEN ? AND ? "
	                   + "GROUP BY SanPham.tenSP";
			 
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, fromDate);
			stmt.setDate(2, toDate);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String tenThuoc = rs.getString("tenSP");
				int tongSoLuong = rs.getInt("TongSoLuong");

				Object[] row = { tenThuoc, tongSoLuong };
				resultList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public List<Object[]> getThongKeSanPham() {
		List<Object[]> resultList = new ArrayList<>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT SanPham.tenSP, SUM(ChiTietHoaDon.soLuong) AS TongSoLuong "
	                   + "FROM SanPham "
	                   + "LEFT JOIN ChiTietHoaDon ON SanPham.maSP = ChiTietHoaDon.maSP "
	                   + "GROUP BY SanPham.tenSP";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						String tenThuoc = resultSet.getString("tenThuoc");
						int tongSoLuong = resultSet.getInt("TongSoLuong");
						int soLuongTon = resultSet.getInt("soLuongTon");

						Object[] row = { tenThuoc, tongSoLuong, soLuongTon };
						resultList.add(row);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public void themChiTietHoaDon(ChiTietHoaDon hd) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "INSERT INTO ChiTietHoaDon VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			// Thiết lập các giá trị cho câu lệnh SQL
			preparedStatement.setString(1, hd.getHoaDon().getMaHD());
			preparedStatement.setString(2, hd.getsanPham().getMaSP());
			preparedStatement.setInt(3, hd.getSoLuong());
			preparedStatement.setDouble(4, hd.getDonGia());

			// Thực hiện câu lệnh SQL để thêm dữ liệu
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Chi tiết hóa đơn đã được thêm thành công.");
			} else {
				System.out.println("Không thể thêm chi tiết hóa đơn.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double getThanhTienBymaSanPhamAndMaHoaDon(String maSanPham, String maHoaDon) {
		double tt = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM ChiTietHoaDon WHERE maSP = ? AND maHoaDon = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, maSanPham);
				preparedStatement.setString(2, maHoaDon);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						// Retrieve data from the result set
						int soLuong = resultSet.getInt("soLuong");
						double donGia = resultSet.getDouble("donGia");
						ChiTietHoaDon cthd = new ChiTietHoaDon(new SanPham(maSanPham), new HoaDon(maHoaDon), soLuong,
								donGia);
						tt = cthd.getThanhTien();

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tt;
	}

	public boolean kiemTraSPTonTai(String maSanPham) {
		boolean tonTai = false;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM Thuoc WHERE maSanPham = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, maSanPham);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					tonTai = resultSet.next();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tonTai;
	}

	public int laySoLuongHienTai(String maSanPham) {
		int soLuongHienTai = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT soLuong FROM ChiTietHoaDon WHERE maSanPham = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, maSanPham);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						soLuongHienTai += resultSet.getInt("soLuong");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return soLuongHienTai;
	}

	public void capNhatSoLuong(String maSanPham, int soLuongMoi) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "UPDATE ChiTietHoaDon SET soLuong = ? WHERE maSP = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setInt(1, soLuongMoi);
				preparedStatement.setString(2, maSanPham);

				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Cập nhật số lượng thành công.");
				} else {
					System.out.println("Không thể cập nhật số lượng.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
