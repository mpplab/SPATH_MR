package OLA;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MPPHelper {

	//���캯�� 
	public MPPHelper(){}
	
	// ��Console�����List<String>
    public void PrintListString(List<String> ls) { 
        for(int i=0; i<ls.size(); i++){
        	System.out.print(" " + ls.get(i));
        }
        System.out.println("");
    } 
	
	
	// ��Console�����List<Integer>
    public void PrintListInteger(List<Integer> li) { 
        for(int i=0; i<li.size(); i++){
        	System.out.print(" " + li.get(i));
        }
        System.out.println("");
    } 
    
	// ��Console�����List<Double>
    public void PrintListDouble(List<Double> li) { 
        for(int i=0; i<li.size(); i++){
        	System.out.print(" " + li.get(i));
        }
        System.out.println("");
    } 
    
	// ��Console������� 
    public void PrintColumn(DataColumn dc) { 
    	System.out.print(dc.columnName);    
        for(String ss : dc.columnList){
        	System.out.print(" " + ss);
        }
    } 
    
	// ��Console�������
    public void PrintTable(DataTable dt) { 
    	for(int i=1; i<=dt.getColumnCount(); i++){
    		PrintColumn(dt.getColumn(i));
    		System.out.println("");
    	}
    } 
    
    
    // DataTable��ת��
    public DataTable TransposeDT(DataTable dt){
    	DataTable dt_Transpose = new DataTable();
    	int columnSize = dt.getColumn(1).size();  //ÿ�����԰���������ֵ�ĸ���  
    	int columnCount = dt.getColumnCount();    //���Ը��� 
    	for(int i=1; i<=columnSize; i++){
    		DataColumn dc = new DataColumn();
    		dc.setColumnName(Integer.toString(i));    //ע��:�����ColumnName�Ѿ�����Ϊ�к�
    		for(int j=1; j<=columnCount; j++){
    			dc.add2ColumnList(dt.getColumn(j).getValue(i));
    		}
    		dt_Transpose.addColumn(dc);
    	}
    	return dt_Transpose;
    }
	
    
	// ��Console�����Node
    public void PrintNode(Node node) { 
    	this.PrintListInteger(node.liGenCom);
    } 
    
	// ��Console�����Nodes
    public void PrintNodes(List<Node> nodes) { 
    	for(int i = 0; i < nodes.size(); i++) {
    		this.PrintListInteger(nodes.get(i).liGenCom);
    	}
    	
    } 
    
	// ��Console�����CiColumn
    public void PrintCiColumn(CiColumn ciC) { 
    	System.out.print(ciC.iD + " ");    
    	for (Map<String,String> map : ciC.columnList) { // ����datatrans(һ������)
            for(Map.Entry<String,String> me : map.entrySet()){ 
            	System.out.print(me.getKey() + " "+ me.getValue() + " ");
           }
	}
    	System.out.print(ciC.parent1 + " ");   
    	System.out.print(ciC.parent2 + " ");  
    	System.out.print(ciC.flag);  
    }
    
	// ��Console�����Ci
    public void PrintCiTable(CiTable ciT) { 
    	for(int i=1; i<=ciT.getCiColumncount(); i++){
    		PrintCiColumn(ciT.getCiColumn(i));
    		System.out.println("");
    	}
    } 

	// ��Console�����EiColumn
	public void PrintEiColumn(EiColumn eiC) { 
	        	System.out.println(eiC.getEiStart() + " "+ eiC.getEiEnd());
	}


	// ��Console�����Ei
	public void PrintEiTable(EiTable eiT) { 
		for(int i=1; i<=eiT.getEiColumncount(); i++){
			PrintEiColumn(eiT.getEiColumn(i));
		}
	}
	
	public void PrintFrequencySet(Map<List<String>, Integer> fs){
		for (Map.Entry<List<String>, Integer> me : fs.entrySet()) {
			List<String> ls = me.getKey();
			for(int i=0; i<ls.size(); i++){
				System.out.print(ls.get(i) + " ");
			}
			System.out.print(me.getValue());
			System.out.println("");
		}
	}
	
	public void PrintFsColumn(FsColumn fsc){
		
		
		// (1) ����ȼ������Լ���
		this.PrintListString(fsc.Item);
		
		// (2) ����õȼ����Ӧ��ID�γɵ�List
//		this.PrintListInteger(fsc.list_ID);
		
		
		// (3) ����ȼ�����Ԫ�����
		System.out.println("�õȼ����е�Ԫ��ĸ���Ϊ��" + fsc.FsCount);
		
		// (4) ������ĵ��ֵ
		double[][] centers = fsc.centers;
		int length = centers.length;
		int dim = centers[0].length;
		for(int i=0; i<length; i++){
			System.out.print("��" + i + "�����ĵ������Ϊ��");
			for(int j=0; j<dim; j++){
				System.out.print(centers[i][j] + " ");
			}
			System.out.println("");
		}
		
		// (5) ���sub_list_ID
		List<List<Integer>> sub_list_ID = fsc.sub_list_ID;
		for (int i = 0; i< sub_list_ID.size(); i++){
			System.out.print("��" + i + "�����ĵ��Ӧ��ID��");
			this.PrintListInteger(sub_list_ID.get(i));
		}
	}
	
	
	public void PrintFsTable(FsTable fst){
		int i = 1;
		for(FsColumn fsc:fst.lfs){
			System.out.println("");
			System.out.println("��" + i + "��Ƶ�����");
			this.PrintFsColumn(fsc);
			i ++;
		}
	}
	
	
	public FsTable getFsTableSatisfyK(FsTable fst, int k) {
		FsTable result = new FsTable();
		for(int i = 0; i < fst.lfs.size(); i++) {
			if(fst.lfs.get(i).FsCount >= k) {
				result.add(fst.lfs.get(i));
			}
		}
		return result;
	}
	
	
	public DataTable delListIDInDataTable(DataTable dataTrans, FsTable fst_Satisfy_k) {
		for(int i = 0; i < fst_Satisfy_k.lfs.size(); i++) {
			dataTrans = this.delDTByID_List(dataTrans, fst_Satisfy_k.lfs.get(i).list_ID);
		}
		return dataTrans;
	}
	
	public DataTable delListIDInDataTable2(DataTable dataTrans, FsTable fst_Satisfy_k) {
		List<Integer> li = new ArrayList<Integer>();
		for(int i = 0; i < fst_Satisfy_k.lfs.size(); i++) {
			li.addAll(fst_Satisfy_k.lfs.get(i).list_ID);
		}
		dataTrans = this.delDTByID_List2(dataTrans, li);
		return dataTrans;
	}
	
	public double getILbyGenGroup(DataTable dt, List<Integer> lGenGroup, List<String> attQ) {
		
		// (1) ��ʼ��������
		DBHelper help=new DBHelper();
        MPPHelper mh = new MPPHelper();
        GenHelper gh = new GenHelper();  // ����������
        
        // (2) ��÷���������ݱ�ת��
		DataTable dt_gen =  gh.getGenDataTable(dt, lGenGroup);          // ��÷�������
		dt_gen = mh.TransposeDT(dt_gen);           // ����������ת��
		
		// (3) ���Ƶ���
		Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // ���Ƶ���
		
		// (4) ����(2)��(3)�Ľṹ����Ƶ�ʼ���
		FsTable fst = mh.getFsTable(dt_gen, frequencySet);
		
		// (5) ����IL_Table
		IL_Table ilt = new IL_Table();
		for(int w=1; w<=fst.lfs.size(); w++){
			// (5.1) ���ÿһ��Ƶ������е�Column
			FsColumn fsc = fst.getFsColumn(w);   // �����������µ�Ƶ���
			
			// (5.2) ����IL_Column��Ϊ��index���и�ֵ
			IL_Column ilc = new IL_Column();
			ilc.index = w;
			
			// (5.3) �Ե�ǰƵ���Column�е�ÿ�����Զ�����IL_Base��add��IL_Column
			for (int q = 0; q < attQ.size(); q++){

				IL_Base ilb = new IL_Base();    
				ilb.sAttrname = attQ.get(q);     // ����������˳����и�ֵ����֤һ����
				ilb.sClass = gh.gt.getGenColumn(ilb.sAttrname).attr_class;  // ���������˳����и�ֵ����֤һ����
				ilb.iCount = fsc.FsCount;    // ���������и�ֵ
				if(ilb.sClass.equals("Numerical")) {
						String sSQL = "select " + ilb.sAttrname + " from adult";
				       	List<String> ls = help.GetListFromColumnbyNum(sSQL, fsc.list_ID);     // ע��lli.get(p)��ʵ���ǰ���Num�б��Ƶ���
			            Double max = Double.parseDouble(ls.get(0));     
			            Double min = Double.parseDouble(ls.get(0)); 
			            for (int r = 0; r < ls.size(); r++) {          
			                      if (min > Double.parseDouble(ls.get(r))) min = Double.parseDouble(ls.get(r));   
			                      if (max < Double.parseDouble(ls.get(r))) max = Double.parseDouble(ls.get(r));        
			            }
			            ilb.dMax = max;
			            ilb.dMin = min;
//						ilb.dMin = 0;
//						ilb.dMax = gh.gtd.getAttrRangeInGenLevel(ilb.sAttrname, node.getGenLevelByAttr(ilb.sAttrname));
			            ilb.dRange = gh.gt.getGenColumn(ilb.sAttrname).dRange;
					}
				else if(ilb.sClass.equals("Categorical")){
					ilb.dHSub = lGenGroup.get(q);
					ilb.dHAll = gh.gt.getGenColumn(ilb.sAttrname).maxGenLevel;
				}
				ilc.lil.add(ilb); 
			
			}
			 
			ilt.lcc.add(ilc);	
		}
		EvaluationMethod em = new EvaluationMethod();
		System.out.println("");
		System.out.println("IL = " + em.IL(ilt));
		
		return em.IL(ilt);

    }
    
    

	
	
	
	/**
	 * ����Ƶ���
	 * 
	 * @param preMap
	 *            ����Ƶ��K���map
	 * @param tgFileWriter
	 *            ����ļ����
	 * @return int Ƶ��i�����Ŀ
	 * @throws IOException
	 */
	public Map<List<String>, Integer> getFrequencySet(DataTable dt) {
		Map<List<String>, Integer> result = new HashMap<List<String>, Integer>();
		for(DataColumn dc:dt.dataT){
			if(result.containsKey(dc.columnList)){
				for (Map.Entry<List<String>, Integer> preMapItem : result.entrySet()) {
				    List<String> keyList = preMapItem.getKey();
					if(isListEqual(keyList,dc.columnList)){
						preMapItem.setValue(preMapItem.getValue()+1);
					}
				}
			}else{
				result.put(dc.columnList, 1);
			}
			
		}
		return result;		
	}


	// �������k��Ƶ���
	public Map<List<String>, Integer> getFrequencySetSatisfyK(Map<List<String>, Integer> frequencySet, int k) {
		Map<List<String>, Integer> result = new HashMap<List<String>, Integer>();
		for (Map.Entry<List<String>, Integer> me : frequencySet.entrySet()) {
			if(me.getValue() >= k) {
				result.put(me.getKey(), me.getValue());
			}
		}
		return result;
	}
	
	
	
	// ����dtΪ������,���Item��FsCount��list_ID
	public FsTable getFsTable(DataTable gen_dt,Map<List<String>, Integer> ml) {
		FsTable fst = new FsTable();
		
			for (Map.Entry<List<String>, Integer> me : ml.entrySet()) {
				FsColumn fsc = new FsColumn();
				fsc.Item = me.getKey();
				fsc.FsCount = me.getValue();
				for(int i = 1; i <= gen_dt.dataT.size(); i++){
					if(isListEqual(gen_dt.getColumn(i).columnList,fsc.Item)){
						fsc.list_ID.add(i);
					}
				}
				fst.add(fsc);
			}
			
		return fst;
		
	}
	
	public FsTable getFsTable2(DataTable gen_dt,Map<List<String>, Integer> ml) {
		FsTable fst = new FsTable();
		int iMax = gen_dt.dataT.size();
		
			System.out.println("��" + ml.entrySet().size() + "��");
			int index = 1;
			for (Map.Entry<List<String>, Integer> me : ml.entrySet()) {
				System.out.println("��ǰ��" + index + "��");
				index ++;
				
				FsColumn fsc = new FsColumn();
				fsc.Item = me.getKey();
				fsc.FsCount = me.getValue();
				// �������������Ż���
				fsc.list_ID.ensureCapacity(me.getValue());
				
				
//				long startTime = System.currentTimeMillis(); // ���ÿ�ʼʱ��
				for(int i = 1; i <= iMax; i++){
					if(isListEqual(gen_dt.getColumn(i).columnList,fsc.Item)){
						fsc.list_ID.add(i);
					}
				}
//				long endTime = System.currentTimeMillis(); // ���ý���ʱ��
//				long totalTime = 0;
//				totalTime+= endTime - startTime; // ��ʱ��
//				System.out.println("����ʱ��" + totalTime + "ms");
				fst.add(fsc);
			}
			
		return fst;
		
	}
	
	// ����dtΪ������,���Item��FsCount��list_ID
	public FsTable getFsTableByDivideN(DataTable gen_dt, Map<List<String>, Integer> ml, int n, int k) {
		FsTable fst = new FsTable();
		
			for (Map.Entry<List<String>, Integer> me : ml.entrySet()) {
				// ��һ�������Ƶ���
				ArrayList<Integer> list_ID_All = new ArrayList<Integer>();
				for(int i = 1; i<=gen_dt.dataT.size(); i++){
					if(compare(gen_dt.getColumn(i).columnList, me.getKey())){
						list_ID_All.add(i);
					}
				}
				if (me.getValue() > n*k) {         // me.getValue()Ӧ���Ǻ�list_ID_All.size()һ����ֵ
					int DivideN = (int) Math.floor(me.getValue()/(n*k));
					for (int index = 1; index <= DivideN; index ++) {
						if (index == DivideN) {
							FsColumn fsc = new FsColumn();
							fsc.Item = (ArrayList<String>) me.getKey(); 
							fsc.FsCount = list_ID_All.size() - index*n*k;
							fsc.list_ID = (ArrayList<Integer>) this.getListByDivideMN(list_ID_All, index*n*k,list_ID_All.size()-1);
							fst.add(fsc);
						}else{
							FsColumn fsc = new FsColumn();
							fsc.Item = (ArrayList<String>) me.getKey(); 
							fsc.FsCount = n*k;
							fsc.list_ID = (ArrayList<Integer>) this.getListByDivideMN(list_ID_All, (index-1)*n*k, index*n*k-1);
							fst.add(fsc);
						}
					}
				}else{
					FsColumn fsc = new FsColumn();
					fsc.Item = (ArrayList<String>) me.getKey();
					fsc.FsCount = me.getValue();
					fsc.list_ID = list_ID_All;
					fst.add(fsc);
				}
				
			}
			return fst;
		
	}
	
	
	public List<Integer> getListByDivideMN(List<Integer> ls, int M, int N) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = M; i <=N; i++) {
			result.add(ls.get(i));
		}
		return result;
	}
	
	
	
	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
	    if(a.size() != b.size())
	        return false;
