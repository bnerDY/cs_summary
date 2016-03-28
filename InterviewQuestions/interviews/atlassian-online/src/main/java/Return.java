//Return
public class Return {

	
	@SuppressWarnings("finally")
	public static boolean isConfusing(){
		try {
			System.out.println("true");
			return true;
		} finally {
			System.out.println("false");
			return false;
		}
	}
	
	public static void main(String[] args) {
		isConfusing();
	}
}
