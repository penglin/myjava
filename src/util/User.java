package util;

public class User implements Cloneable{
	private String name;
	private int age;
	private String[] values;
	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public User(String name, int age, String[] values) {
		super();
		this.name = name;
		this.age = age;
		this.values = values;
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
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public static void main(String[] args) {
		User u1 = new User("lin",10);
		User u2 = new User("penglin",11);
		change(u1,u2);
		System.out.println(u1.getName());
		System.out.println(u2.getName());
		u1 = u2;
		System.out.println(u1.getName());
		System.out.println(u2.getName());
	}
	
	public static void change(User u1,User u2){
		u2.setName("dage");
		u1 = u2;
	}
}
