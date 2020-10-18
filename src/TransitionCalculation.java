import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransitionCalculation {
    public static void main(String[] args) throws Exception {
        String transitionTimeFileName = "山手線駅間距離_20200916//conf//東京駅からの所要時間.txt";
        String stationListFileName = "山手線駅間距離_20200916//input//山手線駅間所要時間_入力.txt";
        String outputFileName = "山手線駅間距離_20200916//output//東京駅からの所要時間_出力.txt";
        FileWriter fw = new FileWriter(outputFileName);
        List<String> transitionTimeList = Files.readAllLines(Paths.get(transitionTimeFileName));
        List<String> stationList = Files.readAllLines(Paths.get(stationListFileName));
        ArrayList<String> timeArray = new ArrayList<String>();
        LinkedHashMap<Integer, String> transitionMap = new LinkedHashMap<Integer, String> ();
        int startStationTime = 0;
        int endStationTime = 0;
        int innerLoopTime = 0; 
        int outerLoopTime = 0;

        for( String transitionTimeData:transitionTimeList){
            String transitionTime[] = transitionTimeData.split(",");
               timeArray.add(transitionTime[1]);
               transitionMap.put(Integer.parseInt(transitionTime[1]), transitionTime[0]);
        }        
        int firstTime = Integer.parseInt(timeArray.get(0));   
        int lastTime = Integer.parseInt(timeArray.get(timeArray.size()-1));      

        for( String stationData:stationList) {
            String station[] = stationData.split(" ");
            String startStation = station[0].replace("\uFEFF", "");
            String endStation= station[1].replace("\uFEFF", "");
            for(Map.Entry<Integer, String> transitionList:transitionMap.entrySet()){            
                if(startStation.equals(transitionList.getValue())){
                   startStationTime = transitionList.getKey();
                }
                if(endStation.equals(transitionList.getValue())){
                    endStationTime = transitionList.getKey();
                }                                               
            }      
            if(startStationTime < endStationTime){
                innerLoopTime = endStationTime - startStationTime;
                outerLoopTime = (startStationTime + firstTime) + (lastTime + endStationTime);
            }
            else if(startStationTime>endStationTime){
                innerLoopTime = (startStationTime + lastTime) + (firstTime + endStationTime);
                outerLoopTime = startStationTime - endStationTime;      
            }
            int shortRideTime = Math.min(innerLoopTime,outerLoopTime); 
            fw.write(startStation + " " + endStation + " " + shortRideTime + "\n");    
        }
            fw.close();       
    }
}
