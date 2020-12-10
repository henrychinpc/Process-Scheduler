

public abstract class Proc {

		private String procLabel;
		private int vt;
		
		public Proc(String procLabel, int vt) throws IllegalArgumentException{
			//proc is the 'value' element of a node. Node can be created that are null, or
			//procLabel is of regex form "P[0-9]" and vt is a positive integer
			
			if (procLabel.charAt(0) == 'P' && (int)procLabel.charAt(1)>0) {
				this.procLabel = procLabel;
			}
			else {
				throw new IllegalArgumentException("Process Label is not valid.");
			}	
			if (vt >= 0) {
				this.vt = vt;
			}
			else {
				throw new IllegalArgumentException("vtruntime must be positive");
			}
		}
		
		public String getProcLabel() {
			return procLabel;
		}
		
		public int getVt() {
			return vt;
		}
		
		public void setProcLabel(String procLabel) throws IllegalArgumentException {
			//procLabel must be of form: 'P[0-9]' 
			
			if (procLabel.charAt(0) == 'P' && (int)procLabel.charAt(1)>0) {
				this.procLabel = procLabel;
			}
			else {
				throw new IllegalArgumentException("Process Label is not valid.");
			}	
		}
		
		public void setVt(int vt) throws IllegalArgumentException {
			//vt must be a positive integer
			
			if (vt > 0) {
				this.vt = vt;
			}
			else {
				throw new IllegalArgumentException("vtruntime must be positive");
			}
		}
		
}