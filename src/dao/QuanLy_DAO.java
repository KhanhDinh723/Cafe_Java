package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connectDB.ConnectDB;
import entity.QuanLy;
import entity.TaiKhoanQuanLy;
import gui.NhanVien_UI;
import gui.Quanly_UI;

public class QuanLy_DAO {
	public QuanLy getQuanLyTheoMaQL(String id) {
		QuanLy ql = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT * FROM QuanLy WHERE MaQL = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String ten = rs.getString("tenQL");
				Boolean gioitinh = rs.getBoolean("gioiTinh");
				String soDienThoai = rs.getString("soDienThoai");
				Date ngaySinh = rs.getDate("ngaySinh");
				double luong = rs.getDouble("luongCoBan");
				ql = new QuanLy(id, ten, gioitinh, soDienThoai, ngaySinh.toLocalDate(), luong);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ql;
	}
}
