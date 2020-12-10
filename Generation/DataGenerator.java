

import java.io.*;
import java.util.Random;

public class DataGenerator {
	protected static final String progName = "Data Generator";
	protected int start = 1;
	protected int end = 100;
	protected String process;
	protected Random randGen;

	public DataGenerator(String process) throws IllegalArgumentException {
		this.process = process;
		// use current time as seed
		randGen = new Random(System.currentTimeMillis());
	} // end of DataGenerator()

	public int sampleWithReplacement() {
		return randGen.nextInt(end - start + 1) + start;
	} // end of sampleWithReplacement()

	public int[] sampleWithReplacement(int sampleSize) {
		int[] samples = new int[sampleSize];

		for (int i = 0; i < sampleSize; i++) {
			samples[i] = sampleWithReplacement();
		}
		return samples;
	} // end of sampleWithReplacement()

	public static void usage() {
		System.err.println(progName + ":<number of values to sample> <File Name> <type of process>");
		System.err.println("<filename should be without file extension> ");
		System.err.println("<type of process> = {EN | DE | PT}.");
		System.exit(1);
	} // end of usage()

	public static void main(String[] args) {
		// check correct number of command line arguments
		if (args.length != 3) {
			usage();
		}
		try {
			int sampleSize = Integer.parseInt(args[0]);
			String fileName = args[1];
			String process = args[2];
			DataGenerator gen = new DataGenerator(process);
			File f = new File(fileName + ".in");
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			int[] samples = null;
			samples = gen.sampleWithReplacement(sampleSize);
			if (process.equals("EN")) {
				if (samples != null) {
					for (int i = 0; i < samples.length; i++) {
						int sample = samples[i];
						out.println(process + " P" + (i + 1) + " " + sample);
					}
				}
				out.close();
			} else if (process.equals("PT")) {
				if (samples != null) {
					for (int i = 0; i < samples.length; i++) {
						out.println(process + " P" + (i + 1));
					}
				}
				out.close();
			} else if (process.equals("DE")) {
				if (samples != null) {
					for (int i = 0; i < samples.length; i++) {
						out.println(process);
					}
				}
				out.close();
			} else {
				out.close();
				System.err.println(process + " is an unknown unknown.");
				usage();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			usage();
		}
	} // end of main()
} // end of class DataGenerator