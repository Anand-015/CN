import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws Exception {

        // Create server
        ServerSocket ss = new ServerSocket(9002);
        System.out.println("Waiting for client...");

        // Accept client
        Socket s = ss.accept();
        System.out.println("Client connected");

        // Receive matrix
        ObjectInputStream in =
            new ObjectInputStream(s.getInputStream());

        int n = in.readInt();
        int[][] a = (int[][]) in.readObject();

        // Display matrix
        System.out.println("Received Matrix:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }

        // Check matrix type
        boolean upper = true;
        boolean lower = true;
        boolean diagonal = true;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {

                if(i > j && a[i][j] != 0)
                    upper = false;

                if(i < j && a[i][j] != 0)
                    lower = false;

                if(i != j && a[i][j] != 0)
                    diagonal = false;
            }
        }

        String result;

        if(diagonal)
            result = "Diagonal Matrix";
        else if(upper)
            result = "Upper Triangular Matrix";
        else if(lower)
            result = "Lower Triangular Matrix";
        else
            result = "Normal Matrix";

        System.out.println("Matrix Type: " + result);

        // Send result to client
        DataOutputStream out =
            new DataOutputStream(s.getOutputStream());

        out.writeUTF(result);

        s.close();
        ss.close();
    }
}
