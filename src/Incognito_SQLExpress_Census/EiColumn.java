package Incognito_SQLExpress_Census;

public class EiColumn {

	int EiStart;
	int EiEnd;
	
	public EiColumn(){};
	
	// ����EiStart
	public void setEiStart(int _EiStart){
		EiStart = _EiStart;
	}
	
	// ����EiEnd
	public void setEiEnd(int _EiEnd){
		EiEnd = _EiEnd;
	}
	
	// ��ȡEiStart
	public int getEiStart(){
		return EiStart;
	}
	
	// ��ȡEiEnd
	public int getEiEnd(){
		return EiEnd;
	}
	
}
