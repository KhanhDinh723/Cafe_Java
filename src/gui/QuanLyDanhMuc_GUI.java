package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DanhMuc_DAO;
import entity.DanhMuc;
import entity.NhanVien;
import entity.TaiKhoanQuanLy;

public class QuanLyDanhMuc_GUI extends JFrame implements ActionListener, MouseListener { 
	private DefaultTableModel modelNCC;
	private JTable tableNCC;
	private JButton btnXoa;
	private JButton btnSua;
	private JButton btnTim;
	private JTextField txtTim;
	private DanhMuc_DAO ncc_dao;
	private QuanLySanPham_GUI quanLySanPhamGui;
	public QuanLyDanhMuc_GUI(QuanLySanPham_GUI quanLySanPhamGui) {
	    this.quanLySanPhamGui = quanLySanPhamGui;
		ncc_dao =  new DanhMuc_DAO();
		setTitle("GIAO DIEN CHO QUAN LY");
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 500);
		String[] colHeader = {"Mã danh mục", "Tên danh mục"};
		modelNCC = new DefaultTableModel(colHeader, 0);
		tableNCC = new JTable(modelNCC);
		add(new JScrollPane(tableNCC));
		docDuLieuDataBaseVaoTable();
		JLabel lbTim = new JLabel("Tìm theo tên: ");
		btnXoa = new JButton("Xóa");
		btnTim = new JButton("Tìm");
		txtTim = new JTextField(20);
		
		JPanel bot= new JPanel();
		bot.add(lbTim);
		bot.add(txtTim);bot.add(btnTim);
		bot.add(Box.createHorizontalStrut(100));
		bot.add(btnXoa);
		
		add(bot, BorderLayout.SOUTH);
		btnXoa.addActionListener(this);
		tableNCC.addMouseListener(this);
	}

	@Override
	 public void mouseClicked(MouseEvent e) {
        int row = tableNCC.getSelectedRow();
        if (row >= 0) {
            // Extract information from the selected row
            String maDM = tableNCC.getValueAt(row, 0).toString();
            String tenDM = tableNCC.getValueAt(row, 1).toString();

            // Pass information to QuanLyThuoc_GUI
            quanLySanPhamGui.updateNCCInfo(maDM, tenDM);
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
		if(e.getSource().equals(btnXoa)) {
			int row = tableNCC.getSelectedRow();
			String ma = tableNCC.getValueAt(row, 0).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa dong nay") == yes) {
				ncc_dao.xoa(ma);
				modelNCC.removeRow(row);
			}
		}
	}
//	public static void main(String[] args) {
//		new QuanLyNhaCungCap().setVisible(true);
//	}
	public void docDuLieuDataBaseVaoTable() {
		List<DanhMuc> list = ncc_dao.getall();
		String gt = "";
		for (DanhMuc ncc : list) {
			modelNCC.addRow(new Object[] {ncc.getMaDM(), ncc.getTenDM()});
		}
	}
	
}
