package ans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Sample solution to CMSC141 MP2. Man Lion Rabbit carrot river crossing
 * problem.
 * 
 * @author NMEnego
 *
 */
public class MP2 {

	// holds the current state.
	private States currentState;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));

		// load codes in a list. this might be a problem if code is too big.
		String line;
		while ((line = br.readLine()) != null) {
			MP2 mp2 = new MP2();
			boolean isValid = mp2.isValid(line);
			if (isValid) {
				bw.write("OK");
			} else {
				bw.write("NG");
			}
			bw.newLine();
		}
		br.close();
		bw.close();

	}

	/**
	 * Constructor.
	 */
	public MP2() {
		// set default initial state.
		this.currentState = States.MLRC_;
	}

	/**
	 * Special constructor if we want to specify initial state.
	 * 
	 * @param initialState
	 */
	public MP2(States initialState) {
		this.currentState = initialState;
	}

	// checks if given solution is valid
	private boolean isValid(String solution) {
//		System.out.println(solution + " "+currentState.name());
		int size = solution.length();
		for (int i = 0; i < size; i++) {
			char curr = solution.charAt(i);
			int moveIndex = -1;
			if (curr == 'N') {
				moveIndex = 0;
			} else if (curr == 'L') {
				moveIndex = 1;
			} else if (curr == 'R') {
				moveIndex = 2;
			} else if (curr == 'C') {
				moveIndex = 3;
			}

			// weed out invalid states
			if (moveIndex >= 0 && currentState.getIdx() >= 0) {
				// swap states
				currentState = STATE_TABLE[currentState.getIdx()][moveIndex];
//				System.out.println(curr + " " + currentState.name());
			} else {
//				System.out.println(currentState.getIdx() + " " + curr
//						+ " count: " + i);
				return false;
			}
		}

		if (currentState == States._MLRC) {
			return true;
		} else {
			return false;
		}
	}
	// this table represents the transition diagram
	// M L R C
	private static final States[][] STATE_TABLE = new States[][]{
			// MAN LION RABBIT CARROT
			{States.LOSE, States.LOSE, States.LC_MR, States.LOSE}, // MLRC_
			{States.MLC_R, States.ERROR, States.MLRC_, States.ERROR}, // LC_MR
			{States.LC_MR, States.C_MLR, States.ERROR, States.L_MRC}, // MLC_R
			{States.LOSE, States.ERROR, States.MLR_C, States.MLC_R}, // L_MRC
			{States.LOSE, States.R_MLC, States.L_MRC, States.ERROR}, // MLR_C
			{States.MR_LC, States.MLR_C, States.ERROR, States.MRC_L}, // R_MLC
			{States.R_MLC, States.ERROR, States._MLRC, States.ERROR}, // MR_LC
			{States.LOSE, States.MLC_R, States.MRC_L, States.ERROR}, // C_MLR
			{States.LOSE, States.ERROR, States.C_MLR, States.R_MLC}, // MRC_L
			{States.LOSE, States.LOSE, States.MR_LC, States.LOSE}, // _MLRC

	};

	/**
	 * An enum to represent the states of the problem.
	 */
	enum States {
		LOSE(-2), // lion eats rabbit, rabbit eats carrot
		ERROR(-1), // invalid moves were made
		MLRC_(0), // start state
		LC_MR(1), // lion carrot - man rabbit
		MLC_R(2), // man lion carrot - rabbit
		L_MRC(3), // lion - man rabbit carrot
		MLR_C(4), // man lion rabbit - carrot
		R_MLC(5), // rabbit - man lion carrot
		MR_LC(6), // man rabbit - lion carrot
		C_MLR(7), // carrot - man lion rabbit
		MRC_L(8), // man rabbit carrot - lion
		_MLRC(9); // final state, winning state!

		private final int idx;

		States(int idx) {
			this.idx = idx;
		}

		public int getIdx() {
			return idx;
		}

		public static States getState(int state) {
			for (States s : States.values()) {
				if (s.getIdx() == state)
					return s;
			}
			throw new IllegalArgumentException("Leg not found. Amputated?");
		}
	}

}
