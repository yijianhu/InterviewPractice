package mineGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class Player {
	/* Idea
	 * Use a Queue to store explore plan
	 * Follow explore Queue to explore
	 * Use a Hashmap to ensure no coordinate will be repeated in explore plan
	 * 
	 * use HashSet to store neighbors and Hashtable<HashSet,Integer> to store mine in coordinates
	 * queryTable records mines in coordinate sets
	 */
	
	private LinkedList<Coordinate> stepQueue;
	private LinkedList<Coordinate> plan;//all coordinates in plan should be uncovered but has covered neighbors
	private Hashtable<HashSet<Coordinate>,Integer> queryTable;
	private int tablecode;
	private int[][] matrix;
	private final int mapSize;
	private int mapExplored;
	private int mineExplored;
	private final int totalMineNum;
	/*	Method list to debug
	 * 	Player(int[][] matrix)
	 * 	Player(int[][] matrix, int mineNum)
	 * 	initPlan()
	 * 	step()
	 * 	randomStep()
	 * 	update(Coordinate coor, int val)
	 * 	explore(Coordinate coor)
	 * 	hasUnexploredNeighbor(Coordinate coor)
	 * 	printPlan()
	 * 	printStepQueue()
	 * 	subSetAnalysis(HashSet<Coordinate> set)
	 * 	getSubSetList(HashSet<Coordinate> set)
	 * 	outOfBound(int row, int col)
	 */
	private boolean debugMode = false;
	private boolean stepDebug = true;
	private boolean exploreDebug = true;
	private boolean subSetAnalysisDebug = true;
	private long debugDelay = 100;
	
	/*	matrix:
	 * 	0 0 0 0 0
	 * 	0 0 0 0 0
	 * 	1 1 1 0 0
	 * 	- - 1 0 0
	 * 	- - 2 1 0
	 * 	- - - 1 0
	 * 	- - - 1 0
	 * 	- will be marked as -1
	 * 	0 ~ 8 represents number of mines in neighbors
	 * 	9 represents current block is a mine
	 */
	Player(int[][] matrix)
	{
		mapSize = matrix.length*matrix[0].length;
		mineExplored=0;
		mapExplored=0;
		totalMineNum=-1;
		this.matrix=matrix;
		plan = new LinkedList<Coordinate>();
		stepQueue = new LinkedList<Coordinate>();
		queryTable = new Hashtable<HashSet<Coordinate>,Integer>();
		tablecode=queryTable.hashCode();
		initPlan();
		
	}
	
	Player(int[][] matrix, int mineNum)
	{
		mapSize = matrix.length*matrix[0].length;
		mineExplored=0;
		mapExplored=0;
		totalMineNum=mineNum;
		this.matrix=matrix;
		plan = new LinkedList<Coordinate>();
		stepQueue = new LinkedList<Coordinate>();
		queryTable = new Hashtable<HashSet<Coordinate>,Integer>();
		tablecode=queryTable.hashCode();
		initPlan();
	}
	

	private void initPlan()
	{
		boolean unexplored = true;
		Coordinate currentCoordinate;
		for(int r=0;r<matrix.length;r++)
		{
			for(int c=0;c<matrix[0].length;c++)
			{
				if(matrix[r][c]!=-1)//explored, might be a mine
				{
					unexplored = false;
					currentCoordinate = new Coordinate(r,c);
					if(hasUnexploredNeighbor(currentCoordinate))
					{
						plan.add(currentCoordinate);
					}
				}
			}
		}
		if(unexplored)
		{
			Coordinate toExplore = randomStep();
			plan.add(toExplore);
			stepQueue.add(toExplore);
		}
	}
	
	public Coordinate step()
	{
		int explored = 1;
		if(!stepQueue.isEmpty())
		{
			if(debugMode && stepDebug)printStepQueue();
			return stepQueue.poll();
		}
		Coordinate coor = explore(plan.peek());
		while(coor==null && plan.size()>0)
		{
			if(explored>(plan.size()+queryTable.size())*2)
			{
				Coordinate nextStep = randomStep();
				plan.add(nextStep);
				return nextStep;
			}
			coor=explore(plan.peek());
			explored++;
			if(stepQueue.size()>0 && coor==null)
				return stepQueue.poll();
		}
		return coor;
	}
	
	private Coordinate randomStep()
	{
		System.out.println("Random step");
		if(plan.size()>0 && totalMineNum!=-1)
		{
			if((totalMineNum-mineExplored)<=8*(mapSize-mapExplored))
			{
				int minMineNum;
				LinkedList<Coordinate> lessMineUnexplored = new LinkedList<Coordinate>();
				Coordinate lessMine = plan.peek();
				minMineNum = getUnexploredNeighborList(lessMine,lessMineUnexplored);
				for(Coordinate eachCoor:plan)
				{
					LinkedList<Coordinate> unexplored = new LinkedList<Coordinate>();
					int mineNum = getUnexploredNeighborList(eachCoor, unexplored);
					if(mineNum*lessMineUnexplored.size()<minMineNum*unexplored.size())
					{
						minMineNum = mineNum;
						lessMine = eachCoor;
						lessMineUnexplored = unexplored;
					}
				}
			}
		}
		Random ran = new Random();
		int row,col;
		do
		{
			row = ran.nextInt(matrix.length);
			col = ran.nextInt(matrix[0].length);
		}
		while(matrix[row][col]!=-1);
		return new Coordinate(row,col);
	}
	
	public void update(Coordinate coor, int val)
	{
		matrix[coor.row][coor.col]=val;
		queryTableUpdate(coor);
	}
	
	private void queryTableUpdate(Coordinate coor)
	{
		if(matrix[coor.row][coor.col]==9)return;
		/*
		 * 1. update coordinate sets which contains the coordinate by remove the current
		 *    coordinate from each set in queryTable.keySet()
		 *    
		 * 2. update coordinate set around current coordinate by create a new set as a key and 
		 *    mine number as value in queryTable
		 */
		//if the coordinate is not a mine
		//for each coordinate set in table's key set
		for(HashSet<Coordinate> set:((Hashtable<HashSet<Coordinate>,Integer>)(queryTable.clone())).keySet())
		{
			//if the set contains the coordinate
			HashSet<Coordinate> newSet = (HashSet<Coordinate>)set.clone();
			if(newSet.contains(coor))
			{
				int mineVal = queryTable.get(set);
				queryTable.remove(set);
				newSet.remove(coor);
				queryTable.put(newSet, mineVal);
				if(newSet.size()==1) 
				{
					newSet.iterator().next().isMine=true;
					stepQueue.add(newSet.iterator().next().clone());
					queryTable.remove(newSet);
				}
			}
		}
		
		
		int mineAround;
		LinkedList<Coordinate> unexplored = new LinkedList<Coordinate>();
		mineAround = getUnexploredNeighborList(coor, unexplored);
		HashSet<Coordinate> set = new HashSet<Coordinate>();
		if(matrix[coor.row][coor.col]!=0)
		{
			for(Coordinate eachCoor:unexplored)
			{
				set.add(eachCoor);
			}
			queryTable.put(set, mineAround);
		}
	}
	
	
	/**
	 * Call the method to explore coordinate in plan, 
	 * if a coordinate has all neighbors been explored, the coordinate will be removed from plan
	 * if a coordinate has the same number of unexplored neighbors and mines around, unexplored neighbors
	 * 		will be marked as mines.
	 * if a coordinate has more unexplored neighbors than mines around it, the coordinate will be moved to the end
	 * 		of plan
	 * 
	 * @param coor 	coordinate to explore
	 * @return		coordinate needs to perform action (explore or mark as mine)
	 */
	private Coordinate explore(Coordinate coor)
	{
		if(debugMode && exploreDebug)
		{
			System.out.println("exploreing: " + coor.toString());
			printPlan();
			try{Thread.sleep(debugDelay);}
			catch(Exception e) {e.printStackTrace();}
		}
		if(coor==null)return null;
		int mineAround;
		mineAround = matrix[coor.row][coor.col];//mineAround => unknown mine around current coordinate
		if(mineAround == -1)
		{
			plan.removeFirst();
			return null;
		}
		LinkedList<Coordinate> unexplored = new LinkedList<Coordinate>();
		mineAround = getUnexploredNeighborList(coor, unexplored);
		
		if(debugMode)
		{
			System.out.print("Unexplored: ");
			for(Coordinate unexCoor:unexplored)
				System.out.print(unexCoor.toString()+", ");
			System.out.println();
		}
		
		if(unexplored.size()==0)
		{
			plan.removeFirst();
			return null;
		}
		if(mineAround == 0)//no mine around
		{
			for(Coordinate eachCoor:unexplored)
			{
				plan.add(eachCoor);
				stepQueue.add(eachCoor);
			}
			plan.removeFirst();
			return stepQueue.poll();
		}
		else if(mineAround>=unexplored.size())//unexplored are mines
		{
			for(Coordinate eachCoor:unexplored)
			{
				eachCoor.isMine=true;
				stepQueue.add(eachCoor);
			}
			plan.removeFirst();
			return stepQueue.poll();
		}
		else
		{
			//make unexplored set and put it in queryTable
			HashSet<Coordinate> unexploredSet = new HashSet<Coordinate>();
			for(Coordinate eachCoor:unexplored)
			{
				unexploredSet.add(eachCoor);
			}
			//queryTable.put(unexploredSet, mineAround);
			if(queryTable.containsKey(unexploredSet))
			{
				plan.add(plan.poll());
				subSetAnalysis(unexploredSet);
			}
			else
			{
				plan.add(plan.poll());
			}
			if(stepQueue.size()>0)
			{
				return stepQueue.poll();
			}
			queryTableAnalysis();
			if(stepQueue.size()>0)
			{
				return stepQueue.poll();
			}
			return null;
		}
	}
	
	private boolean hasUnexploredNeighbor(Coordinate coor)
	{
		int row, col;
		row=coor.row;
		col=coor.col;
		for(int r=row-1;r<=row+1;r++)
		{
			for(int c=col-1;c<=col+1;c++)
			{
				if(!outOfBound(r,c) && !(c==col && r==row))
				{
					if(matrix[r][c]==-1)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param coor	coordinate's surround will be explore
	 * @param list	list will store unexplored neighbor
	 * @return	mines around, marked mines will be exclude
	 */
	private int getUnexploredNeighborList(Coordinate coor, LinkedList<Coordinate> list)
	{
		if(coor==null)return 0;
		int row, col, mineAround;
		row = coor.row;
		col = coor.col;
		mineAround = matrix[coor.row][coor.col];
		for(int r=row-1;r<=row+1;r++)
		{
			for(int c=col-1;c<=col+1;c++)
			{
				if(!outOfBound(r,c) && !(c==col && r==row))
				{
					if(matrix[r][c]==-1)
					{
						list.add(new Coordinate(r,c));
					}
					if(matrix[r][c]==9)
					{
						mineAround--;
					}
				}
			}
		}
		return mineAround;
	}
	
	private void printPlan()
	{
		System.out.print("plan: ");
		for(Coordinate eachCoor:plan)
		{
			System.out.print(eachCoor.toString()+", ");
		}
		System.out.println();
	}
	
	private void printStepQueue()
	{
		System.out.print("stepQueue: ");
		for(Coordinate eachCoor:stepQueue)
		{
			System.out.print(eachCoor.toString()+", ");
		}
		System.out.println();
	}
	
	private void printSet(HashSet<Coordinate> set)
	{
		System.out.print(queryTable.containsKey(set)?queryTable.get(set)+" ":"x ");
		System.out.print(set.toString()+" ");
		System.out.println();
	}
	
	private void printQueryTable()
	{
		System.out.println("queryTable: ");
		for(HashSet<Coordinate> eachSet:queryTable.keySet())
		{
			System.out.println(queryTable.get(eachSet)+" "+eachSet.toString());
		}
	}
	
	private void printSetList(ArrayList<HashSet<Coordinate>> list)
	{
		for(HashSet<Coordinate> set:list)
			printSet(set);
	}
	
	private void queryTableAnalysis()
	{
		if(tablecode==queryTable.hashCode())return;
		Set<HashSet<Coordinate>> keyset = ((Hashtable<HashSet<Coordinate>,Integer>)queryTable.clone()).keySet();
		for(HashSet<Coordinate> set:keyset)
		{
			if(set.size()>2)
			{
				subSetAnalysis(set);
			}
		}
		tablecode=queryTable.hashCode();
	}
	
	/**
	 * 
	 * 
	 * @param set	all subSet of the set will be analyzed to reduce the size of sets
	 */
	private void subSetAnalysis(HashSet<Coordinate> set)
	{
		if(debugMode&&subSetAnalysisDebug) 
		{
			System.out.println("subSetAnalysis Target set:");
			printSet(set);
			printQueryTable();
		}
		ArrayList<HashSet<Coordinate>> subSetList = getSubSetList(set);
		HashSet<Coordinate> mostMineSet;
		if(debugMode&&subSetAnalysisDebug) 
		{
			System.out.println("subSetList");
			printSetList(subSetList);
		}
		if(subSetList.size()>0)
		{
			//get the subSet with most mines
			mostMineSet = subSetList.get(0);
			for(HashSet<Coordinate> eachSet:subSetList)
			{
				if(queryTable.get(mostMineSet)<queryTable.get(eachSet))
				{
					mostMineSet = eachSet;
				}
			}
			if(debugMode && subSetAnalysisDebug)System.out.println("mostMineSet: "+mostMineSet.toString()+" size: "+mostMineSet.size());
			//exclude the subSet from target set
			HashSet<Coordinate> newSet = new HashSet<Coordinate>();
			for(Coordinate coor:set)
			{
				if(!mostMineSet.contains(coor))
				{
					newSet.add(coor);
				}
			}
			if(debugMode && subSetAnalysisDebug)System.out.println("new Set: "+newSet.toString()+" size: "+newSet.size());
			//update new set and remove old set
			int mineNum = queryTable.get(set);
			mineNum = mineNum - queryTable.get(mostMineSet);
			if(mineNum == 0)//no mine in new set, add to stepQueue, and remove old set
			{
				for(Coordinate coor:newSet)
				{
					stepQueue.add(coor);
					plan.add(coor);
				}
			}
			else if(mineNum==newSet.size() && mineNum!=0)//mineNum == newSet.size(): all coordinates left in set have mine
			{
				for(Coordinate coor:newSet)
				{
					coor.isMine=true;
					stepQueue.add(coor);
				}
				
			}
			else//mines left in new set, update new set to queryTable, then analysis new set
			{
				queryTable.remove(set);
				queryTable.put(newSet, mineNum);
				subSetAnalysis(newSet);
			}
			queryTable.remove(set);//finally remove old set
		}
		//if set has no subSet, do nothing
	}
	
	private ArrayList<HashSet<Coordinate>> getSubSetList(HashSet<Coordinate> set)
	{
		//HashSet<Coordinate>[] sets =  (HashSet<Coordinate>[]) (queryTable.keySet()).toArray(new HashSet<Coordinate>[]());
		ArrayList<HashSet<Coordinate>> subSets = new ArrayList<HashSet<Coordinate>>();
		for(HashSet<Coordinate> eachSet:queryTable.keySet())
		{
			Iterator itr = eachSet.iterator();
			boolean isSubSet = true;
			if(eachSet.size()<set.size())
			{
				isSubSet = true;
				while(itr.hasNext() && isSubSet==true)
				{
					if(!set.contains((Coordinate)itr.next()))
					{
						isSubSet=false;
					}
				}
				if(isSubSet)
					subSets.add(eachSet);
				
				
			}
		}
		return subSets;
	}
	
	private boolean outOfBound(int row, int col)
	{
		if(row<0 || row>=matrix.length || col<0 || col>=matrix[0].length)
			return true;
		return false;
	}
}
