package model;

import data.Diagnosis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.AppConstants.variables;

public class ComplexModel extends BaseModel{
    public ComplexModel(List<Diagnosis> data) {
        super(data);
    }



    //Method for reinforcement learning
    //public Map<String, Double> calculate(boolean isTrebitis, double count) {
//    public Map<String, Double> calculate(boolean isTrebitis, double alpha) {
//        Map<String, Double> map = new HashMap<String, Double>();
//
//        //IMPORTANT
//        for (String key: variables){
//            map.put(key, 1/8.0);
//        }
//
//
//        String trebStr = isTrebitis ?  "1" : "0";
//
//        for (Diagnosis ts: data){
//            String key = ts.symp1 + "_" + ts.symp2 + "_" + ts.symp3;
//
//            if (ts.tar.equals(trebStr)) {
//                for (String vkey: variables) {
//                    Double value = map.get(vkey);
//                    if (key.equals(vkey))
//                        value = value + alpha*(1-value);
//                    else
//                        value = value + alpha*(0-value);
//                    map.put(vkey, value);
//                }
//            }
//
//        }
//        //System.out.println("complex" + map);
//
//        return map;
//    }

    //Method for normal average calculation without reinforcement learning
    public Map<String, Double> calculate(boolean isTrebitis, double count) {
        Map<String, Double> map = new HashMap<String, Double>();
        for (String key: variables){
            map.put(key, 0d);
        }

        String trebStr = isTrebitis ?  "1" : "0";

        for (Diagnosis ts: data){
            String key = ts.symp1 + "_" + ts.symp2 + "_" + ts.symp3;
            Double value = map.get(key);
            if (ts.tar.equals(trebStr)) {
                value += 1;
            }
            map.put(key, value);
        }

        for (String key: variables)  {
            Double value = map.get(key);
            Double prob = (value+1) / (count+8);
            map.put(key, prob);
        }
        //System.out.println("complex map: " + map);
        return map;
    }

}
