package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.HoaDon_DAO;
import entity.HoaDon;

public class QuanLyHoaDon_GUI extends JPanel implements MouseListener, ActionListener, DocumentListener {
	private DefaultTableModel modelHD;
	private JTable tableHD;
	private JDateChooser tuNgay;
	private JDateChooser denNgay;
	private JButton btnTimTheoNgay;
	private JTextField txtSoDienThoaiCanTim;
	private JButton btnTimTheoSoDienThoai;
	private JButton btnTimTheoMaHoaDon;
	private JTextField txtMaHoaDonCanTim;
	private HoaDon_DAO qlhd_dao;

	public QuanLyHoaDon_GUI() {
		qlhd_dao = new HoaDon_DAO();
		setLayout(new BorderLayout());
		String[] colHeader = { "Mã hóa đơn", "Mã nhân viên bán hàng", "Số điện thoại khách hàng", "Ngày mua", "Thuế",
				"Tổng tiền" };
		modelHD = new DefaultTableModel(colHeader, 0);
		tableHD = new JTable(modelHD);
		add(new JScrollPane(tableHD));
		taiDuLieu();
		Box total = Box.createVerticalBox();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableHD.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);  // Cột 1
        tableHD.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);  // Cột 3
        tableHD.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);  // Cột 4
        tableHD.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);  // Cột 5
        tableHD.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);  // Cột 6
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableHD.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);  // Cột 5
		
		Box p1 = Box.createHorizontalBox();
		JLabel lbSoDienThoaiCanTim = new JLabel("Số điện thoại cần tìm: ");
		txtSoDienThoaiCanTim = new JTextField(20);
		btnTimTheoSoDienThoai = new JButton(createResizedIcon("icons/search.png", 20, 20));
		p1.add(lbSoDienThoaiCanTim);
		p1.add(txtSoDienThoaiCanTim);
		p1.add(btnTimTheoSoDienThoai);

		Box p2 = Box.createHorizontalBox();
		JLabel lbMaHD = new JLabel("Mã hóa đơn cần tìm: ");
		txtMaHoaDonCanTim = new JTextField(20);
		btnTimTheoMaHoaDon = new JButton(createResizedIcon("icons/search.png", 20, 20));
		p2.add(lbMaHD);
		p2.add(txtMaHoaDonCanTim);
		p2.add(btnTimTheoMaHoaDon);

		Box bot = Box.createHorizontalBox();

		Box p = Box.createHorizontalBox();
		JLabel lbTuNgay = new JLabel("Từ ngày: ");
		tuNgay = new JDateChooser();
		JLabel lbDenNgay = new JLabel("Đến ngày: ");
		denNgay = new JDateChooser();
		p.add(Box.createHorizontalStrut(40));
		p.add(lbTuNgay);
		p.add(tuNgay);
		p.add(Box.createHorizontalStrut(20));
		p.add(lbDenNgay);
		p.add(denNgay);
		p.add(Box.createHorizontalStrut(10));
		btnTimTheoNgay = new JButton(createResizedIcon("icons/search.png", 20, 20));
		p.add(btnTimTheoNgay);
		p.add(Box.createHorizontalStrut(40));
		total.add(Box.createVerticalStrut(10));
		total.add(p);
		total.add(Box.createVerticalStrut(10));
		bot.add(p1);
		bot.add(Box.createHorizontalStrut(20));
		bot.add(p2);
		add(total, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);

		btnTimTheoNgay.addActionListener(this);
		txtSoDienThoaiCanTim.getDocument().addDocumentListener(this);
		txtMaHoaDonCanTim.getDocument().addDocumentListener(this);
	}

	public void taiDuLieu() {
		ArrayList<HoaDon> list = qlhd_dao.getAllHoaDon();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		for (HoaDon hoaDon : list) {
			modelHD.addRow(new Object[] { hoaDon.getMaHD(), hoaDon.getNv().getMaNV(), hoaDon.getKh().getSoDienThoai(),
					hoaDon.getNgayMua(), hoaDon.getThue(), currencyFormatter.format(hoaDon.getTongTien()) });
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnTimTheoNgay)) {
			modelHD.setRowCount(0);
			java.sql.Date fromDate = null;
			java.sql.Date toDate = null;
			// Convert JDateChooser to java.sql.Date
			if (tuNgay != null && tuNgay.getDate() != null) {
				fromDate = new java.sql.Date(tuNgay.getDate().getTime());
			}

			if (denNgay != null && denNgay.getDate() != null) {
				toDate = new java.sql.Date(denNgay.getDate().getTime());
			}
			ArrayList<HoaDon> list = qlhd_dao.getAllHoaDonByDateRange(fromDate, toDate);
			NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
			for (HoaDon hoaDon : list) {
				modelHD.addRow(new Object[] { hoaDon.getMaHD(), hoaDon.getNv().getMaNV(),
						hoaDon.getKh().getSoDienThoai(), hoaDon.getNgayMua(), hoaDon.getThue(),
						currencyFormatter.format(hoaDon.getTongTien()) });
			}
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

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaHoaDonCanTim.getDocument()) {
			delaySearchID();
		} else {
			delaySearchSDT();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaHoaDonCanTim.getDocument()) {
			delaySearchID();
		} else {
			delaySearchSDT();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == txtMaHoaDonCanTim.getDocument()) {
			delaySearchID();
		} else {
			delaySearchSDT();
		}
	}

	private void delaySearchID() {
		Timer timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<HoaDon> list = qlhd_dao.getHoaDonByMaHDLike(txtMaHoaDonCanTim.getText());
				if(list!=null) {
					modelHD.setRowCount(0);
					docDuLieuDataBaseVaoTablebySDT(list);
				}
				if (txtMaHoaDonCanTim.getText().length() == 0) {
					modelHD.setRowCount(0);
					taiDuLieu();
				}
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}

	public void docDuLieuDataBaseVaoTablebyID(ArrayList<HoaDon> list) {
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		for (HoaDon hoaDon : list) {
			modelHD.addRow(new Object[] { hoaDon.getMaHD(), hoaDon.getNv().getMaNV(), hoaDon.getKh().getSoDienThoai(),
					hoaDon.getNgayMua(), hoaDon.getThue(), currencyFormatter.format(hoaDon.getTongTien()) });
		}
	}

	private void delaySearchSDT() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<HoaDon> list = qlhd_dao.getHoaDonBySoDienThoai(txtSoDienThoaiCanTim.getText());
				if(list!=null) {
					modelHD.setRowCount(0);
					docDuLieuDataBaseVaoTablebySDT(list);
				}
				if (txtSoDienThoaiCanTim.getText().length() == 0) {
					modelHD.setRowCount(0);
					taiDuLieu();
				}
				
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}

	public void docDuLieuDataBaseVaoTablebySDT(ArrayList<HoaDon> list) {
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		for (HoaDon hoaDon : list) {
			modelHD.addRow(new Object[] { hoaDon.getMaHD(), hoaDon.getNv().getMaNV(), hoaDon.getKh().getSoDienThoai(),
					hoaDon.getNgayMua(), hoaDon.getThue(), currencyFormatter.format(hoaDon.getTongTien()) });
		}
	}
}
