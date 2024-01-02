
public class DLinkedNode<T> {
	
	private T dataItem;
	private double priority;
	
	private DLinkedNode<T> next;
	private DLinkedNode<T> prev;
	
	//initialize 4 variables in the constructor
	public DLinkedNode (T data, double prio) {
		
		this.dataItem = data;
		this.priority = prio;
		this.next = null;
		this.prev = null;
	}
	
	
	//get methods below for priority, data item, next and prev 
	public double getPriority() {
		return priority;
	}
	
	public T getDataItem () {
		return dataItem;
	}
	
	public DLinkedNode<T> getNext() {
		return next;
	}
	
	public DLinkedNode<T> getPrev() {
		return prev;
	}
	
	
	
	
	
	
	
	//set methods below
	public void setDataItem(T data) {
		this.dataItem = data;
	}
	
	public void setPriority(double prio) {
		this.priority = prio;
	}
	
	public void setNext (DLinkedNode<T> node) {
		this.next = node;
	}
	
	public void setPrev (DLinkedNode<T> node) {
		this.prev = node;
	}
	
}
