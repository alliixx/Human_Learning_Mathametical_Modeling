package app;

import data.AccuracyData;
import data.Diagnosis;
import jdk.jshell.Diag;
import model.ComplexModel;
import model.NaiveModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static app.AccuracyCalc.accuracy;

public class App {
    public static final String FILE_NAME = "/Users/axin/Downloads/Human_Data_Individual.csv";
    public static final String OUTPUT_DIR = "/Users/axin/Downloads/humanmodel.csv";

    private static List<Diagnosis> loadData(String fileName) {
        BufferedReader in = null;
        List<Diagnosis> list = new ArrayList<Diagnosis>();
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    String[] fields = line.split(",");
                    Diagnosis ts = new Diagnosis(fields[0],fields[3],
                            fields[3], fields[3], fields[3], fields[4], fields[5], fields[2]);
                    list.add(ts);
                }
                line = in.readLine();
            }
            list.remove(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        return list;
    }

    private static void exportCsv(Map<Double, List<AccuracyData>> accuracyMap) {
        FileWriter out = null;
        try{
            String outFile = OUTPUT_DIR + "humanmodel_" + (new Date().getTime()) + ".csv";
            out = new FileWriter(outFile);
            for (List<AccuracyData> list: accuracyMap.values()) {
                for (AccuracyData data: list) {
                    out.write(data.toString());
                }
            }
            out.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex){}
            }
        }
    }


