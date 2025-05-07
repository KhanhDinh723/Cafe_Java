package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import dao.DanhMuc_DAO;
import dao.SanPham_DAO;
import entity.ChiTietSanPham;
import entity.DanhMuc;
import entity.NhanVien;
import entity.SanPham;

public class QuanLySanPham_GUI extends JPanel implements ActionListener, MouseListener, DocumentListener {
	private static final long serialVersionUID = 1L;
	private JTextField txtChucNang;
	private JTextField txtTenTP;
	private JTextField txtMaTP;
	private DefaultTableModel modelSanPham;
	private JTable tableSanPham;
	private JTextField txtMaDM;
	private JTextField txtTenNCC;
	private JTextField txtDonGiaBan;
	private JTextField txtKichsp;
	private JComboBox<String> txtDanhMuc;
	private JComboBox<String> txtTenTH;
	private JTextField txtMaTH;
	private JButton btnTaiHinh;
	private JButton btnNhapSanPham;
	private JButton btnXoaSanPham;
	private JTextField txtTimKiemTheoMa;
	private JButton btnSua;
	private SanPham_DAO sp_dao;
	private DanhMuc_DAO ncc_dao;
	private String imagePath = "Anh/default.png";
	private ImageIcon selectedImageIcon;
	private JLabel imgLabel;
	private JButton btnThemDanhMuc;
	private JButton btnXemChiTiet;
	private JButton btnSuaNCC;
	private int SPCounter;
	private int TPCounter;
	private int DTCounter;
	private JButton btnRefresh;

	public QuanLySanPham_GUI() {
	    ncc_dao = new DanhMuc_DAO();
	    setLayout(new BorderLayout());

	    // Tạo các thành phần giao diện
	    Box b = Box.createVerticalBox();
	    b.add(QuanLysp());
	    b.add(QuanLyNhaCungCap());
	    b.add(ChucNang());
	    b.add(Box.createVerticalStrut(10));
	    add(b, BorderLayout.NORTH);

	    // Tạo bảng sản phẩm
	    String[] colHeader = { "Mã Sản phẩm", "Tên Sản phẩm", "Danh mục", "Kích thước", "Giá bán"};
	    modelSanPham = new DefaultTableModel(colHeader, 0);
	    tableSanPham = new JTable(modelSanPham);
	    add(new JScrollPane(tableSanPham), BorderLayout.CENTER);

	    // Căn chỉnh các cột cho bảng
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã Sản phẩm
	    tableSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Danh mục

	    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
	    tableSanPham.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Giá bán

	    // Thêm phần giao diện cập nhật ở phía dưới
	    add(Capnhat(), BorderLayout.SOUTH);

	    // Đọc dữ liệu từ cơ sở dữ liệu vào bảng
	    docDuLieuDataBaseVaoTable();
	    addSK();

	    // Thiết lập bộ sắp xếp cho bảng
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelSanPham);
	    tableSanPham.setRowSorter(sorter);

