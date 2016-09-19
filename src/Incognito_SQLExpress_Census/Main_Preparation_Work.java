package Incognito_SQLExpress_Census;

import java.sql.SQLException;
import java.util.List;

public class Main_Preparation_Work {


    // ���캯��
	public Main_Preparation_Work(){};

	// ɾ�����ݱ��������ݱ�
	public void initTable() throws SQLException {
		
		// ���������ݽ��и�ֵ
		List<String> attQ = public_data.attQ;

		// ��ʼ��������
		MPPHelper mh = new MPPHelper();

		// (1) ɾ��Ei,C1,Ci
		mh.AutoDropC1Table();
		mh.AutoDropCiTable(attQ.size());
		mh.AutoDropEiTable(attQ.size());
		mh.AutoDropCandidateEdgesTable();
        
	    // (2) �Զ�����Ei,C1,Ci,���Զ����C1��E1�������
		mh.AutoCreateC1Table();
		mh.AutoCreateCiTable(attQ.size());
		mh.AutoCreateEiTable(attQ.size());
		mh.AutoFillC1E1Table(attQ);
		mh.AutoCreateCandidateEdgesTable();
	}
	
	
	
}
