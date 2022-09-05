package model;

import data.Diagnosis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.AppConstants.variables;

public abstract class BaseModel {
    protected List<Diagnosis> data;

    public BaseModel(List<Diagnosis> data) {
        this.data = data;
    }


    // Method for reinforcement learning
    //public Map<String, Double> calculateProbability() {
//    public Map<String, Double> calculateProbability(double alpha) {
//        double total = 0;
//        double trebCount = 0;
//        double philCount =  0;
//
//
//        //IMPORTANT
//        Map<String, Double> disease = new HashMap<String, Double>();
//        for (int i = 0; i < 2; i++) {
//            disease.put(String.valueOf(i), 1 / 2.0);
//        }
//        for (Diagnosis ts : data) {
//            for (int i = 0; i < 2; i++) {
//                Double value = disease.get(String.valueOf(i));
//                if (ts.tar.equals(String.valueOf(i)))
//                    value = value + alpha * (1 - value);
//                else
//                    value = value + alpha * (0 - value);
//                disease.put(String.valueOf(i), value);
//
//            }
//        }
//        //System.out.println(disease);
//
//        //normal avg
////        for (Diagnosis ts: data){
////            if (ts.tar.equals("1")) {
////                trebCount++;
////            }else{
////                philCount++;
////            }
////            total++;
////        }
////        Double tProb = (trebCount)/(total);
////        Double pProb = (philCount)/(total);
//        //System.out.println("total: " + total);
//
//        //IMPORTANT
//        Double tProb = disease.get("1");
//        //System.out.println("tProb: " + tProb);
//        Double pProb = disease.get("0");
//       // System.out.println("pProb: " + pProb);
//        //
//
//        //normal avg
////        Map<String, Double> tMap;
////        Map<String, Double> pMap;
////
////        tMap = calculate(true, trebCount);
////        pMap = calculate(false, philCount);
//        //
////
//        //IMPORTANT
//        Map<String, Double> tMap = calculate(true, alpha);
//        Map<String, Double> pMap = calculate(false,  alpha);
//        //System.out.println("tMap:" + tMap);
//        //System.out.println("pMap: " + pMap);
//
//        //normal
//        //Double finalProb = 0.0;
//        //
//        Map<String, Double> map = new HashMap<String, Double>();
//        for (String key: variables){
//            Double tVal = tMap.get(key);
//            Double pVal = pMap.get(key);
//            //System.out.println("pVal" + pVal);
//            //IMPORTANT
//            Double finalProb = (tProb * tVal) / ((tProb * tVal) + (pProb * pVal));
//            //
//
//            //normal avg
////            if (tProb*tVal != 0) {
////                 finalProb = (tProb * tVal) / ((tProb * tVal) + (pProb * pVal));
////            }
//            //
//            map.put(key, finalProb);
//        }
//        //System.out.println("finalProb: " + map);
//        return map;
//    }
//    public abstract Map<String, Double> calculate(boolean isTrebitis, double alpha);


    //Method for normal average calculation without reinforcement learning
    public Map<String, Double> calculateProbability() {
        double trebCount = 0;
        double philCount =  0;
        double total = 0;
        for (Diagnosis ts: data){
            if (ts.tar.equals("1")) {
                trebCount++;
            }else{
                philCount++;
            }
            total++;
        }
        Double tProb = (trebCount+1)/(total+2);
        Double pProb = (philCount+1)/(total+2);

        Map<String, Double> tMap = calculate(true, trebCount);
        Map<String, Double> pMap = calculate(false, philCount);

        Map<String, Double> map = new HashMap<String, Double>();
        for (String key: variables){
            Double tVal = tMap.get(key);
            Double pVal = pMap.get(key);
            Double finalProb = (tProb*tVal) / ((tProb*tVal) + (pProb*pVal));
            map.put(key, finalProb);
        }

        return map;
    }
    public abstract Map<String, Double> calculate(boolean isTrebitis, double count);

}
