package linkedLists;

public class Node<T>
{
	Node(){}
	public Node<T> next;
	public T value;
	public Node<T> clone()
	{
		Node<T> ref=new Node<T>();
		ref.value=value;
		if(next!=null)
		{
			ref.next=next.clone();
		}
		return ref;
	}
}