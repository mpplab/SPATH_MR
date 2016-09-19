package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class public_data {

//	public static final int k = 3;   // k锟斤拷锟斤拷
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","workclass","education","marital_status","race","sex","native_country"});
////	public static final List<String> attQ = Arrays.asList(new String[]{"workclass","education","marital_status","race","sex","native_country"});
////	public static final List<String> attQ_con = Arrays.asList(new String[]{"age","hours_per_week"});	
//	public static final List<String> attQ_con = Arrays.asList(new String[]{"age"});
		
	// 锟斤拷锟届函锟斤拷
	public public_data() {};
	
	
	// (1) Incognito_SQL: k = 3, 6+1  ----- 锟斤拷锟斤拷锟睫革拷k
//	public static final int k = 50;   // k锟斤拷锟斤拷
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","workclass","education","marital_status","race","sex","native_country"});
//	public static final List<String> attQ = Arrays.asList(new String[]{"workclass","education","marital_status","race","sex","native_country"});

	// (2) Incognito_SQL: k = 3, 6+2  ----- 锟斤拷锟斤拷锟睫革拷k
//	public static final int k = 50;   // k锟斤拷锟斤拷
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","hours_per_week","workclass","education","marital_status","race","sex","native_country"});

	
	
//	// (3) NQLG锟姐法: k = 10, 6+1
//	public static final int k = 50;   // k锟斤拷锟斤拷
//	public static final List<String> attQ = Arrays.asList(new String[]{"workclass","education","marital_status","race","sex","native_country"});
//	public static final List<String> attQ_con = Arrays.asList(new String[]{"age"});	
//	public static final int dim = 1;   // 锟斤拷示锟斤拷attQ_con锟斤拷匹锟斤拷锟轿拷锟� 


//	 // (4) NQLG锟姐法: k = 3, 6+2
//	public static final int k = 50;   // k锟斤拷锟斤拷
//	public static final List<String> attQ = Arrays.asList(new String[]{"workclass","education","marital_status","race","sex","native_country"});
//	public static final List<String> attQ_con = Arrays.asList(new String[]{"age","hours_per_week"});	
//	public static final int dim = 2;   // 锟斤拷示锟斤拷attQ_con锟斤拷匹锟斤拷锟轿拷锟� 
	
//	// (5) Paper2: k = 50, 6+1
	public static final int k = 100;   // k锟斤拷锟斤拷
	public static final List<String> attQ = Arrays.asList(new String[]{"age","class_of_worker","education","marital_status","race","sex","country_of_birth_self"});
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","class_of_worker","education"});
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","class_of_worker","education"});
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","workclass","education","marital_status","race","sex","native_country"});
	//"age":0,"workclass":1,"education":2,"marital_status":3,"race":4,"sex":5,"native_country":6
	public static final List<String> attQAll = Arrays.asList(new String[]{"age","class_of_worker","education","marital_status","race","sex","country_of_birth_self"});
//	public static final List<String> attQ = Arrays.asList(new String[]{"age","workclass","education"});
//	
	//public static List<List<String>> attrList = new ArrayList<List<String>>();
	
	public static final String otherArgs[] = new String[]{
			"hdfs://master:9000/input/inputMPP_M/Paper2Test",
			"hdfs://master:9000/output/outputMPP_M/Paper2Test"};
	
	public static final int MR_Condition_Size = 0;
//	otherArgs[0] = "hdfs://master:9000/input/inputMPP_M/Paper2Test";	//input file folder should be empty in the first
//	otherArgs[1] = "hdfs://master:9000/output/outputMPP_M/Paper2Test";//output file folder should not exist in the first
	public static final Integer totalNum = 95130;
	public static final double dSamPer = 0.01;
	public static final Integer AllCount = 95130;
}
