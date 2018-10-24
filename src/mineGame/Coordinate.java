package mineGame;

public class Coordinate
{
	public int row, col;
	public boolean isMine = false;
	Coordinate(int row, int col)
	{
		this.row=row;
		this.col=col;
	}
	
	@Override
	public boolean equals(Object cor)
	{
		Coordinate coor;
		try
		{
			coor = (Coordinate)cor;
		}
		catch(Exception e)
		{
			return false;
		}
		if(row==coor.row && col==coor.col)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		int r,c;
		r=row+1;
		c=col+1;
		return r*(c/10+1)*10+c;
	}
	
	public String toString()
	{
		return "("+row+","+col+")";
	}
	
	public Coordinate clone()
	{
		Coordinate toReturn = new Coordinate(row,col);
		toReturn.isMine = isMine;
		return toReturn;
	}
}