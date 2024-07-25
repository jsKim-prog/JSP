package memberShip;

public class MemberDTO {
	//member 객체
	//필드
	private String id;
	private String pw;
	private String name;
	private String regdate;
	
	public MemberDTO() {}
	//기본생성자

	//메서드
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}
