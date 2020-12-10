import java.io.PrintWriter;
import java.lang.String;

/**
 * Implementation of the run queue interface using an Ordered Link List.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan.
 */
public class OrderedLinkedListRQ implements Runqueue {
	protected List head;
	protected int length;

	/**
	 * Constructs empty linked list
	 */
	public OrderedLinkedListRQ() {
		// Implement me
		head = null;
		length = 0;
	} // end of OrderedLinkedList()

	@Override
	public void enqueue(String procLabel, int vt) {
		// Implement me
		List current;
		List newList = new List(procLabel, vt);
		if (head == null || head.getVt() > vt) {
			newList.next = head;
			head = newList;
			length++;
		} else {
			current = head;
			while (current.next != null && current.next.getVt() <= newList.getVt()) {
				current = current.next;
			}
			newList.next = current.next;
			current.next = newList;
			length++;
		}
	} // end of enqueue()

	@Override
	public String dequeue() {
		// Implement me
		String dqProc = head.getProcLabel();
		if (head == null) {
			return "";
		} else {
			head = head.next;
			length--;
		}
		return dqProc; // placeholder, modify this
	} // end of dequeue()

	@Override
	public boolean findProcess(String procLabel) {
		// Implement me
		List temp = head;
		for (int i = 0; i < length; i++) {
			if (temp.getProcLabel().equals(procLabel)) {
				return true;
			}
			temp = temp.getNext();
		}
		return false; // placeholder, modify this
	} // end of findProcess()

	@Override
	public boolean removeProcess(String procLabel) {
		// Implement me
		if (head == null) {
			return false;
		} else if (head.getProcLabel().equals(procLabel)) {
			head = head.getNext();
			length--;
			return true;
		} else if (head.next == null)
			return false;
		else {
			List temp = head;
			List temp2;
			for (int i = 0; i < length; i++) {
				temp2 = temp.next;
				if (temp2 == null) {
					return false;
				}
				if (temp2.getProcLabel().equals(procLabel)) {
					temp.next = temp2.next;
					length--;
					return true;
				}
				temp = temp.next;
			}
		}
		return false; // placeholder, modify this
	} // End of removeProcess()

	@Override
	public int precedingProcessTime(String procLabel) {
		// Implement me
		List temp = head;
		int totalTime = 0;
		if (head.getProcLabel().equals(procLabel)) {
			return 0;
		} else {
			for (int i = 0; i < length; i++) {
				if (!temp.getProcLabel().equals(procLabel)) {
					totalTime += temp.getVt();
				} else
					return totalTime;
				temp = temp.getNext();
			}
		}
		return -1; // placeholder, modify this
	} // end of precedingProcessTime()

	@Override
	public int succeedingProcessTime(String procLabel) {
		// Implement me
		List temp = head;
		int totalTime = 0;
		for (int i = 0; i < length; i++) {
			if (temp.getProcLabel().equals(procLabel)) {
				if (temp.getNext() == null) {
					return totalTime;
				}
				temp = temp.getNext();
				for (int j = i; j < length; j++) {
					if (temp == null) {
						break;
					}
					totalTime += temp.getVt();
					temp = temp.getNext();
				}
				return totalTime;
			}
			temp = temp.getNext();
		}
		return -1; // placeholder, modify this
	} // end of precedingProcessTime()

	@Override
	public void printAllProcesses(PrintWriter os) {
		// Implement me
		List temp = head;
		while (temp != null) {
			os.print(temp.getProcLabel() + " ");
			temp = temp.next;
		}
		os.println();
		os.flush();
	} // end of printAllProcess()

} // end of class OrderedLinkedListRQ

class List extends Proc {
	protected List next;

	public List(String procLabel, int vt) throws IllegalArgumentException {
		super(procLabel, vt);
		next = null;

	}

	public List getNext() {
		return next;
	}

	public void setNext(List next) {
		this.next = next;
	}
}