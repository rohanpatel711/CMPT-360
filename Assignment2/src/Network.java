import java.io.*;
import java.util.StringTokenizer;
//****************
//START: READ ONLY
//****************
public class Network {
//****************
//END: READ ONLY
//****************

// YOU CAN DEFINE YOUR OWN FUNCTIONS HERE IF YOU REALLY NEED ONE

//****************
//START: READ ONLY
//****************

    /**
     * @param n : The number of packets
     * @param D : An array representing the packet ordering
     * @return The performance metric for D
     */
    public static int metric(int []D, int n) {
//****************
//END: READ ONLY
//****************

        //WRITE YOUR NSID:rsp502

        //start: edit and write your code here
        int test = -2147483648; // Assign test the smallest number in int range.
        int temp = D[0]; // Maximum element of the sub-array
        for(int i = 1; i < n; i++){  // for i from 0 to number of packets
            int difference = temp - D[i];
            // If the difference value is less then the test value
            if(difference > test){
                test = difference;
            }
            if(D[i] > temp){
                temp = D[i];
            }
        }
        return test;
        //end: write your code here
    }
//****************
//START: READ ONLY
//****************
    /**
     * Main Function.
     */
    public static void main(String[] args) {

        BufferedReader reader;
        File file = new File("output.txt");
        int n = 0;
        int []X= new int[1000];
        String line;
        try {
            reader = new BufferedReader(new FileReader("Network.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            while(true){
                line = reader.readLine();
                if(line == null) break;
                StringTokenizer st = new StringTokenizer(line, ",");
                n = 0;
                while (st.hasMoreTokens()) {
                    X[n] = Integer.parseInt(st.nextToken());
                    //System.out.println(""+X[n]);
                    n++;
                }
                writer.write(metric(X,n) + "\n");
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
