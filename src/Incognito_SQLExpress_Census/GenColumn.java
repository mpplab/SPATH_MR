package Incognito_SQLExpress_Census;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenColumn {
	
	String attr_name;    // ������
	String attr_class;   // �������ͣ����Ϊ"Categorical"��ʾ���������ԣ����Ϊ"Numerical"��ʾ��ֵ������
	List<GenBase> lgb = new ArrayList<GenBase>();
	List<Integer> genLevelItem = new ArrayList<Integer>();   // ��AutoCreategenLevelItem()�����г�ʼ��
	int maxGenLevel;
	double dRange;    // ������ֵ�����ԣ���ʾ��Χ
	
	// ���캯��
	public GenColumn(){
	}
	
	// ����������
	public void setAttr_name(String _attr_name){
		attr_name = _attr_name;
	}
	
	// �����������
	public void setAttr_class(String _attr_class){
		attr_class = _attr_class;
	}
	
	// ��ӷ������򣺷���������
	public void addGenRule(int _gen_level,String _original_value,String _gen_value){
		GenBase gb = new GenBase(_gen_level,_original_value,_gen_value);
		lgb.add(gb);
	}
	
	// ��ӷ���������ֵ������
	public void addGenRule(int _gen_level,int _start_num,int _end_num,String _gen_value){
		GenBase gb = new GenBase(_gen_level,_start_num,_end_num,_gen_value);
		lgb.add(gb);
		
	}
	
	// ɾ������Rule
	public void delAllGenRule(){
		Iterator<GenBase> it = lgb.iterator();
		while(it.hasNext()){
		  it.remove();
		}
	}
	
	// ��÷���������б�
	public List<Integer> getLevelItem(){
		List<Integer> li = new ArrayList<Integer>();
		for(int i=0; i<lgb.size(); i++){
			if(!li.contains(lgb.get(i).gen_level)){   //�鿴�¼������Ƿ���ָ����Ԫ�أ����û�������
                li.add(lgb.get(i).gen_level);
            }
		}
		return li;
	}
	
	public void AutoCreategenLevelItem(){
		for(GenBase gb:lgb){
			if(!genLevelItem.contains(gb.gen_level)){
				genLevelItem.add(gb.gen_level);
			}	
		}
		maxGenLevel = Collections.max(genLevelItem);     // ��һ���ܹؼ���������ķ�������
	}
	
	
}
