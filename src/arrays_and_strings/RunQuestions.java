package arrays_and_strings;

public class RunQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("IsUnique:");
		runIsUnique();
		System.out.println("\nCheckPermutation:");
		runCheckPermutation();
		System.out.println("\nURLify:");
		runURLify();
	}
	
	static void runIsUnique()
	{
		String[] input = {"abcdefghijklmn","aaaaaaaaaaaa","abcdaefg","aAbB"};
		for(int i=0;i<input.length;i++)
		{
			IsUnique check = new IsUnique(input[i]);
			System.out.println(input[i]+"\n" + check.brute_force());
		}
	}
	
	static void runCheckPermutation()
	{
		String[] inputOne = {"abcd","abcd"};
		String[] inputTwo = {"dcba","efgh"};
		for(int i = 0;i<inputOne.length;i++)
		{
			CheckPermutation check = new CheckPermutation(inputOne[i],inputTwo[i]);
			System.out.println(inputOne[i]+"\n"+inputTwo[i]+"\n"+check.useHash());
		}
	}
	
	static void runURLify()
	{
		String[] input = {"hello Yijian  ","what day is today      "};
		for(int i=0;i<input.length;i++)
		{
			URLify check = new URLify(input[i].toCharArray());
			System.out.println(input[i]+"\n"+(new String(check.noStruct())));
		}
	}

}
