package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DanhMuc;

public class DanhMuc_DAO {
	public String gettheoma(String ma) {
		String dstp = "";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
			String sql = "Select * from DanhMuc where maDM = ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, ma);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				dstp = rs.getString("tenDM");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dstp;
	}
	public ArrayList<String> getallten() {
		ArrayList<String> dst = new ArrayList<String>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
			String sql = "Select * from DanhMuc";
			statement=con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				dst.add(rs.getString("tenDM"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dst;
	}
	public ArrayList<String> getallma() {
		ArrayList<String> dst = new ArrayList<String>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
			String sql = "Select * from DanhMuc";
			statement=con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String str = rs.getString("tenDM") + "(" + rs.getString("maDM") + ")";
				dst.add(str);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dst;
	}
	public boolean create(DanhMuc ncc) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        // Check and limit the length of tenNCC
	        String tenNCC = ncc.getTenDM();
	        int maxLength = 20;
	        if (tenNCC.length() > maxLength) {
	            tenNCC = tenNCC.substring(0, maxLength);
	        }

	        stmt = con.prepareStatement("insert into DanhMuc values (?, ?)");
	        stmt.setString(1, ncc.getMaDM());
	        stmt.setString(2, tenNCC);
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return n > 0;
	}
	public ArrayList<DanhMuc> getall() {
		ArrayList<DanhMuc> dst = new ArrayList<DanhMuc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
			String sql = "Select * from DanhMuc";
			statement=con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String ma = rs.getString("maDM");
				String ten = rs.getString("tenDM");
				dst.add(new DanhMuc(ma, ten));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dst;
	}
	public void xoa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from DanhMuc where maDM = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean capNhat(DanhMuc ncc) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        stmt = con.prepareStatement("UPDATE DanhMuc SET tenDM = ? WHERE maDM = ?");
	        stmt.setString(1, ncc.getTenDM());
	        stmt.setString(2, ncc.getMaDM());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return n > 0;
	}


}
