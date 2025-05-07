package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.DanhMuc_DAO;
import dao.NhanVien_DAO;
import dao.QuanLy_DAO;
import dao.SanPham_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.QuanLy;
import entity.TaiKhoanNhanVien;
import entity.TaiKhoanQuanLy;
import entity.SanPham;

public class QuanLyBanHang_GUI extends JPanel implements ActionListener, MouseListener, DocumentListener {

	private JTextField txtSoDienThoaiCanTim;
	private JButton btnTim;
	private JTextField txtHoTen;
	private JTextField txtHang;
	private JTextField txtDiem;
	private JTextField txtHoTenNhanVien;
	private JTextField txtMaHoaDon;
	private JTextField txtNgayLap;
	private JTextField txtSoDienThoaiHD;
	private DefaultTableModel modelHoaDon;
	private JTable tableHoaDon;
	private JButton btnXoatHoaDon;
	private JButton btnXoa;
	private JTextField txtMaSPCanTim;
	private Component btnTimSanPham;
	private JTextField txtSoLuongTon;
	private JTextField txtNoiSanXuat;
	private JTextField txtDanhMuc;
	private JTextField txtGiaSanPham;
	private JTextField txtTenSanPham;
	private JButton btnThem;
	private KhachHang_DAO qlkh_dao;
	private JLabel lbThongBao;
	private JButton btnThemKhachHang;
	private JTextField txtNgaySinh;
	private JTextField txtGioiTinh;
	private SanPham_DAO qlt_dao = new SanPham_DAO();;
	private JLabel lbAnh;
	private JTextField txtTB;
	private int Counter;
	private HoaDon_DAO qlhd_dao;
	private QuanLy_DAO ql_dao;
	private NhanVien_DAO qlnv_dao;
	private ChiTietHoaDon_DAO qlcthd_dao;
	private JTextField txtSoLuong;
	private String maHoaDon;
	private DanhMuc_DAO qlncc_dao;
	private JLabel lbTongTien;
	private JLabel lbTongVAT;
	private JLabel lbCoVAT;
	private QuanLyKhachHang_GUI qlkh;
	private NhanVien_DAO nv_dao;
	private NhanVien nv;
//Tạo hóa đơn bán hàng cho khách hàng và sản phẩm được bán 
	
	public QuanLyBanHang_GUI(TaiKhoanNhanVien cur) {
		qlncc_dao = new DanhMuc_DAO();
		ql_dao = new QuanLy_DAO();
		nv_dao = new NhanVien_DAO();
		nv = nv_dao.getNhanVienTheoMaNV(cur.getTaiKhoan());
		qlcthd_dao = new ChiTietHoaDon_DAO();
		this.qlhd_dao = new HoaDon_DAO();
		ql_dao = new QuanLy_DAO();
		setLayout(new BorderLayout());
		TaoHoaDon();
		btnXoatHoaDon.addActionListener(this);
		btnThem.addActionListener(this);
	}

