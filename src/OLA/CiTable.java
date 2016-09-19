package OLA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CiTable {

	public List<CiColumn> lCi = new ArrayList<CiColumn>();
	
	public CiTable() {}
	
	// ���ӽڵ�/��
	public void addCiColumn(CiColumn cic){
		lCi.add(cic);
	}
	
	// ����num���CiColumn
	public CiColumn getCiColumn(int num){
		int i = 0;
		for(CiColumn ciC: lCi){
			i ++;
			if(i == num){
				return ciC;
			}
		}
		return null;
	}
	
	// �������
	public int getCiColumncount() {
		return lCi.size();
	}
	

	// ɾ��ĳ��CiColumn,��1��ʼ
	public void removeColumn(int num) {
		lCi.remove(num-1);
	}
	
	// ͨ������IDɾ��ĳ��CiColumn
	public void removeColumnByAttrName(int id) {
		List<CiColumn> result = new ArrayList<CiColumn>();
		for(CiColumn ciC:lCi){
			if(ciC.iD == id){
				System.out.print("ɾ��ID" + id + " ");
			}else{
				result.add(ciC);
			}
		}
		lCi = result;
	}
	
	// ͨ��CiColumn�����ID����ֵMark�ڵ�
	public void markColumn(int iD) {
		for(int i=0; i<lCi.size(); i++){
			if(iD == lCi.get(i).iD){
				lCi.get(i).flag = 1;
				System.out.print("MarkID" + iD + " ");
			}
		}
	}
	
	public CiColumn getCiColumnByID(int iD){
		for(CiColumn cic:lCi){
			if(cic.iD == iD){
				return cic;
			}
		}
		return null;
	}
	
	
	
	/**
	 * ���бȽ�
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
	    if(a.size() != b.size())
	        return false;
	    Collections.sort(a);
	    Collections.sort(b);
	    for(int i=0;i<a.size();i++){
	        if(!a.get(i).equals(b.get(i)))
	            return false;
	    }
	    return true;
	}
	
	// contain����
	public boolean isContain(CiColumn ciC_for_compare){   
		for(CiColumn ciC:lCi){
			if(ciC.columnList.equals(ciC_for_compare.columnList)){
				return true;
			}	
		}
		return false;
	}
	
}