//    private static List<AccuracyData> buildCases(Map<String, List<Diagnosis>> map){
//        List<AccuracyData> accuracyList = new ArrayList<AccuracyData>();
//        int size = map.size();
//        double runRoot = Math.sqrt(size);
//        int testSize = 1;
//
//        for (int trainSize = 1; trainSize < 300; trainSize += 1) {
//            double[] complexAccuracies = new double[size];
//            double[] naiveAccuracies = new double[size];
//            int repeatIdx = 0;
//
//            for (String key : map.keySet()) {
//                List<Diagnosis> subjectList = map.get(key);
//                List<Diagnosis> trainList = subjectList.subList(0, trainSize);
//                List<Diagnosis> testList = subjectList.subList(trainSize, trainSize + testSize);
//
//                //Complex Bayes Model accuracy calculation
//                ComplexModel cModel = new ComplexModel(trainList);
//                complexAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, cModel);
//
//                //Naive Bayes Model accuracy calculation
//                NaiveModel nModel = new NaiveModel(trainList);
//                naiveAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, nModel);
//                repeatIdx++;
//            }
//            double[] cRet = stats(complexAccuracies);
//            double[] nRet = stats(naiveAccuracies);
//            AccuracyData ret = new AccuracyData(trainSize,
//                    cRet[0] / testSize,
//                    cRet[1] / runRoot / (trainSize + 1),
//                    nRet[0] / testSize,
//                    nRet[1] / runRoot / (trainSize + 1));
//            accuracyList.add(ret);
//        }
//        return accuracyList;
//    }

    private static Map<Double, List<AccuracyData>> buildCases(Map<String, List<Diagnosis>> map) {
        Map<Double, List<AccuracyData>> accuracyMap = new TreeMap<Double, List<AccuracyData>>();
        int size = map.size();
        double runRoot = Math.sqrt(size);
        int testSize = 1;
        int paraSize = 50;

//        double[] cPrevAvg = new double[paraSize];
//        double[] nPrevAvg = new double[paraSize];

//        for (int i = 0; i < paraSize; i++){
//            cPrevAvg[i] = 0.25;
//            nPrevAvg[i] = 0.25;
//        }

        for (int trainSize = 1; trainSize < 300; trainSize += 1){
            double[] complexAccuracies = new double[size];
            double[] naiveAccuracies = new double[size];
           // int repeatIdx = 0;

//            for (int i = 0; i < paraSize; i++) {
//                double alpha = 0.0 + 0.001 * i;
                double alpha = 0.005;
                int repeatIdx = 0;

                for (String key : map.keySet()) {
                    List<Diagnosis> subjectList = map.get(key);
                    List<Diagnosis> trainList = subjectList.subList(0, trainSize);
                    //System.out.println("trainList: " + trainList);
                    List<Diagnosis> testList = subjectList.subList(trainSize, trainSize + testSize);
                    //System.out.println(trainSize + ", testList: " + testList);

                    //Complex Bayes Model accuracy calculation
                    ComplexModel cModel = new ComplexModel(trainList);
                    complexAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, cModel, alpha);
                    //System.out.println(repeatIdx + ": " + complexAccuracies[repeatIdx]);


                    //Naive Bayes Model accuracy calculation
                    NaiveModel nModel = new NaiveModel(trainList);
                    naiveAccuracies[repeatIdx] = AccuracyCalc.accuracy(testList, nModel, alpha);
                    repeatIdx++;

                }

                List<AccuracyData> accuracyList = new ArrayList<AccuracyData>();
                if (accuracyMap.containsKey(alpha)) {
                    accuracyList = accuracyMap.get(alpha);
                } else {
                    accuracyMap.put(alpha, accuracyList);
                }
//                double[] cRet = stats(complexAccuracies, cPrevAvg[i], constant);
//                double[] nRet = stats(naiveAccuracies, nPrevAvg[i], constant);

                double[] cRet = stats(complexAccuracies);
                double[] nRet = stats(naiveAccuracies);

                AccuracyData ret = new AccuracyData(trainSize,
                        Math.sqrt(cRet[0] / testSize),
                        cRet[1] / runRoot / (trainSize + 1),
                        Math.sqrt(nRet[0] / testSize),
                        nRet[1] / runRoot / (trainSize + 1), alpha);
                accuracyList.add(ret);

//                for (int  f = 0; f < cPrevAvg.length; f++){
//                    System.out.println(cPrevAvg[f]);
//                }

//                cPrevAvg[i] = cRet[0];
//                nPrevAvg[i] = nRet[0];
            }

        //}
        return accuracyMap;
    }


    //private static double[] stats(double[] arr, double prevAvg, double constant){
    private static double[] stats(double[] arr){
        double sum = 0;
        double std = 0;
        int size = arr.length;
        double avg = 0;
        avg = Arrays.stream(arr).average().getAsDouble();
//        if (size == 1) {
//
//            avg = Arrays.stream(arr).average().getAsDouble();
//            avg = 0.25 + constant*(Arrays.stream(arr).average().getAsDouble() - 0.25);
//        }else {
           //avg = prevAvg + constant*(arr[size-1] - prevAvg);
            //avg = prevAvg + (arr[size-1] - prevAvg)/size;
        //}
        for (int i = 0; i < size; i++){
            sum += (arr[i]-avg) * (arr[i]-avg);
        }
        std = Math.sqrt(sum/size);
        double[] ret = {avg, std};
        return ret;
    }


    public static void main (String[] args) {
        List<Diagnosis> controlList = loadData(FILE_NAME);
        //System.out.println(controlList);
        Map<String, List<Diagnosis>> map = controlList.stream()
                .collect(Collectors.groupingBy(Diagnosis::getSub));

//        List<AccuracyData> accuracyList = buildCases(map);
        Map<Double, List<AccuracyData>> accuracyMap = buildCases(map);
        System.out.println(accuracyMap);
//
        for (Double key: accuracyMap.keySet()){
            List<AccuracyData> allAvg = accuracyMap.get(key);
            Double cAvg = allAvg.stream().collect(Collectors.averagingDouble(AccuracyData::getComplexAvg));
            Double nAvg = allAvg.stream().collect(Collectors.averagingDouble(AccuracyData::getNaiveAvg));
            System.out.println(key + "," + cAvg + "," + nAvg);
        }

//        double[] arr = {1};
//        double[] ret = stats(arr, 0, 0.1);
//        System.out.println(ret[0]);

        exportCsv(accuracyMap);
    }
}
