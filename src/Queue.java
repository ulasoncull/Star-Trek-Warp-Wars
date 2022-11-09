
public class Queue {
	private int rear, front;
	private Object[] items;
	
	Queue(int capasity){
		items = new Object[capasity];
		rear = -1;
		front =0;
	}

	void enqueue(Object data) {
		if(isFull()) {
			System.out.println("Queue overflow !");
			
		}else {
			rear = (rear+1) % items.length;
			items[rear] = data;
			
		}
	}
	
	Object dequeue() {
		if(isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
		}else {
			Object reData = items[front];
			items[front] = null;
			front = (front+1) % items.length;
			return reData;
			
		}
	}
	
	
	
	Object Peek() {
		if(isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
			
		}else {
			return items[front];
		}
	}
	
	
	boolean isEmpty() {
		return items[front] == null;
	}
	
	boolean isFull() {
		return ( front==(rear+1)%items.length && items[front]!=null && items[rear]!=null  ) ;
	}
	
	int size() {
		if(items[front] == null) {
			return 0;
			
		}else {
			if(rear>=front) {
				return rear-front+1 ;
				
			}else {
				return items.length - (front-rear) +1 ;
			}
			
		}
	}
	
	
}















