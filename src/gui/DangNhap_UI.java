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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connectDB.ConnectDB;
import dao.DangNhap_DAO;

public class DangNhap_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth;
	private JLabel lblError;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMK;
	private JButton btnDangNhap, btnThoat, btnDoiMatKhau;
	private JCheckBox chkQuanLy;
	private DangNhap_DAO dn;

	public DangNhap_UI() throws Exception {
		try {
			ConnectDB.getInstance().connect(); //Kết nối với database và ném lỗi
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createGui();
	}

	public void createGui() {
		setTitle("Đăng nhập");
		setSize(420, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		dangNhap();
	}

	public void dangNhap() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		Color mauNen = new Color(102, 205, 170);

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Đăng nhập tài khoản");
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
		Box bError = Box.createHorizontalBox();
		b1.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
		b2.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
		txtTaiKhoan = new JTextField(15);
		txtMK = new JPasswordField(15);
		b1.add(txtTaiKhoan);
		b2.add(txtMK);
		txtTaiKhoan.setColumns(30);
		lblError = new JLabel(" ");
		lblError.setForeground(Color.red);
		bError.add(lblError);
		Box bCheck = Box.createHorizontalBox();
		JLabel lbQuanLy = new JLabel("Quản lý: ");
		chkQuanLy = new JCheckBox();
		bCheck.add(Box.createHorizontalStrut(330));
		bCheck.add(lbQuanLy);bCheck.add(chkQuanLy);

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(mauNen);
		btnDangNhap.setForeground(Color.WHITE);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.WHITE);
		btnDoiMatKhau = new JButton("Đổi mật khẩu");
		btnDoiMatKhau.setBackground(mauNen);
		btnDoiMatKhau.setForeground(Color.WHITE);
		b3.add(btnDangNhap);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(btnThoat);

		JLabel mota = new JLabel("Nếu bạn muốn đổi mật khẩu thì vui lòng chọn:  ");
		b4.add(mota);
		b4.add(btnDoiMatKhau);

		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 1)));
		b.add(bCheck);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(bError);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b4);
		pnCenter.add(b);

		this.add(pnContent);
		btnDangNhap.addActionListener(this);
		btnDoiMatKhau.addActionListener(this);
		btnThoat.addActionListener(this);
		chkQuanLy.addActionListener(this);
		
		txtTaiKhoan.setText("QL0001");
		txtMK.setText("123");
	}

	public static void main(String[] args) throws Exception {
		new DangNhap_UI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { //xử lý nút đăng nhập
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			dn = new DangNhap_DAO(); //kết nối với class DAO
			dn.dNhap(txtTaiKhoan, txtMK, chkQuanLy, lblError);
		} else if (o.equals(btnDoiMatKhau)) {
			try {
				new DoiMatKhau_UI().setVisible(true); //tryy vấn tới màn hình đổi mật khẩu
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnThoat)) {
			dispose(); //đóng cửa sổ đăng nhập để đi đến sự kiện tiếp theo
		}
	}
}
