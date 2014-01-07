package number;

public class DataAdd {
	public static void main(String[] args) {
		int a ,b , c ,d ,e, f ,g ,h;
		int sum = 2879;
		for(a=1000;a>1;a--){
			for(b=800;b>1;b--){
				for(c=700;c>1;c--){
						int total = a*2 + b*3 + c*4  ;
						if(total==sum&&a>b&&b>c){
							System.out.println(a+"*2 + "+b+"*3 + "+c+"*4="+a*2+"+"+b*3+"+"+c*4);
							System.out.println("-------------------------------");
							break;
					}
				}
			}
		}
	}
}
