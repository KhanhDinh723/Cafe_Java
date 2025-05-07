package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {
	public void capNhatDiemHang(String soDienThoai, int diemMoi, String hangMoi) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			// Kiểm tra xem khách hàng có tồn tại không
			KhachHang khachHangCu = getKhachHangBySoDienThoai(soDienThoai);

			if (khachHangCu != null) {
				// Cập nhật điểm và hạng
				String sql = "UPDATE KhachHang SET diem=?, hang=? WHERE soDienThoai=?";
				PreparedStatement preparedStatement = con.prepareStatement(sql);

				// Thiết lập các giá trị cho câu lệnh SQL
				preparedStatement.setInt(1, diemMoi);
				preparedStatement.setString(2, hangMoi);
				preparedStatement.setString(3, soDienThoai);

				// Thực hiện câu lệnh SQL để cập nhật dữ liệu
				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Thông tin điểm và hạng của khách hàng đã được cập nhật thành công.");
				} else {
					System.out.println("Không thể cập nhật thông tin điểm và hạng của khách hàng.");
				}
			} else {
				System.out.println("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int layDiemHienTai(String soDienThoai) {
		int diemHienTai = -1; // Giá trị mặc định nếu không tìm thấy khách hàng

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			// Kiểm tra xem khách hàng có tồn tại không
			KhachHang khachHang = getKhachHangBySoDienThoai(soDienThoai);

			if (khachHang != null) {
				// Lấy điểm hiện tại của khách hàng
				String sql = "SELECT diem FROM KhachHang WHERE soDienThoai=?";
				PreparedStatement preparedStatement = con.prepareStatement(sql);

				// Thiết lập giá trị cho tham số của câu lệnh SQL
				preparedStatement.setString(1, soDienThoai);

				// Thực hiện câu lệnh SQL để lấy dữ liệu
				ResultSet rs = preparedStatement.executeQuery();

				if (rs.next()) {
					diemHienTai = rs.getInt("diem");
				} else {
					System.out.println("Không tìm thấy điểm của khách hàng với số điện thoại: " + soDienThoai);
				}
			} else {
				System.out.println("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return diemHienTai;
	}

	public ArrayList<KhachHang> getall() {
		ArrayList<KhachHang> dsnv = new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from KhachHang";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String soDienThoai = rs.getString("soDienThoai");
				String tenKH = rs.getString("tenKH");
				Date ns = rs.getDate("ngaySinh");
				LocalDate ngaySinh = ns.toLocalDate();
				boolean gioiTinh = rs.getBoolean("gioiTinh");
				int diem = rs.getInt("diem");
				String hang = rs.getString("hang");
				KhachHang kh = new KhachHang(tenKH, ngaySinh, gioiTinh, diem, soDienThoai, hang);
				dsnv.add(kh);

			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsnv;
	}

	public void suaKhachHang(KhachHang khachHang) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "UPDATE KhachHang SET tenKH=?, ngaySinh=?, gioiTinh=?, diem=?, hang=? WHERE soDienThoai=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Thiết lập các giá trị cho câu lệnh SQL
			preparedStatement.setString(1, khachHang.getTenKH());
			preparedStatement.setDate(2, Date.valueOf(khachHang.getNgaySinh()));
			preparedStatement.setBoolean(3, khachHang.getGioiTinh());
			preparedStatement.setInt(4, khachHang.getDiem());
			preparedStatement.setString(5, khachHang.getHang());
			preparedStatement.setString(6, khachHang.getSoDienThoai());

			// Thực hiện câu lệnh SQL để cập nhật dữ liệu
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Khách hàng đã được cập nhật thành công.");
			} else {
				System.out.println(
						"Không thể cập nhật khách hàng. Có thể không tìm thấy khách hàng với số điện thoại này.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void xoaKhachHang(String soDienThoai) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "DELETE FROM KhachHang WHERE soDienThoai=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Thiết lập giá trị cho tham số của câu lệnh SQL
			preparedStatement.setString(1, soDienThoai);

			// Thực hiện câu lệnh SQL để xóa dữ liệu
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Khách hàng đã được xóa thành công.");
			} else {
				System.out.println("Không thể xóa khách hàng. Có thể không tìm thấy khách hàng với số điện thoại này.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void themKhachHang(KhachHang khachHang) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "INSERT INTO KhachHang (soDienThoai, tenKH, ngaySinh, gioiTinh, diem, hang) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Thiết lập các giá trị cho câu lệnh SQL
			preparedStatement.setString(1, khachHang.getSoDienThoai());
			preparedStatement.setString(2, khachHang.getTenKH());
			preparedStatement.setDate(3, Date.valueOf(khachHang.getNgaySinh()));
			preparedStatement.setBoolean(4, khachHang.getGioiTinh());
			preparedStatement.setInt(5, khachHang.getDiem());
			preparedStatement.setString(6, khachHang.getHang());

			// Thực hiện câu lệnh SQL để thêm dữ liệu
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Khách hàng đã được thêm thành công.");
			} else {
				System.out.println("Không thể thêm khách hàng.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public KhachHang getKhachHangBySoDienThoai(String soDienThoai) {
		KhachHang kh = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM KhachHang WHERE soDienThoai=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			// Thiết lập giá trị cho tham số của câu lệnh SQL
			preparedStatement.setString(1, soDienThoai);

			// Thực hiện câu lệnh SQL để lấy dữ liệu
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String tenKH = rs.getString("tenKH");
				Date ns = rs.getDate("ngaySinh");
				LocalDate ngaySinh = ns.toLocalDate();
				boolean gioiTinh = rs.getBoolean("gioiTinh");
				int diem = rs.getInt("diem");
				String hang = rs.getString("hang");
				kh = new KhachHang(tenKH, ngaySinh, gioiTinh, diem, soDienThoai, hang);
			} else {
				System.out.println("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kh;
	}
}
