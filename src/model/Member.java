package model;

public class Member {
	private String id="", pw, re, phone;
	private boolean sameId=false;

	public boolean isSameId() {
		return sameId;
	}

	public void setSameId(boolean sameId) {
		this.sameId = sameId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
