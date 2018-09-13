package linkedLists;

public class SetOfStacks<T>
{//P99 3.3
	private int sets, height;
	private Node<Node<T>> setsHead;
	private Node<Integer> heightTracker;

	SetOfStacks(int height)
	{
		this.height=height;
		setsHead=null;
		heightTracker=null;
		sets=0;
	}

	public void push(T value)
	{
		if(setsHead==null)
		{
			setsHead=new Node<Node<T>>();
			setsHead.value = new Node<T>();
			setsHead.value.value=value;
			heightTracker = new Node<Integer>();
			heightTracker.value=1;
			sets=1;
		}
		else if(heightTracker.value==height)
		{
			Node<Integer> tempTracker = new Node<Integer>();
			tempTracker.value=1;
			tempTracker.next=heightTracker;
			heightTracker=tempTracker;
			Node<Node<T>> tempSetsHead = new Node<Node<T>>();
			tempSetsHead.next=setsHead;
			tempSetsHead.value = new Node<T>();
			tempSetsHead.value.value=value;
			setsHead=tempSetsHead;
			sets++;
		}
		else
		{
			Node<T> temp = new Node<T>();
			temp.value = value;
			temp.next=setsHead.value;
			setsHead.value=temp;
			heightTracker.value++;
		}
	}

	public T pop()
	{
		if(setsHead!=null)
		{
			T value = setsHead.value.value;
			if(heightTracker.value==1)
			{
				if(setsHead.next==null)
				{
					setsHead=null;
					heightTracker=null;
				}
				else
				{
					setsHead=setsHead.next;
					heightTracker=heightTracker.next;
				}
				sets--;
			}
			else
			{
				setsHead.value=setsHead.value.next;
				heightTracker.value--;
			}
			return value;
		}
		return null;
	}

	public T popAt(int index)
	{
		if(setsHead!=null)
		{
			Node<Node<T>> setItr = setsHead;
			Node<Integer> trackerItr = heightTracker;
			Node<Node<T>> preSetItr=null;
			Node<Integer> preTrackerItr=null;
			for(int i=0;i<index;i++)
			{
				if(i==index-1)
				{
					preSetItr=setItr;
					preTrackerItr=trackerItr;
				}
				setItr=setItr.next;
				trackerItr=trackerItr.next;
				if(setItr==null)
					return null;
			}
			T value=setItr.value.value;
			setItr.value=setItr.value.next;
			trackerItr.value--;
			if(setItr.value==null)//empty stack
			{
				if(preSetItr!=null)
				{
					preSetItr.next=setItr.next;
					preTrackerItr.next=trackerItr.next;
				}
				else
				{
					setsHead=setItr.next;
					heightTracker=trackerItr.next;
				}
				sets--;
			}
			return value;
		}
		return null;
	}
	
	public void print()
	{
		Node<Node<T>> tpSet = setsHead;
		Node<Integer> tpTrc = heightTracker;
		Node<T> stItr;
		int curHeight;
		for(int h = height;h>0;h--)
		{
			tpSet=setsHead;
			tpTrc=heightTracker;
			while(tpSet!=null)
			{
				stItr=tpSet.value;
				curHeight=tpTrc.value;
				if(curHeight<h)
				{
					System.out.print(" \t");
				}
				else
				{
					while(curHeight>h)
					{
						stItr=stItr.next;
						curHeight--;
					}
					System.out.print(stItr.value+"\t");
				}
				tpSet=tpSet.next;
				tpTrc=tpTrc.next;
			}
			System.out.println();
		}
		System.out.println();
	}
}
