package arrays_and_strings;
import java.util.Hashtable;
//given two strings, write a method to decide if one is a permutation of the other
public class CheckPermutation {
	String one, two;
	CheckPermutation(String one, String two){this.one=one;this.two=two;}
	
	public boolean useHash()
	{
		if(one.length()!=two.length()) return false;
		Hashtable<Character, Integer> table = new Hashtable<Character, Integer>();
		for(int i=0;i<one.length();i++)
		{
			if(table.get(one.charAt(i))==null)
				table.put(one.charAt(i), 1);
			else
				table.put(one.charAt(i), table.get(one.charAt(i))+1);
		}
		for(int i=0;i<two.length();i++)
		{
			if(table.get(two.charAt(i))!=null)
			{
				table.put(two.charAt(i), table.get(two.charAt(i))-1);
				if(table.get(two.charAt(i))<0)
					return false;
			}
			else
				return false;
		}
		return true;
	}

}
