import java.util.ArrayList;
import java.util.Arrays;
public class X {
    private ArrayList<double[]> normalizedData;
    private double pChange;
    private int nO;


    X(int n, double priceChange, ArrayList<double[]> xValues ){
        // Normalize data locally to so prediction values are better regualarized by n
        if (n == xValues.size()){
            nO=n;
            normalizedData= new ArrayList<double[]>();
            //Data for mean and standard deviation
            double sumDeltaPrice=0;
            double sumVolatility=0;
            double sumDeltaPriceSq=0;
            double sumVolatilitySq=0;


            //Finding the mean and standard deviation to locally normalize data before feeding into model
            for( int i =0; i<n; i++){
                double[] currDay = xValues.get(i);
                double deltaPrice = currDay[3]-currDay[0];
                double vola = currDay[1]-currDay[2];

                sumDeltaPrice+=deltaPrice;
                sumVolatility+=vola;

                sumDeltaPriceSq+=Math.pow(deltaPrice,2);
                sumVolatilitySq+=Math.pow(vola,2);

            }

            double meanDeltaPrice = sumDeltaPrice/n;
            double meanVolatility = sumVolatility/n;

            //if statements to prevent sqrt of a -ve number
            double bracket1 =(sumDeltaPriceSq/n)-Math.pow(meanDeltaPrice,2);
            double bracket2 =(sumVolatilitySq/n)-Math.pow(meanVolatility,2);
            if(bracket1<0) bracket1= 0.001;
            if(bracket2<0) bracket2=0.001;

            double stdDeltaPrice = Math.sqrt(bracket1);
            double stdVolatility = Math.sqrt(bracket2);

            for(int j =0; j<n; j++){
                double[] currDay = xValues.get(j);
                double deltaPrice = currDay[3]-currDay[0];
                double vola = currDay[1]-currDay[2];

                double normalizeDeltaPrice = (deltaPrice-meanDeltaPrice)/stdDeltaPrice;
                double normalizeVolatility = (vola-meanVolatility)/stdVolatility;

                double[] temp = new double[2];
                temp[0] = normalizeDeltaPrice;
                temp[1] = normalizeVolatility;

                normalizedData.add(temp);


            }


            pChange = smoothenPrice(priceChange);

        }

        else{
            System.out.println("Check class X; Input n doesn't match size of Xvalues");
        }
    }

    public ArrayList<double[]> normalizedData(){
        return normalizedData;
    }

    public void print(){
        System.out.println("\nPrice Change: "+ pChange);
        System.out.println("Number of data rows considered: "+nO);
        System.out.println("Normalized Data: ");
        for(int i =0; i<normalizedData.size(); i++){
            double[] curr = normalizedData.get(i);
            System.out.println("Normalized Price Change"+i+": " +curr[0]);
            System.out.println("Normalized Volatility"+i+": "+ curr[1]);
        }


    }


    // Create method to smoothen price to make model predcition a lot better

    private double smoothenPrice(double x){

        double result = 0.0;
        if (x>=0 && x<0.5) result=0.01;
        else if (x>=0.5 && x<0.75) result = 0.5;
        else if (x>=0.75 && x<1.00) result = 0.75;
        else if (x>=1.00 && x<1.25) result = 1.00;
        else if (x>=1.25 && x<1.50) result = 1.25;
        else if (x>=1.50 && x<1.75) result = 1.50;
        else if (x>=1.75 && x<2.00) result = 1.75;
        else if (x>=2.00 && x<2.50) result = 2.00;
        else if (x>=2.50 && x<3.00) result = 2.50;
        else if (x>=3.00 && x<3.50) result = 3.00;
        else if (x>=3.50 && x<4.00) result = 3.50;
        else if (x>=4.00 && x<4.50) result = 4.00;
        else if (x>=4.50 && x<5.00) result = 4.50;
        else if (x>=5.00 && x<5.50) result = 5.00;
        else if (x>=5.50 && x<6.00) result = 5.50;
        else if (x>=6.00 && x<6.50) result = 6.00;
        else if (x>=6.50 && x<7.00) result = 6.50;
        else if (x>=7.00) result = 7.00;
        else if (x<0 && x>-0.50)result =-0.01;
        else if (x<=-0.50 && x>-0.75)result =-0.50;
        else if (x<=-0.75 && x>-1.00)result =-0.75;
        else if (x<=-1.00 && x>-1.25)result =-1.00;
        else if (x<=-1.25 && x>-1.50)result =-1.25;
        else if (x<=-1.50 && x>-1.75)result =-1.50;
        else if (x<=-1.75 && x>-2.00)result =-1.75;
        else if (x<=-2.00 && x>-2.50)result =-2.00;
        else if (x<=-2.50 && x>-3.00)result =-2.50;
        else if (x<=-3.00 && x>-3.50)result =-3.00;
        else if (x<=-3.50 && x>-4.00)result =-3.50;
        else if (x<=-4.00 && x>-4.50)result =-4.00;
        else if (x<=-4.50 && x>-5.00)result =-4.50;
        else if (x<=-5.00 && x>-5.50)result =-5.00;
        else if (x<=-5.50 && x>-6.00)result =-5.50;
        else if (x<=-6.00 && x>-6.50)result =-6.00;
        else if (x<=-6.50 && x>-7.00)result =-6.50;
        else if (x<=-7.00 )result =-7.00;
        
        return result;
    }
    
}