	    // Thiết lập comparator cho các cột
	    Comparator<String> stringComparator = Comparator.naturalOrder();
	    Comparator<Integer> integerComparator = Comparator.naturalOrder();
	    sorter.setComparator(0, stringComparator); // Mã Sản phẩm
	    sorter.setComparator(1, stringComparator); // Tên Sản phẩm
	}

	public JPanel Capnhat() {
		JPanel panel = new JPanel();

		btnNhapSanPham = new JButton("Nhập sản phẩm ");
		btnXoaSanPham = new JButton("Xóa sản phẩm");
		btnSua = new JButton("Sửa thông tin sản phẩm");
		btnTaiHinh = new JButton("Tải hình");
		btnRefresh = new JButton("Refresh");
		btnTaiHinh.addActionListener(this);
		panel.add(btnTaiHinh);
		panel.add(btnNhapSanPham);
		panel.add(btnXoaSanPham);
		panel.add(btnSua);
		panel.add(btnRefresh);

		return panel;
	}

	public JPanel ChucNang() {
		JPanel panel = new JPanel(new BorderLayout());

		// Tim theo mã
		txtTimKiemTheoMa = new JTextField(20);
		JLabel iconLabel = new JLabel(createResizedIcon("icons/search.png", 20, 20));

		Box b = Box.createHorizontalBox();
//		b.add(Box.createHorizontalStrut(50));
		b.add(txtTimKiemTheoMa);
		b.add(iconLabel);
//		b.add(Box.createHorizontalStrut(50));
//		b.add(Box.createHorizontalStrut(20));
		panel.add(b);

		return panel;
	}

	public JPanel QuanLysp() {
		sp_dao = new SanPham_DAO();
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Thông tin Sản phẩm"));
		imgLabel = new JLabel(createResizedIcon("Anh/default.png", 140, 140));
		imgLabel.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
		JLabel lbMaTH = new JLabel("Mã sản phẩm: ");
		JLabel lbTenTH = new JLabel("Tên sản phẩm: ");
		JLabel lbDanhMuc = new JLabel("Danh mục: ");
		JLabel lbKichsp = new JLabel("Kích thước: ");
		JLabel lbDonGiaBan = new JLabel("Đơn giá bán: ");

		txtMaTH = new JTextField(20);
		txtMaTH.setEditable(false);
		txtTenTH = new JComboBox<String>();
		txtTenTH.setEditable(true);
		List<String> danhSachTensp = sp_dao.getallTenSanPham();
		for (String tensp : danhSachTensp) {
			txtTenTH.addItem(tensp);
		}
		txtDanhMuc = new JComboBox<String>();
		txtDanhMuc.setEditable(true);
		List<String> danhSachTenNhaCC = ncc_dao.getallma();
		for (String ten : danhSachTenNhaCC) {
			txtDanhMuc.addItem(ten);
		}
		txtKichsp = new JTextField(20);
		txtDonGiaBan = new JTextField(20);

		Box b = Box.createHorizontalBox();
		Box br = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		b1.add(lbMaTH);
		b1.add(txtMaTH);

		Box b2 = Box.createHorizontalBox();
		b2.add(lbDanhMuc);
		b2.add(txtDanhMuc);

		Box b5 = Box.createHorizontalBox();
		b5.add(lbDonGiaBan);
		b5.add(txtDonGiaBan);

		br.add(b1);
		br.add(b2);
		br.add(b5);

		Box br0 = Box.createVerticalBox();
		Box br1 = Box.createHorizontalBox();
		br1.add(lbTenTH);
		br1.add(txtTenTH);

		Box br2 = Box.createHorizontalBox();
		br2.add(lbKichsp);
		br2.add(txtKichsp);

		br0.add(br1);
		br0.add(br2);

		b.add(Box.createHorizontalStrut(20));
		b.add(imgLabel);
		b.add(Box.createHorizontalStrut(20));
		b.add(br);
		b.add(Box.createHorizontalStrut(20));
		b.add(br0);

		panel.add(b);
		return panel;
	}

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public JPanel QuanLyThanhPhan() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel lbMaTP = new JLabel("Mã thành phần: ");
		JLabel lbTenTP = new JLabel("Tên thành phần: ");
		JLabel lbChucNang = new JLabel("Mô tả chức năng: ");

		txtMaTP = new JTextField(20);
		txtTenTP = new JTextField(100);
		txtChucNang = new JTextField(200);

		Box b0, b, b1, b2, b3, b4;
		b0 = Box.createVerticalBox();
		b = Box.createVerticalBox();
		b.setBorder(BorderFactory.createTitledBorder("Thành phần: "));
		b1 = Box.createHorizontalBox();
		b1.add(lbMaTP);
		b1.add(txtMaTP);

		b2 = Box.createHorizontalBox();
		b2.add(lbTenTP);
		b2.add(txtTenTP);

		b3 = Box.createHorizontalBox();
		b3.add(lbChucNang);
		b3.add(txtChucNang);

		b.add(b1);
		b.add(b2);
		b.add(b3);

		b0.add(b);
