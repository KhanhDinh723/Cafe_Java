package dao;

import java.awt.Checkbox;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoanNhanVien;
import entity.TaiKhoanQuanLy;
import gui.DangNhap_UI;
import gui.NhanVien_UI;
import gui.Quanly_UI;

public class DangNhap_DAO extends JFrame{
	public DangNhap_DAO() {};
	public void dNhap(JTextField txtTaiKhoan, JPasswordField txtMatKhau, JCheckBox chkQuanLy, JLabel lblError) {
		PreparedStatement stmt = null;
		Connection con = ConnectDB.getConnection();
		try (Statement statement = con.createStatement()) {
			String user = txtTaiKhoan.getText();
			char[] pass = txtMatKhau.getPassword();
			boolean phanQuyen = chkQuanLy.isSelected();
			if (phanQuyen){
				String sql = "SELECT * FROM TaiKhoanQuanLy WHERE taikhoan = ? AND matkhau = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				String pwd = new String(pass);
				stmt.setString(2, pwd);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					TaiKhoanQuanLy curUser = new TaiKhoanQuanLy(txtTaiKhoan.getText());
					new Quanly_UI(curUser).setVisible(true);
					new DangNhap_UI().setVisible(false);
				} else {
					lblError.setText("Vui lòng kiểm tra: tên tài khoản phải bắt đầu bằng QL__ hoặc NV__");
					JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
					txtMatKhau.setText("");
					txtMatKhau.requestFocus();
				}
				rs.close();
				stmt.close();	
			}
			else {
				String sql = "SELECT * FROM TaiKhoanNhanVien WHERE taikhoan = ? AND matkhau = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				String pwd = new String(pass);
				stmt.setString(2, pwd);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					TaiKhoanNhanVien curUser = new TaiKhoanNhanVien(txtTaiKhoan.getText());
					new NhanVien_UI(curUser).setVisible(true);
				} else {
					lblError.setText("Vui lòng kiểm tra: tên tài khoản phải bắt đầu bằng QL__ hoặc NV__");
					JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
					txtMatKhau.setText("");
					txtMatKhau.requestFocus();
				}
				rs.close();
				stmt.close();	
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