//	    Collections.sort(a);
//	    Collections.sort(b);
	    for(int i=0;i<a.size();i++){
	        if(!a.get(i).equals(b.get(i)))
	            return false;
	    }
	    return true;
	}	
	
	public boolean isListEqual(List<String> ls1,List<String> ls2){
		boolean result = true;
		for(int i=0; i<ls1.size(); i++){
			if(!ls1.get(i).equals(ls2.get(i))){
//			if(ls1.get(i)!=ls2.get(i)){
				result = false;
				break;
			}
		}
		return result;
	}
	
	public boolean isk(Map<List<String>, Integer> fs,int k){
		boolean result = true;
		for (Map.Entry<List<String>, Integer> me : fs.entrySet()) {
			if(me.getValue() < k){
				result = false;
			}
		}
		return result;
	}
	
	
	
	public void markAllDirectGenNode(CiColumn node,CiTable Ci,EiTable Ei){
		// (1) ���ȶ��������еķ���ID�б�
		List<Integer> iDList = Ei.getAllDirectGenNode(node.iD);
		// (2) ����Լ�
		System.out.print("MarkID" + node.iD + " ");
		// (2) ����Լ��ĸ���
		for(int i=0; i<iDList.size(); i++){
			Ci.markColumn(iDList.get(i));
		}
	}
	
	
	public double[][] convertDT2DoubleArray(DataTable dt){
		// ��ȱ��һ����String�ܷ�ת����double���ж�
		
		int i = dt.dataT.size();
		int j = dt.dataT.get(0).columnList.size();
		
		double[][] result = new double[i][j];
		
		i = 0;
		for(DataColumn dc:dt.dataT){
			for(int m = 0; m< j; m++){
				result[i][m] = Double.valueOf(dc.columnList.get(m));
			}
			i++;
		}
		
		return result;
	}
	
	
	public int getNumFromID_ID_Dist(List<Integer> ID_Item, int ID1){
		for (int i = 0; i < ID_Item.size(); i++) {
			if(ID_Item.get(i) == ID1){
				return i;
			}
		}
		return 0;
	}
	
	
	
	// ������ID���ɵ�Listͬ��ɾ��dt_for_delete
	public DataTable delDTByID_List(DataTable dt_for_delete, List<Integer> List_ID_of_firstK) {
		DataTable result = dt_for_delete;
		for (int i = 0; i < List_ID_of_firstK.size(); i++) {
			for (int j = 0; j < result.dataT.size(); j++){
				if (List_ID_of_firstK.get(i) == Integer.parseInt(result.dataT.get(j).columnName)){
					result.dataT.remove(j);
				}
			}
		}
		return result;
	}
	
	
	// ������ID���ɵ�Listͬ��ɾ��dt_for_delete ----- ע��㣬�÷���ɾ���� delDTByID_List��֮ͬ������DataTable�Ǻ��е�
	public DataTable delDTByID_List2(DataTable dataTrans, List<Integer> iForDel) {	
		DataTable dt_new = new DataTable();
		
	    int jMax = dataTrans.dataT.get(1).size();
	    
	    for(int i = 0; i < dataTrans.dataT.size(); i++) {
	    	List<String> lsc_new = new ArrayList<String>();
	    	for(int j = 0; j < jMax; j++) {
	    		int flag = 1;
	    		for(int m = 0; m < iForDel.size(); m ++) {
	    			Integer iForDelReal = iForDel.get(m)-1;
	    			if(j == iForDelReal){
	    				flag = 0;
	    				break;
	    			}
	    		}
	    		if(flag == 1) {
		    		String ss = dataTrans.dataT.get(i).columnList.get(j);
		    		lsc_new.add(ss);
	    		}
	    		
	    	}
	    	dt_new.addColumn(dataTrans.dataT.get(i).columnName,lsc_new);
	    }
	    return dt_new;
	}
	
	
	// ������ID���ɵ�Listͬ��ɾ��ID_Item
	public List<Integer> delID_ItemByID_List(List<Integer> ID_Item, List<Integer> List_ID_of_firstK) {
		
		for (int i = 0; i < List_ID_of_firstK.size(); i++) {
			for (int j = 0; j < ID_Item.size(); j++){
				if(List_ID_of_firstK.get(i).equals(ID_Item.get(j))){
					ID_Item.remove(j);
				}
			}
		}
		return ID_Item;
	}
	
	
	// ������ɾ���쳣����	
	/**delAbData
	 * ɾ�������д�����������
	 * 
	 * @dataTrans���������ݼ�
	 * @abFlag���쳣��־������Ϊ���������� ����
	 */
	public DataTable delAbData(DataTable dataTrans_pre,String abFlag) {
		DataTable dataTrans_new = new DataTable();
		Integer flag = 0;
		for(DataColumn dc:dataTrans_pre.dataT){
			flag = 0;
			for(String s:dc.columnList){
				if (s.equals(abFlag)) {
					flag = 1;
					break;
				} 
			}
			if (flag == 0) {
				dataTrans_new.addColumn(dc);;
			}
		}
		return dataTrans_new;
	}

	
	// �Զ�����Ei
	public void AutoCreateEiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=1; i<=n_attQ; i++){
			String cSQL = "create table E" + i + "(EiStart int, EiEnd int)";
			dh.CreateDataBase(cSQL);
		}
	}
	
	// �Զ�ɾ��Ei
	public void AutoDropEiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=1; i<=n_attQ; i++){
			String cSQL = "drop table E" + i;
			dh.CreateDataBase(cSQL);
		}
	}
	
	// �Զ�����CandidateEdgesTable
	public void AutoCreateCandidateEdgesTable(){
		DBHelper dh = new DBHelper();
			String cSQL = "create table CandidateEdges(EiStart int,EiEnd int)";
			String cSQL1 = "create table CandidateEdges1(EiStart int,EiEnd int)";
			String cSQL2 = "create table CandidateEdges2(EiStart int,EiEnd int)";
			String cSQL3 = "create table CandidateEdges3(EiStart int,EiEnd int)";
			dh.CreateDataBase(cSQL);
			dh.CreateDataBase(cSQL1);
			dh.CreateDataBase(cSQL2);
			dh.CreateDataBase(cSQL3);
	}
	
	// �Զ�ɾ��CandidateEdgesTable
	public void AutoDropCandidateEdgesTable(){
		DBHelper dh = new DBHelper();
			String cSQL = "drop table CandidateEdges";
			String cSQL1 = "drop table CandidateEdges1";
			String cSQL2 = "drop table CandidateEdges2";
			String cSQL3 = "drop table CandidateEdges3";
			dh.CreateDataBase(cSQL);
			dh.CreateDataBase(cSQL1);
			dh.CreateDataBase(cSQL2);
			dh.CreateDataBase(cSQL3);
	}


	
	// �Զ�����C1
	public void AutoCreateC1Table(){
		DBHelper dh = new DBHelper();
		String cSQL = "create table C1(ID int IDENTITY(1,1) NOT NULL,dim1 nvarchar(50),index1 int)";
		dh.CreateDataBase(cSQL);
	}
	
	// �Զ�ɾ��C1
	public void AutoDropC1Table(){
		DBHelper dh = new DBHelper();
		String cSQL = "drop table C1";
		dh.CreateDataBase(cSQL);
	}
	
	// �Զ�����Ci����C1��
	public void AutoCreateCiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=2; i<=n_attQ; i++){
			String cSQL = "create table C" + i + "(ID int IDENTITY(1,1) NOT NULL,";
			for(int j=1;j<=i;j++){
				cSQL = cSQL + "dim" + j + " nvarchar(50),index" + j + " int,";
			}
			cSQL = cSQL + "parent1 int,parent2 int)";
			dh.CreateDataBase(cSQL);
			String cSQL_ON = "SET IDENTITY_INSERT C" + i + " ON";
			dh.CreateDataBase(cSQL_ON);
		}
	}
	
	// �Զ�ɾ��Ci����C1��
	public void AutoDropCiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=2; i<=n_attQ; i++){
			String cSQL = "drop table C" + i;
			dh.CreateDataBase(cSQL);
		}
	}
	
	
	// �Զ����C1��
	public void AutoFillC1E1Table(List<String> attQ) throws SQLException{
		GenHelper gh = new GenHelper();
		DBHelper dh = new DBHelper();
		int iD = 1;
		for(int i=0; i<attQ.size(); i++){
			GenAttrLevel gal = gh.getAttrLevel(attQ.get(i));
			
			// ��������ʾ���֣�����ע�͵�
//			System.out.print(gal.attr + " ");
//			for(int j=0; j<gal.level_Item.size(); j++){
//				System.out.print(gal.level_Item.get(j) + " ");
//			}
//			System.out.println("");
			
			
			// д��C1��E1
			iD = dh.insert2C1E1(gal, iD);
			
		}
		
	}
	
	//ȥ��columnlist�е�һ��map
			public CiColumn removeAttr(int i,CiColumn ciC){
				CiColumn rNode = new CiColumn();
				for(int j =0;j<ciC.columnList.size();j++){
					if(i != j){
						rNode.columnList.add(ciC.columnList.get(j));
					}
				}
//				for(Map<String,String> col:ciC.columnList){
//					rNode.columnList.add(col);
//				}
				rNode.iD = ciC.iD;
				rNode.flag = ciC.flag;
				rNode.parent1 = ciC.parent1;
				rNode.parent2 = ciC.parent2;
				return rNode;
			}
	
}

