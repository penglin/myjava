package util;

public class TestClone {
	public static void main(String[] args) throws CloneNotSupportedException {
		User user = new User("lin",23);
		User cloneUser = (User) user.clone();
		cloneUser.setAge(343);
		System.out.println(user.getAge());
		System.out.println(cloneUser.getAge());
	}
}
