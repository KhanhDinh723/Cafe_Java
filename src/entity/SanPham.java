package entity;

import java.util.Objects;

public class SanPham {
	private String maSP;
	private String tenSP;
	private String hinhSP;
	private DanhMuc danhMuc;
	private String kichThuoc;
	private double donGiaBan;
	public SanPham() {
		super();
	}
	public SanPham(String maSP) {
		super();
		this.maSP = maSP;
	}
	
	
	public SanPham(String maSP, String tenSP, String hinhSP, DanhMuc danhMuc, String kichThuoc,
			double donGiaBan) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.hinhSP = hinhSP;
		this.danhMuc = danhMuc;
		this.kichThuoc = kichThuoc;
		this.donGiaBan = donGiaBan;
	}
	public String getMaSP() {
		return maSP;
	}
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public String getHinhSP() {
		return hinhSP;
	}
	public void setHinhSP(String hinhSP) {
		this.hinhSP = hinhSP;
	}
	public DanhMuc getDanhMuc() {
		return danhMuc;
	}
	public void setDanhMuc(DanhMuc danhMuc) {
		this.danhMuc = danhMuc;
	}
	public String getKichThuoc() {
		return kichThuoc;
	}
	public void setKichThuoc(String kichThuoc) {
		this.kichThuoc = kichThuoc;
	}
	public double getDonGiaBan() {
		return donGiaBan;
	}
	public void setDonGiaBan(double donGiaBan) {
		this.donGiaBan = donGiaBan;
	}
	

	@Override
	public String toString() {
		return "SanPham [maSP=" + maSP + ", tenSP=" + tenSP + ", hinhSP=" + hinhSP + ", danhMuc=" + danhMuc
				+ ", kichThuoc=" + kichThuoc + ", donGiaBan=" + donGiaBan + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maSP);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSP, other.maSP);
	}

	
	
	
}

