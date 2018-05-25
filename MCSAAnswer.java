// Joe

import java.util.Scanner;

public class MCSAAnswer extends MCAnswer {

	protected MCSAAnswer(String text, double creditIfSelected) {
		super(text, creditIfSelected);
	}
	
	public MCSAAnswer(Scanner inp) {
		super(inp);
	}
}