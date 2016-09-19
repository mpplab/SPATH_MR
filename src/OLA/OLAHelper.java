package OLA;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 
 


public class OLAHelper {

    GenTableDefinition_census_income gtd = new GenTableDefinition_census_income();   // ���巺������
    GenTable gt = gtd.getGenTable();   // �������еķ�����÷�����
    MPPHelper mh = new MPPHelper();    // ������
    GenHelper gh = new GenHelper();  // ����������
    
    
    
    // ����tagΪ0��Nodes
	public List<Node> getNodesByGenLatLevel(int iGenLatLevel) {
		List<Node> result = new ArrayList<Node>();
		for(int i = 0; i < OLA.nodes.size(); i++) {
			if(OLA.nodes.get(i).iGenLatLevel == iGenLatLevel && OLA.nodes.get(i).tag == 0) {
				result.add(OLA.nodes.get(i));
			}
		}
		return result;
	}
	
	
	// ����������iLow��iHigh����м䷺�������Ӧ��nodes
	public List<Node> getMiddleQueue(int iLow, int iHigh) {
		// (1) ����iLow��iHigh����м�ķ����񼶱�
		int iGenLatLevel = (int) Math.floor((iHigh - iLow)/2) + iLow;
		// (2) ȡ��middleQueue
		List<Node> middleQueue = new ArrayList<Node>();
		middleQueue = getNodesByGenLatLevel(iGenLatLevel);
		return middleQueue;
	}
	
	
//	public List<Node> getMinQueue(List<Node> nodes, int iLow, int iHigh) {
//		// (1) �����ж�node��Ϊ��
//		if(nodes != null) {
//			// () ��nodes����ѭ��
//			for(int i = 0; i < nodes.size(); i ++) {
//				node_now = nodes.get(i);
//				List<Node> middleQueue = getMiddleQueue(int iLow, int iHigh);
//						
//			}
//		}	
//	}
	
//	public List<Node> getMinQueueForSingleNode(DataTable dataTrans, Node node, int k, int iLow, int iHigh) {
//		
//	}
	
	
	// OLA����ĵĺ���
	public void getMinQueueForNodes(DataTable dataTrans, List<Node> nodes, int k, int iLow, int iHigh) {
		
		// (1) ��nodes�ڵ����������0��ʱ�򣬻�һֱѭ��
		while(nodes.size() > 0) {
			// (2) ��õ�һ��node����������ͬʱ������nodes��ɾ��
			Node node = nodes.get(0);
			nodes.remove(0);
			
			// (3) �жϸ�node��tag�Ƿ�Ϊ0��������Ϊ1����2����ô��ζ�Ų���Ҫ������
			if(node.tag == 0) {
				// (0) ��������ͳ�Ƶ�
				OLA.recursionTimes = OLA.recursionTimes + 1;
				System.out.println("��ǰ�ǵ�" + OLA.recursionTimes + "�μ���Ƶ����Ĺ��̣�");
				
				// (4) �жϵ�ǰ�ڵ��Ƿ�����k����
					// (4.1) ��÷�����ת��(���ڼ���)
					DataTable dt_gen = getGenDataTable(dataTrans, node.liGenCom);   
					dt_gen = mh.TransposeDT(dt_gen);           // ����������ת��
					
					// (4��2) ���Ƶ���
					Map<List<String>, Integer> frequencySet = new HashMap<List<String>, Integer>();
					frequencySet =  mh.getFrequencySet(dt_gen);
					
					// (4.3) ͨ��Ƶ��������ж��Ƿ�����k
					if(mh.isk(frequencySet, k)){
						OLA.minQueue.add(node);   // �������k��������װ��minQueue
						markAllGenNode(node);    // ����markΪOK��Ȼ������ȡ1/2
						if(node.iGenLatLevel - iLow == 1) {
							// ʲô������
						} else {
							List<Node> nodes_middle = getMiddleQueue(iLow, node.iGenLatLevel);  // ����м���е�ʱ����tag���жϣ����������еĶ���ӽ���	 
							// () �ǳ��ؼ���һ�����ݹ�
							getMinQueueForNodes(dataTrans, nodes_middle, k, iLow, node.iGenLatLevel);
						}
					}else{
						markAllFatherNode(node);   // ����markΪdel��Ȼ������ȡ1/2
						if(iHigh - node.iGenLatLevel== 1) {
							// ʲô������
						} else {
							List<Node> nodes_middle = getMiddleQueue(node.iGenLatLevel,iHigh);  // ����м���е�ʱ����tag���жϣ����������еĶ���ӽ���	 
							// () �ǳ��ؼ���һ�����ݹ�
							getMinQueueForNodes(dataTrans, nodes_middle, k, node.iGenLatLevel ,iHigh);
						}
					}
			}
			
		}
		
	}
	
	
	// ɸѡ���
	public List<Node> getFiltrate(List<Node> liNode) {
		List<Node> result = new ArrayList<Node>();
		for(int i = 0; i < liNode.size(); i ++) {
			Node node = liNode.get(i);
			int flag = 1;
			for(int j = 0; j < liNode.size(); j ++) {
				// �жϵ�ǰ�ڵ��ǲ������нڵ���ĳ���ڵ�ķ����ڵ㣬����ǵģ���ô�Ͱ�flag����Ϊ0
				if(isGenNode(node.liGenCom,liNode.get(j).liGenCom)) {
					flag = 0;
				}
			}
			// ������������󣬸ýڵ㲻���κνڵ�ĸ��ڵ㣬��ô���Լ��뵽���ս����
			if(flag == 1) {
				result.add(node);
			}
		}
		return result;
	}
	
