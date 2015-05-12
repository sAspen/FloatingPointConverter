import java.util.Scanner;

public class FloatingPointConverter {
	static final short MANTRISSA_LENGTH = 23, EXPONENT_LENGTH = 8;
	short fractionBinaryLength = 0;
	short integerBinaryLength = 0;
	
	/**
	 * @param input
	 */
	FloatingPointConverter() {
	}

	String integerToBinaryString(int input) {
		String s = "";
		int i, j = 0;
		
		for (i = 1; input >= i; i *= 2);
		if (i == 1) return "0";
		
		i /= 2;
		do {
			if (input >= i)  {
				s += "1";
				input -= i;
			} else 
				s += "0";
			
//			if (++j == 3) {
//				s += " ";
//				j = 0;
//			}
			
			i /= 2;
			integerBinaryLength++;
		} while (i > 1);
		
		s = s.trim();
		
		return s;
	}

	String fractionToBinaryString(int input) {
		String s = "", t;
		int ceiling, i = 0, j = 0, k;
		
		System.out.println("fractionToBinaryString(" + input + ")");
		
		if (input == 0) return "0";
		
		for (ceiling = 10; input >= ceiling; ceiling *= 10);
		System.out.println("Ceiling: " + ceiling);
		
		while (input > 0 && i <= MANTRISSA_LENGTH) {
			for (k = input, t = ""; k < ceiling / 10; k *= 10, t += "0");
			System.out.print("0." + t + input + " * 2 = ");
			j = input * 2;
			
			if (j >= ceiling) {
				for (k = j, t = ""; k - ceiling < ceiling / 10; k *= 10, t += "0");
				System.out.println("1." + t + (j - ceiling));
			} else {
				for (k = j, t = ""; k < ceiling / 10; k *= 10, t += "0");
				System.out.println("0." + t + j);
			}
			
			input *= 2;
			
			if (input >= ceiling) {
				s += "1";
				input -= ceiling;
			} else 
				s += "0";
			
//			if (++j == 3) {
//				s += " ";
//				j = 0;
//			}
			
			i++;
			fractionBinaryLength++;
		}
		
		return s;
	}
	
/*	String fractionToBinaryStringHex(int input) {
		String s = "", t;
		int ceiling, i = 0, j = 0, k;
		
		System.out.println("fractionToBinaryStringHex(" + input + ")");
		
		if (input == 0) return "0";
		
		for (ceiling = 10; input >= ceiling; ceiling *= 10);
		System.out.println("Ceiling: " + ceiling);
		
		while (input > 0 && i < MANTRISSA_LENGTH) {
			j = input * 16;
			
			input *= 16;
			
			if (input >= ceiling) {
				System.out.println("test " + (input / 16) + ", " + input + ", " + Integer.toHexString(input) + ", " + Integer.toHexString(input).substring(0, 1) + ", " + Integer.parseInt(Integer.toHexString(input).substring(0, 1), 16) + ", " + Integer.toBinaryString(Integer.parseInt(Integer.toHexString(input).substring(0, 1), 16)));
			
				s += Integer.toBinaryString(Integer.parseInt(Integer.toHexString(input).substring(0, 1), 16));
				input -= Integer.parseInt(Integer.toHexString(input).substring(0, 1), 16);
				} else 
				s += "0";
			
//			for (k = input, t = ""; k < ceiling / 10; k *= 16, t += "0");
//			System.out.print("0." + t + Integer.toHexString(input) + " * 16 = ");
//			j = input * 16;
//			
//			if (j >= ceiling) {
//				for (k = j, t = ""; k - Integer.parseInt("" + Integer.toHexString(ceiling).charAt(0), 16) < ceiling / 10; k *= 16, t += "0");
//				System.out.println(Integer.toHexString(ceiling).charAt(0) + "." + t + Integer.toHexString((j - Integer.parseInt("" + Integer.toHexString(ceiling).charAt(0), 16))));
//			} else {
//				for (k = j, t = ""; k < ceiling / 10; k *= 16, t += "0");
//				System.out.println("0." + t + Integer.toHexString(j));
//			}
//			
//			input *= 16;
//			
//			if (input >= ceiling) {
//				s += Integer.toBinaryString(Integer.parseInt("" + Integer.toHexString(input).charAt(0), 16));
//				input -= Integer.parseInt("" + Integer.toHexString(input).charAt(0), 16);
//			} else 
//				s += "0";
//			
//			if (++j == 3) {
//				s += " ";
//				j = 0;
//			}
			
			i += 4;
			fractionBinaryLength += 4;
		}
		
		return s;
	}*/
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int integerPart, fractionPart, exponent, i;
		String integerBinary, fractionBinary, representation, inputString, s;
		FloatingPointConverter fpc = new FloatingPointConverter();
		
		System.out.println("Decimal to floating point converter v0.1");
//		System.out.println("Input integer part of the number:");
//		integerPart = input.nextInt();
//		System.out.println("Input decimal part of the number:");
//		fractionPart = input.nextInt();
		
		inputString = input.next();
		integerPart = Integer.parseInt(inputString.substring(0, inputString.indexOf(".")));
		fractionPart = Integer.parseInt(inputString.substring(inputString.indexOf(".") + 1));
		
		integerBinary = Integer.toBinaryString(Math.abs(integerPart));
		fractionBinary = fpc.fractionToBinaryString(fractionPart);
		
		System.out.println(integerPart + "." + fractionPart + " in binary:\n" + integerBinary + "." + fractionBinary);
		
		
		if (integerPart != 0) 
			exponent = 127 + integerBinary.length() - 1;
		else if (fractionPart != 0)
			exponent = 127 - fractionBinary.indexOf("1");
		else 
			exponent = 0;
		
		representation = (inputString.charAt(0) == '-' ? "1 " : "0 ");
		
		for (s = "", i = Integer.toBinaryString(exponent).length(); i < EXPONENT_LENGTH; s += "0", i++);
		representation += s + Integer.toBinaryString(exponent) + " ";
		
		if (integerPart != 0) {
			s = integerBinary + fractionBinary;
			representation += s.substring(1, s.length() - 1 < MANTRISSA_LENGTH + 1 ? s.length() : MANTRISSA_LENGTH + 1);
		} else if (fractionPart != 0) 
			representation += fractionBinary.substring(fractionBinary.indexOf("1") + 1);
		
		for (s = "", i = fpc.fractionBinaryLength - 1; i < MANTRISSA_LENGTH; s += "0", i++);
		representation += s;
		
		System.out.println("Floating point representation:\n" + representation);
	}
}
