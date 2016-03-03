package ans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample solution to CMSC141 MP1.
 * 
 * @author NMEnego
 *
 */
public class MP1 {

	// tells us if current string is first line in code
	private boolean firstString;
	// storage for current state of our URM
	private String[] memory;
	// next line to execute
	private int nextLine = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("mp1.in"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("mp1.out"));
		List<String> codes = new ArrayList<>();
		List<String> output = new ArrayList<>();

		// load codes in a list. this might be a problem if code is too big.
		String line;
		while ((line = br.readLine()) != null) {
			codes.add(line);
		}

		MP1 mp1 = new MP1(true, null);
		output = mp1.execute(codes);

		for (String str : output) {
			bw.write(str);
			bw.newLine();
		}

		br.close();
		bw.close();
	}

	/**
	 * Constructor.
	 * 
	 * @param firstString
	 * @param memory
	 */
	public MP1(boolean firstString, String[] memory) {
		this.firstString = firstString;
		this.memory = memory;
	}

	public List<String> execute(List<String> codes) {
		List<String> list = new ArrayList<String>();
		for (nextLine = 0; nextLine < codes.size(); nextLine++) {
			String line = codes.get(nextLine);
			String out = parse(line);
			list.add(out);
		}
		return list;
	}

	private String parse(String line) {
		String[] splitString = line.split("\\s+");
		if (firstString) {
			// seed our memory with initial data
			firstString = false;
			memory = splitString;
		}

		else {
			String command = splitString[0];
			if (URMCommands.isSuccessor(command)) {
				successor(splitString[1]);
			} else if (URMCommands.isCopy(command)) {
				copy(splitString[1], splitString[2]);
			} else if (URMCommands.isZero(command)) {
				zero(splitString[1]);
			} else if (URMCommands.isJump(command)) {
				jump(splitString[1], splitString[2], splitString[3]);
			} else {
				// TODO invalid command!
			}
		}
		return printMemory();
	}

	private void jump(String index1, String index2, String codeLine) {
		int i1 = Integer.parseInt(index1);
		int i2 = Integer.parseInt(index2);
		int line = Integer.parseInt(codeLine);
		if (memory[i1].equals(memory[i2])) {
			// goto codeLine
			nextLine = line;
		} else {
			// do nothing
		}
	}

	private void zero(String index) {
		int i1 = Integer.parseInt(index);
		memory[i1] = "0";
	}

	private void copy(String index1, String index2) {
		int i1 = Integer.parseInt(index1);
		int i2 = Integer.parseInt(index2);
		memory[i2] = memory[i1];
	}

	private void successor(String index) {
		int i = Integer.parseInt(index);
		int curVal = Integer.parseInt(memory[i]);
		memory[i] = String.valueOf(++curVal);
	}

	// generate a string to represent current state of memory
	private String printMemory() {
		StringBuilder nameBuilder = new StringBuilder();

		for (String n : memory) {
			nameBuilder.append(n).append(" ");
		}

		// remove last space
		nameBuilder.deleteCharAt(nameBuilder.length() - 1);

		return nameBuilder.toString();
	}

	enum URMCommands {
		S, C, Z, J;

		public static boolean isSuccessor(String command) {
			if (S.name().equalsIgnoreCase(command)) {
				return true;
			}
			return false;
		}

		public static boolean isCopy(String command) {
			if (C.name().equalsIgnoreCase(command)) {
				return true;
			}
			return false;
		}

		public static boolean isZero(String command) {
			if (Z.name().equalsIgnoreCase(command)) {
				return true;
			}
			return false;
		}

		public static boolean isJump(String command) {
			if (J.name().equalsIgnoreCase(command)) {
				return true;
			}
			return false;
		}
	}

}
