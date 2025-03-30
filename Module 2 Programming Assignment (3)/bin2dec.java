import java.util.Scanner;

// Defining our custom exception
class BinaryFormatException extends Exception {
    public BinaryFormatException() { // No-arg
        super("Not a binary number");
    }
    public BinaryFormatException(String message) { // Custom message
        super(message);
    }
}

public class bin2dec {
    public static int bin2Dec(String binaryString) throws BinaryFormatException {
        if (binaryString == null || binaryString.isEmpty()) {
            throw new BinaryFormatException(); // Nothing is still not a binary string.
        }
        
        int decimal = 0;
        for (int i = 0; i < binaryString.length() ; i++) {
            char c = binaryString.charAt(i);
            if (c != '0' && c != '1') {
                throw new BinaryFormatException();
            }
             // Shift the binary value one place over.
            decimal *= 2;
            // Subtract the Unicode value of '0' from the input, leaving just 0 or 1.
            c -= '0';
            // Add the input's value to the decimal number.
            decimal += c;
            /*  
                * Equivalent:
                * decimal = decimal * 2 + (c - '0');
            */
        }
        return decimal;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a binary number: ");
        String binaryString = input.nextLine();
        
        try {
            int decimal = bin2Dec(binaryString);
            System.out.println("The decimal number is: " + decimal);
        } catch (BinaryFormatException e) {
            System.out.println(e.getMessage());
        }
        
        input.close();
    }
}