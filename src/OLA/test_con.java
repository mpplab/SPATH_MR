package OLA;

import java.sql.SQLException;

/*
 * �ж��ܷ����ӳɹ�
 */
public class test_con {

	public static void main(String[] args) throws SQLException {  
        DBHelper help=new DBHelper();  
        if(help.TestConn()) 
            System.out.println("���ӳɹ�1"); 
        else 
            System.out.println("����ʧ��1");
        
//        help.insert2NextCi(2);
//        help.insert2NextCi(3);
//        help.insert2NextCi(4);
//        help.insert2NextCi(5);
//        help.insert2NextCi(6);
//        help.insert2NextCi(7);
        
        
//        help.insert2CandidateEdges(2);
//        System.out.println("");
//        help.insert2CandidateEdges(3);
//        System.out.println("");
//        help.insert2CandidateEdges(4);
//        System.out.println("");
//        help.insert2CandidateEdges(5);
//        System.out.println("");
//        help.insert2CandidateEdges(6);
//        System.out.println("");
//        help.insert2CandidateEdges(7);
//        System.out.println("");
        
        
        help.insert2NextEi(2);
        System.out.println("");
        help.insert2NextEi(3);
        System.out.println("");
        help.insert2NextEi(4);
        System.out.println("");
        help.insert2NextEi(5);
        System.out.println("");
        help.insert2NextEi(6);
        System.out.println("");
        help.insert2NextEi(7);
        System.out.println("");
        
          
}  
	
}
