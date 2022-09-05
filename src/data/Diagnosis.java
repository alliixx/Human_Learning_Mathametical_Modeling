package data;

public class Diagnosis {
    public String sub;
    public String tar;
    public String stim;
    public String symp1 = "0";
    public String symp2 = "0";
    public String symp3 = "0";
    public String prob;
    public String cho;

    public String getSub(){
        return this.sub;
    }

    public Diagnosis (String sub, String stim, String symp1, String symp2, String symp3,
                      String cho, String prob, String tar){
        this.sub = sub;
        this.stim = stim;
        this.cho = cho;
        this.tar = tar;
        this.prob = prob;

        switch (stim) {
            case "1":
                this.symp1 = "1";
                this.symp2 = "1";
                this.symp3 = "1";
                break;
            case "2":
                this.symp1 = "1";
                this.symp2 = "1";
                break;
            case "3":
                this.symp1 = "1";
                this.symp3 = "1";
                break;
            case "4":
                this.symp1 = "1";
                break;
            case "5":
                this.symp2 = "1";
                this.symp3 = "1";
                break;
            case "6":
                this.symp2 = "1";
                break;
            case "7":
                this.symp3 = "1";
                break;
            default:
                break;


        }
    }

    public String toString() {
        return "sub: " + this.sub + ", stim: " + this.stim + ", symp1: " + this.symp1
                + ", symp2: " + this.symp2 + ", symp3: " + this.symp3
                + ", cho: " + this.cho + ", prob: " + this.prob + ", tar: " + this.tar + ", \n";
    }

}
