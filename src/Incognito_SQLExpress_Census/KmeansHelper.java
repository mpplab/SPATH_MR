package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.List;

public class KmeansHelper {

	// ���캯��
	public KmeansHelper(){};
	
    // Nosound��ӣ��ж�ĳ���������ĸ����ĵ�
    public double[] whichCenter(double[] singlepoint,double[][] centers){
        // array.length   ��һά�ĳ���
    	// array[0].length  �ڶ�ά�ĳ��ȣ���Ȼ���array������Ҫ��Ԫ�أ���Ȼȡarray[0]�ͻᱨ��ģ�
    	List<Double> ld = new ArrayList<Double>();
    	for (int i = 0; i < centers.length; i++){
    		double dDist = dist(singlepoint, centers[i]);
    		ld.add(dDist);
    	}
    	
    	// �����Сֵ����С��center
    	double min = (double) ld.get(0);
    	int index = 0;
    	for (int j = 0; j < ld.size(); j++) {          
            if (min > (Double)ld.get(j)){
            	min = (Double)ld.get(j);   
            	index = j;
            }
         }        
    	return centers[index];
     }
    
    
    /**
     * ��������ŷ�Ͼ���
     * 
     * @param pa
     *            double[]
     * @param pb
     *            double[]
     * @param dim
     *            int ά��
     * @return double ����
     */
    public static double dist(double[] pa, double[] pb) {
        double rv = 0;
        int dim = pb.length;
        for (int i = 0; i < dim; i++) {
            double temp = pa[i] - pb[i];
            temp = temp * temp;
            rv += temp;
        }
        return Math.sqrt(rv);
    }

}
