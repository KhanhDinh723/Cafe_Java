package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connectDB.ConnectDB;

public class DoiMatKhau_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMK, txtMKMoi, txtXacNhanMK;
	private JButton btnDangNhap, btnThoat, btnDoiMK;

	public DoiMatKhau_UI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createGui();
	}

	public void createGui() {
		setTitle("Đổi mật khẩu");
		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		doiMK();
	}

	public void doiMK() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		Color mauNen = new Color(102, 205, 170);

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Đổi mật khẩu");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.RED);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		b1.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
		b2.setBorder(BorderFactory.createTitledBorder("Mật khẩu cũ"));
		b3.setBorder(BorderFactory.createTitledBorder("Mật khẩu mới"));
		b4.setBorder(BorderFactory.createTitledBorder("Xác nhận mật khẩu mới"));
		txtTaiKhoan = new JTextField(15);
		txtMK = new JPasswordField(15);
		txtMKMoi = new JPasswordField(15);
		txtXacNhanMK = new JPasswordField(15);
		b1.add(txtTaiKhoan);
		b2.add(txtMK);
		b3.add(txtMKMoi);
		b4.add(txtXacNhanMK);
		txtTaiKhoan.setColumns(30);

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(mauNen);
		btnDangNhap.setForeground(Color.WHITE);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.WHITE);
		btnDoiMK = new JButton("Đổi mật khẩu");
		btnDoiMK.setBackground(mauNen);
		btnDoiMK.setForeground(Color.WHITE);
		b5.add(btnDoiMK);
		b5.add(Box.createHorizontalStrut(10));
		b5.add(btnThoat);

		JLabel mota = new JLabel("Nếu bạn muốn đăng nhập hãy chọn:  ");
		b6.add(mota);
		b6.add(btnDangNhap);

		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b4);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b5);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b6);
		pnCenter.add(b);

		this.add(pnContent);
		btnDangNhap.addActionListener(this);
		btnDoiMK.addActionListener(this);
		btnThoat.addActionListener(this);

	}

	public static void main(String[] args) throws Exception {
		new DoiMatKhau_UI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			try {
				new DangNhap_UI().setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnDoiMK)) {
			if (txtMKMoi.getPassword().equals("") || txtMK.getPassword().equals("") || txtMKMoi.getPassword().equals("")
					|| txtXacNhanMK.getPassword().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn chưa nhập đủ thông tin");
			} else {
				char[] pwd = txtMKMoi.getPassword();
				char[] xacNhanPWD = txtXacNhanMK.getPassword();
				String pwdd = new String(pwd);
				String xacNhanPWDD = new String(xacNhanPWD);
				if (pwdd.equalsIgnoreCase(xacNhanPWDD)) {
					ktraTaiKhoan();
				} else {
					JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp");
					txtMKMoi.requestFocus();
					txtXacNhanMK.setText("");
				}
			}
		} else if (o.equals(btnThoat)) {
			dispose();
		}
	}
//Kiểm tra tài khoản có hợp lệ hay không
	public void ktraTaiKhoan() {
		ConnectDB.getInstance();
		PreparedStatement stmt = null;
		Connection con = ConnectDB.getConnection();
		try (Statement statement = con.createStatement()) {
			String user = txtTaiKhoan.getText();
			char[] pass = txtMK.getPassword();
			if (user.matches("QL\\d+")) { //Kiểm tra xem có đúng nhập liệu QL hay không
				String sql = "SELECT * FROM TaiKhoanQuanLy WHERE taiKhoan = ? AND matKhau = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				String pwd = new String(pass);
				stmt.setString(2, pwd);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					thucHienDoiMK();
				} else {
					JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
					txtMK.setText("");
					txtMK.requestFocus();
				}
				rs.close();
				stmt.close();
			}
			else if (user.matches("NV\\d+")){ //Kiểm tra xem có đúng nhập liệu nhân viên hay không
				String sql = "SELECT * FROM TaiKhoanNhanVien WHERE taiKhoan = ? AND matKhau = ?"; //Phương thức SELECT để chọn
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				String pwd = new String(pass);
				stmt.setString(2, pwd);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					thucHienDoiMK();
				} else {
					JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
					txtMK.setText("");
					txtMK.requestFocus();
				}
				rs.close();
				stmt.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void thucHienDoiMK() {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null; //khai báo để chuẩn bị truy vấn tới các câu lệnh SQL
		String user = txtTaiKhoan.getText();
		if (user.matches("QL\\d+")) {
			String sql = "UPDATE TaiKhoanQuanLy SET matKhau = ? WHERE taiKhoan = ?"; //Phương thức update để cập nhật
			try {
				char[] pass = txtMKMoi.getPassword();
				String pwd = new String(pass);
				stmt = con.prepareStatement(sql);
				stmt.setString(1, pwd);
				stmt.setString(2, user);
				stmt.executeUpdate();
				stmt.close();
				JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			String sql = "UPDATE TaiKhoanNhanVien SET matKhau = ? WHERE taiKhoan = ?";
			try {
				char[] pass = txtMKMoi.getPassword();
				String pwd = new String(pass);
				stmt = con.prepareStatement(sql);
				stmt.setString(1, pwd);
				stmt.setString(2, user);
				stmt.executeUpdate();
				stmt.close();
				JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