//		add(b0, BorderLayout.NORTH);

		lbMaTP.setPreferredSize(lbChucNang.getPreferredSize());
		lbTenTP.setPreferredSize(lbChucNang.getPreferredSize());
		panel.add(b0);

		return panel;
	}

	public JPanel QuanLyNhaCungCap() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel lbMaDM = new JLabel("Mã danh mục: ");
		JLabel lbTenNCC = new JLabel("Tên danh mục: ");

		txtMaDM = new JTextField(20);
		txtTenNCC = new JTextField(20);

		Box b0, b, b1, b2, b3, b4, b5, b6;
		b0 = Box.createHorizontalBox();
		b = Box.createVerticalBox();
		b.setBorder(BorderFactory.createTitledBorder("Danh mục: "));
		b1 = Box.createHorizontalBox();
		b1.add(lbMaDM);
		b1.add(txtMaDM);

		b2 = Box.createHorizontalBox();
		b2.add(lbTenNCC);
		b2.add(txtTenNCC);

		b.add(b1);
		b.add(b2);

		b0.add(b);
		b0.add(Box.createHorizontalStrut(10));

		Box b7 = Box.createVerticalBox();
		btnThemDanhMuc = new JButton("Thêm danh mục");
		btnXemChiTiet = new JButton("  Xem chi tiết  ");
		btnSuaNCC = new JButton("        Sửa         ");
		b7.add(btnThemDanhMuc);
		b7.add(Box.createVerticalStrut(10));
		b7.add(btnXemChiTiet);
		b7.add(Box.createVerticalStrut(10));
		b7.add(btnSuaNCC);

		b0.add(b7);
		b0.add(Box.createHorizontalStrut(10));

		lbMaDM.setPreferredSize(lbTenNCC.getPreferredSize());
		panel.add(b0);

		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableSanPham.getSelectedRow();
		if (row >= 0) {
			// Lấy thông tin từ hàng đã chọn và hiển thị lên các component
			txtMaTH.setText(modelSanPham.getValueAt(row, 0).toString());
			txtTenTH.setSelectedItem(modelSanPham.getValueAt(row, 1).toString());
			txtDanhMuc.setSelectedItem(modelSanPham.getValueAt(row, 2).toString());
			txtKichsp.setText(modelSanPham.getValueAt(row, 3).toString());
			txtDonGiaBan.setText(modelSanPham.getValueAt(row, 4).toString());
			if (sp_dao.getanhSanPham(modelSanPham.getValueAt(row, 0).toString()) != null) {
				selectedImageIcon = new ImageIcon(sp_dao.getanhSanPham(modelSanPham.getValueAt(row, 0).toString()));
				Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
				imgLabel.setIcon(new ImageIcon(image));
				revalidate();
				repaint();
			} else {
				selectedImageIcon = new ImageIcon("Anh/default.png");
				Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
				imgLabel.setIcon(new ImageIcon(image));
				revalidate();
				repaint();
			}
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
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			modelSanPham.setRowCount(0);
			docDuLieuDataBaseVaoTable();
		}
		
		// TẢI HÌNH TỪ THƯ MỤC. DÙNG JFILECHOOSER 
		if (o.equals(btnTaiHinh)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				imagePath = fileChooser.getSelectedFile().getAbsolutePath();
				selectedImageIcon = new ImageIcon(imagePath);
				Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);

				// Hiển thị hình ảnh trên imgLabel
				imgLabel.setIcon(new ImageIcon(image));

				// Yêu cầu vẽ lại giao diện để hiển thị hình ảnh mới
				revalidate();
				repaint();
			}
		}
		if (o.equals(btnXemChiTiet)) {
			new QuanLyDanhMuc_GUI(this).setVisible(true);
		}
		//Thêm danh mục mới
		if (o.equals(btnThemDanhMuc)) {
			if (checkValid()) {
				ncc_dao.create(taoNhaCC());
				txtDanhMuc.removeAllItems();
				List<String> danhSachTenNhaCC = ncc_dao.getallma();
				for (String ten : danhSachTenNhaCC) {
					txtDanhMuc.addItem(ten);
				}
				JOptionPane.showMessageDialog(this, "Thêm thành công 1 Danh mục");
			}
		}
		if (o.equals(btnSuaNCC)) {
			if (checkValid()) {
				suaThongTinNCC();
				new QuanLyDanhMuc_GUI(this).dispose();
				new QuanLyDanhMuc_GUI(this).setVisible(true);
				txtDanhMuc.removeAllItems();
				List<String> danhSachTenNhaCC = ncc_dao.getallma();
				for (String ten : danhSachTenNhaCC) {
					txtDanhMuc.addItem(ten);
				}
				JOptionPane.showMessageDialog(this, "Sửa thành công 1 danh mục");
			}

		}
		if (o.equals(btnNhapSanPham)) {

			if (checkValidInput()) {
				SanPham th = taosp();
				sp_dao.addSanPham(taosp());
				JOptionPane.showMessageDialog(this, "Thêm thành công Sản phẩm");
				modelSanPham.setRowCount(0);
				docDuLieuDataBaseVaoTable();
			}
		}
		if (o.equals(btnXoaSanPham)) {
			int row = tableSanPham.getSelectedRow();
			String ma = modelSanPham.getValueAt(row, 0).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này") == yes) {
				sp_dao.deleteSPc(ma);
				modelSanPham.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
			}
		}
		if (o.equals(btnSua)) {
			int selectedRow = tableSanPham.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một d để sửa.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (checkValidInput()) {
				String masp = txtMaTH.getText();
				String tensp = txtTenTH.getSelectedItem().toString();
				String hinhsp = imagePath; // assuming imagePath is a class variable containing the image path
				DanhMuc danhMuc = new DanhMuc(getSubstringInParentheses(txtDanhMuc.getSelectedItem().toString()));
				String kichsp = txtKichsp.getText();
				double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());

				// Create and return a new sp instance
				SanPham th = new SanPham(masp, tensp, hinhsp, danhMuc, kichsp, donGiaBan);
				boolean success = sp_dao.updateSP(th);
				if (success) {
					// Update the table model
					modelSanPham.setRowCount(0);
					docDuLieuDataBaseVaoTable();

					JOptionPane.showMessageDialog(this, "Sản phẩm đã được cập nhật thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm không thành công.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}


	private void suaThongTinNCC() {
		if (txtMaDM.getText().length() != 0) {
			String maDM = txtMaDM.getText();
			String tenNCC = txtTenNCC.getText();

			DanhMuc ncc = new DanhMuc(maDM, tenNCC);

			// Gọi hàm cập nhật thông tin Danh mục trong lớp DAO
			boolean capNhatThanhCong = ncc_dao.capNhat(ncc);
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần sửa");
		}
	}

	public void updateNCCInfo(String maDM, String tenNCC) {
		// Update text fields with the received information
		txtMaDM.setText(maDM);
		txtTenNCC.setText(tenNCC);
	}

	public void passNCCInfo(String maDM, String tenNCC) {
		// Update text fields with the received information
		txtMaDM.setText(maDM);
		txtTenNCC.setText(tenNCC);
	}

	public void docDuLieuDataBaseVaoTable() {
		List<SanPham> list = sp_dao.getallSanPham();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (SanPham sp : list) {
			String str = "";
			modelSanPham.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(),
					ncc_dao.gettheoma(sp.getDanhMuc().getMaDM()), sp.getKichThuoc(),
					currencyFormatter.format(sp.getDonGiaBan()) });

		}
	}

	public void docDuLieuDataBaseVaoTablebyName(String ten) {
		List<SanPham> list = sp_dao.getSanPhambyTen(ten);
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (SanPham sp : list) {
			String str = "";
			modelSanPham.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(),
					ncc_dao.gettheoma(sp.getDanhMuc().getMaDM()), sp.getKichThuoc(),
					currencyFormatter.format(sp.getDonGiaBan()) });
		}
	}

	public DanhMuc taoNhaCC() {
		String ma = txtMaDM.getText();
		String ten = txtTenNCC.getText();
		return new DanhMuc(ma, ten);
	}

	private boolean checkValidInput() {
		// Kiểm tra cú pháp và tính hợp lệ của các trường dữ liệu
		if (txtTenTH.getSelectedItem().toString().isEmpty() || txtKichsp.getText().isEmpty()
				|| txtDonGiaBan.getText().isEmpty() || txtDanhMuc.getSelectedItem().toString().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
			return false;
		}

		// Kiểm tra tên Sản phẩm chỉ được chứa chữ cái, số và dấu cách
		if (!txtTenTH.getSelectedItem().toString().matches("[a-zA-Z0-9\\s]+")) {
			JOptionPane.showMessageDialog(this, "Tên Sản phẩm chỉ được chứa chữ cái, số và dấu cách");
			return false;
		}

		// Kiểm tra Danh mục chỉ được chứa chữ cái, số và dấu cách
		if (!txtDanhMuc.getSelectedItem().toString().matches("[a-zA-Z0-9\\s\\(\\)]+")) {
			JOptionPane.showMessageDialog(this, "Danh mục chỉ được chứa chữ cái, số và dấu cách");
			return false;
		}

		// Kiểm tra số lượng nhập, đơn giá nhập và đơn giá bán phải là số dương
		try {
			double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());

			if (donGiaBan <= 0) {
				JOptionPane.showMessageDialog(this, "Đơn giá bán phải là số dương");
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Đơn giá bán phải là số");
			return false;
		}

		// Nếu tất cả điều kiện đều hợp lệ, trả về true
		return true;
	}

	public void addSK() {
		tableSanPham.addMouseListener(this);
		btnXemChiTiet.addActionListener(this);
		btnThemDanhMuc.addActionListener(this);
		btnSuaNCC.addActionListener(this);
		btnNhapSanPham.addActionListener(this);
		btnXoaSanPham.addActionListener(this);
		btnSua.addActionListener(this);
		btnTaiHinh.addActionListener(this);
		txtTimKiemTheoMa.getDocument().addDocumentListener(this);
	}

	private String generatespID() {
		// Get the current maximum sp ID from the database
		int maxspID = sp_dao.getMaxSP();

		// Increment the counter based on the current maximum sp ID
		SPCounter = maxspID + 1;

		// Create the sp ID by concatenating "TH" with the sequential number
		return "SP" + String.format("%04d", SPCounter);
	}

	public SanPham taosp() {
		// Retrieve data from GUI components
		String masp = generatespID();
		String tensp = txtTenTH.getSelectedItem().toString();
		String hinhsp = imagePath; // assuming imagePath is a class variable containing the image path
		DanhMuc danhMuc = new DanhMuc(getSubstringInParentheses(txtDanhMuc.getSelectedItem().toString()));
		String kichsp = txtKichsp.getText();
		double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());

		// Create and return a new sp instance
		return new SanPham(masp, tensp, hinhsp, danhMuc, kichsp, donGiaBan);
	}

	public static String getSubstringInParentheses(String input) {
		String patternString = "\\(([^)]+)\\)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			// Group 1 contains the substring inside the parentheses
			return matcher.group(1);
		} else {
			// Return an empty string if no match is found
			return "";
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		delaySearch();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		delaySearch();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		delaySearch();
	}

	private void delaySearch() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Perform search or any other action here
				modelSanPham.setRowCount(0);
				docDuLieuDataBaseVaoTablebyName(txtTimKiemTheoMa.getText());
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}

	public boolean checkValid() {
		String maDM = txtMaDM.getText();
		String tenNCC = txtTenNCC.getText();

		if (maDM.isEmpty() || tenNCC.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
			return false;
		}

		if (!isValidTenNCC(tenNCC)) {
			JOptionPane.showMessageDialog(this, "Tên danh mục không được chứa ký tự đặc biệt và số");
			return false;
		}

		// Additional validation for other fields if needed

		return true;
	}

	private boolean isValidTenNCC(String tenNCC) {
		// Your validation logic for Tên Danh mục (Tên không chứa ký tự đặc biệt và
		// số)
		// Modify the regex pattern based on your specific requirements
		return tenNCC.matches("^[a-zA-Z0-9\\s]+$");
	}

}
