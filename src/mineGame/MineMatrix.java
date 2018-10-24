package mineGame;

public class MineMatrix {
	private int[][] matrix;
	private int[][] coveredMatrix;
	private int matrixRowSize, matrixColSize;
	

	MineMatrix(int rowSize, int colSize)
	{
		//TODO need complete
		matrix = new int[rowSize][colSize];
		matrixRowSize = matrix.length;
		matrixColSize = matrix[0].length;
	}
	
	/*
	 * only need to distribute mines (9)
	 * 	0 0 0 0 0
	 * 	0 9 0 0 9
	 * 	0 0 0 0 0
	 *  0 0 0 0 0
	 */
	MineMatrix(int[][] mines)
	{
		matrix = mines;
		matrixRowSize = matrix.length;
		matrixColSize = matrix[0].length;
		coveredMatrix = new int[matrixRowSize][matrixColSize];
		for(int r=0;r<matrix.length;r++)
		{
			for(int c=0;c<matrix[0].length;c++)
			{
				calcAround(r,c);
				coveredMatrix[r][c]=-1;
			}
		}
	}
	
	private void calcAround(int row, int col)
	{
		if(matrix[row][col]==9)
		{
			for(int r=row-1;r<=row+1;r++)
			{
				for(int c=col-1;c<=col+1;c++)
				{
					if(!outOfBound(r,c) && !(r==row && c==col))
					{
						if(matrix[r][c]!=9)
							matrix[r][c]++;
					}
				}
			}
		}
	}
	
	public int verify(Coordinate coor)
	{
		coveredMatrix[coor.row][coor.col] = matrix[coor.row][coor.col];
		return matrix[coor.row][coor.col];
	}

	private boolean outOfBound(int row, int col)
	{
		if(row<0 || row>=matrix.length || col<0 || col>=matrix[0].length)
			return true;
		return false;
	}
	
	public void printMatrix(boolean showMine)
	{
		System.out.print("  ");
		for(int c=0;c<matrixColSize;c++)
			System.out.print(c+" ");
		System.out.println();
		for(int r=0;r<matrixRowSize;r++)
		{
			for(int c=0;c<matrixColSize;c++)
			{
				if(c==0)
				{
					System.out.print(r+" ");
				}
				System.out.print((
						coveredMatrix[r][c]==-1)?
								"- ":
									((showMine)?
											coveredMatrix[r][c]+" ":
												(coveredMatrix[r][c]==9)?"* ":coveredMatrix[r][c]+" "));
				//System.out.print(matrix[r][c]+" ");
			}
			System.out.println();
		}
	}
	
	public int[][] getMatrix()
	{
		return coveredMatrix;
	}
}
