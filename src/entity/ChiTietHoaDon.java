package entity;

public class ChiTietHoaDon {
	private SanPham sanPham;
	private HoaDon hoaDon;
	private int soLuong;
	private double donGia;
	//cons
	public double getThanhTien() {
		return soLuong*donGia;
	}
	public SanPham getsanPham() {
		return sanPham;
	}
	public void setsanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public ChiTietHoaDon(SanPham sanPham, HoaDon hoaDon, int soLuong, double donGia) {
		super();
		this.sanPham = sanPham;
		this.hoaDon = hoaDon;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}
	public ChiTietHoaDon() {
		super();
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [sanPham=" + sanPham + ", hoaDon=" + hoaDon + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ "]";
	}
	
}
