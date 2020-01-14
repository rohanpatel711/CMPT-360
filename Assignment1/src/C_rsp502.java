import java.io.*;

public class C_rsp502{
  public int recursion(int m, int n){
        if(n==0 || m==0){ //If either one of m and n are 0 ==> return 0
            return 0;
        }
        if((m%2!=0) && (n%2==0)){ // If m is odd and n is even ==> decrement m
            m--;
        }
        else if ((m%2==0) && (n%2!=0)){ // If n is odd and m is even ==> decrement n
            n--;
        }
        int temp;
        //Assign the smaller number from m and n to temp
        if(n > m){
            temp = m;
        }
        else{
            temp = n;
        }
        return ((m*n)+temp); // Returns the expression
    }

    public static void main(String[] args) {

        C_rsp502 test = new C_rsp502();

        BufferedReader reader;
        File file = new File("C.rsp502.txt");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\recursion.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String line = reader.readLine();
            line = reader.readLine();

            while(line != null)
            {
                line = line.substring(0,line.length() - 1); // Removes period/full stop "."
                int count = 0;
                int seperator = 0;

                //Used to find the index of the comma ","
                while(line.charAt(count)!=','){
                    count++;
                    seperator=count;
                }
                // Separates both the numbers and converts them to int from string
                int m = Integer.parseInt(line.substring(0,seperator));
                int n = Integer.parseInt(line.substring(seperator+1,line.length()));
                //Writes the output from the function call to the output file
                long start = System.currentTimeMillis();
                writer.write(test.recursion(m,n) + ". \n");
                long end = System.currentTimeMillis();
                float sec = (end - start) / 1000F;
                System.out.println(sec + " seconds");
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
