package Incognito_SQLExpress_Census;

import kmeans.kmeans;
import kmeans.kmeans_data;
import kmeans.kmeans_param;

public class Kmeans_fun {

	public kmeans_data data;
	public kmeans_param param;
	public int k;
	public int length;
	public int dim;
	
	public Kmeans_fun(double[][] points,int _length,int _dim,int _k){
        // double[][] points = {{0, 0}, {4, 10}, {1, 1}, {5, 8}}; //�������ݣ��ĸ���ά�ĵ�
//        int length = 4;
//        int dim = 2;
        length = _length;
        dim = _dim;
		data = new kmeans_data(points, length, dim); //��ʼ�����ݽṹ
        param = new kmeans_param(); //��ʼ�������ṹ
        param.initCenterMethod = kmeans_param.CENTER_RANDOM; //���þ������ĵ�ĳ�ʼ��ģʽΪ���ģʽ
         
        //��kmeans���㣬������
//        int k = 2;
        k = _k;
        kmeans.doKmeans(k, data, param);
        
        
        // �����ǩ
//        PrintLabels();
       
        // �������
//        PrintCenters();

	}
	
	
	// ������� 
	public void PrintCenters(){
        // �������
        System.out.println("");
        for(int i=0; i<k; i++){
      	  int w = i+1;
      	  System.out.print("��" + w + "�����ĵ������Ϊ��");
      	  for(int j=0; j<dim; j++){
      		  System.out.print(data.centers[i][j] + " ");
      	  }
      	  
      	  System.out.println(" �ô��еĸ���Ϊ��" + data.centerCounts[i] + "!");
        }
	}
	
	
	// �����ǩ
	public void PrintLabels(){
        //�鿴ÿ���������������
        System.out.print("The labels of points is: ");
        for (int lable : data.labels) {
           System.out.print(lable + "  ");
        }
	}
	
}
