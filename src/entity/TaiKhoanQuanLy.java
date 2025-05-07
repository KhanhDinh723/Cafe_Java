package entity;

import java.util.Objects;

public class TaiKhoanQuanLy {
	private String taiKhoan;
	private String matKhau;
	private QuanLy QL;
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
	public QuanLy getQL() {
		return QL;
	}
	public void setQL(QuanLy qL) {
		QL = qL;
	}
	public TaiKhoanQuanLy(String taiKhoan, String matKhau, QuanLy qL) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		QL = qL;
	}
	public TaiKhoanQuanLy() {
		super();
		this.matKhau  = taiKhoan;
	}
	public TaiKhoanQuanLy(String taiKhoan) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau  = taiKhoan;
	}
	@Override
	public String toString() {
		return "TaiKhoanQuanLy [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", QL=" + QL + "]";
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
		TaiKhoanQuanLy other = (TaiKhoanQuanLy) obj;
		return Objects.equals(taiKhoan, other.taiKhoan);
	}
}
