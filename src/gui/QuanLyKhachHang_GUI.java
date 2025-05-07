package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuanLyKhachHang_GUI extends JPanel implements ActionListener, MouseListener {
	private JTextField txtSoDienThoai;
	private JTextField txtTen;
	private JDateChooser txtNgaySinh;
	private JCheckBox chkGioiTinh;
	private JTextField txtDiem;
	private JTextField txtHang;
	private JTable table;
	private JButton btnThem, btnXoa, btnSua;
	private JTextField txtTim;
	private Component tableNhanVien;
	private JTable tableKhachHang;
	private DefaultTableModel modelKhachHang;
	private KhachHang_DAO qlkh_dao;
	private JButton btnRefresh;
	private JButton btnXoaRong;

	public QuanLyKhachHang_GUI() {
		setLayout(new BorderLayout());
		qlkh_dao = new KhachHang_DAO();
		// Phần North
		JLabel lbSoDienThoai = new JLabel("Số điện thoại: ");
		JLabel lbTen = new JLabel("Tên: ");
		JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
		JLabel lbGioiTinh = new JLabel("Giới tính: ");
		JLabel lbDiem = new JLabel("Điểm: ");
		JLabel lbHang = new JLabel("Hạng: ");

		txtSoDienThoai = new JTextField(20);
		txtTen = new JTextField(100);
		txtNgaySinh = new JDateChooser();
		chkGioiTinh = new JCheckBox("Nam");
		txtDiem = new JTextField(20);
		txtHang = new JTextField(100);

		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		b1.add(lbSoDienThoai);
		b1.add(txtSoDienThoai);
		b1.add(Box.createHorizontalStrut(20));

		Box b2 = Box.createHorizontalBox();
		b2.add(lbTen);
		b2.add(txtTen);
		b2.add(Box.createHorizontalStrut(20));
		b2.add(lbGioiTinh);
		b2.add(chkGioiTinh);

		Box b4 = Box.createHorizontalBox();
		b4.add(lbNgaySinh);
		b4.add(txtNgaySinh);

		Box b3 = Box.createHorizontalBox();
		b3.add(lbDiem);
		b3.add(txtDiem);
		b3.add(Box.createHorizontalStrut(20));
		b3.add(lbHang);
		b3.add(txtHang);

		b.add(Box.createVerticalStrut(10));
		b.add(b1);
		b.add(Box.createVerticalStrut(10));
		b.add(b2);
		b.add(Box.createVerticalStrut(10));
		b.add(b3);
		b.add(Box.createVerticalStrut(10));
		b.add(b4);

		Box bform = Box.createVerticalBox();
		b.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
		bform.add(b);

		Box b5 = Box.createHorizontalBox();
		JLabel lbtimTheoMa = new JLabel("Tìm theo số điện thoại: ");
		txtTim = new JTextField(20);
		b5.add(Box.createHorizontalStrut(10));
		b5.add(lbtimTheoMa);
		b5.add(txtTim);
		JLabel lbLogo = new JLabel(createResizedIcon("icons/search.png", 20, 20));
		b5.add(lbLogo);
		b5.add(Box.createHorizontalStrut(1000));
		bform.add(Box.createVerticalStrut(10));
		bform.add(b5);
		bform.add(Box.createVerticalStrut(10));

		add(bform, BorderLayout.NORTH);

		lbTen.setPreferredSize(lbSoDienThoai.getPreferredSize());
		lbNgaySinh.setPreferredSize(lbSoDienThoai.getPreferredSize());
		lbDiem.setPreferredSize(lbSoDienThoai.getPreferredSize());

		// Phần Center - Bảng
		String[] columnNames = { "Số điện thoại", "Tên", "Ngày sinh", "Giới tính", "Điểm", "Hạng" };
		modelKhachHang = new DefaultTableModel(columnNames, 0);
		tableKhachHang = new JTable(modelKhachHang);
		add(new JScrollPane(tableKhachHang), BorderLayout.CENTER);
		docDuLieuDataBaseVaoTable();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableKhachHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);  // Cột 1
        tableKhachHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);  // Cột 3
        tableKhachHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);  // Cột 4
        tableKhachHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);  // Cột 5
        tableKhachHang.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);  // Cột 6
        
		// Phần South - Nút Thêm, Xóa, Sửa
		JPanel southPanel = new JPanel();
		btnThem = new JButton("Thêm");
		btnXoa = new JButton("Xóa");
		btnSua = new JButton("Sửa");
		btnRefresh = new JButton("Refresh");
		btnXoaRong = new JButton("Xóa rỗng");

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnRefresh.addActionListener(this);
		tableKhachHang.addMouseListener(this);
		btnXoaRong.addActionListener(this);

		southPanel.add(btnThem);
		southPanel.add(btnXoa);
		southPanel.add(btnSua);
		southPanel.add(btnRefresh);
		southPanel.add(btnXoaRong);

		add(southPanel, BorderLayout.SOUTH);
		
		txtDiem.setText("0");
		txtHang.setText("D");
	}

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableKhachHang.getSelectedRow();

		if (row != -1) { // Kiểm tra xem có dòng được chọn không
			// Lấy thông tin từ bảng
			String soDienThoai = modelKhachHang.getValueAt(row, 0).toString();
			String ten = modelKhachHang.getValueAt(row, 1).toString();
			String ngaySinh = modelKhachHang.getValueAt(row, 2).toString();
			String gioiTinh = modelKhachHang.getValueAt(row, 3).toString();
			String diem = modelKhachHang.getValueAt(row, 4).toString();
			String hang = modelKhachHang.getValueAt(row, 5).toString();

			// Đưa thông tin lên các component
			txtSoDienThoai.setText(soDienThoai);
			txtTen.setText(ten);

			// Chuyển đổi ngày sinh từ String sang LocalDate
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate ngaySinhDate = LocalDate.parse(ngaySinh, dateFormatter);
			txtNgaySinh.setDate(java.util.Date.from(ngaySinhDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

			chkGioiTinh.setSelected(gioiTinh.equals("Nam"));

			txtDiem.setText(diem);
			txtHang.setText(hang);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// Xử lý sự kiện khi nhấn các nút Thêm, Xóa, Sửa
		if (e.getSource().equals(btnThem)) {
			if (kiemTraDuLieuNhap()) {
				KhachHang khachHang = taoKhachHang();
				qlkh_dao.themKhachHang(khachHang);
				themKhachHangVaoBang(khachHang);
				JOptionPane.showMessageDialog(this, "Thêm thành công 1 khách hàng");
			}
		}
		if (e.getSource().equals(btnXoaRong)) {
			txtSoDienThoai.setText("");
			txtTen.setText("");
			txtNgaySinh.setDate(null);
			chkGioiTinh.setSelected(false);
			txtDiem.setText("");
			txtHang.setText("");
		}
		if (e.getSource() == btnXoa) {
			int row = tableKhachHang.getSelectedRow();
			String ma = tableKhachHang.getValueAt(row, 0).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này?") == yes) {
				qlkh_dao.xoaKhachHang(ma);
				modelKhachHang.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa thành công 1 khách hàng");
			}
		}
		if (e.getSource() == btnRefresh) {
			// Refresh action
			xoaTable(); // Clear the table
			docDuLieuDataBaseVaoTable(); // Load data from the database to the table
		}
		if (e.getSource() == btnSua) {
			int row = tableKhachHang.getSelectedRow();

			if (row != -1) {
				// Lấy thông tin từ các JTextField
				String soDienThoai = txtSoDienThoai.getText().trim();
				String ten = txtTen.getText().trim();
				LocalDate ngaySinh = txtNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				boolean gioiTinh = chkGioiTinh.isSelected();
				int diem = Integer.parseInt(txtDiem.getText().trim());
				String hang = txtHang.getText().trim();

				// Cập nhật thông tin vào cơ sở dữ liệu
				qlkh_dao.suaKhachHang(new KhachHang(ten, ngaySinh, gioiTinh, diem, soDienThoai, hang));

				// Cập nhật thông tin trong bảng
				modelKhachHang.setValueAt(soDienThoai, row, 0);
				modelKhachHang.setValueAt(ten, row, 1);
				modelKhachHang.setValueAt(ngaySinh.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), row, 2);
				modelKhachHang.setValueAt(gioiTinh ? "Nam" : "Nữ", row, 3);
				modelKhachHang.setValueAt(diem, row, 4);
				modelKhachHang.setValueAt(hang, row, 5);
				JOptionPane.showMessageDialog(this, "Sửa thành công!");
			}
		}
	}

	public void docDuLieuDataBaseVaoTable() {
		List<KhachHang> list = qlkh_dao.getall();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (KhachHang kh : list) {
			modelKhachHang
					.addRow(new Object[] { kh.getSoDienThoai(), kh.getTenKH(), dateFormatter.format(kh.getNgaySinh()),
							kh.getGioiTinh() ? "Nam" : "Nữ", kh.getDiem(), kh.getHang() });
		}
	}

	public KhachHang taoKhachHang() {
		LocalDate ngaySinh = txtNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return new KhachHang(txtTen.getText().trim(), ngaySinh, chkGioiTinh.isSelected(),
				Integer.parseInt(txtDiem.getText().trim()), txtSoDienThoai.getText().trim(), txtHang.getText().trim());
	}

	public void xoaTable() {
		DefaultTableModel dm = (DefaultTableModel) tableKhachHang.getModel();
		dm.getDataVector().removeAllElements();
	}

	private boolean kiemTraDuLieuNhap() {
		// Kiểm tra số điện thoại không được rỗng
		String soDienThoai = txtSoDienThoai.getText().trim();
		if (soDienThoai.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Kiểm tra tên chỉ chứa chữ và dấu cách
		String ten = txtTen.getText().trim();
		if (!ten.matches("^[a-zA-Z\\s]+$")) {
			JOptionPane.showMessageDialog(this, "Tên chỉ được chứa chữ và dấu cách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Kiểm tra ngày sinh không được để trống
		if (txtNgaySinh.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Kiểm tra ngày sinh phải trước ngày hiện tại
		LocalDate ngaySinh = txtNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ngayHienTai = LocalDate.now();
		if (ngaySinh.isAfter(ngayHienTai)) {
			JOptionPane.showMessageDialog(this, "Ngày sinh phải trước ngày hiện tại.", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Các kiểm tra khác có thể thêm ở đây

		// Nếu không có lỗi, trả về true
		return true;
	}

	private void themKhachHangVaoBang(KhachHang khachHang) {
		modelKhachHang.addRow(new Object[] { khachHang.getSoDienThoai(), khachHang.getTenKH(),
				khachHang.getNgaySinh().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
				khachHang.getGioiTinh() ? "Nam" : "Nữ", khachHang.getDiem(), khachHang.getHang() });
	}
}
