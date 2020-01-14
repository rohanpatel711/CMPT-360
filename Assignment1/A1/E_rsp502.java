import java.io.*;

public class E_rsp502 {
    public static int sequence(int num) {
        int counter=1;             // Base Case when n is 1==> return 1
        while(num!=1){
            if (num%2!=0){         //If n is odd n ==> (3n+1)
                num=((3*num)+1);
            }
            else{                 //Any other case n ==> n/2
                num=(num/2);
            }
            counter++;            // Increments the counter every time the n is not 1
        }
        return counter;          // Returns the count of the number of times it passes through the loop
    }
    public static void main(String[] args){
        BufferedReader reader;
        File file = new File("E.rsp502.txt");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\sequence.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null)
            {
                int num = Integer.parseInt(line.substring(0,line.length() - 1)); // Removes the period/full stop "." and assigns to the num
                writer.write( sequence(num)+ ". \n"); //Writes the output of the function in the output file
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
