package arrays_and_strings;
//Write a method to replace all spaces in a string with '%20'. You may assume
//that the string has sufficient space at the end to hold the additional characters,
//and that you are given the "true" length of the string. (Note:
public class URLify {
	char[] input;
	URLify(char[] input){this.input=input;}
	
	public char[] noStruct()
	{
		int leftindex, rightindex;
		leftindex = input.length-1;
		rightindex = input.length-1;
		
		while(input[leftindex]==' ')
		{
			leftindex--;
		}
		
		while(leftindex!=rightindex)
		{
			if(input[leftindex]!=' ')
			{
				input[rightindex]=input[leftindex];
			}
			else
			{
				input[rightindex]='0';
				input[rightindex-1]='2';
				input[rightindex-2]='%';
				rightindex-=2;
			}
			rightindex--;
			leftindex--;
		}
		return input;
	}

}
