package entity;

public class ChiTietSanPham {
	private String ma;
	private SanPham sp;
	
	public ChiTietSanPham(String ma, SanPham sp) {
		super();
		this.ma = ma;
		this.sp = sp;
	}
	public ChiTietSanPham(String ma) {
		super();
		this.ma = ma;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public SanPham getSp() {
		return sp;
	}
	public void setSp(SanPham sp) {
		this.sp = sp;
	}
	@Override
	public String toString() {
		return "ChiTietSanPham [ma=" + ma + ", sp=" + sp + "]";
	}
	
}
