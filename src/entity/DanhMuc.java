package entity;

import java.util.Objects;

public class DanhMuc {
	private String maDM;
	private String tenDM;
	public DanhMuc() {
		super();
	}
	public DanhMuc(String maDM) {
		super();
		this.maDM = maDM;
	}
	public DanhMuc(String maDM, String tenDM) {
		super();
		this.maDM = maDM;
		this.tenDM = tenDM;
	}
	public String getMaDM() {
		return maDM;
	}
	public void setMaDM(String maDM) {
		this.maDM = maDM;
	}
	public String getTenDM() {
		return tenDM;
	}
	public void setTenDM(String tenDM) {
		this.tenDM = tenDM;
	}
	@Override
	public String toString() {
		return "DanhMuc [maDM=" + maDM + ", tenDM=" + tenDM + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maDM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DanhMuc other = (DanhMuc) obj;
		return Objects.equals(maDM, other.maDM);
	}
	
	
}
