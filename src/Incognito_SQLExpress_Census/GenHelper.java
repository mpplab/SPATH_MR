package Incognito_SQLExpress_Census;

import java.util.List;
import java.util.Map;

public class GenHelper {

    GenTableDefinition_census_income gtd = new GenTableDefinition_census_income();   // ���巺������
    GenTable gt = gtd.getGenTable();   // �������еķ�����÷�����
	
	// ���캯��
	public GenHelper(){
		
	}

	// ������Ի�÷�������
	public DataColumn getGenDataColumn(DataColumn dc,int genlevel){
		DataColumn genDataCol = new DataColumn();	// ���巺����genDataCol��Ϊ���
		genDataCol.setColumnName(dc.getColumnName());
		GenColumn gc = gt.getGenColumn(dc.columnName);
		switch(gc.attr_class){
			case "Categorical":
				for (int i=1;i<=dc.size();i++) {
					String dcValue = dc.getValue(i).toLowerCase();
					for(GenBase gb:gc.lgb){
						if(genlevel == 0){         // �������Ϊ0����ʾ����Ҫ����
							genDataCol.add2ColumnList(dcValue);
							break;
						}else if(genlevel == gb.gen_level && dcValue.equals(gb.original_value.toLowerCase())){
//							System.out.print(i + "�ɹ���");
//							System.out.print(dcValue);
//							System.out.println();
							genDataCol.add2ColumnList(gb.gen_value);
							break;
						}else{
//							System.out.print(i + "ʧ�ܣ�");
//							System.out.print(gb.original_value.toLowerCase() + " ");
//							System.out.print(gb.gen_level + " ");
//							System.out.print(dc.getValue(i).toLowerCase() + " ");
//							System.out.println();
						}
						
					}
				};
				break;
			case "Numerical":
				for (int i=1;i<=dc.size();i++) {
					int dcv = Integer.parseInt(dc.getValue(i));
					for(GenBase gb:gc.lgb){
						if(genlevel == 0){         // �������Ϊ0����ʾ����Ҫ����
							genDataCol.add2ColumnList(dc.getValue(i));
							break;
						}else if(genlevel == gb.gen_level && dcv >= gb.start_num && dcv < gb.end_num){
							genDataCol.add2ColumnList(gb.gen_value);
							break;
						}
					}
				}
				break;
		}
		if(dc.size() == genDataCol.size()){
			return genDataCol;
		}else{
			System.out.println("Attribute " + dc.columnName + "��"+ String.valueOf(genlevel) + "���𷺻����������ܸ���ȫ������");
			return genDataCol;
		}
		
	}
	
	

	// ������Ի�÷�������
		public DataTable getGenDataTable(DataTable dt,CiColumn ciC){
			DataTable dt_gen = new DataTable();
			for(Map<String,String> ms:ciC.columnList){
				for(Map.Entry<String,String> me : ms.entrySet()){
					DataColumn dc_pregen = dt.getColumn(me.getKey());
					DataColumn dc_gen = getGenDataColumn(dc_pregen,Integer.parseInt(me.getValue()));
					dt_gen.addColumn(dc_gen);
					
				}
			}
			return dt_gen;
		}

		
		// ��ݷ�������б��÷�����
		public DataTable getGenDataTable(DataTable dt, List<Integer> lGenGroup){
			DataTable dt_gen = new DataTable();
			for(int i = 0; i < lGenGroup.size(); i++) {
				DataColumn dc_gen = getGenDataColumn(dt.dataT.get(i), lGenGroup.get(i));
				dt_gen.addColumn(dc_gen);
			}
			return dt_gen;
		}
		
		
		// ������Ժͷ�������
		public GenAttrLevel getAttrLevel(String attr){
			GenAttrLevel gal = new GenAttrLevel();
			GenColumn gc = gt.getGenColumn(attr);
			gal.setAttr(gc.attr_name);
			gal.setLevel_Item(gc.getLevelItem());
			return gal;
		}
		
		
		// ���������ƻ����󷺻�����
		public int getMaxAttrLevelbyAttrName(String attr){
			// (1) �ж�genLevelItem�ǲ����Ѿ���ɹ��ˣ����Ϊ�����Զ����
			if(gt.lgc.get(0).genLevelItem.size() == 0){
				gt.AutoCreateGenItem();
			}
			
			// (2) �ҵ������Եķ����в�����
			return gt.getGenColumn(attr).maxGenLevel;
		}
		
}
