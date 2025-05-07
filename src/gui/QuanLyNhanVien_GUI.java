package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import com.toedter.calendar.JDateChooser;

import dao.NhanVien_DAO;
import dao.TaiKhoanNhanVien_DAO;
import entity.NhanVien;
import entity.TaiKhoanNhanVien;

public class QuanLyNhanVien_GUI extends JPanel implements ActionListener, MouseListener {
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JCheckBox chkgioiTinh;
	private JTextField txtSoDienThoai;
	private JDateChooser NgaySinh;
	private JTextField txtLuong;
	private DefaultTableModel modelNhanVien;
	private JTable tableNhanVien;
	private JButton btnThemNhanVien;
	private JButton btnXoaNhanVien;
	private JButton btnSuaNhanVien;
	private JButton btnXoaRong;
	private JTextField txtTimNhanVien;
	private JButton btnTimNhanVien;
	private JButton btnReset;
	private NhanVien_DAO nv_dao;
	private TaiKhoanNhanVien_DAO tk_dao;
	private int employeeCounter;
	private JButton btnXemTaiKhoan;

	public QuanLyNhanVien_GUI() {
		setLayout(new BorderLayout());
		nv_dao = new NhanVien_DAO();
		tk_dao = new TaiKhoanNhanVien_DAO();
		createNhanVienComponents();
		btnThemNhanVien.addActionListener(this);
		btnXoaNhanVien.addActionListener(this);
		tableNhanVien.addMouseListener(this);
		btnTimNhanVien.addActionListener(this);
		btnReset.addActionListener(this);
		btnXoaRong.addActionListener(this);
		btnSuaNhanVien.addActionListener(this);
		btnXemTaiKhoan.addActionListener(this);
	}

