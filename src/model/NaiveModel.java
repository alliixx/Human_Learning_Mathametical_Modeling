package model;

import data.Diagnosis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.AppConstants.variables;

public class NaiveModel extends BaseModel{
    public NaiveModel(List<Diagnosis> data) {
        super(data);
    }

    private void calcAttributes(String key, boolean isAdd, Map<String, Double> inMap) {
        Double value = inMap.get(key);
        if (isAdd) {
            value += 1;
        }
        inMap.put(key, value);
    }



    //Method for reinforcement learning
//    public Map<String, Double> calculate(boolean isTrebitis, double alpha) {
//        Map<String, Double> map = new HashMap<String, Double>();
//
//        String trebStr = isTrebitis ?  "1" : "0";
//
//        Map<String, Double> symp1Map = new HashMap<String, Double>();
//        //IMPORTANT
//        for (int i = 0; i < 2; i++) {
//            symp1Map.put(String.valueOf(i), 1/2.0);
//        }
//
//        for (Diagnosis ts: data) {
//
//            if (ts.tar.equals(trebStr)) {
//                for (int i = 0; i < 2; i++) {
//                    Double value = symp1Map.get(String.valueOf(i));
//                    if (ts.symp1.equals(String.valueOf(i)))
//                        value = value + alpha*(1 - value);
//                    else
//                        value = value + alpha*(0 - value);
//                    symp1Map.put(String.valueOf(i), value);
//                }
//            }
//
//        }
//        //System.out.println("symp1" + symp1Map);
//
//
//        //normal avg
////        symp1Map.put("1", 0d);
////        symp1Map.put("0", 0d);
////        for (Diagnosis ts: data) {
////            calcAttributes(ts.symp1, ts.tar.equals(trebStr), symp1Map);
////        }
//        //
//
//        Map<String, Double> symp2Map = new HashMap<String, Double>();
//        //IMPORTANT
//        for (int i = 0; i < 2; i++) {
//            symp2Map.put(String.valueOf(i), 1/2.0);
//        }
//
//        for (Diagnosis ts: data) {
//
//            if (ts.tar.equals(trebStr)) {
//                for (int i = 0; i < 2; i++) {
//                    Double value = symp2Map.get(String.valueOf(i));
//                    if (ts.symp2.equals(String.valueOf(i)))
//                        value = value + alpha*(1 - value);
//                    else
//                        value = value + alpha*(0 - value);
//                    symp2Map.put(String.valueOf(i), value);
//                }
//            }
//
//        }
//        //System.out.println("symp2: " + symp2Map);
//
//        //normal avg
////        symp2Map.put("1", 0d);
////        symp2Map.put("0", 0d);
////        for (Diagnosis ts: data) {
////            calcAttributes(ts.symp2, ts.tar.equals(trebStr), symp2Map);
////        }
//        //
//
//
//        Map<String, Double> symp3Map = new HashMap<String, Double>();
//        //IMPORTANT
//        for (int i = 0; i < 2; i++) {
//            symp3Map.put(String.valueOf(i), 1/2.0);
//        }
//
//        for (Diagnosis ts: data) {
//
//            if (ts.tar.equals(trebStr)) {
//                for (int i = 0; i < 2; i++) {
//                    Double value = symp3Map.get(String.valueOf(i));
//                    if (ts.symp3.equals(String.valueOf(i)))
//                        value = value + alpha*(1 - value);
//                    else
//                        value = value + alpha*(0 - value);
//                    symp3Map.put(String.valueOf(i), value);
//                }
//            }
//        }
//        //System.out.println("symp3: " + symp3Map);
//
//
//        //normal avg
////        symp3Map.put("1", 0d);
////        symp3Map.put("0", 0d);
////        for (Diagnosis ts: data) {
////            calcAttributes(ts.symp3, ts.tar.equals(trebStr), symp3Map);
////        }
//        //
//
//
//        Double prob = 0.0;
//
//        for (String key: variables)  {
//            String[] keys  = key.split("_");
////            Double prob = (symp1Map.get(keys[0]) + 1) / (count+2) * (symp2Map.get(keys[1]) + 1) / (count + 2) *
////                    (symp3Map.get(keys[2])+1) / (count + 2);
//            //count = 1;
//
//            //IMPORTANT
//            prob = (symp1Map.get(keys[0])) * (symp2Map.get(keys[1])) * (symp3Map.get(keys[2]));
//            //System.out.println(symp1Map.get(keys[0]));
//            //
//
//            //normal avg
////            if (count != 0) {
////                prob = (symp1Map.get(keys[0]) / count) * (symp2Map.get(keys[1]) / count) * (symp3Map.get(keys[2]) / count);
////            }
//            //
//
//            map.put(key, prob);
//        }
//        //System.out.println("naive: " + map);
//        return map;
//    }

    //Method for normal average calculation without reinforcement learning
    public Map<String, Double> calculate(boolean isTrebitis, double count) {
        Map<String, Double> map = new HashMap<String, Double>();

        String trebStr = isTrebitis ?  "1" : "0";

        Map<String, Double> symp1Map = new HashMap<String, Double>();
        symp1Map.put("1", 0d);
        symp1Map.put("0", 0d);
        for (Diagnosis ts: data) {
            calcAttributes(ts.symp1, ts.tar.equals(trebStr), symp1Map);
        }

        Map<String, Double> symp2Map = new HashMap<String, Double>();
        symp2Map.put("1", 0d);
        symp2Map.put("0", 0d);
        for (Diagnosis ts: data) {
            calcAttributes(ts.symp2, ts.tar.equals(trebStr), symp2Map);
        }

        Map<String, Double> symp3Map = new HashMap<String, Double>();
        symp3Map.put("1", 0d);
        symp3Map.put("0", 0d);
        for (Diagnosis ts: data) {
            calcAttributes(ts.symp3, ts.tar.equals(trebStr), symp3Map);
        }

        for (String key: variables)  {
            String[] keys  = key.split("_");
            Double prob = (symp1Map.get(keys[0]) + 1) / (count+2) * (symp2Map.get(keys[1]) + 1) / (count + 2) *
                    (symp3Map.get(keys[2])+1) / (count + 2);
            map.put(key, prob);
        }
        //System.out.println("naive map: " + map);
        return map;
    }
}
