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

public class TaiKhoanQuanLy_DAO {
	public boolean create(TaiKhoanNhanVien tk) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into TaiKhoanNhanVien values(?, ?, ?)");
			stmt.setString(1,tk.getTaiKhoan());
			stmt.setString(2,tk.getMatKhau());
			stmt.setString(3,tk.getNV().getMaNV());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		finally {
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
}

