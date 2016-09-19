package Incognito_SQLExpress_Census;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
	Connection _CONN = null;
	
	private boolean GetConn(String sUser, String sPwd) {
		if(_CONN!=null)return true;
		try {			
//			String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//			// String sDBUrl ="jdbc:sqlserver://192.168.0.74;databaseName=all_Adult";
//			// String sDBUrl = "jdbc:sqlserver://USER-20160313ZO;databaseName=adult";
//			String sDBUrl = "jdbc:sqlserver://192.168.100.120:1433;databaseName=adult";
//
//			Class.forName(sDriverName).newInstance();
//			_CONN = DriverManager.getConnection(sDBUrl, sUser, sPwd);
			
			String address = "jdbc:sqlserver://192.168.100.1:1433;";
			String user = "sa";
			String pwd = "asdf1234*";
			String database = "adult";
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			_CONN = DriverManager.getConnection(address, user, pwd);
			_CONN.setCatalog(database);

		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	private boolean GetConn()
	{
		return GetConn("sa","asdf1234*");
	}
	
	private void CloseConn()
	{
		try {
			_CONN.close();
			_CONN = null;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			_CONN=null;	
		}
	}
 
	public boolean TestConn() {
		if (!GetConn())
			return false;

		CloseConn();
		return true;
	}
	
	public ResultSet GetResultSet(String sSQL,Object[] objParams)
	{
		GetConn();
		ResultSet rs=null;
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			if(objParams!=null)
			{
				for(int i=0;i< objParams.length;i++)
				{
					ps.setObject(i+1, objParams[i]);
				}
			}
			rs=ps.executeQuery();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			//CloseConn();			
		}
		return rs;
	}
	
	public Object GetSingle(String sSQL,Object... objParams)
	{
		GetConn();
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			if(objParams!=null)
			{
				for(int i=0;i< objParams.length;i++)
				{
					ps.setObject(i+1, objParams[i]);
				}
			}
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				return rs.getString(1);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			CloseConn();			
		}
		return null;
	}
	
	public List<String> GetSingleColumn(String sSQL)
	{
		GetConn();
		List<String> ls = new ArrayList<String>();
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ls.add(rs.getString(1).trim());
			}
			return ls;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			CloseConn();			
		}
		return null;
	}	
	
	public List<String> GetListFromColumnbyNum(String sSQL, List<Integer> list_Num)
	{
		GetConn();
		List<String> ls = new ArrayList<String>();
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			ResultSet rs=ps.executeQuery();
			int index = 1;
			while(rs.next()){
				if(list_Num.contains(index)){
					ls.add(rs.getString(1).trim());
				}
				index ++;
			}
			return ls;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			CloseConn();			
		}
		return null;
	}
	
	public int UpdateData(String sSQL,Object[] objParams)
	{
		GetConn();
		int iResult=0;
		
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			if(objParams!=null)
			{
				for(int i=0;i< objParams.length;i++)
				{
					ps.setObject(i+1, objParams[i]);
				}
			}
			iResult = ps.executeUpdate(sSQL);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return -1;
		}
		finally
		{
			CloseConn();			
		}
		return iResult;
	}
	
	public int CreateDataBase(String sSQL)
	{
		GetConn();
		int iResult=0;
		
		try
		{
			Statement ps = _CONN.createStatement();
			iResult = ps.executeUpdate(sSQL);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return -1;
		}
		finally
		{
			CloseConn();			
		}
		return iResult;
	}
		

	public int CreateDataTable(String sSQL)
	{
		GetConn();
		int iResult=0;
		
		try
		{
			Statement ps = _CONN.createStatement();
			iResult = ps.executeUpdate(sSQL);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return -1;
		}
		finally
		{
			CloseConn();			
		}
		return iResult;
	}
		
	
	 //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�,每锟斤拷锟斤拷锟揭伙拷锟�
    public int insertDatabySQL(String sSQL,Object[] objParams) throws SQLException{
         
    	GetConn();
		int iResult=0;
		
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			if(objParams!=null)
			{
				for(int i=0;i< objParams.length;i++)
				{
					ps.setObject(i+1, objParams[i]);
				}
			}
			iResult = ps.executeUpdate();

		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return -1;
		}
		finally
		{
			CloseConn();			
		}
		return iResult;

    }
    
    
    public void insert2SQLFromDataTable(String tableName,DataTable dt) throws SQLException{	
    	GetConn();
    	Statement stmt = _CONN.createStatement();
    	
    	//锟斤拷锟斤拷锟斤拷
        try{
        	int row = dt.getColumnCount();
        	int col = dt.getColumn(1).size();
        	
        	for(int i=1;i<=row;i++){
            	String sSQL="insert into " + tableName + " values('" + dt.getColumn(i).columnList.get(0) + "'";
            	
        		for(int j=2;j<=col;j++){       			
        			sSQL = sSQL + ",'" + dt.getColumn(i).columnList.get(j-1) + "'";
        		}
            	
            	sSQL = sSQL + ")";
            	stmt.addBatch(sSQL);
        	}
        	stmt.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally
		{
			CloseConn();		
		}
    }
    
    public int[] test() throws SQLException{
    	
    	GetConn();
    	Statement stmt_c1 = _CONN.createStatement();
    	String sSQL = "set identity_insert C1 on";
    	stmt_c1.addBatch(sSQL);
    	sSQL="insert into C1(ID,dim1,index1) values(18,'abc',6)";
    	stmt_c1.addBatch(sSQL);
    	int[] result = stmt_c1.executeBatch();
    	
    	
   
    	
    	
    	return result;
    }
    
    
    @SuppressWarnings("finally")
	public int insert2C1E1(GenAttrLevel gal,int iD) throws SQLException{	
    	GetConn();
    	Statement stmt_c1 = _CONN.createStatement();
    	Statement stmt_e1 = _CONN.createStatement();
    	
    	String sSQL_Ide = "set identity_insert C1 on";
    	stmt_c1.addBatch(sSQL_Ide);
    	
//		System.out.println("abc");
			
    	//锟斤拷锟斤拷锟斤拷
        try{
        	int row = gal.level_Item.size();
        	for(int i=0;i<row;i++){
        		
        		String sSQL_C1="insert into C1(ID,dim1,index1) values(" + iD + ",'" + gal.attr + "'," + gal.level_Item.get(i) + ")";
            	stmt_c1.addBatch(sSQL_C1);
            	if(i<row-1){
                	int E1Start = iD;
                	int E1End = iD +1;
                	String sSQL_E1="insert into E1 values(" + E1Start + "," + E1End + ")";
                	stmt_e1.addBatch(sSQL_E1);
            	}
            	iD++;
        	}
        	stmt_c1.executeBatch();
        	stmt_e1.executeBatch();
        	return iD;
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
            return iD;
        }finally{
			CloseConn();	
			return iD;
		}
    }
    
    
    
    public void insert2Ci(CiTable ciT, int i) throws SQLException{
    	GetConn();
    	Statement stmt_ci = _CONN.createStatement();
        try{
        	String sSQL_Ci_Trun = "truncate table C" + i;
        	stmt_ci.addBatch(sSQL_Ci_Trun);
        	String sSQL_Ide = "set identity_insert C" + i +" on";
        	stmt_ci.addBatch(sSQL_Ide);
        	for(CiColumn cic:ciT.lCi){
        		String sSQL_1="insert into C" + i + "("; 
        		
        		String sSQL_2 = "ID";
        		for(int k=1;k<=i;k++){
        			sSQL_2 = sSQL_2 + ",dim" + k + ",index" + k;
        		}
        		String sSQL_3 = " values(" + cic.iD;
        		for(Map<String,String> ms:cic.columnList){
        			for (Map.Entry<String,String> me : ms.entrySet()){
        				sSQL_3 = sSQL_3 + ",'" + me.getKey() + "'," + Integer.parseInt(me.getValue());
        			}

        		}

        		if(i!=1){
        			sSQL_2 = sSQL_2 + ",parent1,parent2";
        			sSQL_3 = sSQL_3 + "," + cic.parent1 + "," + cic.parent2;
        		}
        		String sSQL_Ci = sSQL_1 + sSQL_2 + ")" + sSQL_3 + ")";
            	// System.out.println(sSQL_Ci);     // 锟斤拷锟斤拷锟斤拷锟阶拷偷锟�
        		stmt_ci.addBatch(sSQL_Ci);
        	}
    		stmt_ci.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally{
			CloseConn();	
		}
    }
    
    // 注锟斤拷i一锟斤拷锟角达拷锟斤拷2锟斤拷
    public void insert2NextCi(int i) throws SQLException{
    	GetConn();
    	Statement stmt_ci = _CONN.createStatement();
    	
    	String sSQL_1 = "C" + i + " (dim1,index1";
    	for(int k=2;k<=i;k++){
    		sSQL_1 = sSQL_1 + ",dim" + k + ",index" + k;
    	}
    	sSQL_1 = sSQL_1 + ",parent1,parent2)";
    	
    	int j = i-1;
    	
    	String sSQL_2 = "p.dim1,p.index1";
    	for(int k=2;k<=i-1;k++){
    		sSQL_2 = sSQL_2 + ",p.dim" + k +",p.index" + k;
    	}
    	sSQL_2 = sSQL_2 + ",q.dim" + j + ",q.index" + j;
    	sSQL_2 = sSQL_2 + ",p.ID,q.ID";
    	
    	
    	String sSQL_3 = "C" + j + " p,C" + j + " q";
    	
    	String sSQL_4 = "";
    	if(i>2){
    		sSQL_4 = "p.dim1 = q.dim1 and p.index1 = q.index1 and";
    	}
    	for(int k=2;k<=i-2;k++){
    		sSQL_4 = sSQL_4 + " p.dim" + k + " = q.dim" + k + " and p.index" + k + " = q.index" + k + " and";
    	}
    	
    	sSQL_4 = sSQL_4 + " p.dim" + j + " < q.dim" + j;
    	
    	String sSQL_Ci = "insert into " + sSQL_1 + " SELECT " + sSQL_2 + " FROM " + sSQL_3 + " WHERE " + sSQL_4;
//    	System.out.println(sSQL_Ci);     // 锟斤拷锟斤拷锟斤拷锟阶拷偷锟�
    	
        try{
        	 stmt_ci.addBatch(sSQL_Ci);
        	 stmt_ci.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally{
			CloseConn();	
		}
    	
    }
    
    
    public void insert2NextEi(int i) throws SQLException{
    	GetConn();
    	Statement stmt_ce = _CONN.createStatement();
    	Statement stmt_ce1 = _CONN.createStatement();
    	Statement stmt_ce2= _CONN.createStatement();
    	Statement stmt_ce3= _CONN.createStatement();// CandidateEdges锟斤拷(锟叫硷拷锟�)
    	Statement stmt_ei = _CONN.createStatement();    // Ei锟斤拷(锟斤拷锟秸憋拷)
    	
    	// (1) 锟斤拷时锟斤拷一锟斤拷锟斤拷娲拷锟捷ｏ拷锟剿憋拷锟斤拷锟斤拷馗锟绞癸拷锟�(使锟斤拷前锟斤拷锟斤拷锟�)
    	String sSQL_CE_Trun = "truncate table CandidateEdges";
    	String sSQL_CE_Trun1 = "truncate table CandidateEdges1";
    	String sSQL_CE_Trun2 = "truncate table CandidateEdges2";
    	String sSQL_CE_Trun3 = "truncate table CandidateEdges3";
    	stmt_ce.addBatch(sSQL_CE_Trun);
    	stmt_ce.addBatch(sSQL_CE_Trun1);
    	stmt_ce.addBatch(sSQL_CE_Trun2); 
    	stmt_ce.addBatch(sSQL_CE_Trun3); 
    	stmt_ce.executeBatch(); 
    	//锟斤拷锟斤拷一锟斤拷使锟斤拷or锟斤拷锟斤拷
//    	String sSQL_1 = "CandidateEdges (Eistart,EiEnd)";
//    	String sSQL_2 = "p.ID as PID, q.ID as QID";
//    	int j = i-1;
//    	String sSQL_3 = "C" + i + " p, C" + i + " q, E" + j + " e,E" + j + " f";
//    	String sSQL_4 = "(e.EiStart = p.parent1 and e.EiEnd = q.parent1 and f.EiStart = p.parent2 and f.EiEnd = q.parent2) or (e.EiStart = p.parent1 and e.EiEnd = q.parent1 and p.parent2 = q.parent2) or (e.EiStart = p.parent2 and e.EiEnd = q.parent2 and p.parent1 = q.parent1)";
//    	String sSQL_ce = "insert into " + sSQL_1 + " SELECT " + sSQL_2 + " FROM " + sSQL_3 + " WHERE " + sSQL_4;
//    	stmt_ce.addBatch(sSQL_ce);
    	//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷使锟斤拷or锟斤拷锟斤拷
    	int j = i-1;
    	String sSQL_11="CandidateEdges1(Eistart,EiEnd) ";
    	String sSQL_12 = "p.ID as PID, q.ID as QID";
    	String sSQL_13 = "C" + i + " p, C" + i + " q, E" + j + " e,E" + j + " f";
    	String sSQL_14 = "(e.EiStart = p.parent1 and e.EiEnd = q.parent1 and f.EiStart = p.parent2 and f.EiEnd = q.parent2)";
    	String sSQL_ce1 = "insert into " + sSQL_11 + " SELECT " + sSQL_12 + " FROM " + sSQL_13 + " WHERE " + sSQL_14;
    	stmt_ce1.addBatch(sSQL_ce1);
    	stmt_ce1.executeBatch(); 
    	String sSQL_21="CandidateEdges2(Eistart,EiEnd)";
    	String sSQL_22 = "p.ID as PID, q.ID as QID ";
    	String sSQL_23 = "C" + i + " p, C" + i + " q, E" + j + " e,E" + j + " f";
    	String sSQL_24 = "(e.EiStart = p.parent1 and e.EiEnd = q.parent1 and p.parent2 = q.parent2)";
    	String sSQL_ce2 = "insert into " + sSQL_21 + " SELECT " + sSQL_22 + " FROM " + sSQL_23 + " WHERE " + sSQL_24;
    	stmt_ce2.addBatch(sSQL_ce2);
    	stmt_ce2.executeBatch(); 
    	String sSQL_31="CandidateEdges3(Eistart,EiEnd) ";
    	String sSQL_32 = "p.ID as PID, q.ID as QID";
    	String sSQL_33 = "C" + i + " p, C" + i + " q, E" + j + " e,E" + j + " f";
    	String sSQL_34 = "(e.EiStart = p.parent2 and e.EiEnd = q.parent2 and p.parent1 = q.parent1)";
    	String sSQL_ce3 = "insert into " + sSQL_31 + " SELECT " + sSQL_32 + " FROM " + sSQL_33 + " WHERE " + sSQL_34;
    	stmt_ce3.addBatch(sSQL_ce3);
    	String sSQL_1="insert into CandidateEdges select * from CandidateEdges1";
    	stmt_ce3.addBatch(sSQL_1);
    	String sSQL_2="insert into CandidateEdges select * from CandidateEdges2";
    	stmt_ce3.addBatch(sSQL_2);
    	String sSQL_3="insert into CandidateEdges select * from CandidateEdges3";
    	stmt_ce3.addBatch(sSQL_3);
    	stmt_ce3.executeBatch(); 
//    	System.out.println(sSQL_ce);     // 锟斤拷锟斤拷锟斤拷锟阶拷偷锟�
    	
    	// (2) 锟斤拷锟紼i
    	String sSQL_ei = "insert into E" + i + " (Eistart,EiEnd) SELECT D.EiStart, D.EiEnd FROM CandidateEdges D EXCEPT SELECT D1.EiStart, D2.EiEnd FROM CandidateEdges D1, CandidateEdges D2 WHERE D1.EiEnd = D2.EiStart";
//    	System.out.println(sSQL_ei);     // 锟斤拷锟斤拷锟斤拷锟阶拷偷锟�
    	stmt_ei.addBatch(sSQL_ei);
    	try{
    		 
        	 stmt_ei.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally{
			CloseConn();	
		}
    }

    
    // 锟斤拷锟紺i锟轿筹拷Ei锟斤拷锟斤拷锟矫伙拷锟斤拷锟斤拷锟絊QL锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷洌拷锟斤拷源锟斤拷锟斤拷锟斤拷时锟斤拷
    public void insert2NextEi2(int i) throws SQLException{
    	// (1) 锟斤拷取Ci
    	String Ci = "C" + Integer.toString(i);
    	CiTable ciT = this.readSql2Ci(Ci);
    	
    	// (2) 锟斤拷始锟斤拷Ei
    	EiTable eiT = new EiTable();
    	
    	// (3) 通锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷EiStart锟斤拷EiEnd
    	for(int p = 0; p < ciT.lCi.size(); p++) {
    		// (3.1) 锟斤拷锟絚olumnList锟斤拷锟斤拷锟节对憋拷
    		List<Map<String,String>> p_columnList = ciT.lCi.get(p).columnList;   // 锟斤拷锟絚olumnList锟斤拷锟斤拷锟节对憋拷
    		
    		for(int q = 0; q < ciT.lCi.size(); q++) {
    			// (3.2) 锟斤拷锟絚olumnList锟斤拷锟斤拷锟节对憋拷
    			List<Map<String,String>> q_columnList = ciT.lCi.get(q).columnList;   // 锟斤拷锟絚olumnList锟斤拷锟斤拷锟节对憋拷
    			
    			// (3.3) 锟斤拷始锟斤拷p_totalGenLevel锟斤拷q_totalGenLevel
    			int p_totalGenLevel = 0;
    			int q_totalGenLevel = 0;
    			
    			// (3.4) 锟斤拷锟斤拷每锟斤拷List锟叫碉拷Map锟斤拷要锟斤拷锟斤拷锟姐：1.每锟斤拷锟斤拷锟斤拷锟斤拷<=锟斤拷2.锟杰猴拷锟斤拷锟�1
    			// (3.4.1) 锟斤拷锟矫憋拷志锟斤拷 
    			int flag = 1;
    			// (3.4.2) 循锟斤拷每锟斤拷List锟叫碉拷Map值
    			for(int z = 0; z < p_columnList.size(); z++) {
    				// (3.4.3) 锟斤拷锟絧锟斤拷每锟斤拷value
    				Map<String,String> pMap = p_columnList.get(z);
    				String pKey = "";
    				int pValue = 0;
    				for (Map.Entry<String,String> pME : pMap.entrySet()) { 
    					pKey = pME.getKey();
    					pValue = Integer.parseInt(pME.getValue());
    				} 
    				// (3.4.4) 锟斤拷锟絨锟斤拷每锟斤拷value
    				Map<String,String> qMap = q_columnList.get(z);
    				String qKey = "";
    				int qValue = 0;
    				for (Map.Entry<String,String> qME : qMap.entrySet()) { 
    					qKey = qME.getKey();
    					qValue = Integer.parseInt(qME.getValue());
    				}
    				
    				if(pKey.equals(qKey) && pValue <= qValue) {
    					p_totalGenLevel = p_totalGenLevel + pValue;
    					q_totalGenLevel = q_totalGenLevel + qValue;
    				}else{
    					flag = 0;
    					break;
    				}
    			}
    			// (3.4.3) 锟斤拷锟斤拷锟斤拷锟揭伙拷榉拷锟絝lag锟斤拷锟斤拷1锟斤拷锟斤拷锟斤拷锟杰猴拷锟斤拷锟�1锟斤拷锟斤拷么锟斤拷锟斤拷要锟揭的癸拷系
    			if(flag == 1 && q_totalGenLevel-p_totalGenLevel==1) {
    				EiColumn eiC = new EiColumn();
    				eiC.EiStart = ciT.lCi.get(p).iD;
    				eiC.EiEnd = ciT.lCi.get(q).iD;
    				eiT.lEi.add(eiC);
    			}
    		}
    	}
    	
    	// (4) 锟斤拷eiT写锟斤拷锟斤拷菘锟�
     	GetConn();
    	Statement stmt = _CONN.createStatement();
        try{
        	for(int f = 0; f < eiT.lEi.size(); f++) {
            	String sSQL="insert into E" + i + " values('" + eiT.lEi.get(f).EiStart + "','" + eiT.lEi.get(f).EiEnd + "')";
            	stmt.addBatch(sSQL);
        	}
        	stmt.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally
		{
			CloseConn();		
		}
    	
    	
    }
    
    
    
    
//    public void insert2CandidateEdges(int i) throws SQLException{
//    	GetConn();
//    	Statement stmt_ce = _CONN.createStatement();
//    	
//    	String sSQL_CE_Trun = "truncate table CandidateEdges";
//    	stmt_ce.addBatch(sSQL_CE_Trun);
//    	
//    	String sSQL_1 = "into CandidateEdges (Eistart,EiEnd)";
//    	
//    	String sSQL_2 = "p.ID as PID, q.ID as QID";
//    	
//    	int j = i-1;
//    	String sSQL_3 = "C" + i + " p, C" + i + " q, E" + j + " e,E" + j + " f";
//    	
//    	String sSQL_4 = "(e.EiStart = p.parent1 and e.EiEnd = q.parent1 and f.EiStart = p.parent2 and f.EiEnd = q.parent2) or (e.EiStart = p.parent1 and e.EiEnd = q.parent1 and p.parent2 = q.parent2) or (e.EiStart = p.parent2 and e.EiEnd = q.parent2 and p.parent1 = q.parent1)";
//    	
//    	String sSQL_ce = "insert into " + sSQL_1 + " SELECT " + sSQL_2 + " FROM " + sSQL_3 + " WHERE " + sSQL_4;
//    	
//    	System.out.println(sSQL_ce);     // 锟斤拷锟斤拷锟斤拷锟阶拷偷锟�
//    	
//        try{
//        	// stmt_ce.addBatch(sSQL_ce);
//        	// stmt_ce.executeBatch();
//        }catch(Exception ex) 
//        { 
//            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
//        }finally{
//			CloseConn();	
//		}
//    	
//    }
    
    
    
    public void insert2E1(GenAttrLevel gal,GenTable gt) throws SQLException{	
    	GetConn();
    	Statement stmt = _CONN.createStatement();
    	
    	//锟斤拷锟斤拷锟斤拷
        try{
        	int row = gal.level_Item.size();
        	
        	for(int i=0;i<row-1;i++){
            	String sSQL="insert into E1 values(" + gal.level_Item.get(i) + "," + gal.level_Item.get(i+1) + ")";
            	stmt.addBatch(sSQL);
        	}
        	stmt.executeBatch();
        }catch(Exception ex) 
        { 
            System.out.println("锟斤拷锟斤拷锟斤拷示锟斤拷" + ex.getMessage()); 
        }finally
		{
			CloseConn();		
		}
    }
    
    // 锟斤拷取锟斤拷菘锟斤拷锟斤拷锟斤拷LLM锟斤拷
    // attl锟斤拷示锟斤拷锟斤拷list
    // List<String> attl = Arrays.asList(new String[]{"age","workclass","fnlwgt","education","education-num","marital-status","occupation","relationship","race","sex","capital-gain","capital-loss","hours-per-week","native-country","xinshui"});
	public List<Map<String,String>> readTrans2LLM(String sSQL,List<String> attl,Object[] objParams) {
		
		GetConn();
		ResultSet rs=null;
		List<Map<String,String>> lmap = new ArrayList<Map<String,String>>();
		
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			if(objParams!=null)
			{
				for(int i=0;i< objParams.length;i++)
				{
					ps.setObject(i+1, objParams[i]);
				}
			}
			rs=ps.executeQuery();
			
			
			while(rs.next()){ 
				 Map<String, String> map = new HashMap<String, String>();
	             for(int i=0;i<attl.size();i++){ 
	            	 map.put(attl.get(i).toString(), rs.getString(attl.get(i).toString()));  
	            	 
	             }
	             lmap.add(map);
	         }
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			CloseConn();			
		}
		return lmap;
	}
    
	// 锟斤拷取锟斤拷菘锟斤拷锟斤拷锟斤拷LLM锟斤拷
    // attl锟斤拷示锟斤拷锟斤拷list
    // List<String> attl = Arrays.asList(new String[]{"age","workclass","fnlwgt","education","education-num","marital-status","occupation","relationship","race","sex","capital-gain","capital-loss","hours-per-week","native-country","xinshui"});
	public void writeLLM2SqlServer(List<Map<String,String>> lmap,List<String> attl,String sTableName) {
		String flag = "?";
		for(int i=1;i<attl.size();i++)
		{
			flag = flag + ",?";
		}
		String sSQL="insert into " + sTableName + "values(" + flag + ");";
		GetConn();
		try{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			for (Map<String,String> map : lmap) { // 锟斤拷锟斤拷datatrans(一锟斤拷锟斤拷锟斤拷)
		              for(int j=0;j<attl.size();j++){ 
		            	 ps.setObject(j+1, map.get(attl.get(j).toString()));
		             }
				ps.addBatch();
			}
			ps.executeBatch();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			CloseConn();			
		}
		
		
	}
	
	// 禄帽脠隆赂霉陆脷碌茫
	public CiTable readRootNode2Ci(String sTable, String eTable){
		GetConn();
		ResultSet rs=null;
		CiTable ciT = new CiTable();
		String sSQL = "Select * From " + sTable + " Where ID not in (Select distinct EiEnd From " + eTable + " where EiStart in (Select ID From " + sTable + "))";
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			rs=ps.executeQuery();
			while(rs.next()){ 
				CiColumn ciC = new CiColumn();
				ciC.setiD(Integer.parseInt(rs.getString(1))); //掳脩碌脷脪禄赂枚脡猫脰脙碌陆ID脰脨
				ResultSetMetaData rsmd = rs.getMetaData();    
				int columnCount = rsmd.getColumnCount();   //禄帽碌脙脳脺脕脨脢媒 	
				if(columnCount < 5){	    //脠莽鹿没脢脟C1			
					Map<String,String> map = new HashMap<String,String>();
					map.put(rs.getString(2), rs.getString(3));
					ciC.addMap2columnList(map);
				}else{                      //脠莽鹿没脢脟C2拢卢C3拢卢隆拢隆拢隆拢
					for(int i=2;i<=columnCount-2;i++){
						if(i%2 == 0){
							Map<String,String> map = new HashMap<String,String>();
							map.put(rs.getString(i), rs.getString(i+1));
							ciC.addMap2columnList(map);
							
						}
					}
					ciC.setparent1(Integer.parseInt(rs.getString(columnCount-1)));
					ciC.setparent2(Integer.parseInt(rs.getString(columnCount)));
				}

				ciT.addCiColumn(ciC);
	        
	         }
			return ciT;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			CloseConn();			
		}
		return ciT;
	}
	
	// 
    // sTable锟斤拷示要锟斤拷取锟侥憋拷锟斤拷锟斤拷
    // List<String> attl = Arrays.asList(new String[]{"age","workclass","fnlwgt","education","education-num","marital-status","occupation","relationship","race","sex","capital-gain","capital-loss","hours-per-week","native-country","xinshui"});
	public CiTable readSql2Ci(String sTable) {
		GetConn();
		ResultSet rs=null;
		CiTable ciT = new CiTable();
		String sSQL = "Select * from " + sTable;
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			rs=ps.executeQuery();
			while(rs.next()){ 
				CiColumn ciC = new CiColumn();
				ciC.setiD(Integer.parseInt(rs.getString(1))); //锟窖碉拷一锟斤拷锟斤拷锟矫碉拷ID锟斤拷
				ResultSetMetaData rsmd = rs.getMetaData();    
				int columnCount = rsmd.getColumnCount();   //锟斤拷锟斤拷锟斤拷锟斤拷锟� 	
				if(columnCount < 5){	    //锟斤拷锟斤拷锟紺1			
					Map<String,String> map = new HashMap<String,String>();
					map.put(rs.getString(2), rs.getString(3));
					ciC.addMap2columnList(map);
				}else{                      //锟斤拷锟斤拷锟紺2锟斤拷C3锟斤拷锟斤拷锟斤拷锟斤拷
					for(int i=2;i<=columnCount-2;i++){
						if(i%2 == 0){
							Map<String,String> map = new HashMap<String,String>();
							map.put(rs.getString(i), rs.getString(i+1));
							ciC.addMap2columnList(map);
							
						}
					}
					ciC.setparent1(Integer.parseInt(rs.getString(columnCount-1)));
					ciC.setparent2(Integer.parseInt(rs.getString(columnCount)));
				}

				ciT.addCiColumn(ciC);
	        
	         }
			return ciT;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			CloseConn();			
		}
		return ciT;
	}
	
	// 
    // sTable锟斤拷示要锟斤拷取锟侥憋拷锟斤拷锟斤拷
    // List<String> attl = Arrays.asList(new String[]{"age","workclass","fnlwgt","education","education-num","marital-status","occupation","relationship","race","sex","capital-gain","capital-loss","hours-per-week","native-country","xinshui"});
	public EiTable readSql2Ei(String sTable) {
		GetConn();
		ResultSet rs=null;
		EiTable eiT = new EiTable();
		String sSQL = "Select * from " + sTable;
		try
		{
			PreparedStatement ps = _CONN.prepareStatement(sSQL);
			rs=ps.executeQuery();
			while(rs.next()){ 
				EiColumn eiC = new EiColumn();
				eiC.setEiStart(Integer.parseInt(rs.getString(1)));
				eiC.setEiEnd(Integer.parseInt(rs.getString(2)));
				eiT.addEiColumn(eiC);
				}
			return eiT;	
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			CloseConn();
		}
		finally
		{
			CloseConn();			
		}
		return eiT;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

