import java.io.PrintWriter;
import java.lang.String;

/**
 * Implementation of the Runqueue interface using a Binary Search Tree.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan
 */
public class BinarySearchTreeRQ implements Runqueue {

	private Node root;
	private String dequeueLabel;
	private int ptProcess;
	private int stProcess;

	/**
	 * Constructs empty queue
	 */
	public BinarySearchTreeRQ() {
		// Implement Me
		root = null;
		dequeueLabel = "";
	} // end of BinarySearchTreeRQ()

	@Override
	public void enqueue(String procLabel, int vt) {
		// Implement me
		if (!(findProcess(procLabel))) {
			root = enqueue(root, procLabel, vt);
		}
	}

	// An operation to insert new elements. If the current node is null, it
	// has reached a leaf node is a new node
	// is inserted in that position.
	private Node enqueue(Node node, String procLabel, int vt) {
		if (node == null) {
			return new Node(procLabel, vt);
		}

		// If the new node's value is lower than the current node's, we go to the left
		// child
		if ((node.getVt() > vt) && (!(node.getProcLabel().equals(procLabel)))) {
			node.setLeft(enqueue(node.getLeft(), procLabel, vt));
		}
		// If the new node's value is greater than the current node's, we go to the
		// right child
		else if ((node.getVt() <= vt) && (!(node.getProcLabel().equals(procLabel)))) {
			node.setRight(enqueue(node.getRight(), procLabel, vt));
		}
		return node;
	}

	@Override
	public String dequeue() {
		dequeueLabel = "";
		root = dequeue(root);
		return dequeueLabel;
	}

	// An operation to delete elements from the tree, on FIFO basis.
	private Node dequeue(Node node) {

		if (node == null) {
			return node;
		}

		if (node.getLeft() != null) {
			node.setLeft(dequeue(node.getLeft()));

		} else {

			if (node.getRight() == null) {
				dequeueLabel = node.getProcLabel();
				return null;
			} else {
				dequeueLabel = node.getProcLabel();
				return node.getRight();
			}
		}
		return node;
	} // End of dequeue()

	@Override
	public boolean findProcess(String procLabel) {
		// Implement me
		if (containsProcess(root, procLabel))
			return true;
		else
			return false;
	} // end of findProcess()

	private boolean containsProcess(Node node, String procLabel) {
		if (node == null) {
			return false;
		}
		if (node.getProcLabel().equals(procLabel)) {
			return true;
		}

		boolean containsLeft = containsProcess(node.getLeft(), procLabel);
		if (containsLeft)
			return true;

		boolean containsRight = containsProcess(node.getRight(), procLabel);
		return containsRight;

	}

	@Override
	public boolean removeProcess(String procLabel) {
		// Implement me
		boolean deleted = false;

		if (containsProcess(root, procLabel)) {
			root = removeProcess(root, procLabel);
			deleted = true;
		}

		return deleted;
	} // end of removeProcess()

	// Removal of node from the tree
	private Node removeProcess(Node node, String procLabel) {
		if (node == null) {
			return null;
		}

		if (node.getProcLabel().equals(procLabel)) {

			// Case 1: Node has no children and will be replaced with 'null'
			if (node.getLeft() == null && node.getRight() == null) {
				return null;
			}

			// Case 2: Node has exactly one child and will be replaced with its only child.
			if (node.getRight() == null) {
				return (node.getLeft());
			}
			if (node.getLeft() == null) {
				return (node.getRight());
			}

			// Case 3: Node has 2 children. Use the smallest node to be deleted's right
			// sub-tree.
			Node smallestValue = findSmallestNode(node.getRight());
			node.setVt(smallestValue.getVt());
			node.setProcLabel(smallestValue.getProcLabel());
			node.setRight(removeProcess(node.getRight(), smallestValue.getProcLabel()));
			return node;
		}

		node.setRight(removeProcess(node.getRight(), procLabel));
		node.setLeft(removeProcess(node.getLeft(), procLabel));
		return node;
	}

	// Assigning the smallest value to the node.
	private static Node findSmallestNode(Node node) {
		return (node.getLeft() == null ? node : findSmallestNode(node.getLeft()));
	}

	@Override
	public int precedingProcessTime(String procLabel) {
		// Implement me
		// Set value if no process is found
		ptProcess = -1;

		if (root != null) {
			if (root.getProcLabel().equals(procLabel)) {
				ptProcess = getProcessTotal(root.getLeft());
			} else {
				root = startingNodePT(root, procLabel);
			}

		}
		return ptProcess; // placeholder, modify this
	} // end of precedingProcessTime()

	private Node startingNodePT(Node node, String procLabel) {

		if (node == null) {
			return null;
		}

		if ((node.getRight() != null) && (node.getRight().getProcLabel().equals(procLabel))) {
			int parentTotal = getProcessTotal(node.getLeft());
			int childTotal = getProcessTotal(node.getRight().getLeft());
			ptProcess = node.getVt() + parentTotal + childTotal;
			return node;
		}

		if ((node.getLeft() != null) && (node.getLeft().getProcLabel().equals(procLabel))) {
			ptProcess = getProcessTotal(node.getLeft().getLeft());
			return node;
		}

		startingNodePT(node.getRight(), procLabel);
		startingNodePT(node.getLeft(), procLabel);

		return node;
	}

	private int getProcessTotal(Node node) {
		int total = 0;
		return total = addProcess(node);
	}

	private int addProcess(Node node) {
		return (node == null ? 0 : addProcess(node.getLeft()) + node.getVt() + addProcess(node.getRight()));
	}

	@Override
	public int succeedingProcessTime(String procLabel) {
		// Implement me
		stProcess = -1;

		if ((root != null) && (root.getProcLabel().equals(procLabel))) {
			stProcess = getProcessTotal(root.getRight());
		} else {
			root = startingNodeST(root, procLabel);
		}

		return stProcess; // placeholder, modify this
	} // end of precedingProcessTime()

	private Node startingNodeST(Node node, String procLabel) {
		if (node == null) {
			return null;
		}

		if ((node.getRight() != null) && (node.getRight().getProcLabel().equals(procLabel))) {
			stProcess = getProcessTotal(node.getRight().getRight());
			return node;
		}

		if ((node.getLeft() != null) && (node.getLeft().getProcLabel().equals(procLabel))) {
			int parentTotal = getProcessTotal(node.getRight());
			int childTotal = getProcessTotal(node.getLeft().getRight());
			stProcess = parentTotal + node.getVt() + childTotal;
			return node;
		}

		startingNodeST(node.getLeft(), procLabel);
		startingNodeST(node.getRight(), procLabel);

		return node;
	}

	@Override
	public void printAllProcesses(PrintWriter os) {
		// Implement me
		os.println(printAll());
	} // end of printAllProcess()

	private String printAll() {
		return toStringAll(root);
	}

	// Prints procLabels based on priority
	private static String toStringAll(Node node) {
		return (node == null ? ""
				: toStringAll(node.getLeft()) + node.getProcLabel() + " " + toStringAll(node.getRight()));
	}

	private class Node extends Proc {

		private Node nLeft;
		private Node nRight;

		public Node(String procLabel, int vt) throws IllegalArgumentException {
			super(procLabel, vt);
			this.nLeft = null;
			this.nRight = null;
		}

		public Node getLeft() {
			return nLeft;
		}

		public Node getRight() {
			return nRight;
		}

		public void setRight(Node right) {
			nRight = right;
		}

		public void setLeft(Node left) {
			nLeft = left;
		}

	}

} // end of class BinarySearchTreeRQ
