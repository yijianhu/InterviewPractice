package arrays_and_strings;

//implement an algorithm to determine if a string has all unique characters. 
//What if you cannot use additional data structures.
public class IsUnique {
	String aString;
	IsUnique(String aString){this.aString=aString;}
	
	public boolean brute_force()
	{
		for(int i =0;i<aString.length()-1;i++)
		{
			for(int j=i+1;j<aString.length();j++)
			{
				if(aString.charAt(i)==aString.charAt(j))
					return false;
			}
		}
		return true;
	}

}
