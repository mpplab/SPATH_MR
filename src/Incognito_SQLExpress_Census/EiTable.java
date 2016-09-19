package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.List;

public class EiTable {

List<EiColumn> lEi = new ArrayList<EiColumn>();
	
	public EiTable() {};
	
	
	// ��ӽڵ�/��
	public void addEiColumn(EiColumn eic) {
		lEi.add(eic);
	}
	
	// ����num���CiColumn
	public EiColumn getEiColumn(int num) {
		int i = 0;
		for(EiColumn eiC: lEi){
			i ++;
			if(i == num){
				return eiC;
			}
		}
		return null;
	}
	
	// �������
	public int getEiColumncount(){
		return lEi.size();
	}
	
	// ���ֱ�ӷ����ĵ�
	public List<Integer> getDirectGenNode(List<Integer> startList){
		List<Integer> result = new ArrayList<Integer>();
//		try{
			for(int i=0; i<startList.size(); i++){
				int wantFind = startList.get(i);
				for(int j=0; j<lEi.size(); j++){
					if(wantFind == lEi.get(j).EiStart){
						result.add(lEi.get(j).EiEnd);
					}
				}
			}
//		}catch (Exception ex) {
//			System.out.println(ex.getMessage());
//		}

		return result;
	}
	
	// ������е��������ĵ�
	public List<Integer> getAllDirectGenNode(int ciID){
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> startList = new ArrayList<Integer>();
		startList.add(ciID);
		while(this.getDirectGenNode(startList).size()!=0){
			List<Integer> directGenNode = this.getDirectGenNode(startList);
			for(int i=0; i<directGenNode.size(); i++){
				result.add(directGenNode.get(i));
			}
			startList = directGenNode;
		}
		return result;
	}
	
}
