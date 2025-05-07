package entity;

import java.util.Objects;

public class TaiKhoanNhanVien {
	private String taiKhoan;
	private String matKhau;
	private NhanVien NV;
	public String getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public NhanVien getNV() {
		return NV;
	}
	public void setNV(NhanVien NV) {
		NV = NV;
	}
	public TaiKhoanNhanVien(String taiKhoan, String matKhau, NhanVien NV) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.NV = NV;
	}
	public TaiKhoanNhanVien() {
		super();
		this.matKhau  = taiKhoan;
	}
	public TaiKhoanNhanVien(String taiKhoan) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau  = taiKhoan;
	}
	@Override
	public String toString() {
		return "TaiKhoanNhanVien [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", NV=" + NV + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(taiKhoan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		TaiKhoanNhanVien other = (TaiKhoanNhanVien) obj;
		return Objects.equals(taiKhoan, other.taiKhoan);
	}
}
