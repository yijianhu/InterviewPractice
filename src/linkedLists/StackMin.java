package linkedLists;
//P98 3.2
public class StackMin<T extends Comparable<T>> {
	private Node<T> head;
	private Node<T> minhead;
	
	public StackMin(){};
	
	public void push(T value)
	{
		if(head==null)
		{
			head=new Node<T>();
			minhead=new Node<T>();
			head.value=value;
			minhead.value=value;
		}
		else
		{
			Node<T> temp= new Node<T>();
			temp.value=value;
			temp.next=head;
			head=temp;
			if(value.compareTo(minhead.value)<0)
			{
				temp=new Node<T>();
				temp.value = value;
				temp.next=minhead;
				minhead=temp;
			}
			else
			{
				temp=new Node<T>();
				temp.value=minhead.value;
				temp.next=minhead;
				minhead=temp;
			}
		}
	}
	
	public T pop()
	{
		Node<T> temp=head;
		if(head!=null)
		{
			head=head.next;
			minhead=minhead.next;
			return temp.value;
		}
		return null;
	}
	
	public T min()
	{
		return minhead.value;
	}

}
