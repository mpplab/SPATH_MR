package OLA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CiColumn {
	
	int iD;    // ID��
	public List<Map<String,String>> columnList = new ArrayList<Map<String,String>>();     // ���ﵱʱ��ƵĲ��ã�Ӧ��ֱ��ʹ��Map<String,String>
	int flag = 0;   // 1��ʾmark
	int parent1;
	int parent2;
	
	//���캯��
	public CiColumn() {}
	
	//����iD
	public void setiD(int _id){
		iD = _id;
	}
	
	//����columnList
	public void setcolumnList(List<Map<String,String>> _columnList){
		columnList = _columnList;
	}
	
	//����columnList
	public void addMap2columnList(Map<String,String> map){
		columnList.add(map);
	}
	
	//����iD����flag,��ʵ����mark����
	public void setflag(int id){
		flag = 1;
	}
	
	public void setparent1(int _parent1){
		parent1 = _parent1;
	}
	
	public void setparent2(int _parent2){
		parent2 = _parent2;
	}
	
	//����iD
	public int getGenLevelByAttr(String Attr){
		for (int i = 0; i < this.columnList.size(); i++) {
			Map<String,String> ms = this.columnList.get(i);
			for (Map.Entry<String,String> me : ms.entrySet()) {
				if (me.getKey().equals(Attr)) {
					return Integer.valueOf(me.getValue());
				}
			}
		}
		return -1;        // ��ʾ����û���ҵ�
	}
	
}
