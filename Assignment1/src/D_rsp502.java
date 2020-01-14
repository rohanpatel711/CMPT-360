/*
import java.io.*;

public class D_rsp502{
    public static long fib(int num) {
        num = ((num*2)+2); // A expression derived from the question 2n+2
        */
/*
        *   Makes the fibonacci of the 2n+2 number which is the result.
        *//*

        long result = 1;
        long prev = 1;
        for(int i=2;i<num;++i){
            long temp = result;
            result += prev;
            prev = temp;
        }
        return result;
    }

    public static void main(String[] args){
        BufferedReader reader;
        File file = new File("D.rsp502.txt");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\software.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null)
            {
                int num = Integer.parseInt(line.substring(0,line.length() - 1)); // Removes the period/full stop "." and assigns to the num
                long start = System.currentTimeMillis();
                writer.write( fib(num)+ ". \n"); //Writes the output of the function in the output file
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
}*/
import java.io.*;

public class D_rsp502{
    public static long fib(int num) {
        num = ((num*2)+2); // A expression derived from the question 2n+2
        /*
         *   Makes the fibonacci of the 2n+2 number which is the result.
         */
        System.out.println("new num:"+num);
        long result = 1;
        long prev = 1;
        for(int i=2;i<num;++i){
            long temp = result;
            result += prev;
            prev = temp;
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] args){
        BufferedReader reader;
        File file = new File("D.rsp502.txt");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\software.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null)
            {
                int num = Integer.parseInt(line.substring(0,line.length() - 1)); // Removes the period/full stop "." and assigns to the num
                System.out.println("num:"+num);
                writer.write( fib(num)+ ". \n"); //Writes the output of the function in the output file
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