import java.io.PrintWriter;
import java.lang.String;

/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan
 */
public class OrderedArrayRQ implements Runqueue {
	protected Structure arr[];

	/**
	 * Constructs empty queue
	 */
	public OrderedArrayRQ() {
		// Implement Me

		arr = null;
	} // end of OrderedArrayRQ()

	@Override
	public void enqueue(String procLabel, int vt) throws IllegalArgumentException {
		// Implement me
		if (arr == null) {
			arr = new Structure[1];
			arr[0] = new Structure(procLabel, vt);
		} else {
			if (isUniqueLabel(procLabel)) {
				Structure tempArr[] = new Structure[arr.length + 1];

				for (int i = 0; i < arr.length; i++) {
					tempArr[i] = new Structure(arr[i].getProcLabel(), arr[i].getVt());
				}

				tempArr[arr.length] = new Structure(procLabel, vt);
				arr = tempArr;
				Structure tempStruct = new Structure(procLabel, vt);
				for (int i = arr.length - 1; i > 0; i--) {
					if (arr[i].getVt() < arr[i - 1].getVt()) {
						tempStruct = arr[i];
						arr[i] = arr[i - 1];
						arr[i - 1] = tempStruct;
					} else {
						break;
					}
				}

			} else {

				throw new IllegalArgumentException("Process already exists.");
			}
		}
	} // end of enqueue()

	@Override
	public String dequeue() {
		// Implement me
		String dqProc = null;
		dqProc = arr[0].getProcLabel();

		if (arr.length == 1) {
			arr = null;
			return dqProc;
		}
		Structure tempArr[] = new Structure[arr.length - 1];

		for (int i = 0; i < arr.length - 1; i++) {
			tempArr[i] = arr[i + 1];
		}
		arr = tempArr;

		return dqProc;

		// placeholder,modify this
	} // end of dequeue()

	@Override
	public boolean findProcess(String procLabel) {
		// Implement me
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getProcLabel().equals(procLabel)) {
				return true;
			}
		}
		return false; // placeholder, modify this
	} // end of findProcess()

	@Override
	public boolean removeProcess(String procLabel) {
		// Implement me
		boolean found = false;
		if (arr.length == 0) {
			return false;
		} else if (arr.length == 1 && arr[0].getProcLabel().equals(procLabel)) {
			arr = null;
			return true;
		} else {
			Structure tempArr[] = new Structure[arr.length - 1];
			for (int i = 0, j = 0; i < arr.length && j < arr.length - 1; i++) {
				if (arr[i].getProcLabel().equals(procLabel)) {
					found = true;
					continue;
				}
				tempArr[j] = arr[i];
				j++;
			}

			arr = tempArr;
			return found;
		}
	} // end of removeProcess()

	@Override
	public int precedingProcessTime(String procLabel) {
		// Implement me
		int totalTime = 0;
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].getProcLabel().equals(procLabel)) {
				totalTime += arr[i].getVt();
			} else {
				return totalTime;

			}
		}
		return -1;// placeholder, modify this
	}// end of precedingProcessTime()

	@Override
	public int succeedingProcessTime(String procLabel) {
		// Implement me

		int totalTime = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getProcLabel().equals(procLabel)) {
				for (int j = i + 1; j < arr.length; j++) {
					totalTime += arr[j].getVt();
				}
				return totalTime;
			}
		}

		return -1; // placeholder, modify this

	} // end of succeedingProcessTime()

	@Override
	public void printAllProcesses(PrintWriter os) {
		// Implement me
		if (arr == null) {
			os.print("Empty Queue");
		} else {
			for (int i = 0; i < arr.length; i++) {
				os.print(arr[i].getProcLabel() + " ");
			}
		}
		os.println();
		os.flush();
	} // end of printAllProcesses()

	// end of class OrderedArrayRQ

	private boolean isUniqueLabel(String procLabel) {
		if (arr.length == 0) // nothing in the list therefore procLabel is unique
			return true;
		else { // traverse list to compare procLabels
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].getProcLabel().equals(procLabel))
					return false;
			}
			return true;
		}
	}
}

class Structure extends Proc {

	public Structure(String procLabel, int vt) throws IllegalArgumentException {
		super(procLabel, vt);
	}

}