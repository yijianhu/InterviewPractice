package linkedLists;

public class RunQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runKthToLast();
		runRemoveDups();
		runStackMin();
		runSetOfStacks();
	}
	
	public static void runKthToLast()
	{
		System.out.println("Running KthToLast");
		KthToLast Q=new KthToLast();
		Node<Integer> head = initChain(new Integer[]{1,2,3,4,5,6,7,8});
		Node<Integer> temp = Q.run(head, 3);
		printList(head);
		printList(temp);
		System.out.println("Finished");
	}
	
	public static void runRemoveDups()
	{
		System.out.println("Running RemoveDups");
		RemoveDups q = new RemoveDups();
		Node<Integer> head = initChain(new Integer[]{1,1,2,2,3,3,4,4});
		Node<Integer> temp = q.removeDup(head);
		printList(head);
		printList(temp);
		System.out.println("Finished");
	}
	
	public static void runStackMin()
	{
		System.out.println("Running StackMin");
		StackMin<Integer> st = new StackMin<Integer>();
		st.push(1);
		st.push(2);
		st.push(3);
		st.push(4);
		st.push(5);
		st.push(-1);
		st.push(-2);
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println(st.min());
		st.pop();
		System.out.println("Finished");
	}
	
	public static void runSetOfStacks()
	{
		System.out.println("Running SetOfStakcs");
		SetOfStacks<Integer> ss = new SetOfStacks<Integer>(5);
		for(int i=0;i<25;i++)
			ss.push(i);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.popAt(2);
		ss.print();
		ss.pop();
		ss.print();
		ss.pop();
		ss.print();
		ss.pop();
		ss.print();
		ss.pop();
		ss.print();
		ss.pop();
		ss.print();
		ss.pop();
		ss.print();
		System.out.println("Finished");
	}
	
	public static <T> Node<T> initChain(T[] chain)
	{
		if(chain.length>0)
		{
			Node<T> head = new Node<T>();
			Node<T> temp=head;
			for(int i=0;i<chain.length-1;i++)
			{
				temp.value=chain[i];
				temp.next=new Node<T>();
				temp=temp.next;
			}
			temp.value=chain[chain.length-1];
			return head;
		}
		return null;
	}
	
	public static <T> void printList(Node<T> head)
	{
		while(head!=null)
		{
			System.out.print(head.value+" ");
			head=head.next;
		}
		System.out.println();
	}

}
