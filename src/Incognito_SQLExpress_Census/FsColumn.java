package Incognito_SQLExpress_Census;


import java.util.ArrayList;
import java.util.List;

public class FsColumn {

	List<String> Item = new ArrayList<String>();     // �ȼ������Լ���
	List<Integer> list_ID = new ArrayList<Integer>();     // �õȼ����Ӧ��ID�γɵ�List
	int FsCount = 0;          // �ȼ�����Ԫ�����
	DataTable dt = new DataTable();    // �ȼ���Ԫ�鹹�ɵ�table,dt��˳���list_IDһ��
	double[][] centers;    //���ĵ��ֵ
	List<List<Integer>> sub_list_ID = new ArrayList<List<Integer>>();  // ����centers�γɵ�list
	
	
	public FsColumn() {};
	
	
	
}
