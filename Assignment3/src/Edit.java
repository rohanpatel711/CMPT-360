
import java.io.*;
import java.util.StringTokenizer;
//****************
//START: READ ONLY
//****************
public class Edit {
//****************
//END: READ ONLY
//****************

// YOU CAN DEFINE YOUR OWN FUNCTIONS HERE IF YOU REALLY NEED ONE

//****************
//START: READ ONLY
//****************

    public static int EditDistance(String a, String b) {
//****************
//END: READ ONLY
//****************

        //WRITE YOUR NSID: rsp502

        //start: edit and write your code here

        int max_size=1000;
        int a_len = a.length();
        int b_len = b.length();
        int [][][] matrix = new int[a_len+1][b_len+1][4];
        //Inserts all elements
        for (int c = 0; c < b_len+1; c++) {
            int d = 0;
            while(d <= 3){
                if (c > 1)
                    matrix[0][c][d] = max_size;
                else
                    matrix[0][c][d] = c;
                d++;
            }
        }
		//Removes all elements
        for (int c = 0; c <a_len+1; c++) {   
            int d = 0;
            while(d <= 3){
                if (c > 1)
                    matrix[c][0][d] = max_size;
                else
                    matrix[c][0][d] = c;
                d++;
            }
        }
		// Calculates the Insertion, Deletion, Substituion, Same Moves
        for (int c = 1; c < a_len+1 ; c++) {
            for (int d = 1; d < b_len+1 ; d++) {
                int e = 0;
                while(e <= 3){
                    if(a.charAt(c-1) == b.charAt(d-1))
                        matrix[c][d][e] = Math.min(matrix[c-1][d-1][1],Math.min(matrix[c-1][d-1][2],matrix[c-1][d-1][3]));
                    else {
                        if ( e == 1 )
                            matrix[c][d][e] = 1 + Math.min(matrix[c - 1][d][2], matrix[c - 1][d][3]);
                        else if ( e == 2 )
                            matrix[c][d][e] = 1 + Math.min(matrix[c][d - 1][1], matrix[c][d - 1][3]);
                        else if( e == 3 )
                            matrix[c][d][e] = 1 + Math.min(matrix[c - 1][d - 1][1], matrix[c - 1][d - 1][2]);
                        else
                            matrix[c][d][e] = max_size;
                    }
                    e++;
                }
            }
        }

        int distance = Math.min(matrix[a_len][b_len][1],Math.min(matrix[a_len][b_len][2],Math.min(matrix[a_len][b_len][3],matrix[a_len][b_len][0])));
        if (distance >= max_size)
            return -1;
        else
            return distance;
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
        String line;
        try {
            reader = new BufferedReader(new FileReader("edit.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            while(true){
                line = reader.readLine();
                if(line == null) break;
                StringTokenizer st = new StringTokenizer(line, ",");
                String a = st.nextToken();
                String b = st.nextToken();
                writer.write(EditDistance(a,b) + "\n");
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