	public void TaoHoaDon() {
		Box b;
		b = Box.createHorizontalBox();
		JPanel bLeft = new JPanel();
		bLeft.setLayout(new BorderLayout());
		Box pThongTinKhachHang = Box.createVerticalBox();
		pThongTinKhachHang.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

		Box b1, b2, b3, b4;
		JLabel lbSoDienThoaiCanTim = new JLabel("Số điện thoại cần tìm: ");
		txtSoDienThoaiCanTim = new JTextField(20);
		btnTim = new JButton(createResizedIcon("icons/search.png", 20, 20));
		JLabel lbHoTen = new JLabel("Họ tên: ");
		txtHoTen = new JTextField(20);
		JLabel lbHang = new JLabel("Hạng: ");
		txtHang = new JTextField(20);
		JLabel lbDiem = new JLabel("Điểm: ");
		txtDiem = new JTextField(20);
		JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
		txtNgaySinh = new JTextField();
		JLabel lbGioiTinh = new JLabel("Giới tính: ");
		txtGioiTinh = new JTextField();
		txtDiem = new JTextField(20);
		lbThongBao = new JLabel("* Thông báo");
		lbThongBao.setForeground(Color.RED);

		JPanel b0 = new JPanel();
		b0.setLayout(new GridLayout(6, 2));
		new JPanel();
		b1 = Box.createHorizontalBox();
		b1.add(lbSoDienThoaiCanTim);
		b1.add(txtSoDienThoaiCanTim);
		b1.add(btnTim);

		new JPanel();
		b2 = Box.createHorizontalBox();
		b2.add(lbHoTen);
		txtHoTen.setEditable(false);
		txtHoTen.setBorder(new EmptyBorder(0, 0, 0, 0));
		b2.add(txtHoTen);
		b2.add(lbNgaySinh);
		b2.add(txtNgaySinh);
		txtNgaySinh.setEditable(false);
		txtNgaySinh.setBorder(new EmptyBorder(0, 0, 0, 0));

		new JPanel();
		b3 = Box.createHorizontalBox();
		b3.add(lbHang);
		txtHang.setEditable(false);
		txtHang.setBorder(new EmptyBorder(0, 0, 0, 0));
		b3.add(txtHang);
		b3.add(lbGioiTinh);
		b3.add(txtGioiTinh);
		b3.add(Box.createVerticalStrut(20));
		txtGioiTinh.setEditable(false);
		txtGioiTinh.setBorder(new EmptyBorder(0, 0, 0, 0));

		new JPanel();
		b4 = Box.createHorizontalBox();
		b4.add(lbDiem);
		txtDiem.setEditable(false);
		txtDiem.setBorder(new EmptyBorder(0, 0, 0, 0));
		b4.add(txtDiem);

		Box b5 = Box.createHorizontalBox();
		b5.add(lbThongBao);

		Box b6 = Box.createHorizontalBox();
		btnThemKhachHang = new JButton("Thêm khách hàng");
		b6.add(Box.createHorizontalGlue());
		b6.add(btnThemKhachHang);
		b6.add(Box.createHorizontalGlue());

		b0.add(b1);
		b0.add(b2);
		b0.add(b3);
		b0.add(b4);
		b0.add(b5);
		pThongTinKhachHang.add(b0);

		lbHang.setPreferredSize(lbHoTen.getPreferredSize());
		lbDiem.setPreferredSize(lbHoTen.getPreferredSize());

		// KhachHang
		txtSoDienThoaiCanTim.getDocument().addDocumentListener(this);
		qlkh_dao = new KhachHang_DAO();

		JPanel pThongTinSanPham = new JPanel();
		pThongTinSanPham.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
		pThongTinSanPham.setLayout(new BorderLayout());

		Box bMTim = Box.createHorizontalBox();
		JLabel lbMaSanPham = new JLabel("Mã sản phẩm: ");
		bMTim.add(lbMaSanPham);
		txtMaSPCanTim = new JTextField(20);
		bMTim.add(txtMaSPCanTim);
		bMTim.add(btnTimSanPham = new JButton(createResizedIcon("icons/search.png", 20, 20)));

		Box bAnhSanPham = Box.createHorizontalBox();
		bAnhSanPham.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
		lbAnh = new JLabel(createResizedIcon("Anh/default.png", 240, 240));
		bAnhSanPham.add(lbAnh);

		Box bThongTinSanPham = Box.createVerticalBox();
		Box bTenThuoc, bNhaCungCap, bGiaThuoc, bNoiSanXuat, bSoLuongTon;
		JLabel lbTenThuoc = new JLabel("Tên sản phẩm:");
		txtTenSanPham = new JTextField(20);
		txtTenSanPham.setEditable(false);
		txtTenSanPham.setBorder(new EmptyBorder(0, 0, 0, 0));

		JLabel lbNhaCungCap = new JLabel("Danh mục: ");
		txtDanhMuc = new JTextField(20);
		txtDanhMuc.setEditable(false);
		txtDanhMuc.setBorder(new EmptyBorder(0, 0, 0, 0));

		JLabel lbGiaThuoc = new JLabel("Giá: ");
		txtGiaSanPham = new JTextField(20);
		txtGiaSanPham.setEditable(false);
		txtGiaSanPham.setBorder(new EmptyBorder(0, 0, 0, 0));

		txtTB = new JTextField(20);
		txtTB.setEditable(false);
		txtTB.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtTB.setForeground(Color.red);

		bTenThuoc = Box.createHorizontalBox();
		bTenThuoc.add(lbTenThuoc);
		bTenThuoc.add(txtTenSanPham);

		bNhaCungCap = Box.createHorizontalBox();
		bNhaCungCap.add(lbNhaCungCap);
		bNhaCungCap.add(txtDanhMuc);

		bGiaThuoc = Box.createHorizontalBox();
		bGiaThuoc.add(lbGiaThuoc);
		bGiaThuoc.add(txtGiaSanPham);

		Box bChonSoLuong = Box.createHorizontalBox();
		JLabel lbChonSoLuong = new JLabel("Chọn số lượng: ");
		txtSoLuong = new JTextField(5);
		bChonSoLuong.add(lbChonSoLuong);
		bChonSoLuong.add(txtSoLuong);
		bChonSoLuong.add(Box.createHorizontalStrut(100));

		Box thongBao = Box.createHorizontalBox();
		thongBao.add(txtTB);

		bThongTinSanPham.add(Box.createVerticalStrut(20));
		bThongTinSanPham.add(bAnhSanPham);
		bThongTinSanPham.add(Box.createVerticalStrut(20));
		bThongTinSanPham.add(bTenThuoc);
		bThongTinSanPham.add(bNhaCungCap);
		bThongTinSanPham.add(bGiaThuoc);
		bThongTinSanPham.add(bChonSoLuong);
		bThongTinSanPham.add(thongBao);
		JPanel pp = new JPanel();
		btnThem = new JButton("Thêm sản phẩm");
		pp.add(btnThem);

		Box total = Box.createHorizontalBox();
		total.add(bAnhSanPham);
		total.add(bThongTinSanPham);

		Box total1 = Box.createVerticalBox();
		total1.add(total);
		JPanel bot11 = new JPanel();
		bot11.setLayout(new BorderLayout());
		bot11.add(btnThem);
		total1.add(bot11);

		pThongTinSanPham.add(bMTim, BorderLayout.NORTH);
		pThongTinSanPham.add(total1);

		// San pham
		txtMaSPCanTim.getDocument().addDocumentListener(this);
		qlt_dao = new SanPham_DAO();

		bLeft.add(pThongTinKhachHang, BorderLayout.NORTH);
		bLeft.add(pThongTinSanPham);
		b.add(bLeft);

		Box bRight = Box.createVerticalBox();
		JPanel pThongTinHoaDon = new JPanel();
		pThongTinHoaDon.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
		pThongTinHoaDon.setLayout(new BorderLayout());
		Box bBig, bl, br;
		bBig = Box.createHorizontalBox();
		bl = Box.createVerticalBox();
		br = Box.createVerticalBox();
		Box bl1, bl2;
		bl1 = Box.createHorizontalBox();
		JLabel lbHoTenNhanVien = new JLabel("Họ tên nhân viên: ");
		txtHoTenNhanVien = new JTextField(20);
		txtHoTenNhanVien.setEditable(false);
		txtHoTenNhanVien.setText(nv.getTenNV());
		txtHoTenNhanVien.setBorder(new EmptyBorder(0, 0, 0, 0));
		bl1.add(lbHoTenNhanVien);
		bl1.add(txtHoTenNhanVien);

		bl2 = Box.createHorizontalBox();
		JLabel lbMaHoaDon = new JLabel("Mã hóa đơn: ");
		txtMaHoaDon = new JTextField(20);
		txtMaHoaDon.setEditable(false);
		txtMaHoaDon.setText(generateID());
		txtMaHoaDon.setBorder(new EmptyBorder(0, 0, 0, 0));
		bl2.add(lbMaHoaDon);
		bl2.add(txtMaHoaDon);

		Box br1, br2;
		br1 = Box.createHorizontalBox();
		JLabel lbNgayLap = new JLabel("Ngày lập hóa đơn: ");
		txtNgayLap = new JTextField(20);
		txtNgayLap.setEditable(false);
		txtNgayLap.setText(LocalDate.now().toString());
		txtNgayLap.setBorder(new EmptyBorder(0, 0, 0, 0));
		br1.add(lbNgayLap);
		br1.add(txtNgayLap);

		br2 = Box.createHorizontalBox();
		JLabel lbSoDienThoaiHD = new JLabel("Số điện thoại: ");
		txtSoDienThoaiHD = new JTextField(20);
		txtSoDienThoaiHD.setText(nv.getSoDienThoai());
		txtSoDienThoaiHD.setEditable(false);
		txtSoDienThoaiHD.setBorder(new EmptyBorder(0, 0, 0, 0));
		br2.add(lbSoDienThoaiHD);
		br2.add(txtSoDienThoaiHD);

		bl.add(bl1);
		bl.add(bl2);
		br.add(br1);
		br.add(br2);
		bBig.add(bl);
		bBig.add(br);

		String[] columnNames = { "Số thứ tự", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "VAT", "Thành tiền" };
		modelHoaDon = new DefaultTableModel(columnNames, 0);
		tableHoaDon = new JTable(modelHoaDon);
		JScrollPane scrollPane = new JScrollPane(tableHoaDon);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

		Box bBot, bBot1, bBot2, bBot3, bBot4, bBot5;
		bBot = Box.createVerticalBox();
		bBot1 = Box.createHorizontalBox();
		bBot2 = Box.createHorizontalBox();
		bBot3 = Box.createHorizontalBox();
		bBot4 = Box.createHorizontalBox();
		bBot5 = Box.createHorizontalBox();
		JLabel label1 = new JLabel("Tổng tiền:");
		lbTongTien = new JLabel();
		JLabel label2 = new JLabel("Tổng VAT:");
		lbTongVAT = new JLabel();
		JLabel label3 = new JLabel("Tổng tiền (có VAT):");
		lbCoVAT = new JLabel();

		JPanel pBtn = new JPanel();
		btnXoatHoaDon = new JButton("Xuất hóa đơn");
		btnXoa = new JButton("Xóa");
		pBtn.add(btnXoatHoaDon);
		pBtn.add(btnXoa);

		bBot1.setAlignmentX(LEFT_ALIGNMENT);
		bBot2.setAlignmentX(LEFT_ALIGNMENT);
		bBot3.setAlignmentX(LEFT_ALIGNMENT);
		bBot4.setAlignmentX(LEFT_ALIGNMENT);
		bBot5.setAlignmentX(LEFT_ALIGNMENT);

		bBot1.add(label1);
		bBot1.add(lbTongTien);
		bBot2.add(label2);
		bBot2.add(lbTongVAT);
		bBot3.add(label3);
		bBot3.add(lbCoVAT);
		bBot5.add(pBtn);

		bBot.add(bBot1);
		bBot.add(bBot2);
		bBot.add(bBot3);
		bBot.add(bBot4);
		bBot.add(bBot5);

		pThongTinHoaDon.add(bBig, BorderLayout.NORTH);
		pThongTinHoaDon.add(scrollPane);
		pThongTinHoaDon.add(bBot, BorderLayout.SOUTH);

		bRight.add(pThongTinHoaDon);
		b.add(bRight);
		add(b);
		btnXoa.addActionListener(this);

	}

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public static void main(String[] args) {
//        new  TrangChuQuanLy().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnXoatHoaDon)) {
			if (modelHoaDon.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Chưa có sản phẩm để xuất hóa đơn");
			} else {
				JOptionPane.showMessageDialog(this, "Xuất hóa đơn thành công!");
				Counter += 1;
				maHoaDon = "HD" + String.format("%04d", Counter);
				txtMaHoaDon.setText(generateID());
				modelHoaDon.setRowCount(0);
				lbTongTien.setText("");
				lbTongVAT.setText("");
				lbCoVAT.setText("");
			}

		}
		if (o.equals(btnXoa)) {
			int row = tableHoaDon.getSelectedRow();
			String ma = tableHoaDon.getValueAt(row, 1).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này") == yes) {
				SanPham th = qlt_dao.getSanPhambyMa(txtMaSPCanTim.getText());
//				int old = qlt_dao.getSoLuongTonByMaThuoc(th.getMaThuoc());
				int soLuong = Integer.parseInt(txtSoLuong.getText());
//				qlt_dao.updateSoLuongTon(th.getMaThuoc(), old + soLuong);
//				txtSoLuongTon.setText(String.valueOf(old + soLuong));
				NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
				double thanhtien = qlcthd_dao.getThanhTienBymaSanPhamAndMaHoaDon(ma, txtMaHoaDon.getText());
				double old1 = qlhd_dao.getTongTienByMaHD(txtMaHoaDon.getText());
				qlhd_dao.updateTongTien(maHoaDon, old1 - thanhtien);
				double tongtien = qlhd_dao.getTongTienByMaHD(maHoaDon);
				double tongvat = qlhd_dao.getHoaDonByMaHD(txtMaHoaDon.getText()).getThue()
						* qlhd_dao.getTongTienByMaHD(maHoaDon);
				double tongcovat = tongtien + tongvat;
				lbTongTien.setText(currencyFormatter.format(tongtien));
				lbTongVAT.setText(currencyFormatter.format(tongvat));
				lbCoVAT.setText(currencyFormatter.format(tongcovat));
				KhachHang kh = qlkh_dao.getKhachHangBySoDienThoai(txtSoDienThoaiCanTim.getText());
				int diemcu = kh.getDiem();
				qlkh_dao.capNhatDiemHang(txtSoDienThoaiCanTim.getText(), diemcu - (int) tongtien, kh.xepHang());
				txtDiem.setText(
						String.valueOf(qlkh_dao.getKhachHangBySoDienThoai(kh.getSoDienThoai()).getDiem()-tongtien));
				qlkh = new QuanLyKhachHang_GUI();
				qlcthd_dao.deleteChiTietHoaDonBymaSanPham(ma);
				modelHoaDon.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
			}
		}
		//thêm sản phẩm qua table
		if (o.equals(btnThem)) {
			SanPham th = qlt_dao.getSanPhambyMa(txtMaSPCanTim.getText());
//			
			int soLuong = Integer.parseInt(txtSoLuong.getText());
//			
				HoaDon hd = taoHD();
				qlhd_dao.themHoaDon(hd);
				qlcthd_dao.themChiTietHoaDon(new ChiTietHoaDon(th, hd, soLuong, th.getDonGiaBan()));
//				
				NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
				double thanhtien = qlcthd_dao.getThanhTienBymaSanPhamAndMaHoaDon(th.getMaSP(), hd.getMaHD());
				double old1 = qlhd_dao.getTongTienByMaHD(hd.getMaHD());
				qlhd_dao.updateTongTien(maHoaDon, old1 + thanhtien);
				modelHoaDon.addRow(new Object[] { tableHoaDon.getRowCount() + 1, th.getMaSP(), th.getTenSP(),
						txtSoLuong.getText(), hd.getThue(), currencyFormatter.format(thanhtien) });
				double tongtien = qlhd_dao.getTongTienByMaHD(maHoaDon);
				double tongvat = hd.getThue() * qlhd_dao.getTongTienByMaHD(maHoaDon);
				double tongcovat = tongtien + tongvat;
				lbTongTien.setText(currencyFormatter.format(tongtien));
				lbTongVAT.setText(currencyFormatter.format(tongvat));
				lbCoVAT.setText(currencyFormatter.format(tongcovat));
				KhachHang kh = qlkh_dao.getKhachHangBySoDienThoai(txtSoDienThoaiCanTim.getText());
				int diemcu = kh.getDiem();// cộng thêm điểm vào tài khoản để cập nhật thứ hạng
				kh.setDiem(diemcu + (int) tongtien);
				txtDiem.setText(String.valueOf(diemcu + (int) tongtien));
				qlkh_dao.capNhatDiemHang(txtSoDienThoaiCanTim.getText(), diemcu + (int) tongtien, kh.xepHang());
				qlkh = new QuanLyKhachHang_GUI();
			}
		}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaSPCanTim.getDocument()) {
			delaySanPham();
		} else {
			delayKhach();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaSPCanTim.getDocument()) {
			delaySanPham();
		} else {
			delayKhach();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaSPCanTim.getDocument()) {
			delaySanPham();
		} else {
			delayKhach();
		}
	}