	// �ж�li1�Ƿ�Ϊli2�ķ����ڵ�(Ҫ��ÿһ����Ҫ���ڵ��ں��ߣ�������Ҫ���ں���)
	public boolean isGenNode(List<Integer> li1, List<Integer> li2) {
		boolean result = false;
		int li1sum = 0;
		int li2sum = 0;
		int flag = 1;
		for(int i = 0; i < li1.size(); i ++) {
			li1sum = li1sum + li1.get(i);
			li2sum = li2sum + li2.get(i);
			if(li1.get(i) < li2.get(i)) {
				flag = 0;
			}
		}
		if(flag == 1) {
			if (li1sum > li2sum) {
				result = true;
			}
		}
		return result;
	}
	
	
	// ���������node�����з����ڵ�
	public void markAllGenNode(Node node) {
		  
		 int iMax = OLA.nodes.size();
		 int jMax = OLA.nodes.get(0).liGenCom.size();
		 for(int i = 0; i < iMax; i++) { 
			 Node node_now = OLA.nodes.get(i);
			 List<Integer> liGenCom = node_now.liGenCom;
			 int flag = 1;
			 for(int j = 0; j < jMax; j++) {
				 if(liGenCom.get(j) < node.liGenCom.get(j)) {
					 flag = 0;
					 break;
				 }
			 }
			 if(flag == 1) {
				 OLA.nodes.get(i).tag = 1;
//				 System.out.println("mark�Ľڵ�Ϊ��" + OLA.nodes.get(i).iGenLatLevel);
			 }
		 }
		  
	}
	
	// ���������node�����и��ڵ�
	public void markAllFatherNode(Node node) {
		  
		 int iMax = OLA.nodes.size();
		 int jMax = OLA.nodes.get(0).liGenCom.size();
		 for(int i = 0; i < iMax; i++) { 
			 Node node_now = OLA.nodes.get(i);
			 List<Integer> liGenCom = node_now.liGenCom;
			 int flag = 1;
			 for(int j = 0; j < jMax; j++) {
				 if(liGenCom.get(j) > node.liGenCom.get(j)) {
					 flag = 0;
					 break;
				 }
			 }
			 if(flag == 1) {
				 OLA.nodes.get(i).tag = 2;
			 }
		 }
		 
	}
	
	
	// �������Ի�÷������ԣ�����liGenCom��ʾ�������
	public DataTable getGenDataTable(DataTable dt, List<Integer> liGenCom){
		DataTable dt_gen = new DataTable();
		for(int i = 0; i < liGenCom.size(); i++) {
			DataColumn dc_pregen = dt.getColumn(i+1);
			DataColumn dc_gen = gh.getGenDataColumn(dc_pregen,liGenCom.get(i));
			dt_gen.addColumn(dc_gen);
		}
		return dt_gen;
	}
	
	
	 
}
