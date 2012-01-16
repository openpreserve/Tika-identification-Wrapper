package dk.statsbiblioteket.scape.tika;

import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import javax.swing.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 1/16/12
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Report {


    Map<String,Map<String,Integer>> wrongs = new HashMap<String, Map<String,Integer>>();
    Map<String,Integer> rights = new HashMap<String, Integer>();

    Map<Long,Integer> times = new TreeMap<Long, Integer>();

    public void reportWrong(String truthMime, String detection) {
        Map<String, Integer> detectionsForThisTruth = wrongs.get(truthMime);
        if (detectionsForThisTruth == null){
            detectionsForThisTruth = new HashMap<String, Integer>();
            detectionsForThisTruth.put(detection,1);
            wrongs.put(truthMime,detectionsForThisTruth);
        } else {
            Integer detectionForThisTruth = detectionsForThisTruth.get(detection);
            if (detectionForThisTruth == null){
                detectionForThisTruth = 0;
            }
            detectionForThisTruth++;
            detectionsForThisTruth.put(detection,detectionForThisTruth);
        }
    }

    public void reportRight(String truthMime) {
        Integer detectionForThisTruth = rights.get(truthMime);
        if (detectionForThisTruth == null){
            detectionForThisTruth = 0;
        }
        detectionForThisTruth++;
        rights.put(truthMime,detectionForThisTruth);
    }




    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String truthMime : wrongs.keySet()) {
            Integer rightIdentifications = rights.get(truthMime);
            if (rightIdentifications == null){
                rightIdentifications = 0;
            }
            result.append("For mime '").append(truthMime).append("' we made "+rightIdentifications+" correct identifications, and these wrong identifications:\n");
            Map<String, Integer> identifications = wrongs.get(truthMime);
            for (String identification : identifications.keySet()) {
                result.append("    ").append(identification).append("  :   ")
                        .append(identifications.get(identification)).append("\n");
            }
        }
        result.append("\n");
        List<Long> timeList = new ArrayList<Long>();
        timeList.addAll(times.keySet());
        Collections.sort(timeList);
        for (Long time : timeList) {
            result.append(times.get(time)).append(" files were identified in ").append(time).append("ms \n");
        }
        return result.toString();
    }



    public void reportTime(long time) {
        Integer hits = times.get(time);
        if (hits == null){
            hits = 0;
        }
        hits++;
        times.put(time,hits);
    }
}