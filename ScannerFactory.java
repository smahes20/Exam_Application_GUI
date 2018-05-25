import java.util.Scanner;

public class ScannerFactory {

	private static Scanner keyboardScanner;

	public static Scanner getScanner() {
		if (keyboardScanner == null) {
			keyboardScanner = new Scanner(System.in);
		}
		return keyboardScanner;
	}
}