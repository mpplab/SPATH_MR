package Incognito_SQLExpress_Census;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Incognito_SQL {

	
	public static void main(String[] args) throws SQLException {  
		
		// ��¼��ʼʱ��
		long startTime = System.currentTimeMillis(); // ���ÿ�ʼʱ��
		
		
	    // ���������ݽ��и�ֵ
		int k = public_data.k;   // k����
		List<String> attQ = public_data.attQ;
		
		// ��ʼ��������
		DBHelper help=new DBHelper();
        MPPHelper mh = new MPPHelper();
        GenHelper gh = new GenHelper();  // ����������
		
        // n_attQ��ʾ׼��ʶ�����Եĸ���
        int n_attQ = attQ.size();  
		
		// ��ʼ�����ݱ�dataTrans
		DataTable dataTrans = new DataTable(); 
          
		
       // ����attQ��׼��ʶ�����ԣ���ȡ���ݵ�dataTrans��
       List<String> lsc = new ArrayList<String>();
        for(int i = 0; i < n_attQ; i++) {
        	String att_now = attQ.get(i).toString();
//	       	String sSQL = "select top 5000 " + att_now + " from adult";
        	String sSQL = "select " + att_now + " from census_income";
	       	lsc = help.GetSingleColumn(sSQL);
	       	dataTrans.addColumn(att_now, lsc);
       }
         
        // ��һ���ǽ����б���գ������C1��E1
        Main_Preparation_Work mpw = new Main_Preparation_Work();
        mpw.initTable();
        
        CiTable queue = new CiTable();
        int n = attQ.size();
        for(int i = 1; i <= n; i++) {
        	System.out.println("==================��ǰΪ��" + i + "�����Եļ��㣡==================");
        	
        	// (1) ���Ci��Ei
        	String Ci = "C" + Integer.toString(i);
        	String Ei = "E" + Integer.toString(i);
        	CiTable ciT = help.readSql2Ci(Ci);
        	EiTable eiT = help.readSql2Ei(Ei);
        	// mh.PrintCiTable(ciT);
        	
        	// (2) ���root�ڵ㲢ȥ�ظ�
        	List<Integer> lint = new ArrayList<Integer>();
        	for(int j = 1; j <= eiT.getEiColumncount(); j++) {
        		int flag = 1;
        		for(int k1 = 1; k1 <= eiT.getEiColumncount(); k1++) {
        			if(eiT.getEiColumn(j).EiStart == eiT.getEiColumn(k1).EiEnd){
        				flag = 0;
        			}
        		}
        		if(flag ==1){
        			// queue.addCiColumn(ciT.getCiColumn(eiT.getEiColumn(j).EiStart));
        			// queue.addCiColumn(ciT.getCiColumn(eiT.getEiColumn(j).EiStart));
        			lint.add(eiT.getEiColumn(j).EiStart);
        		}
        	}
        	// ��ʾroot�ڵ�
//        	 mh.PrintCiTable(queue);
        	
        	 
        	// (3) ��root�ڵ����ȥ��
        	List<Integer> lint2 = new ArrayList<Integer>();
        	CiTable root_queue = new CiTable();
            for (int j = 0; j < lint.size(); j++){
                if(!lint2.contains(lint.get(j))){
                	lint2.add(lint.get(j));
                	// System.out.println(lint.get(j));
                }
            }
        	for(int j = 0; j < lint2.size(); j++){
        		queue.addCiColumn(ciT.getCiColumn(lint2.get(j)));
        		root_queue.addCiColumn(ciT.getCiColumn(lint2.get(j)));
        	}
        	// ��ʾroot�ڵ�
//        	System.out.println("");
//        	mh.PrintCiTable(queue);
        	
        	
        	// (4) ��queue�ڵ��������
        	

        	// (5,6,...) whileѭ��
        	while(queue.getCiColumncount() != 0){
        		// (5) Remove first item from queue
        		CiColumn node = queue.getCiColumn(1);   // �ѵ�һ��Ci���Ƹ�node
        		queue.removeColumn(1);                 // �Ƴ���һ��Ci
        		// mh.PrintCiColumn(node);
        		// System.out.println("");
            	// mh.PrintCiTable(queue);
        		
        		
        		Map<List<String>, Integer> frequencySet = new HashMap<List<String>, Integer>();
        		if(node.flag == 0){     // if node is not marked then
        			DataTable dt_gen = new DataTable();
        			if(root_queue.isContain(node)){   // if node is a root
        				dt_gen =  gh.getGenDataTable(dataTrans, node);          // ��÷�������
        				dt_gen = mh.TransposeDT(dt_gen);           // ����������ת��
        				// mh.PrintTable(dt_gen);
        				frequencySet = mh.getFrequencySet(dt_gen);
        				// mh.PrintFrequencySet(frequencySet);
//        				System.out.println("");
//        				System.out.println("");
//        				System.out.println("");
//        				System.out.println("");
        			}else{
        				dt_gen =  gh.getGenDataTable(dataTrans, node);   
        				dt_gen = mh.TransposeDT(dt_gen);
        				frequencySet = mh.getFrequencySet(dt_gen);
        			}
        			
//        			// ����һ���������Ŵ��
//        			if(node.columnList.size() == 2){
//        				if(node.columnList.get(0).containsKey("age") && node.columnList.get(1).containsKey("native_country")) {
//        					System.out.println("Good!");
//        				}else {
//        					System.out.println("Bad!");
//        				}
//        			}
        			
        			// �����ж��Ƿ�Ϊk����
        			if(mh.isk(frequencySet, k)){
        				mh.markAllDirectGenNode(node, ciT, eiT);
        			}else{
//        				mh.PrintCiTable(ciT);
        				// (1) Delete node from Ci
        				ciT.removeColumnByAttrName(node.iD);    // ��Ci��ɾ������
//        				mh.PrintCiColumn(node);
//        				System.out.println("");
//        				mh.PrintCiTable(ciT);
        				
        				// (2) Insert direct generalizations of node into queue
        				List<Integer> nodenow = new ArrayList<Integer>();
        				nodenow.add(node.iD);
        				List<Integer> directGenNode = eiT.getDirectGenNode(nodenow);
        				for(int z=0; z< directGenNode.size(); z++){
        					if (ciT.getCiColumnByID(directGenNode.get(z))!=null){
        					queue.addCiColumn(ciT.getCiColumnByID(directGenNode.get(z)));
        					}
//        					System.out.println("");
//        					mh.PrintCiColumn(ciT.getCiColumnByID(directGenNode.get(z)));
        				}
        			}

        		}
        		
        		// break;    // ����ط�Ҫɾ��
        		
        	}
        	System.out.println("");
        	if(i == n){
            	// ����Ci+1��Ei+1
            	help.insert2Ci(ciT, i);    // �������ݿ��е�Ci��
            	mh.PrintCiTable(ciT);
            	// lastSi = ciT;
        	}else{
            	// ����Ci+1��Ei+1
            	help.insert2Ci(ciT, i);    // �������ݿ��е�Ci��
            	// mh.PrintCiTable(ciT);
            	help.insert2NextCi(i+1);
            	// (ע�⣺)���������ǹؼ�
            	// (1) ����SQL�������������㷨
            	help.insert2NextEi(i+1);
            	// (2) �������ڴ�����Ei���������㷨
            	// help.insert2NextEi2(i+1);
            	System.out.println("test");
        	}

        	
        }
        
        
        // ����㷨ʱ��
		long endTime = System.currentTimeMillis(); // ���ý���ʱ��
		long totalTime = 0;
		totalTime+= endTime - startTime; // ��ʱ��
		System.out.println("Incognito�㷨����ʱ��" + totalTime + "ms");
	}
        
        
	
	
	
	
	
	
	
	
	
	

	
	
}
