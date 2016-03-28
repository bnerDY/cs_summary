//Method invocation
public class Foo
{
   static
   {
      System.out.println("In <clinit>");
   }

   public Foo()
   {
      System.out.println("In <init>");
   }

   public static void getName()
   {
      System.out.println("In getName()");
   }
   
   public static void main(String[] args) {
	   new Foo().getName();
   }
}