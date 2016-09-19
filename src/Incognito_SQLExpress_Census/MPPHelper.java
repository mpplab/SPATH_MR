package Incognito_SQLExpress_Census;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPPHelper {
	public MPPHelper(){}
	
	// ÔÚConsoleÖÐÊä³öList<String>
    public void PrintListString(List<String> ls) { 
        for(int i=0; i<ls.size(); i++){
        	System.out.print(" " + ls.get(i));
        }
        System.out.println("");
    } 
    
 // ÔÚConsoleÖÐÊä³öList<Integer>
    public void PrintListInteger(List<Integer> li) { 
        for(int i=0; i<li.size(); i++){
        	System.out.print(" " + li.get(i));
        }
        System.out.println("");
    } 
    
	// ÔÚConsoleÖÐÊä³öList<Double>
    public void PrintListDouble(List<Double> li) { 
        for(int i=0; i<li.size(); i++){
        	System.out.print(" " + li.get(i));
        }
        System.out.println("");
    } 
    
	// ÔÚConsoleÖÐÊä³öÁÐ 
    public void PrintColumn(DataColumn dc) { 
    	System.out.print(dc.columnName);    
        for(String ss : dc.columnList){
        	System.out.print(" " + ss);
        }
    } 
    
	// ÔÚConsoleÖÐÊä³ö±í
    public void PrintTable(DataTable dt) { 
    	for(int i=1; i<=dt.getColumnCount(); i++){
    		PrintColumn(dt.getColumn(i));
    		System.out.println("");
    	}
    } 
    
 // DataTableµÄ×ªÖÃ
    public DataTable TransposeDT(DataTable dt){
    	DataTable dt_Transpose = new DataTable();
    	int columnSize = dt.getColumn(1).size();  //Ã¿¸öÊôÐÔ°üº¬µÄÊôÐÔÖµµÄ¸öÊý  
    	int columnCount = dt.getColumnCount();    //ÊôÐÔ¸öÊý 
    	for(int i=1; i<=columnSize; i++){
    		DataColumn dc = new DataColumn();
    		dc.setColumnName(Integer.toString(i));    //×¢Òâ:ÕâÀïµÄColumnNameÒÑ¾­ÉèÖÃÎªÁÐºÅ
    		for(int j=1; j<=columnCount; j++){
    			dc.add2ColumnList(dt.getColumn(j).getValue(i));
    		}
    		dt_Transpose.addColumn(dc);
    	}
    	return dt_Transpose;
    }
	
	// ÔÚConsoleÖÐÊä³öCiColumn
    public void PrintCiColumn(CiColumn ciC) { 
    	System.out.print(ciC.iD + " ");    
    	for (Map<String,String> map : ciC.columnList) { // ±éÀúdatatrans(Ò»ÌõÊÂÎñ)
            for(Map.Entry<String,String> me : map.entrySet()){ 
            	System.out.print(me.getKey() + " "+ me.getValue() + " ");
           }
	}
    	System.out.print(ciC.parent1 + " ");   
    	System.out.print(ciC.parent2 + " ");  
    	System.out.print(ciC.flag);  
    }
    
	// ÔÚConsoleÖÐÊä³öCi
    public void PrintCiTable(CiTable ciT) { 
    	for(int i=1; i<=ciT.getCiColumncount(); i++){
    		PrintCiColumn(ciT.getCiColumn(i));
    		System.out.println("");
    	}
    } 

	// ÔÚConsoleÖÐÊä³öEiColumn
	public void PrintEiColumn(EiColumn eiC) { 
	        	System.out.println(eiC.getEiStart() + " "+ eiC.getEiEnd());
	}
	
	// ÔÚConsoleÖÐÊä³öEi
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
		
		
		// (1) Êä³öµÈ¼ÛÀàÊôÐÔ¼¯ºÏ
		this.PrintListString(fsc.Item);
		
		// (2) Êä³ö¸ÃµÈ¼ÛÀà¶ÔÓ¦µÄIDÐÎ³ÉµÄList
//			this.PrintListInteger(fsc.list_ID);
		
		
		// (3) Êä³öµÈ¼ÛÀàÖÐÔª×é¸öÊý
		System.out.println("¸ÃµÈ¼ÛÀàÖÐµÄÔª×éµÄ¸öÊýÎª£º" + fsc.FsCount);
		
		// (4) Êä³öÖÐÐÄµãµÄÖµ
		double[][] centers = fsc.centers;
		int length = centers.length;
		int dim = centers[0].length;
		for(int i=0; i<length; i++){
			System.out.print("µÚ" + i + "¸öÖÐÐÄµãµÄ×ø±êÎª£º");
			for(int j=0; j<dim; j++){
				System.out.print(centers[i][j] + " ");
			}
			System.out.println("");
		}
		
		// (5) Êä³ösub_list_ID
		List<List<Integer>> sub_list_ID = fsc.sub_list_ID;
		for (int i = 0; i< sub_list_ID.size(); i++){
			System.out.print("µÚ" + i + "¸öÖÐÐÄµã¶ÔÓ¦µÄID£º");
			this.PrintListInteger(sub_list_ID.get(i));
		}
	}
	
	
	public void PrintFsTable(FsTable fst){
		int i = 1;
		for(FsColumn fsc:fst.lfs){
			System.out.println("");
			System.out.println("µÚ" + i + "¸öÆµ·±Ïî¼¯£º");
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
		
		// (1) ³õÊ¼»¯°ïÖúÀà
		DBHelper help=new DBHelper();
        MPPHelper mh = new MPPHelper();
        GenHelper gh = new GenHelper();  // ·º»¯°ïÖúÀà
        
        // (2) »ñµÃ·º»¯ºóµÄÊý¾Ý±í²¢×ªÖÃ
		DataTable dt_gen =  gh.getGenDataTable(dt, lGenGroup);          // »ñµÃ·º»¯Êý¾Ý
		dt_gen = mh.TransposeDT(dt_gen);           // ½«·º»¯Êý¾Ý×ªÖÃ
		
		// (3) »ñµÃÆµ·±Ïî¼¯
		Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // »ñµÃÆµ·±Ïî¼¯
		
		// (4) ¸ù¾Ý(2)ºÍ(3)µÄ½á¹¹¹¹½¨ÆµÂÊ¼¯±í
		FsTable fst = mh.getFsTable(dt_gen, frequencySet);
		
		// (5) ¹¹½¨IL_Table
		IL_Table ilt = new IL_Table();
		for(int w=1; w<=fst.lfs.size(); w++){
			// (5.1) »ñµÃÃ¿Ò»¸öÆµ·±Ïî¼¯±íÖÐµÄColumn
			FsColumn fsc = fst.getFsColumn(w);   // µ½·ÖÀàÊôÐÔÏÂµÄÆµ·±Ïî¼¯
			
			// (5.2) ¹¹½¨IL_Column²¢ÎªÆäindex½øÐÐ¸³Öµ
			IL_Column ilc = new IL_Column();
			ilc.index = w;
			
			// (5.3) ¶Ôµ±Ç°Æµ·±Ïî¼¯ColumnÖÐµÄÃ¿¸öÊôÐÔ¶¼¼ÆËãIL_Base²¢add½øIL_Column
			for (int q = 0; q < attQ.size(); q++){

				IL_Base ilb = new IL_Base();    
				ilb.sAttrname = attQ.get(q);     // ¶ÔÊôÐÔÃû°´Ë³Ðò½øÐÐ¸³Öµ£¬±£Ö¤Ò»ÖÂÐÔ
				ilb.sClass = gh.gt.getGenColumn(ilb.sAttrname).attr_class;  // ¶ÔÊôÐÔÀà±ð°´Ë³Ðò½øÐÐ¸³Öµ£¬±£Ö¤Ò»ÖÂÐÔ
				ilb.iCount = fsc.FsCount;    // ¶ÔÊýÁ¿½øÐÐ¸³Öµ
				if(ilb.sClass.equals("Numerical")) {
						String sSQL = "select " + ilb.sAttrname + " from adult";
				       	List<String> ls = help.GetListFromColumnbyNum(sSQL, fsc.list_ID);     // ×¢Òâlli.get(p)ÆäÊµ¾ÍÊÇ°üº¬NumÁÐ±íµÄÆµ·±Ïî¼¯
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
	 * ¼ÆËãÆµ·±Ïî¼¯
	 * 
	 * @param preMap
	 *            ±£´æÆµ·±KÏî¼¯µÄmap
	 * @param tgFileWriter
	 *            Êä³öÎÄ¼þ¾ä±ú
	 * @return int Æµ·±iÏî¼¯µÄÊýÄ¿
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


	// »ñµÃÂú×ãkµÄÆµ·±Ïî¼¯
	public Map<List<String>, Integer> getFrequencySetSatisfyK(Map<List<String>, Integer> frequencySet, int k) {
		Map<List<String>, Integer> result = new HashMap<List<String>, Integer>();
		for (Map.Entry<List<String>, Integer> me : frequencySet.entrySet()) {
			if(me.getValue() >= k) {
				result.put(me.getKey(), me.getValue());
			}
		}
		return result;
	}
	
	
	public FsTable getFsTable_MRAndSatisfyK(Map<List<String>, String> ml) {
		FsTable fst = new FsTable();
		
		for (Map.Entry<List<String>, String> me : ml.entrySet()) {
			String count = me.getValue().split(":")[1];
			Integer countInt = Integer.parseInt(count);
			
			if(countInt >= public_data.k)
			{
				String listID[] = me.getValue().split(":")[0].split(",");
				FsColumn fsc = new FsColumn();
				fsc.Item = me.getKey();
				fsc.FsCount = countInt;
				
//					Integer intListID[] = new Integer[listID.length];
//					for(int i = 0; i<listID.length; i++){
//						intListID[i] = Integer.parseInt(listID[i]);
//					}
//					Arrays.sort(intListID);
				
				for(int i = 0; i<listID.length; i++){
//						fsc.list_ID.add(intListID[i]);
					fsc.list_ID.add(Integer.parseInt(listID[i]));
				}
				fst.add(fsc);
			}
		}
			
		return fst;
		
	}
	
	public FsTable getFsTable_MR(Map<List<String>, String> ml) {
		FsTable fst = new FsTable();
		
			for (Map.Entry<List<String>, String> me : ml.entrySet()) {
				String count = me.getValue().split(":")[1];
				Integer countInt = Integer.parseInt(count);
				
				String listID[] = me.getValue().split(":")[0].split(",");
				FsColumn fsc = new FsColumn();
				fsc.Item = me.getKey();
				fsc.FsCount = countInt;
				for(int i = 0; i<listID.length; i++){
					fsc.list_ID.add(Integer.parseInt(listID[i]));
				}
				fst.add(fsc);
			}
			
		return fst;
		
	}
	
	// ÆäÖÐdtÎª·º»¯±í,Ìî³äItem£¬FsCountºÍlist_ID
	public FsTable getFsTable(DataTable gen_dt,Map<List<String>, Integer> ml) {
		FsTable fst = new FsTable();
		
			for (Map.Entry<List<String>, Integer> me : ml.entrySet()) {
				FsColumn fsc = new FsColumn();
				fsc.Item = me.getKey();
				fsc.FsCount = me.getValue();
				for(int i = 1; i<=gen_dt.dataT.size(); i++){
					if(isListEqual(gen_dt.getColumn(i).columnList,fsc.Item)){
						fsc.list_ID.add(i);
					}
				}
				fst.add(fsc);
			}
			
		return fst;
		
	}
	
	// ÆäÖÐdtÎª·º»¯±í,Ìî³äItem£¬FsCountºÍlist_ID
	public FsTable getFsTableByDivideN(DataTable gen_dt, Map<List<String>, Integer> ml, int n, int k) {
		FsTable fst = new FsTable();
		
			for (Map.Entry<List<String>, Integer> me : ml.entrySet()) {
				// µÚÒ»²½£º»ñµÃÆµ·±Ïî¼¯
				ArrayList<Integer> list_ID_All = new ArrayList<Integer>();
				for(int i = 1; i<=gen_dt.dataT.size(); i++){
					if(compare(gen_dt.getColumn(i).columnList, me.getKey())){
						list_ID_All.add(i);
					}
				}
				if (me.getValue() > n*k) {         // me.getValue()Ó¦¸ÃÊÇºÍlist_ID_All.size()Ò»¸öÊýÖµ
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
//		    Collections.sort(a);
//		    Collections.sort(b);
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
//				if(ls1.get(i)!=ls2.get(i)){
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
		// (1) Ê×ÏÈ¶¨Òå»ñµÃËùÓÐµÄ·º»¯IDÁÐ±í
		List<Integer> iDList = Ei.getAllDirectGenNode(node.iD);
		// (2) ±ê¼Ç×Ô¼º
		System.out.print("MarkID" + node.iD + " ");
		// (2) ±ê¼Ç×Ô¼ºµÄ¸¸±²
		for(int i=0; i<iDList.size(); i++){
			Ci.markColumn(iDList.get(i));
		}
	}
	
	
	public double[][] convertDT2DoubleArray(DataTable dt){
		// »¹È±ÉÙÒ»¸ö¶ÔStringÄÜ·ñ×ª»»³ÉdoubleµÄÅÐ¶Ï
		
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
	
	// »ñµÃ¾àÀë×îÐ¡µÄk¸öID£¬ÓÉÓÚ°üÀ¨×Ô¼º
	public List<Integer> getFirstK(List<ID_ID_Dist> liid, int k) {
		List<Integer> List_ID = new ArrayList<Integer>();
		for (int i = 0; i < k; i ++){
			// »ñµÃ×î´ó¾àÀëÁ½¶ËµÄIDÖµ
		    Double min = liid.get(0).dist;
		    int min_index = 0;
		    for (int r = 1; r < liid.size(); r++) {          
		    	if (liid.get(r).dist < min) {
		    		min = liid.get(r).dist;
		    		min_index = r;
		    	}
		    } 
			List_ID.add(liid.get(min_index).ID2);
			liid.remove(min_index);
		}
		return List_ID;
	}
	
	
	// ¸ù¾ÝÓÉID¹¹³ÉµÄListÍ¬²½É¾³ýdt_for_delete
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
	
	
	// ¸ù¾ÝÓÉID¹¹³ÉµÄListÍ¬²½É¾³ýdt_for_delete ----- ×¢Òâµã£¬¸Ã·½·¨É¾³ýºÍ delDTByID_List²»Í¬Ö®´¦ÔÚÓÚDataTableÊÇºáÐÐµÄ
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
	
	
	// ¸ù¾ÝÓÉID¹¹³ÉµÄListÍ¬²½É¾³ýID_Item
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
	
	
	// ·½·¨£ºÉ¾³ýÒì³£Êý¾Ý	
	/**delAbData
	 * É¾³ýÊý¾ÝÖÐ´ø¡°£¿¡±µÄÐÐ
	 * 
	 * @dataTrans£ºÊÂÎñÊý¾Ý¼¯
	 * @abFlag£ºÒì³£±êÖ¾£¬¿ÉÒÔÎª¡°£¿¡±¡¢¡° ¡±µÈ
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
	
	// ×Ô¶¯Éú³ÉEi
	public void AutoCreateEiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=1; i<=n_attQ; i++){
			String cSQL = "create table E" + i + "(EiStart int, EiEnd int)";
			dh.CreateDataBase(cSQL);
		}
	}
	
	// ×Ô¶¯É¾³ýEi
	public void AutoDropEiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=1; i<=n_attQ; i++){
			String cSQL = "drop table E" + i;
			dh.CreateDataBase(cSQL);
		}
	}
	
	// ×Ô¶¯Éú³ÉCandidateEdgesTable
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
	
	// ×Ô¶¯É¾³ýCandidateEdgesTable
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


	
	// ×Ô¶¯Éú³ÉC1
	public void AutoCreateC1Table(){
		DBHelper dh = new DBHelper();
		String cSQL = "create table C1(ID int IDENTITY(1,1) NOT NULL,dim1 nvarchar(50),index1 int)";
		dh.CreateDataBase(cSQL);
	}
	
	// ×Ô¶¯É¾³ýC1
	public void AutoDropC1Table(){
		DBHelper dh = new DBHelper();
		String cSQL = "drop table C1";
		dh.CreateDataBase(cSQL);
	}
	
	// ×Ô¶¯Éú³ÉCi£¨³ýC1£©
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
	
	// ×Ô¶¯É¾³ýCi£¨³ýC1£©
	public void AutoDropCiTable(int n_attQ){
		DBHelper dh = new DBHelper();
		for(int i=2; i<=n_attQ; i++){
			String cSQL = "drop table C" + i;
			dh.CreateDataBase(cSQL);
		}
	}
	
	
	// ×Ô¶¯Ìî³äC1±í
	public void AutoFillC1E1Table(List<String> attQ) throws SQLException{
		GenHelper gh = new GenHelper();
		DBHelper dh = new DBHelper();
		int iD = 1;
		for(int i=0; i<attQ.size(); i++){
			GenAttrLevel gal = gh.getAttrLevel(attQ.get(i));
			
			// ÒÔÏÂÊÇÏÔÊ¾²¿·Ö£¬¿ÉÒÔ×¢ÊÍµô
//				System.out.print(gal.attr + " ");
//				for(int j=0; j<gal.level_Item.size(); j++){
//					System.out.print(gal.level_Item.get(j) + " ");
//				}
//				System.out.println("");
			
			
			// Ð´ÈëC1ºÍE1
			iD = dh.insert2C1E1(gal, iD);
			
		}
		
	}
	
	//È¥µôcolumnlistÖÐµÄÒ»¸ömap
	public CiColumn removeAttr(int i,CiColumn ciC){
		CiColumn rNode = new CiColumn();
		for(int j =0;j<ciC.columnList.size();j++){
			if(i != j){
				rNode.columnList.add(ciC.columnList.get(j));
			}
		}
//					for(Map<String,String> col:ciC.columnList){
//						rNode.columnList.add(col);
//					}
		rNode.iD = ciC.iD;
		rNode.flag = ciC.flag;
		rNode.parent1 = ciC.parent1;
		rNode.parent2 = ciC.parent2;
		return rNode;
	}


	public double getILbyGenGroup_M(DataTable dt, List<Integer> lGenGroup, List<String> attQ, DataTable dtAll) {
		
		// (1) \ufffd\ufffd\u02bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
		DBHelper help=new DBHelper();
	    MPPHelper mh = new MPPHelper();
	    GenHelper gh = new GenHelper();  // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
	    
	    // (2) \ufffd\ufffd÷\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0771\ufffd\u05ea\ufffd\ufffd
		DataTable dt_gen =  gh.getGenDataTable(dt, lGenGroup);          // \ufffd\ufffd÷\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
		dt_gen = mh.TransposeDT(dt_gen);           // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\ufffd
		
		long fsTime = System.currentTimeMillis();
		// (3) \ufffd\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
		Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // \ufffd\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
		
		// (4) \ufffd\ufffd\ufffd\ufffd(2)\ufffd\ufffd(3)\ufffd\u013d\u1e79\ufffd\ufffd\ufffd\ufffd\u01b5\ufffd\u02bc\ufffd\ufffd\ufffd
		FsTable fst = mh.getFsTable(dt_gen, frequencySet);
		
		long feTime = System.currentTimeMillis();
		System.out.println("get frequency set use time:"+String.valueOf(feTime - fsTime)+"ms");
		
		long ilsTime = System.currentTimeMillis();
		// (5) \ufffd\ufffd\ufffd\ufffdIL_Table
		IL_Table ilt = new IL_Table();
		for(int w=1; w<=fst.lfs.size(); w++){
			// (5.1) \ufffd\ufffd\ufffdÿ\u04bb\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f\ufffd\ufffd\ufffd\u0435\ufffdColumn
			FsColumn fsc = fst.getFsColumn(w);   // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffdµ\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
			
			// (5.2) \ufffd\ufffd\ufffd\ufffdIL_Column\ufffd\ufffd\u03aa\ufffd\ufffdindex\ufffd\ufffd\ufffd\u0438\ufffd\u05b5
			IL_Column ilc = new IL_Column();
			ilc.index = w;
			
			// (5.3) \ufffd\u0535\ufffd\u01f0\u01b5\ufffd\ufffd\ufffd\uef2fColumn\ufffd\u0435\ufffdÿ\ufffd\ufffd\ufffd\ufffd\ufffd\u0536\ufffd\ufffd\ufffd\ufffd\ufffdIL_Base\ufffd\ufffdadd\ufffd\ufffdIL_Column
			for (int q = 0; q < attQ.size(); q++){
	
				IL_Base ilb = new IL_Base();    
				ilb.sAttrname = attQ.get(q);     // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02f3\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5\ufffd\ufffd\ufffd\ufffd\u05a4\u04bb\ufffd\ufffd\ufffd\ufffd
				ilb.sClass = gh.gt.getGenColumn(ilb.sAttrname).attr_class;  // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02f3\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5\ufffd\ufffd\ufffd\ufffd\u05a4\u04bb\ufffd\ufffd\ufffd\ufffd
				ilb.iCount = fsc.FsCount;    // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5
				if(ilb.sClass.equals("Numerical")) {
	//							String sSQL = "select " + ilb.sAttrname + " from adult";
	//							long time1 = System.currentTimeMillis();
				       	List<String> ls = GetListFromColumn(dt.getColumn(ilb.sAttrname), fsc.list_ID);     // \u05e2\ufffd\ufffdlli.get(p)\ufffd\ufffd\u02b5\ufffd\ufffd\ufffd\u01f0\ufffd\ufffd\ufffdNum\ufffd\u0431\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
	//					       	long time2 = System.currentTimeMillis();
	//					       	System.out.println("get list from sql server use:"+String.valueOf(time2-time1)+"ms");
	//				            
	//					       	time1 = System.currentTimeMillis();
				       	Double max = Double.parseDouble(ls.get(0));     
			            Double min = Double.parseDouble(ls.get(0)); 
			            for (int r = 0; r < ls.size(); r++) {          
			                      if (min > Double.parseDouble(ls.get(r))) min = Double.parseDouble(ls.get(r));   
			                      if (max < Double.parseDouble(ls.get(r))) max = Double.parseDouble(ls.get(r));        
			            }
			            ilb.dMax = max;
			            ilb.dMin = min;
	//							ilb.dMin = 0;
	//							ilb.dMax = gh.gtd.getAttrRangeInGenLevel(ilb.sAttrname, node.getGenLevelByAttr(ilb.sAttrname));
			            ilb.dRange = gh.gt.getGenColumn(ilb.sAttrname).dRange;
	//				            time2 = System.currentTimeMillis();
	//					       	System.out.println("get gen column use:"+String.valueOf(time2-time1)+"ms");
					}
				else if(ilb.sClass.equals("Categorical")){
					ilb.dHSub = lGenGroup.get(q);
					ilb.dHAll = gh.gt.getGenColumn(ilb.sAttrname).maxGenLevel;
				}
				ilc.lil.add(ilb); 
			
			}
			 
			ilt.lcc.add(ilc);	
		}
		long ileTime = System.currentTimeMillis();
		System.out.println("get il table use time:"+String.valueOf(ileTime - ilsTime)+"ms");
		
		EvaluationMethod em = new EvaluationMethod();
		System.out.println("");
		System.out.println("IL = " + em.IL(ilt));
		
		return em.IL(ilt);
	
	}
	
	public List<String> GetListFromColumn(DataColumn dc, List<Integer> list_Num)
	{
		List<String> ls = new ArrayList<String>();
		
		for(int i = 0; i < list_Num.size(); i++)
		{
			int index = list_Num.get(i);
			String value = dc.getValue(index); 
			ls.add(value);
		}
		return ls;
	}
    
	public double getILbyGenGroup_MR(DataTable dt, List<Integer> lGenGroup, List<String> attQ) throws Exception {
		
		// (1) \ufffd\ufffd\u02bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
		DBHelper help=new DBHelper();
        MPPHelper mh = new MPPHelper();
        GenHelper gh = new GenHelper();  // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
        
        // (2) \ufffd\ufffd÷\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0771\ufffd\u05ea\ufffd\ufffd
		DataTable dt_gen =  gh.getGenDataTable(dt, lGenGroup);          // \ufffd\ufffd÷\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd
		dt_gen = mh.TransposeDT(dt_gen);           // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\ufffd
		
		long fsTime = System.currentTimeMillis();
		
		// (3) \ufffd\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
		FsTable fst = null;
		if(dt_gen.getColumnCount() >= public_data.MR_Condition_Size)
		{
			GetFrequencySet gs = new GetFrequencySet();
			Map<List<String>, String> frequencySet = gs.GetFrequencySet(dt_gen, public_data.otherArgs);
	//		Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // \ufffd\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
			
			// (4) \ufffd\ufffd\ufffd\ufffd(2)\ufffd\ufffd(3)\ufffd\u013d\u1e79\ufffd\ufffd\ufffd\ufffd\u01b5\ufffd\u02bc\ufffd\ufffd\ufffd
			fst = mh.getFsTable_MR(frequencySet);
		}
		else
		{
			Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // \ufffd\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
			
			// (4) \ufffd\ufffd\ufffd\ufffd(2)\ufffd\ufffd(3)\ufffd\u013d\u1e79\ufffd\ufffd\ufffd\ufffd\u01b5\ufffd\u02bc\ufffd\ufffd\ufffd
			fst = mh.getFsTable(dt_gen, frequencySet);
		}
		
		long feTime = System.currentTimeMillis();
		
		System.out.println("get frequency set use time:" + String.valueOf(feTime - fsTime)+"ms");
		
		long ilsTime = System.currentTimeMillis();
		
		// (5) \ufffd\ufffd\ufffd\ufffdIL_Table
		IL_Table ilt = new IL_Table();
		for(int w=1; w<=fst.lfs.size(); w++){
			// (5.1) \ufffd\ufffd\ufffdÿ\u04bb\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f\ufffd\ufffd\ufffd\u0435\ufffdColumn
			FsColumn fsc = fst.getFsColumn(w);   // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffdµ\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
			
			// (5.2) \ufffd\ufffd\ufffd\ufffdIL_Column\ufffd\ufffd\u03aa\ufffd\ufffdindex\ufffd\ufffd\ufffd\u0438\ufffd\u05b5
			IL_Column ilc = new IL_Column();
			ilc.index = w;
			
			// (5.3) \ufffd\u0535\ufffd\u01f0\u01b5\ufffd\ufffd\ufffd\uef2fColumn\ufffd\u0435\ufffdÿ\ufffd\ufffd\ufffd\ufffd\ufffd\u0536\ufffd\ufffd\ufffd\ufffd\ufffdIL_Base\ufffd\ufffdadd\ufffd\ufffdIL_Column
			for (int q = 0; q < attQ.size(); q++){

				IL_Base ilb = new IL_Base();    
				ilb.sAttrname = attQ.get(q);     // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02f3\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5\ufffd\ufffd\ufffd\ufffd\u05a4\u04bb\ufffd\ufffd\ufffd\ufffd
				ilb.sClass = gh.gt.getGenColumn(ilb.sAttrname).attr_class;  // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02f3\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5\ufffd\ufffd\ufffd\ufffd\u05a4\u04bb\ufffd\ufffd\ufffd\ufffd
				ilb.iCount = fsc.FsCount;    // \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0438\ufffd\u05b5
				if(ilb.sClass.equals("Numerical")) {
						String sSQL = "select " + ilb.sAttrname + " from adult";
				       	List<String> ls = help.GetListFromColumnbyNum(sSQL, fsc.list_ID);     // \u05e2\ufffd\ufffdlli.get(p)\ufffd\ufffd\u02b5\ufffd\ufffd\ufffd\u01f0\ufffd\ufffd\ufffdNum\ufffd\u0431\ufffd\ufffd\u01b5\ufffd\ufffd\ufffd\uef2f
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
		long ileTime = System.currentTimeMillis();
		System.out.println("get il table use time:"+String.valueOf(ileTime-ilsTime)+"ms");
		EvaluationMethod em = new EvaluationMethod();
		System.out.println("");
		System.out.println("IL = " + em.IL(ilt));
		
		return em.IL(ilt);

    }
}

