package mineGame;

public class GameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MineMatrix matrix = new MineMatrix(new int[][] {
					{0,0,0,0,0,0,0,0},
					{0,0,9,0,9,0,0,9},
					{9,0,0,0,0,0,0,0},
					{0,0,0,9,0,0,9,0},
					{0,0,0,0,0,0,0,0},
					{9,0,0,0,0,0,0,0},
					{0,0,9,0,0,0,0,0},
					{0,0,0,0,0,0,0,0}});
		matrix.printMatrix(false);
		Player p1 = new Player(matrix.getMatrix());
		Coordinate step;
		do
		{
			step = p1.step();
			if(step!=null)
			{
				int result = matrix.verify(step);
				if(result!=9 && step.isMine)
				{
					System.out.println("Game Over, Mine mismarked");
					break;
				}
				else if(result==9 && !step.isMine)
				{
					System.out.println("Game Over, Mine toggled");
					break;
				}
				else
				{
					p1.update(step,result);
					System.out.println();
					matrix.printMatrix(false);
				}
			}
		}while(step!=null);
		System.out.println("\nPlayer finished");
		matrix.printMatrix(true);

	}

}
