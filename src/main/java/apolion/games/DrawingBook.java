import java.io.*;

import static java.util.stream.Collectors.joining;

class Result {

    /*
     * Complete the 'pageCount' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER p
     */

    public static int pageCount(int n, int p) {
        if(p==1||n==p)
            return 0;
        int numberOfPages = n;
        int numberOfStacksOfPages = numberOfPages /2;
        int numberOfStackOfPInPages = (p/2);
        return Math.min(numberOfStackOfPInPages ,numberOfStacksOfPages-numberOfStackOfPInPages );
    }

}

public class DrawingBook {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.pageCount(n, p);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
