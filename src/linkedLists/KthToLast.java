package linkedLists;
//P94 2.2
public class KthToLast {
	public KthToLast(){};
	
	public <T> Node<T> run(Node<T> head, int k)
	{
		if(head==null || k<2)
		{
			return head;
		}
		else
		{
			return run(head.next,k-1);
		}
	}
}
