package common;

public class Person {
	//자바 빈즈 규약
	//1 : default 이외의 패키지에 생성
	//2 : 필드(멤버변수) 접근지정자는 private로 보호
	//3 : 기본생성자는 반드시 만들어야 함
	//4 : 값을 전달할 수 있는 getter/setter 메서드가 있어야 함
	//5 : getter/setter 메서드는 public 으로 설정되어야 함
	
	private String name; //필드
	private int age;
	
	public Person() {} //기본생성자
	
	//커스텀 생성자
	public Person(String name, int age) {
		//super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
