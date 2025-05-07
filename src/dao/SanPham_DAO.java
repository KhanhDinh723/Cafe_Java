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
import entity.DanhMuc;
import entity.SanPham;

public class SanPham_DAO {

	public boolean updateSP(SanPham sp) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(
					"UPDATE SanPham SET tenSP = ?, hinhSP = ?, kichThuoc = ?, danhMuc = ?, donGia = ? WHERE maSP = ?");

			stmt.setString(1, sp.getTenSP());
			stmt.setString(2, sp.getHinhSP());
			stmt.setString(3, sp.getKichThuoc());
			stmt.setString(4, sp.getDanhMuc().getMaDM());
			stmt.setDouble(5, sp.getDonGiaBan());
			stmt.setString(6, sp.getMaSP());

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

	public int getMaxSP() {
		int maxSPID = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		Statement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT MAX(CAST(SUBSTRING(maSP, 3, LEN(maSP) - 2) AS INT)) AS maxSPID FROM SanPham";
			statement = con.createStatement();
			rs = statement.executeQuery(sql);

			if (rs.next()) {
				maxSPID = rs.getInt("maxSPID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxSPID;
	}

	public ArrayList<SanPham> getSanPhambyTen(String tenSP) {
		ArrayList<SanPham> dst = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM SanPham WHERE tenSP LIKE ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, "%" + tenSP + "%");

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String ma = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String hinh = rs.getString("hinhSP");
				DanhMuc nhaCC = new DanhMuc(rs.getString("danhMuc"));
				String noiCC = rs.getString("kichThuoc");
				double giaBan = rs.getDouble("donGia");
				SanPham th = new SanPham(ma, ten, hinh, nhaCC, noiCC, giaBan);
				dst.add(th);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dst;
	}

	public SanPham getSanPhambyMa(String ma) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		SanPham th = null;
		try {

			String sql = "SELECT * FROM SanPham WHERE maSP = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ten = rs.getString("tenSP");
				String hinh = rs.getString("hinhSP");
				DanhMuc nhaCC = new DanhMuc(rs.getString("danhMuc"));
				String noiCC = rs.getString("kichThuoc");
				double giaBan = rs.getDouble("donGia");
				th = new SanPham(ma, ten, hinh, nhaCC, noiCC, giaBan);			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}

	public boolean deleteSPc(String maSP) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("DELETE FROM SanPham WHERE maSP = ?");
			stmt.setString(1, maSP);

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

	public ArrayList<SanPham> getallSanPham() {
		ArrayList<SanPham> dst = new ArrayList<SanPham>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from SanPham";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String ma = rs.getString("maSP");
				String ten = rs.getString("tenSP");
				String hinh = rs.getString("hinhSP");
				DanhMuc nhaCC = new DanhMuc(rs.getString("danhMuc"));
				String noiCC = rs.getString("kichThuoc");
				double giaBan = rs.getDouble("donGia");
				SanPham th = new SanPham(ma, ten, hinh, nhaCC, noiCC, giaBan);
				dst.add(th);

			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dst;
	}

	public String getanhSanPham(String ma) {
		String path = "";
		PreparedStatement statement = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from SanPham where maSP = ? ";
			statement = con.prepareStatement(sql);
			statement.setString(1, ma);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				path = rs.getString("hinhSP");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return path;
	}

	public ArrayList<String> getallTenSanPham() {
		ArrayList<String> dst = new ArrayList<String>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from SanPham";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String ten = rs.getString("tenSP");
				dst.add(ten);

			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dst;
	}

	public boolean addSanPham(SanPham drink) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO SanPham (maSP, tenSP, hinhSP, danhMuc, kichThuoc, donGia) VALUES (?, ?, ?, ?, ?, ?)");

			stmt.setString(1, drink.getMaSP());
			stmt.setString(2, drink.getTenSP());
			stmt.setString(3, drink.getHinhSP());
			stmt.setString(4, drink.getDanhMuc().getMaDM());
			stmt.setString(5, drink.getKichThuoc());
			stmt.setDouble(6, drink.getDonGiaBan());

			int n = stmt.executeUpdate();

			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int countSP() {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT COUNT(*) AS count FROM SanPham";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	
	public int getSoLuongTonByMaSP(String maSP) {
		int soLuongTon = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT soLuongTon FROM Thuoc WHERE maSP = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maSP);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				soLuongTon = rs.getInt("soLuongTon");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return soLuongTon;
	}
}
