import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_CONNECTION_TEST {
	public static void main(String[] args) {
		//������ �߰�ȣ �ȿ� ������ ���ú����� �Ǿ� ������ �� �� ���� ������ ���� ���־� �Ѵ�
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver"); // �̸����� Ŭ������ ã�ڴ� �� (����̹� ã��)
			//"oracle.jdbc.OracleDriver" -> oracle �� jdbc�� OracleDriver��� Ŭ���� ���� (�ܺ� �����̶� try catch�ʿ�)
			conn =  // ����̹� ã�� Ŀ�ؼ�(���� ���ֱ�)
			DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger"); // ����
			//����̹� �Ŵ����κ��� �������ڴ�(������ Ʋ,����,��й�ȣ) - Ʈ���� Ĺ�� -> ��������� �����Ű�� ��
			System.out.println("db����");
			stmt = conn.createStatement(); // ���� ����� = Ŀ�ؼ� ������ ���� �����(����(sql) �غ�)
			rs = stmt.executeQuery("SELECT * FROM EMP");  // ����(sql) ������(��õ) - ������ redultset���� ���´�
			//���� ������(��������)
			if(rs!=null) { // ���� null�� �ƴ϶�
				System.out.println("���� ������ �亯 �ޱ� ����");
			}
			while(rs.next()) { // ��� ������� �𸣴� �ݺ��� �̿� rs.next()���� ���� �������� ���
				String empNo = rs.getString("empno");// ���� ������
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println("empNo : "+empNo+"=== ename : "+ename+"=== job : "+ job);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("db���ῡ��");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// �������� �ݴ� ���� �����̶� �ݾ� ��� �Ѵ� 
			try {//(�ݴ� ���� ����)
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
