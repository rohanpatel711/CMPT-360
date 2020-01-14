import java.io.*;

public class B_rsp502 {

    public static int beerIndexCalculator(int input){
        int beerIndex;
        double m =(int)(Math.log(input)/Math.log(2));

        double power = Math.pow(2,m);
        double x = 0;
        if (power!=input){
            power = Math.pow(2,m);
            x = input - power;
        }
        beerIndex=(int)(2*x+1);
        return beerIndex;
    }

    public static void main(String[] args) {
        BufferedReader reader;
        File file = new File("B.rsp502.txt");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\rohan\\Desktop\\Fall 2019\\CMPT 360\\Assignments\\Assignment1\\beer.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                long num = Integer.parseInt(line.substring(0,line.length() - 1)); // Removes period/full stop "." and converts the String to Integer
                writer.write(beerIndexCalculator((int) num) + ". \n"); // Writes the output of the function in the output file
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
