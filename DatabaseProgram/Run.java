
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Run {

    public static void main(String[] args) {

String command = "python3 run.py";
 
try {
    Process process = Runtime.getRuntime().exec(command);
 
    BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
 
    reader.close();
 
} catch (IOException e) {
    e.printStackTrace();
}

    }

}
