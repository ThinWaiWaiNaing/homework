import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CSVLayout {
    public static void main(String[] args) throws Exception {

        String inputFileName = "固定長→CSV変換プログラム//input//労働者名簿_固定長.txt";
        String fileLayoutName = "固定長→CSV変換プログラム//input//file_layout.txt";
        String outputFileName = "固定長→CSV変換プログラム//output//労働者名簿_固定長_CSV.txt";
        FileWriter fw = new FileWriter(outputFileName);
        int[] layoutList = new int[10];
        List<String>  inputLists = Files.readAllLines(Paths.get(inputFileName));
        List<String> fileLayoutList = Files.readAllLines(Paths.get(fileLayoutName));     

        for(String fileLayout:fileLayoutList){
            layoutList = Arrays.stream(fileLayout.split(",")).mapToInt(Integer::parseInt).toArray();         
       }

        for(String input:inputLists){
            int oldLayoutPos = 0;
            StringBuilder builder = new StringBuilder(input.length() + layoutList.length);
            for(int layoutPos:layoutList) {
                builder.append(input.substring(oldLayoutPos, oldLayoutPos+layoutPos)).append(',');
                oldLayoutPos += layoutPos;
            }
            builder.append(input.substring(oldLayoutPos));
            fw.write(builder.toString().replaceAll(",$", "") + "\n");
        }
        
        fw.close();
    }
}