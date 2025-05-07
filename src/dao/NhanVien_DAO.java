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
import entity.NhanVien;

public class NhanVien_DAO {
	public int getMaxEmployeeID() {
		int maxEmployeeID = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT MAX(CAST(SUBSTRING(maNV, 3, LEN(maNV) - 2) AS INT)) AS maxID FROM NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			if (rs.next()) {
				maxEmployeeID = rs.getInt("maxID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxEmployeeID;
	}

	public boolean update(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement(
					"UPDATE NhanVien SET tenNV=?, gioiTinh=?, soDienThoai=?, ngaySinh=?, luongCoBan=? WHERE maNV=?");
			stmt.setString(1, nv.getTenNV());
			stmt.setBoolean(2, nv.isGioiTinh());
			stmt.setString(3, nv.getSoDienThoai());
			stmt.setDate(4, Date.valueOf(nv.getNgaySinh()));
			stmt.setDouble(5, nv.getLuongCoBan());
			stmt.setString(6, nv.getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public ArrayList<NhanVien> getalltbNhanVien() {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String maNV = rs.getString("maNV");
				String tenNV = rs.getString("tenNV");
				boolean gioiTinh = rs.getBoolean("gioiTinh");
				String soDienThoai = rs.getString("soDienThoai");
				Date ns = rs.getDate("ngaySinh");
				LocalDate ngaySinh = ns.toLocalDate();
				double luong = rs.getDouble("luongCoBan");
				NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, soDienThoai, ngaySinh, luong);
				dsnv.add(nv);

			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsnv;
	}

	public int countNhanVien() {
		int cnt = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				cnt++;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}

	public NhanVien getNhanVienTheoMaNV(String id) {
		NhanVien nv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "Select * from NhanVien where maNV = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, id);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
			ResultSet rs = statement.executeQuery();
			// Duyệt trên kết quả trả về.
			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
				String maNV = rs.getString("maNV");
				String tenNV = rs.getString("tenNV");
				boolean gioiTinh = rs.getBoolean("gioiTinh");
				String soDienThoai = rs.getString("soDienThoai");
				Date ns = rs.getDate("ngaySinh");
				LocalDate ngaySinh = ns.toLocalDate();
				double luong = rs.getDouble("luongCoBan");
				nv = new NhanVien(maNV, tenNV, gioiTinh, soDienThoai, ngaySinh, luong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}

	public boolean create(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into" + " NhanVien values(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setBoolean(3, nv.isGioiTinh());
			stmt.setString(4, nv.getSoDienThoai());
			stmt.setDate(5, Date.valueOf(nv.getNgaySinh()));
			stmt.setDouble(6, nv.getLuongCoBan());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public void xoa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from nhanvien where manv = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
