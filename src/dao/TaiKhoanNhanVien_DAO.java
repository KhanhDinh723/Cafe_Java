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
import entity.TaiKhoanNhanVien;

public class TaiKhoanNhanVien_DAO {
	public boolean create(TaiKhoanNhanVien tk) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into TaiKhoanNhanVien values(?, ?, ?)");
			stmt.setString(1, tk.getTaiKhoan());
			stmt.setString(2, tk.getMatKhau());
			stmt.setString(3, tk.getNV().getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n > 0;
	}

	public void xoa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from TaiKhoanNhanVien where manv = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<TaiKhoanNhanVien> getAllTaiKhoan() {
		ArrayList<TaiKhoanNhanVien> dsTaiKhoan = new ArrayList<TaiKhoanNhanVien>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM TaiKhoanNhanVien");
			rs = stmt.executeQuery();

			while (rs.next()) {
				String taiKhoan = rs.getString("taiKhoan");
				String matKhau = rs.getString("matKhau");
				String maNV = rs.getString("maNV");

				// Tạo đối tượng NhanVien tương ứng với mỗi dòng trong ResultSet
				NhanVien_DAO nvDao = new NhanVien_DAO();
				NhanVien nv = nvDao.getNhanVienTheoMaNV(maNV);

				// Tạo đối tượng TaiKhoanNhanVien và thêm vào danh sách
				TaiKhoanNhanVien taiKhoanNV = new TaiKhoanNhanVien(taiKhoan, matKhau, nv);
				dsTaiKhoan.add(taiKhoanNV);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dsTaiKhoan;
	}

	public TaiKhoanNhanVien timTaiKhoanTheoTaiKhoan(String taiKhoan) {
		TaiKhoanNhanVien taiKhoanNV = null;
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM TaiKhoanNhanVien WHERE taiKhoan = ?");
			stmt.setString(1, taiKhoan);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String matKhau = rs.getString("matKhau");
				String maNV = rs.getString("maNV");

				// Tạo đối tượng NhanVien tương ứng với mã nhân viên
				NhanVien_DAO nvDao = new NhanVien_DAO();
				NhanVien nv = nvDao.getNhanVienTheoMaNV(maNV);

				// Tạo đối tượng TaiKhoanNhanVien và trả về
				taiKhoanNV = new TaiKhoanNhanVien(taiKhoan, matKhau, nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return taiKhoanNV;
	}
}
