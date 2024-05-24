import java.util.Random;

public class Main{
    public static void main(String[] arguments){
        int[] c = new int[11];
        int k = 24;
        for (int i=0;i<11;i++){
            c[i] = k;
            k = k-2;
        }
        double[] x = new double[19];
        for (int i=0;i<19;i++){
            x[i] = new Random().nextDouble(-5.0,4.0);
        }
        double[][] square = new double[11][19];
        for (int i=0;i<11;i++){
            for (int j=0;j<19;j++){
                int a = c[i];
                if (c[i]==14){
                    //double result = Math.atan((double) 1 / Math.exp(Math.acos(Math.cos(x[j]))));
                    square[i][j]= Math.atan((double) 1 / Math.exp(Math.acos(Math.cos(x[j]))));
                } else if ((c[i]==6)||(c[i]==8)||(c[i]==12)||(c[i]==18)||(c[i]==24)) {
                    //double result = Math.cos(((double) 2/3) * (((double) 2/3) + Math.sin(x[j])));
                    square[i][j] =  Math.cos(((double) 2/3) * (((double) 2/3) + Math.sin(x[j])));
                }else {
                    //double result = Math.atan( 1.0 / Math.exp(Math.pow(Math.abs((Math.cos(x[j])-3)/0.25) /3,2)));
                    square[i][j] = Math.atan( 1.0 / Math.exp(Math.pow(Math.abs((Math.cos(x[j])-3)/0.25) /3,2)));
                }
            }
        }

        for (int i=0;i<11;i++){
            for (int j=0;j<19;j++){
                System.out.printf("%.4f ",square[i][j]);
            }
            System.out.println();
        }
    }}
