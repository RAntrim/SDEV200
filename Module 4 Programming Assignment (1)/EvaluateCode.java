import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class EvaluateCode
{
    public static boolean checkSymbols(String filePath) throws IOException
    {
        Stack<Character> stack = new Stack<>();
        boolean insideSingleQuote = false;
        boolean insideDoubleQuote = false;

        try (Scanner scanner = new Scanner(new File(filePath)))
        {
            int lineNumber = 0;
            while (scanner.hasNextLine())
            {
                lineNumber++;
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); i++)
                {
                    char c = line.charAt(i);

                    // Handle single quotes
                    if (insideSingleQuote)
                    {
                        if (c == '\'' && !isEscaped(line, i))
                        {
                            insideSingleQuote = false; // Exit single quote
                        }
                        continue;
                    }
                    if (c == '\'' && !isEscaped(line, i))
                    {
                        insideSingleQuote = true; // Enter single quote
                        continue;
                    }

                    // Handle double quotes
                    if (insideDoubleQuote)
                    {
                        if (c == '"' && !isEscaped(line, i))
                        {
                            insideDoubleQuote = false; // Exit double quote
                        }
                        continue;
                    }
                    if (c == '"' && !isEscaped(line, i))
                    {
                        insideDoubleQuote = true; // Enter double quote
                        continue;
                    }

                    // Handle grouping symbols
                    if (c == '(' || c == '[' || c == '{')
                    { // Opening symbols
                        stack.push(c);
                    }
                    else if (c == ')' || c == ']' || c == '}')
                    { // Closing symbols
                        if (stack.isEmpty())
                        {
                            System.out.printf("Error: Unmatched closing symbol '%c' at line %d%n", c, lineNumber);
                            return false;
                        }
                        char last = stack.pop();
                        if (!isMatchingPair(last, c))
                        {
                            System.out.printf("Error: Mismatched symbol '%c' at line %d%n", c, lineNumber);
                            return false;
                        }
                    }
                }
            }
        }

        if (!stack.isEmpty())
        {
            System.out.println("Error: Unmatched opening symbols remain.");
            return false;
        }
        return true;
    }

    private static boolean isEscaped(String line, int index)
    {
        // Check if the character at index is escaped with a backslash
        int backslashes = 0;
        while (index > 0 && line.charAt(--index) == '\\')
        {
            backslashes++;
        }
        return backslashes % 2 != 0;
    }

    private static boolean isMatchingPair(char open, char close)
    {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java EvaluateCode <source-code-file>");
            return;
        }

        String filePath = args[0];
        try
        {
            boolean result = checkSymbols(filePath);
            if (result)
            {
                System.out.println("All symbols are properly matched.");
            }
            else
            {
                System.out.println("Symbols are improperly matched or overlapping.");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
