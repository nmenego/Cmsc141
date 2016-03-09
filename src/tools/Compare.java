package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Compare {

	public static void main(String[] args) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
		BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
		List<String> fails = new ArrayList<>();
		String line1, line2;
		int count = 0;
		while ((line1 = br1.readLine()) != null) {
			count++;
			line2 = br2.readLine();

			if (!line1.equals(line2)) {
				fails.add("line: " + count + " expected: " + line1
						+ " output: " + line2);
			}
		}

		if (fails.size() == 0) {
			System.out.println("pass");
		} else {
			for (String fail : fails) {
				System.out.println(fail);
			}
		}
		br1.close();
		br2.close();
	}
}
