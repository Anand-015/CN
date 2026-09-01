import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws Exception {

        // Connect to server
        Socket s = new Socket("localhost", 9002);

        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        // Get matrix size
        System.out.print("Enter matrix order: ");
        int n = sc.nextInt();

        // Create matrix
        int[][] a = new int[n][n];

        // Fill with random numbers
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                a[i][j] = r.nextInt(50) + 1;
            }
        }

        // Randomly select matrix type
        int type = r.nextInt(4);

        if(type == 0) {              // Upper
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if(i > j)
                        a[i][j] = 0;
        }
        else if(type == 1) {         // Lower
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if(i < j)
                        a[i][j] = 0;
        }
        else if(type == 2) {         // Diagonal
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if(i != j)
                        a[i][j] = 0;
        }

        // Display matrix
        System.out.println("Generated Matrix:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }

        // Send matrix to server
        ObjectOutputStream out =
            new ObjectOutputStream(s.getOutputStream());

        out.writeInt(n);
        out.writeObject(a);
        out.flush();

        // Receive result
        DataInputStream in =
            new DataInputStream(s.getInputStream());

        String result = in.readUTF();

        System.out.println("Matrix Type: " + result);

        s.close();
    }
}