//cập nhật với mỗi mili giây để tìm kiếm
	private void delayKhach() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KhachHang kh = qlkh_dao.getKhachHangBySoDienThoai(txtSoDienThoaiCanTim.getText());
				if (kh != null) {
					txtHoTen.setText(kh.getTenKH());
					txtHang.setText(kh.getHang());
					txtDiem.setText(String.valueOf(kh.getDiem()));
					txtNgaySinh.setText(kh.getNgaySinh().toString());
					txtGioiTinh.setText(kh.getGioiTinh() ? "Nam          " : "Nữ          ");
					lbThongBao.setText("Đã tìm thấy khách hàng với số điện thoại này");
				} else {
					txtHoTen.setText("");
					txtHang.setText("");
					txtDiem.setText("");
					txtNgaySinh.setText("");
					txtGioiTinh.setText("");
					lbThongBao.setText("Không tìm thấy khách hàng với số điện thoại này");
				}
				if (txtSoDienThoaiCanTim.getText().length() == 0) {
					lbThongBao.setText("* Thông báo");
				}
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}
//Show
	private void delaySanPham() {
		Timer timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SanPham th = qlt_dao.getSanPhambyMa(txtMaSPCanTim.getText().trim());
				if (th != null) {
					if (th.getHinhSP() != null) {
						lbAnh.setIcon(createResizedIcon(th.getHinhSP(), 240, 240)); //Lấy từ DAO qua ( lập hóa đơn )
					} else {
						lbAnh.setIcon(createResizedIcon("Anh/default.png", 240, 240));
					}
					txtTenSanPham.setText(th.getTenSP());
					qlncc_dao = new DanhMuc_DAO();
					txtDanhMuc.setText(qlncc_dao.gettheoma(th.getDanhMuc().getMaDM()));
					NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
					txtGiaSanPham.setText(currencyFormatter.format(th.getDonGiaBan()));
					txtTB.setText("Đã tìm thấy sản phẩm");
				} else { 
					lbAnh.setIcon(createResizedIcon("Anh/default.png", 240, 240));
					txtTenSanPham.setText("");
					txtDanhMuc.setText("");
					txtGiaSanPham.setText("");
					txtTB.setText("Không tìm thấy sản phẩm!");
				}
				if (txtMaSPCanTim.getText().length() == 0) {
					txtTB.setText("");
				}
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}

	private String generateID() {
		if (maHoaDon == null) {
			// Increment the counter for each new invoice
			Counter = this.qlhd_dao.demSoLuongHoaDon() + 1;

			// Create the invoice ID by concatenating "HD" with the sequential number
			maHoaDon = "HD" + String.format("%04d", Counter);
		}
		return maHoaDon;
	}

	public HoaDon taoHD() {
		String ma = generateID(); // Use the existing or generate a new invoice ID
		KhachHang kh = null;
		if (txtSoDienThoaiCanTim.getText().length() != 0)
			kh = qlkh_dao.getKhachHangBySoDienThoai(txtSoDienThoaiCanTim.getText());
		else
			kh = new KhachHang("0");

		qlnv_dao = new NhanVien_DAO();
		return new HoaDon(ma, nv, kh, LocalDate.now(), 0.1, 0);
	}
}
