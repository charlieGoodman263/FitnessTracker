import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    public static void appendToFile(String appendedText) {
        try (FileWriter myWriter = new FileWriter("userLoginInfo.txt", true)) {
            myWriter.write("\n" + appendedText);
            //System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    public static ArrayList<String> retrieveUserData() {
        File userData = new File("userLoginInfo.txt");
        ArrayList<String> ret = new ArrayList<>();

        try (Scanner myReader = new Scanner(userData)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ret.add(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No user data, creating directory now...");
            try {
                userData.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return ret;
    }
}
