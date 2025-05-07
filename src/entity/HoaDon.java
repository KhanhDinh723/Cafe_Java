package entity;

import java.time.LocalDate;
import java.util.Objects;

public class HoaDon {
	private String maHD;
    private NhanVien nv;
    private KhachHang kh;
    private LocalDate ngayMua;
    private double thue;
    private double tongTien;
	//cons
	public HoaDon(String maHD, NhanVien nv, KhachHang kh, LocalDate ngayMua, double thue, double tongTien) {
		super();
		this.maHD = maHD;
		this.nv = nv;
		this.kh = kh;
		this.ngayMua = ngayMua;
		this.thue = thue;
		this.tongTien = tongTien;
	}
	public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", nv=" + nv + ", kh=" + kh + ", ngayMua=" + ngayMua + ", thue=" + thue
				+ ", tongTien=" + tongTien + "]";
	}
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public NhanVien getNv() {
		return nv;
	}
	public void setNv(NhanVien nv) {
		this.nv = nv;
	}
	public KhachHang getKh() {
		return kh;
	}
	public void setKh(KhachHang kh) {
		this.kh = kh;
	}
	public LocalDate getNgayMua() {
		return ngayMua;
	}
	public void setNgayMua(LocalDate ngayMua) {
		this.ngayMua = ngayMua;
	}
	public double getThue() {
		return thue;
	}
	public void setThue(double thue) {
		this.thue = thue;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
}
