package app;

import data.Diagnosis;
import model.BaseModel;

import java.util.List;
import java.util.Map;

public class AccuracyCalc {
    public static double accuracy (List<Diagnosis> testList , BaseModel model, double alpha){
        //Map<String, Double> probMap = model.calculateProbability(alpha);
        Map<String, Double> probMap = model.calculateProbability();

        double accuracy = 0;
        double prob = 0;
        for(Diagnosis ts: testList) {
            String key = ts.symp1 + "_" + ts.symp2 + "_" + ts.symp3;
            //compare to subject response (use cho)
            prob = Double.parseDouble(ts.prob);
            //System.out.println("prob: " + probMap.get(key));


            if ("1".equals(ts.cho)){
                accuracy = accuracy + (probMap.get(key)-(prob/100))*(probMap.get(key)-(prob/100));
            }else{
                accuracy = accuracy + (probMap.get(key)-(1-(prob/100)))*(probMap.get(key)-(1-(prob/100)));
            }

//            if ("1".equals(ts.tar)){
//                accuracy = accuracy + (1-probMap.get(key))*(1-probMap.get(key));
//            }else{
//                accuracy = accuracy + (probMap.get(key))*(probMap.get(key));
//            }
        }
        //System.out.println("accuracy: " + accuracy);

        return accuracy;
    }
}
