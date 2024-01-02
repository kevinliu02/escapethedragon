public class DLPriorityQueue<T> implements PriorityQueueADT<T> {

    private DLinkedNode<T> front;
    private DLinkedNode<T> rear;
    private int count;

    
    //constructor that initializes the values of count, front, and rear
    public DLPriorityQueue() {
    	count = 0;
        front = null;
        rear = null;
    }

    //adds new node to the priority queue given data item and priority
    public void add(T dataItem, double priority) {
    	
        DLinkedNode<T> newNode = new DLinkedNode<T>(dataItem, priority);
        //count increases when a new node is added to the doubly linked list
        count = count + 1;
        
        
        //if  the priority queue is empty then point both front and rear node to the new node
        if (front == null) {
     
            front = newNode;
            rear = newNode;
            
        //the new node as smallest priority so it will be the new front
        } else if (front.getPriority() < priority) {
        	
       
        	DLinkedNode<T> curr = front.getNext();
            
        	//traverse linked list until we find right spot based on priority numbers to put new node
            while (curr != null && curr.getPriority() < priority) {
                curr = curr.getNext();
            }
            
            //the right spot is found and the new node is inserted before the current node
            if (curr != null) { 
            	newNode.setNext(curr);
            	newNode.setPrev(curr.getPrev());
                
                curr.getPrev().setNext(newNode);
                curr.setPrev(newNode);
                
                
            } else {
            //if new node does not have lower priority than any existing node, then
            //insert it at the end of list, so that rear points to it
            	newNode.setPrev(rear);
                rear.setNext(newNode);
                rear = newNode;
            }
            
            
            
            
        } else {
        	//insert new node at front of the list
        	front.setPrev(newNode);
            newNode.setNext(front);
           
            front = newNode;
            
        }
       
    }
    
    
    

    public void updatePriority(T dataItem, double newPriority) throws InvalidElementException {
    	
        DLinkedNode<T> curr = front;
        double oldPriority;
        
        //given the data item, find the node with that item
        while (curr != null && curr.getDataItem() != null && dataItem != null && !(curr.getDataItem()).equals(dataItem)) {
            curr = curr.getNext();
        }
        
        //throw execption if node is not found
        if (curr == null) {
            throw new InvalidElementException("Data item not found");
        }
        
        
        //store the node's old priority in a variable
        oldPriority = curr.getPriority();
        
        //update priority of the node to the new priority value
        curr.setPriority(newPriority);
        
        
        if (oldPriority > newPriority) {
        	// if the node's new priority is a lower value than it was before, then move it towards the front of the list
            if (rear != curr) {
            	//if the node is not at the rear of the queue, then move its next node's previous pointer to become its previous node
            	if(curr.getNext() != null)
            		curr.getNext().setPrev(curr.getPrev());
            } else {
            	//if the node is at rear of queue, then update the rear pointer to its previous node
                rear = curr.getPrev();
            }
            
            //we can delete the node with the outdated old priority by getting the pointer that was pointing to current and point it to the node after current
            if(curr.getNext() != null)
            	curr.getPrev().setNext(curr.getNext());
            
            
       
            
            if (front.getPriority() < newPriority) {
            	
            	DLinkedNode<T> temp = front;
            	
            	//traverse doubly linked list until the right priority spot to insert new node
                if (newPriority > temp.getNext().getPriority()&& temp.getNext() != null) {
                    temp = temp.getNext();
                } 
                
                curr.setPrev(temp);
                curr.setNext(temp.getNext());
                
                temp.setNext(curr);
                temp.getNext().setPrev(curr);
                
                
            } else {
            	//insert node at the front of doubly linked list
            	curr.setNext(front);
                curr.setPrev(null);
                
                front.setPrev(curr);
                front = curr;
                
     
            }
            	
            
        } else { //this means that new priority is not smaller than old priority
        	// Move the node towards the rear of the list
            if (curr != front) {
            	if(curr.getNext() != null)
            		curr.getPrev().setNext(curr.getNext());
                
            } else {
            	front = curr.getNext();
            }
            if(curr.getNext() != null)
            	curr.getNext().setPrev(curr.getPrev());
            
            if (newPriority >= rear.getPriority()) {
            	
                curr.setPrev(rear);
                curr.setNext(null);
                
                rear.setNext(curr);
                rear = curr;
                
            } else {
            	//this means the new priority of the node is less than the priority of rear
            	
                DLinkedNode<T> temp = rear;
                
                while(temp.getPrev() != null && newPriority < temp.getPrev().getPriority()) {
                    temp = temp.getPrev();
                }
                
                
                curr.setNext(temp);
                curr.setPrev(temp.getPrev());
                
                temp.getPrev().setNext(curr);
                temp.setPrev(curr);
        
        
            }
        }

    }
    
    
    public T removeMin() throws EmptyPriorityQueueException {
    	
        if (isEmpty() != true) {
        	--count;
        		if(this.front != null) {
        		T dataItem = front.getDataItem();
        		
        	 
        	 //remove the front node by changing the pointer that front points to to point the node after it
             if (count > 1 && front != rear) {
                
            	 front = front.getNext();

                 
        	 //if only one node in the list then remove it by setting front and 
        	 //rear both to null
             } else {
            	   front = null;
                   rear = null;
                   
             }
   
             return dataItem;
        		}
        		else {
        			T dataItem = null;
        			return dataItem;
        		}
        } else {
        	throw new EmptyPriorityQueueException("Priority queue is empty");
        }
    }

    public boolean isEmpty() {
    	//variable bool stores true if count equals 0, false otherwise
    	boolean bool = (count == 0);
        return bool;
    }

    //variable count determines the length of linked list by decreasing by 1 when a node is removed
    //and increasing by 1 when a node is added
    public int size() {
        return count;
    }

    
    
    
    
    
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        DLinkedNode <T> curr = front;
        
        //As long as the node is not null, keep appending the node's data item to str and moving to the next node
        while (!(curr == null)) {
        	
            str.append(curr.getDataItem());
            curr = curr.getNext();
        }
       //convert the str object of StringBuilder type into String type to return
        String result = str.toString();
        
        return result;
    }

    
    
    public DLinkedNode<T> getRear() throws EmptyPriorityQueueException {
        //if the queue is not empty then return the rear node, otherwise throw exception telling user the queue is empty
    	if (isEmpty() != true)
    		return rear;
    		
        else
        	throw new EmptyPriorityQueueException("Priority queue is empty");
            
    }
}
