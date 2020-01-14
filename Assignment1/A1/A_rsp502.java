import java.io.*;

public class A_rsp502 {

public static String palindrome(String str) {

        char l_char = str.charAt(str.length()-1); // Stores the last character of the string.
        int count = str.length()-1; // Stores the length of the String - 1
        int cursor = str.length()-1; // Stores the length of the String - 1
        /*
         * Finds out the index of the character from which we have to appending to the end of the input string to make it a palindrome.
         */
        while (count>0){
        if (str.charAt(count)!=l_char){
        cursor=count;
        break;
        }
        count--;
        }
        String newString= ""; // New string to store the palindrome.
        newString = newString+str; // Adds the original input string to the new string
        /*
         * Adds the remaining characters from the index obtained in the previous while loop to the end of the new string to make it a palindrome.
         */
        while(cursor>=0){
        newString += str.charAt(cursor);
        cursor--;
        }
        return newString;
        }

        public static void main(String[] args) {
                BufferedReader reader;
                File file = new File("A.rsp502.txt");
                try {
                        reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\palindrome.txt"));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        String line = reader.readLine();
                        line = reader.readLine();
                        while (line != null) {
                                // Writes the output of the function in the output file
                                writer.write(palindrome(line.substring(0, line.length() - 1)) + ". \n");  // Removes period/full stop "." and passes the String to palindrome function.
                                line = reader.readLine();
                        }
                reader.close();
                writer.close();
        } catch (FileNotFoundException e) {
        System.out.println("Could not locate input file.");
        } catch (Exception e) {
        System.out.println(e.toString());
        }
        }
        }
