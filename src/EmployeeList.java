import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeList {
   public static void main(String[] args) throws Exception {
        try {
            String empFileName = "UnionTec_theme_20200909//input//employee.txt";
            String cerfFileName = "UnionTec_theme_20200909//input//oracle_bronze_certificate_list.txt";
            String outputFileName = "UnionTec_theme_20200909//output//oracle_bronze_certificate_list_name.txt";
            FileWriter fw = new FileWriter(outputFileName);
            List<String> empLists = Files.readAllLines(Paths.get(empFileName), StandardCharsets.UTF_8);
            List<String> cerfLists = Files.readAllLines(Paths.get(cerfFileName), StandardCharsets.UTF_8);
            HashMap<String, String> empMap = new HashMap<String, String>();
            HashMap<String, String> cerfMap = new HashMap<String, String>();
            List<String> nameList = new ArrayList<String>();

            for (String empData:empLists) {
                String[] empKeyValue = empData.split(",");
                empMap.put(empKeyValue[0], empKeyValue[1]);
            }

            for (String cfList:cerfLists) {
                String cerfKeyValue[] = cfList.split(",");
                cerfMap.put(cerfKeyValue[0], cerfKeyValue[1]);
            }

            for (Map.Entry<String, String> emp:empMap.entrySet()) {
                for (Map.Entry<String, String> cf:cerfMap.entrySet()) { 
                    if (emp.getKey().equals(cf.getValue())) {
                        String keyValue = cf.getKey() + "," + cf.getValue() + "," + emp.getValue();
                        //タイトル設定
                        if (emp.getKey().equals("社員番号")) {
                            fw.write(keyValue);
                        } else {
                            nameList.add(keyValue);
                        }
                    }                    
                }
            }

            //日付降順設定
            Collections.sort(nameList);

            for (String lists:nameList) {
                fw.write("\n" + lists);
            }
            
            fw.close();                   
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}