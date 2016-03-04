package ans;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MP2Generator {

	static BufferedWriter bw;
	public static void main(String[] args) throws IOException {

		bw = new BufferedWriter(new FileWriter("mp2.in"));
		// Create an alphabet to work with
		char[] alphabet = new char[]{'N', 'L', 'R', 'C'};
		possibleStrings(7, alphabet, "");
		possibleStrings(9, alphabet, "");
		bw.close();

	}
	public static void possibleStrings(int maxLength, char[] alphabet,
			String curr) throws IOException {

		// If the current string has reached it's maximum length
		if (curr.length() == maxLength) {
			bw.write(curr);
			bw.newLine();
			// Else add each letter from the alphabet to new strings and process
			// these new strings again
		} else {
			for (int i = 0; i < alphabet.length; i++) {
				String oldCurr = curr;
				curr += alphabet[i];
				possibleStrings(maxLength, alphabet, curr);
				curr = oldCurr;
			}
		}
	}
}
