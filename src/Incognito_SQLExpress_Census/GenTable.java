package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.List;

public class GenTable {

	List<GenColumn> lgc = new ArrayList<GenColumn>();
	
	// ���캯��
	public GenTable(){
		
	}
	
	// ��ӹ���
	public void addGenColumn(GenColumn gc){
		lgc.add(gc);
	}
	
	// ��ù���
	public GenColumn getGenColumn(String _attr_name){
		for(GenColumn gc:lgc){
			if(gc.attr_name.equals(_attr_name)){
				return gc;
			}
		}
		return null;
	}
	
	// �Զ����ɷ�������
	public void AutoCreateGenItem(){
		for(int i=0; i<lgc.size(); i++){
			lgc.get(i).AutoCreategenLevelItem();
		}
	}
	
	
	// ������Լ���
	public List<String> GetAllAttr(){
		List<String> allAttr = new ArrayList<String>();
		for(int i=0; i<lgc.size(); i++){
			allAttr.add(lgc.get(i).attr_name);
		}
		return allAttr;
	}
	
}
