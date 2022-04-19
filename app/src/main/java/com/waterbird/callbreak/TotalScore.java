package com.waterbird.callbreak;

public class TotalScore {

    int major;
    int minor;

    public TotalScore() {
        this.major = 0;
        this.minor = 0;
    }

    void add(int major, int minor) {
         // -2.0 + 3.7 = 1.3, 3.4 - 2.6 = -0.8 -20 + 37 = 17 1.7  34 -26 = 8
            Integer temp1;
            Integer temp2;
            if(this.major >= 0)  temp1 = this.major*10 + this.minor; else temp1 = this.major*10 - this.minor;
            if(major >= 0)  temp2 = major*10 + minor; else temp2 = major*10 - minor;
            Integer temp3 = temp1+temp2;
           // System.out.println("TEMP: "+temp1 + ","+ temp2+","+temp3);

            this.minor = Math.abs(temp3%10);
            this.major = (temp3 - temp3%10) / 10;
            //System.out.println("MAJOR: "+this.major + ","+ this.minor);
    }

    public void clear(){
        this.major = 0;
        this.minor = 0;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}