	private void createNhanVienComponents() {

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		JLabel lbMaNV = new JLabel("Mã nhân viên: ");
		txtMaNV = new JTextField(20);
		txtMaNV.setEditable(false);
		JLabel lbTenNV = new JLabel("Tên nhân viên: ");
		txtTenNV = new JTextField(100);
		JLabel lbGioiTinh = new JLabel("Giới tính: ");
		chkgioiTinh = new JCheckBox("Nam");
		JLabel lbSoDienThoai = new JLabel("Số điện thoại: ");
		txtSoDienThoai = new JTextField(10);
		JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
		NgaySinh = new JDateChooser();
		JLabel lbLuong = new JLabel("Lương: ");
		txtLuong = new JTextField(10);
		Box b, b1, b2, b3;
		b = Box.createVerticalBox();
		// Dong 1
		b1 = Box.createHorizontalBox();
		b1.add(lbMaNV);
		b1.add(txtMaNV);
		// Dong 2
		b2 = Box.createHorizontalBox();
		b2.add(lbTenNV);
		b2.add(txtTenNV);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lbGioiTinh);
		b2.add(chkgioiTinh);
		b2.add(Box.createHorizontalStrut(20));
		// Dong 3
		Box b31 = Box.createHorizontalBox();
		b31.add(lbSoDienThoai);
		b31.add(txtSoDienThoai);
		// Dong 4
		b3 = Box.createHorizontalBox();
		b3.add(lbNgaySinh);
		b3.add(NgaySinh);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lbLuong);
		b3.add(txtLuong);
		// Dong 4
		JPanel b4 = new JPanel();
		b4.add(btnThemNhanVien = new JButton("Thêm"));
		b4.add(btnXoaNhanVien = new JButton("Xóa"));
		b4.add(btnSuaNhanVien = new JButton("Sửa"));
		b4.add(btnXoaRong = new JButton("Xóa rỗng"));
		b4.add(btnXemTaiKhoan = new JButton("Quản lý tài khoản"));

		b.add(Box.createVerticalStrut(10));
		b.add(b1);
		b.add(Box.createVerticalStrut(10));
		b.add(b2);
		b.add(Box.createVerticalStrut(10));
		b.add(b31);
		b.add(Box.createVerticalStrut(10));
		b.add(b3);
		b.add(Box.createVerticalStrut(10));
		b.add(b4);
		b.add(Box.createVerticalStrut(10));

		lbMaNV.setPreferredSize(lbTenNV.getPreferredSize());
		lbNgaySinh.setPreferredSize(lbTenNV.getPreferredSize());
		lbSoDienThoai.setPreferredSize(lbTenNV.getPreferredSize());

		String[] colHeader = { "Mã NV", "Tên", "Giới tính", "Số điện thoại", "Ngày sinh", "Lương" };
		modelNhanVien = new DefaultTableModel(colHeader, 0);
		tableNhanVien = new JTable(modelNhanVien);
		p.add(new JScrollPane(tableNhanVien), BorderLayout.CENTER);
		docDuLieuDataBaseVaoTable();
		DefaultTableCellRenderer Cen = new DefaultTableCellRenderer();
		Cen.setHorizontalAlignment(JLabel.CENTER); // Căn giữa dữ liệu
		// Thiết lập renderer cho cột "Lương"
		DefaultTableCellRenderer Right = new DefaultTableCellRenderer();
		Right.setHorizontalAlignment(JLabel.RIGHT); // Căn phải dữ liệu
		tableNhanVien.getColumnModel().getColumn(2).setCellRenderer(Cen);
		tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(Cen);
		tableNhanVien.getColumnModel().getColumn(3).setCellRenderer(Cen);
		tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(Right);

		JPanel pBot = new JPanel();
		JLabel lbTimNhanVien = new JLabel("Mã nhân viên: cần tìm: ");
		pBot.add(lbTimNhanVien);
		pBot.add(txtTimNhanVien = new JTextField(20));
		pBot.add(btnTimNhanVien = new JButton("Tìm"));
		pBot.add(btnReset = new JButton("Refresh"));

		p.add(b, BorderLayout.NORTH);
		p.add(pBot, BorderLayout.SOUTH);
		add(p, BorderLayout.CENTER);

	}

	private void clearInputFields() {
		txtMaNV.setText("");
		txtTenNV.setText("");
		chkgioiTinh.setSelected(false);
		txtSoDienThoai.setText("");
		NgaySinh.setDate(null);
		txtLuong.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnXoaRong)) {
			clearInputFields();
		}
		if (o.equals(btnThemNhanVien)) {
			String gt = "";
			if (checkValid()) {
				NhanVien nv = taoNhanVien();
				TaiKhoanNhanVien tk = new TaiKhoanNhanVien(nv.getMaNV(), nv.getSoDienThoai(), taoNhanVien());
				System.out.println(tk.toString());
				nv_dao.create(nv);
				NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				modelNhanVien.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), gt = nv.isGioiTinh() ? "Nam" : "Nữ",
						nv.getSoDienThoai(), nv.getNgaySinh().format(dateFormatter),
						currencyFormatter.format(nv.getLuongCoBan()) });
				tk_dao.create(tk);
				JOptionPane.showMessageDialog(this, "Thêm thành công 1 nhân viên");
			}
		}
		if (o.equals(btnXoaNhanVien)) {
			int row = tableNhanVien.getSelectedRow();
			String ma = tableNhanVien.getValueAt(row, 0).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này") == yes) {
				tk_dao.xoa(ma);
				nv_dao.xoa(ma);
				modelNhanVien.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
			}
		}
		if (o.equals(btnTimNhanVien)) {
			xoaTable();
			NhanVien nv = nv_dao.getNhanVienTheoMaNV(txtTimNhanVien.getText().trim());
			String gt1 = "";
			NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			modelNhanVien.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), gt1 = nv.isGioiTinh() ? "Nam" : "Nữ",
					nv.getSoDienThoai(), nv.getNgaySinh().format(dateFormatter),
					currencyFormatter.format(nv.getLuongCoBan()) });
		}
		if (o.equals(btnReset)) {
			xoaTable();
			docDuLieuDataBaseVaoTable();
		}
		if(o.equals(btnXemTaiKhoan)) {
			new QuanLyTaiKhoanNhanVien_GUI().setVisible(true);
		}
		if (o.equals(btnSuaNhanVien)) {
			// Check if a row is selected
			int selectedRow = tableNhanVien.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Validate input fields
			if (checkValid()) {
				// Create a new NhanVien object with updated information
				String ma = txtMaNV.getText();
				String ten = txtTenNV.getText();
				boolean gioiTinh = chkgioiTinh.isSelected();
				String sdt = txtSoDienThoai.getText();
				LocalDate ngaySinh = NgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				double luong = Double.parseDouble(txtLuong.getText());
				NhanVien updatedNhanVien = new NhanVien(ma, ten, gioiTinh, sdt, ngaySinh, luong);
				
				// Update the database
				boolean success = nv_dao.update(updatedNhanVien);

				if (success) {
					// Update the table model
					modelNhanVien.setValueAt(updatedNhanVien.getMaNV(), selectedRow, 0);
					modelNhanVien.setValueAt(updatedNhanVien.getTenNV(), selectedRow, 1);
					String gioiTinh1 = updatedNhanVien.isGioiTinh() ? "Nam" : "Nữ";
					modelNhanVien.setValueAt(gioiTinh1, selectedRow, 2);
					modelNhanVien.setValueAt(updatedNhanVien.getSoDienThoai(), selectedRow, 3);
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					modelNhanVien.setValueAt(updatedNhanVien.getNgaySinh().format(dateFormatter), selectedRow, 4);
					NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
					modelNhanVien.setValueAt(currencyFormatter.format(updatedNhanVien.getLuongCoBan()), selectedRow, 5);

					JOptionPane.showMessageDialog(this, "Nhân viên đã được cập nhật thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật nhân viên không thành công.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public static void main(String[] args) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableNhanVien.getSelectedRow();
		if (row >= 0 && row < tableNhanVien.getRowCount()) {
			txtMaNV.setText(modelNhanVien.getValueAt(row, 0).toString());
			txtTenNV.setText(modelNhanVien.getValueAt(row, 1).toString());
			chkgioiTinh.setSelected(modelNhanVien.getValueAt(row, 2) == "Nam" ? true : false);
			txtSoDienThoai.setText(modelNhanVien.getValueAt(row, 3).toString());
			String ngaySinhValue = modelNhanVien.getValueAt(row, 4).toString();
			// Chuyển đổi giá trị ngày sinh thành LocalDate
			LocalDate ngaySinhDate = LocalDate.parse(ngaySinhValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			// Đặt giá trị cho JDateChooser
			NgaySinh.setDate(java.sql.Date.valueOf(ngaySinhDate));
			txtLuong.setText(modelNhanVien.getValueAt(row, 5).toString());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void docDuLieuDataBaseVaoTable() {
		List<NhanVien> list = nv_dao.getalltbNhanVien();
		String gt = "";
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (NhanVien nv : list) {
			modelNhanVien.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), gt = nv.isGioiTinh() ? "Nam" : "Nữ",
					nv.getSoDienThoai(), nv.getNgaySinh().format(dateFormatter),
					currencyFormatter.format(nv.getLuongCoBan()) });
		}
	}

	private String generateEmployeeID() {
	    // Get the current maximum Employee ID from the database
	    int maxEmployeeID = nv_dao.getMaxEmployeeID();

	    // Increment the counter based on the current maximum Employee ID
	    employeeCounter = maxEmployeeID + 1;

	    // Create the Employee ID by concatenating "NV" with the sequential number
	    return "NV" + String.format("%04d", employeeCounter);
	}
	

	public NhanVien taoNhanVien() {
		String ma = generateEmployeeID();
		String ten = txtTenNV.getText();
		boolean gioiTinh = chkgioiTinh.isSelected();
		String sdt = txtSoDienThoai.getText();
		LocalDate ngaySinh = NgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		double luong = Double.parseDouble(txtLuong.getText());
		return new NhanVien(ma, ten, gioiTinh, sdt, ngaySinh, luong);
	}

	public void xoaTable() {
		DefaultTableModel dm = (DefaultTableModel) tableNhanVien.getModel();
		dm.getDataVector().removeAllElements();
	}

	public boolean checkValid() {
		String ma = txtMaNV.getText();
		String ten = txtTenNV.getText();
		String sdt = txtSoDienThoai.getText();
		LocalDate ngaySinhDate;
		double luong;
		// Check if the phone number is numeric and has a valid length
		try {
			Long.parseLong(sdt);
			if (sdt.length() != 10) {
				JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 chữ số.", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Số điện thoại phải là số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Check if the birthdate is not in the future and the person is at least 18
		// years old
		if (NgaySinh.getDate() != null) {
			ngaySinhDate = NgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			LocalDate minBirthdate = LocalDate.now().minusYears(18);
			if (ngaySinhDate.isAfter(LocalDate.now()) || ngaySinhDate.isAfter(minBirthdate)) {
				JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Nhân viên phải đủ 18 tuổi.", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			luong = Double.parseDouble(txtLuong.getText());
			if (luong <= 0) {
				JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Lương phải là số thực.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Additional checks can be added based on your specific requirements

		return true;
	}

}