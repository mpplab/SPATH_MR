package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Paper_AL_NOMR {

		
	public static void main(String[] args) {
		int k = public_data.k;
		List<String> attQ = public_data.attQ;

		String tableName = "census_income";
        Integer totalNum = 95130;
		double dSamPer = 0.01;
		
		long startTime = System.currentTimeMillis();
		
		
		DBHelper help=new DBHelper();
        MPPHelper mh = new MPPHelper();
        GenHelper gh = new GenHelper();
        
        System.out.println("The current access to the first step: the original data sampling.");
		List<Integer> lIndex = getSysSampling(totalNum, dSamPer);
		System.out.println("The number of the samples is:");
		mh.PrintListInteger(lIndex);
		
		DataTable dataTrans = new DataTable(); 
		DataTable dataTrans_Sample = new DataTable();
		
        int n_attQ = attQ.size();  
		
	       List<String> lsc = new ArrayList<String>();
	        for(int i=0;i<n_attQ;i++){
	        	String att_now = attQ.get(i).toString();
	        	String sSQL = "select " + att_now + " from " + tableName;
		       	lsc = help.GetSingleColumn(sSQL);
		       	dataTrans.addColumn(att_now, lsc);
	       }
	        DataTable dataTrans_DT = mh.TransposeDT(dataTrans); 
	        
	        
	        
	        for(int i = 0; i < lIndex.size(); i ++) {
	        	dataTrans_Sample.addColumn(dataTrans_DT.getColumn(lIndex.get(i)));
	        }
	     
	    System.out.println("The current access to the second step: get the optimal generalization path.");
		dataTrans_Sample = mh.TransposeDT(dataTrans_Sample);
		for (int i = 0; i < attQ.size(); i++){
			dataTrans_Sample.dataT.get(i).columnName = attQ.get(i);
		}
		// "age","hours_per_week","workclass","education","marital_status","race","sex","native_country"
		int genLevel_age = 0;                // 1
		int genLevel_class_of_worker = 0;          // 3
		int genLevel_education = 0;          // 4
		int genLevel_marital_status = 0;     // 5
		int genLevel_race = 0;               // 6
		int genLevel_sex = 0;                // 7
		int genLevel_country_of_birth_self = 0;     // 8
		int seed = 1;
		int totalMaxAttrLevel = gh.getMaxAttrLevelbyAttrName("age") + gh.getMaxAttrLevelbyAttrName("class_of_worker") + gh.getMaxAttrLevelbyAttrName("education") + gh.getMaxAttrLevelbyAttrName("marital_status") + gh.getMaxAttrLevelbyAttrName("race") + gh.getMaxAttrLevelbyAttrName("sex") + gh.getMaxAttrLevelbyAttrName("country_of_birth_self");
		List<List<Integer>> llBestPath = new ArrayList<List<Integer>>();
		
		while(seed <= totalMaxAttrLevel) {
			
			Map<List<Integer>, Double> mGenGroupPlusOne = new HashMap<List<Integer>, Double>();
			// 1 "age"
			if(genLevel_age < gh.getMaxAttrLevelbyAttrName("age")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age + 1, genLevel_class_of_worker, genLevel_education, genLevel_marital_status, genLevel_race, genLevel_sex, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 3 "workclass"
			if(genLevel_class_of_worker < gh.getMaxAttrLevelbyAttrName("class_of_worker")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker + 1, genLevel_education, genLevel_marital_status, genLevel_race, genLevel_sex, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 4 "education"
			if(genLevel_education < gh.getMaxAttrLevelbyAttrName("education")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker, genLevel_education + 1, genLevel_marital_status, genLevel_race, genLevel_sex, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 5 "marital_status"
			if(genLevel_marital_status < gh.getMaxAttrLevelbyAttrName("marital_status")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker, genLevel_education, genLevel_marital_status + 1, genLevel_race, genLevel_sex, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 6 "race"
			if(genLevel_race < gh.getMaxAttrLevelbyAttrName("race")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker, genLevel_education, genLevel_marital_status, genLevel_race + 1, genLevel_sex, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 7 "sex"
			if(genLevel_sex < gh.getMaxAttrLevelbyAttrName("sex")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker, genLevel_education, genLevel_marital_status, genLevel_race, genLevel_sex + 1, genLevel_country_of_birth_self});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			// 8 "native_country"
			if(genLevel_country_of_birth_self < gh.getMaxAttrLevelbyAttrName("country_of_birth_self")) {
				List<Integer> lGenGroup = Arrays.asList(new Integer[]{genLevel_age, genLevel_class_of_worker, genLevel_education, genLevel_marital_status, genLevel_race, genLevel_sex, genLevel_country_of_birth_self + 1});
				
				double dIL_GenGroup = mh.getILbyGenGroup_M(dataTrans_Sample, lGenGroup, attQ, dataTrans);
//				double dIL_GenGroup = mh.getILbyGenGroup(dataTrans_Sample, lGenGroup, attQ);
				
				mGenGroupPlusOne.put(lGenGroup, dIL_GenGroup);
			}
			
			double min = 10000000000000.00;
			List<Integer> li_min = new ArrayList<Integer>();
			for (Map.Entry<List<Integer>, Double> me : mGenGroupPlusOne.entrySet()) {
				if(me.getValue() < min) {
					min = me.getValue();
					li_min = me.getKey();
				}
			}
			genLevel_age = li_min.get(0);                // 1
			genLevel_class_of_worker = li_min.get(1);          // 3
			genLevel_education = li_min.get(2);          // 4
			genLevel_marital_status = li_min.get(3);     // 5
			genLevel_race = li_min.get(4);               // 6
			genLevel_sex = li_min.get(5);                // 7
			genLevel_country_of_birth_self = li_min.get(6);     // 8
			
			
			llBestPath.add(li_min);
			
			
			seed = seed + 1;
		}
		
		
//	    List<List<Integer>> llBestPath = new ArrayList<List<Integer>>();
//		List<Integer> lGenGroup = Arrays.asList(new Integer[]{0, 1, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{0, 2, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{0, 3, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{0, 4, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{1, 4, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{2, 4, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{3, 4, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 0, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 1, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 2, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 0, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 1, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 2, 0, 0, 0});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 2, 0, 0, 1});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 2, 0, 1, 1});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 2, 1, 1, 1});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 0, 3, 2, 1, 1, 2});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 1, 3, 2, 1, 1, 2});
//		llBestPath.add(lGenGroup);
//		lGenGroup = Arrays.asList(new Integer[]{4, 4, 2, 3, 2, 1, 1, 2});
//		llBestPath.add(lGenGroup);
		
		
		System.out.println("The optimal generalization path is: ");
		for(int j = 0; j < llBestPath.size(); j++) {
			mh.PrintListInteger(llBestPath.get(j));
		}
		
		long pathTime = System.currentTimeMillis();
		long phase2Time = 0;
		phase2Time+= pathTime - startTime;
		
		
		int index = 1;
		double totalIL = 0.0;
		
//		dataTrans = mh.TransposeDT(dataTrans); 
		// (3.1) -- (3.7)
		while(dataTrans.dataT.get(0).columnList.size() > k && index <= llBestPath.size()) {
			// ÌáÊ¾µ±Ç°ÎªµÚ¼¸ÂÖ 
			System.out.println("==================The current is progressing No." + index + " times calculation.==================");
			System.out.println("The count of rows is " + dataTrans.dataT.size());
			System.out.println("The coutn of columns is " + dataTrans.dataT.get(0).columnList.size());
			List<Integer> lGenGroup_Now = llBestPath.get(index - 1);
			
	        // (3.1) »ñµÃ·º»¯ºóµÄÊý¾Ý±í²¢×ªÖÃ
			// (3.1.1) ½«dataTrans½øÐÐ×ªÖÃ
//						dataTrans = mh.TransposeDT(dataTrans); 
//						System.out.println("OK");
			// (3.1.2) ¶Ô×ªÖÃºóµÄdataTransµÄcolumnName½øÐÐ¸³Öµ
//						for(int p = 0; p < attQ.size(); p++) {
//							dataTrans.dataT.get(p).columnName = attQ.get(p);
//						}
			// (3.1.3) »ñµÃ·º»¯
			DataTable dt_gen =  gh.getGenDataTable(dataTrans, lGenGroup_Now);          // »ñµÃ·º»¯Êý¾Ý
			// (3.1.4) ½«dt_gen½øÐÐ×ªÖÃ
			dt_gen = mh.TransposeDT(dt_gen);           // ½«·º»¯Êý¾Ý×ªÖÃ
			// (3.1.5) ½«dataTrans½øÐÐ×ªÖÃ
//						dataTrans = mh.TransposeDT(dataTrans); 

			
			// (3.2) »ñµÃÆµ·±Ïî¼¯
			System.out.println("Calculating frequent items sets...");
			Map<List<String>, Integer> frequencySet = mh.getFrequencySet(dt_gen);   // »ñµÃÆµ·±Ïî¼¯
			
			// (3.3)(3.4) getFrequencySetSatisfyK(Map<List<String>, Integer> frequencySet, int k)
			System.out.println("Calculating frequency sets...");
			Map<List<String>, Integer> frequencySetSatisfyK = mh.getFrequencySetSatisfyK(frequencySet, k);
			FsTable fst_Satisfy_k = mh.getFsTable(dt_gen, frequencySetSatisfyK);
			
//						// (3.3) ¸ù¾Ý(2)ºÍ(3)µÄ½á¹¹¹¹½¨ÆµÂÊ¼¯±í
//						System.out.println("ÕýÔÚ¼ÆËãÆµÂÊ¼¯±í¡£¡£¡£");
//						FsTable fst = mh.getFsTable(dt_gen, frequencySet);
//						
//						// (3.4) ¶ÔÆµ·±Ïî¼¯±íÒÀ¾ÝkÖµ×÷ÎªÃÅÏÞ½øÐÐ´¦Àí£¬¼´±£Áô´óÓÚkµÄÆµ·±Ïî¼¯
//						FsTable fst_Satisfy_k = mh.getFsTableSatisfyK(fst, k);
			
			// (3.5) ¼ÆËãfst_Satisfy_kµÄÐÅÏ¢ËðÊ§
			IL_Table ilt = new IL_Table();
			for(int w=1; w<=fst_Satisfy_k.lfs.size(); w++){
				// (3.5¡£1) »ñµÃÃ¿Ò»¸öÆµ·±Ïî¼¯±íÖÐµÄColumn
				FsColumn fsc = fst_Satisfy_k.getFsColumn(w);   // µ½·ÖÀàÊôÐÔÏÂµÄÆµ·±Ïî¼¯
				
				// (3.5.2) ¹¹½¨IL_Column²¢ÎªÆäindex½øÐÐ¸³Öµ
				IL_Column ilc = new IL_Column();
				ilc.index = w;
				
				// (3.5.3) ¶Ôµ±Ç°Æµ·±Ïî¼¯ColumnÖÐµÄÃ¿¸öÊôÐÔ¶¼¼ÆËãIL_Base²¢add½øIL_Column
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
//										ilb.dMin = 0;
//										ilb.dMax = gh.gtd.getAttrRangeInGenLevel(ilb.sAttrname, node.getGenLevelByAttr(ilb.sAttrname));
				            ilb.dRange = gh.gt.getGenColumn(ilb.sAttrname).dRange;
						}
					else if(ilb.sClass.equals("Categorical")){
						ilb.dHSub = lGenGroup_Now.get(q);
						ilb.dHAll = gh.gt.getGenColumn(ilb.sAttrname).maxGenLevel;
					}
					ilc.lil.add(ilb); 				
				}				 
				ilt.lcc.add(ilc);	
			}
			EvaluationMethod em = new EvaluationMethod();
			double IL_ilt = em.IL(ilt);
			totalIL = totalIL + IL_ilt;
			System.out.println("");
			System.out.println("Information Loss in this round: IL = " + IL_ilt);
			
			
			
			// (3.6) ÔÚdataTransÖÐÉ¾³ýfst_Satisfy_kÖÐµÄlist_ID£¬È»ºóÔÙ½«dataTrans×ªÖÃ»ØÈ¥²¢½«ÊôÐÔÖµ½øÐÐ¸³Öµ
			dataTrans = mh.delListIDInDataTable2(dataTrans, fst_Satisfy_k);

			
			
			// (3.7) index ++
			index = index + 1;
		}
		
		System.out.println("Total information loss: IL = " + totalIL);
		
		long endTime = System.currentTimeMillis();
		long totalTime = 0;
		totalTime+= endTime - startTime;
		long phase3Time = 0;
		phase3Time+= endTime - pathTime;
		System.out.println("Path: " + phase2Time + "ms");
		System.out.println("IL: " + phase3Time + "ms");
		System.out.println("Total time: " + totalTime + "ms");
		
		
		
	}
	
	public static List<Integer> getSysSampling(Integer totalNum, double dSamPer) {
		
	
		int iCount = (int) Math.floor(totalNum * dSamPer);
		int iInteval = (int) Math.floor(totalNum / iCount);
		
		List<Integer> result = new ArrayList<Integer>();
		int index = 1;
		while(index < totalNum) {
			result.add(index);
			index = index + iInteval;
		}
		return result;
		
	}
	
}
