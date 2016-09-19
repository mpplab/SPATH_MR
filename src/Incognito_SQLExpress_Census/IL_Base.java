package Incognito_SQLExpress_Census;

public class IL_Base {

	public String sAttrname;  // ������
	public String sClass;   // �����Numerical��ʾ��ֵ�ͣ������Categorical��ʾ������
	public int iCount;      // ��ֵ�ͺͷ����Ͷ��õ�
	public double dMax;     // ��ֵ���õ�
	public double dMin;     // ��ֵ���õ�
	public double dRange;   // ��ֵ���õ�
	public double dHSub;    // �������õ���������С�������ȵķ��������ĸ߶�
	public double dHAll;    // �������õ�
	
	// ���캯��
	public IL_Base() {};
	
	// ���캯����������ֵ��
	public IL_Base(String _sAttrname, String _sClass, int _iCount, double _dMax, double _dMin, double _dRange){
		this.sAttrname = _sAttrname;
		this.sClass = _sClass;
		this.iCount = _iCount;
		this.dMax = _dMax;
		this.dMin = _dMin;
		this.dRange = _dRange;
	}
	
	// ���캯�������ڷ�����
	public IL_Base(String _sAttrname, String _sClass, int _iCount, double _dHSub, double _dHAll){
		this.sAttrname = _sAttrname;
		this.sClass = _sClass;
		this.iCount = _iCount;
		this.dHSub = _dHSub;
		this.dHAll = _dHAll;
	}
	
	
}
