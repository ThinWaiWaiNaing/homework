import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CertificationNameList {
    public static void main(String[] args) throws Exception {
        String empFileName = "UnionTec_theme_20200909//input//employee.txt";
        String cerfFileName = "UnionTec_theme_20200909//input//oracle_bronze_certificate_list.txt";
        String outputFileName = "UnionTec_theme_20200909//output//oracle_bronze_certificate_list_name.txt";
        FileWriter fw = new FileWriter(outputFileName);
        List<String> empList = Files.readAllLines(Paths.get(empFileName));
        List<String> cerfList = Files.readAllLines(Paths.get(cerfFileName));
        LinkedHashMap<String, String> empMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> cerfMap = new LinkedHashMap<String, String>();

        for (String empData:empList) {
            String[] empKeyValue = empData.split(",");
            empMap.put(empKeyValue[0], empKeyValue[1]);
        }

        for (String cerfData:cerfList) {
            String cerfKeyValue[] = cerfData.split(",");
            cerfMap.put(cerfKeyValue[0], cerfKeyValue[1]);
        }

        for (Map.Entry<String, String> cf:cerfMap.entrySet()) {
            for (Map.Entry<String, String> emp:empMap.entrySet()) { 
                if (cf.getValue().equals(emp.getKey())) {
                    fw.write(cf.getKey() + "," + cf.getValue() + "," + emp.getValue() + "\n");
                }                    
            }
        }  
        fw.close();                   
    }
}
