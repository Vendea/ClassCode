package ds1;
// Framework for HW2

public class HW2{

//define fields here

static String firstname;
static char middleInitial;
static String lastname;
static int age;
static String ft;
static String lt;
static int nya;

public static void main(String[] args){

//give values to fields here

firstname = "Katherine";
middleInitial = 'M';
lastname = "Beine";
age = 18;
ft = lastname.substring(0, 2);
lt = lastname.substring(3, 5);
nya = age + 1;

System.out.println(firstname + " " + middleInitial + ". " + lastname + " is " + age + " years old.");
System.out.println("The first two characters of the last name are " + ft);
System.out.println("The last two characters of the last name are " + lt);
System.out.println("Next year, that person's age will be " + nya);
	}
}