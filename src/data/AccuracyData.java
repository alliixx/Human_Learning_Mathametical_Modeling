package data;

public class AccuracyData {
    public Integer trainSize;
    public Double complexAvg;
    public Double complexStd;
    public Double naiveAvg;
    public Double naiveStd;
    public Double alpha;

    public AccuracyData(Integer trainSize, Double complexAvg, Double complexStd, Double naiveAvg, Double naiveStd,
                        Double alpha) {
        this.trainSize = trainSize;
        this.complexAvg = complexAvg;
        this.complexStd = complexStd;
        this.naiveAvg = naiveAvg;
        this.naiveStd = naiveStd;
        this.alpha =  alpha;
    }

    public Double getComplexAvg(){
        return this.complexAvg;
    }

    public Double getNaiveAvg(){
        return this.naiveAvg;
    }

    public String toString() {
        return this.alpha + ", " + this.trainSize + ", " + this.complexAvg + ", " + this.naiveAvg
                + ", " + this.complexStd + ", " + this.naiveStd + "\n";
    }
}
