package OLA;

import java.util.ArrayList;
import java.util.List;



public class DataColumn {

	
	String columnName = new String();
	List<String> columnList = new ArrayList<String>();

	
	// ���캯��
	public DataColumn() {}
	
	// ���캯��
	public DataColumn(String _columnName, List<String> _columnList) {
		columnName = _columnName;
		columnList = _columnList;
	}
	
	// ����ColumnName
	public void setColumnName(String sc){
		columnName = sc;
	}
	
	// ���ColumnName
	public String getColumnName(){
		return columnName;
	}
	
	// ���columnList
	public List<String> getColumnList(){
		return columnList;
	}
	
	
	// ����num�������ֵ��num��1��ʼ���
	public String getValue(int num){
		return columnList.get(num-1);	
	}
	
	
	public int size(){
		return columnList.size();	
	}
	
	// ��ĳ��string��ӵ�columnList������
	public void add2ColumnList(String sl){
		columnList.add(sl);
	}
	
	

    
	
	
}
