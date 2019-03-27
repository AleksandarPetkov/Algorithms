import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class T00_RecursiveSumArray {

    private static final int STARTING_INDEX = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] array = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();


        System.out.println(sumArray(array, STARTING_INDEX));
    }

    private static int sumArray(int[] array, int index){

        if (array.length == index){
            return 0;
        }

        int currentResult = array[index] + sumArray(array, index + 1);
        return currentResult;
    }
}
