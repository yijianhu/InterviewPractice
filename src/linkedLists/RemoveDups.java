package linkedLists;
//P94 2.1
public class RemoveDups {
	public RemoveDups(){};
	public <T> Node<T> removeDup(Node<T> sp)
	{
		Node<T> refer, temp, last, head;
		head=sp.clone();
		refer=head;
		temp=head;
		last=head;
		while(refer!=null)
		{
			last=refer;
			temp=refer.next;
			while(temp!=null)
			{
				if(refer.value==temp.value)
				{
					last.next=temp.next;
				}
				else
				{
					last=temp;
				}
				temp=temp.next;
			}
			refer=refer.next;
		}
		return head;
	}

}
