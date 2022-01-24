import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBC01 {	
	static final String driver = "oracle.jdbc.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String id = "TIS001";
	static final String pw = "1234";
	
	private static void selectOne() { // id�Է� �޾Ƽ� �ϳ��� ���
		Connection conn = null;
		PreparedStatement pstmt = null; // ���� ? �� ���� PreparedStatement ���
		ResultSet rs = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("������ ������ ���� �ͳ���? ID�� �Է��ϼ���.");
		int _id = scanner.nextInt(); // ID �Է� �渣 ����
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM MEMBER WHERE ID = ? ORDER BY ID DESC"; // ? �� �ϳ� �� �ش�
			pstmt = conn.prepareStatement(sql);// �� ������ ���� ������ PreparedStatement 'd'�� ����
			pstmt.setInt(1, _id); //? �� �ֱ� ������ scanner����
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				String hp = rs.getString("hp");
				String regDate = rs.getString("REGDATE");
				System.out.println("id : " + id + "=== name : " + name + "=== email : " + email + "=== hp : " + hp
						+ "=== date : " + regDate);
				System.out.println();

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	private static void updateData() {// ������Ʈ �ޱ�
		// ������ id�� �Է� �޾Ƽ�
		// �̸��� �ٲټ���
		// email�� �ٲ� �ּ���
		// hp�� �ٲ��ּ���
		Connection conn = null;
		PreparedStatement pstmt = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("������ ���̵� �Է��Ͻÿ�");
		int _id = scanner.nextInt();
		scanner.nextLine(); // �ϳ� �� ���־�� ���� ��ø�� �ȵȴ�
		System.out.println("�̸��� �ٲٽÿ�.");
		String _name = scanner.nextLine();
		System.out.println("�̸����� �ٲٽÿ�.");
		String _email = scanner.nextLine();
		System.out.println("��ȭ��ȣ�� �ٲٽÿ�.");
		String _hp = scanner.nextLine();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "UPDATE MEMBER SET NAME = ?, EMAIL = ?, HP = ? WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(4, _id); // ���� �ּҿ� ������ ���߾�� �Ѵ�
			pstmt.setString(1, _name);
			pstmt.setString(2, _email);
			pstmt.setString(3, _hp);
			int result = pstmt.executeUpdate();
			if (result > 0)
				System.out.println(_id + "���� ������ ���� �Ǿ����ϴ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void deleteData() { // ����� ��
		Connection conn=null;
		PreparedStatement pstmt =null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("������ ���̵带 �Է��Ͻÿ�");
		int _id = scanner.nextInt();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "DELETE FROM MEMBER WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id);//pstmt�ε� select�� �� �ִ�
			int result = pstmt.executeUpdate(); //result - ��� ������ ���ƴ��� select�� �����ϰ� ���� ������Ʈ�̴�
			if(result>0) System.out.println(_id+"���� ���� �Ǿ����ϴ�");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
				try {
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}// �̰����� ������ �ϸ� sql���� comit�� ���� �ʾƵ� ������ �ȴ� (pstmt�� commit�� ���� �������� �׷���)
		
	}
	
	private static void getData() {
		Connection conn=null;
		Statement stmt = null;// Statement = �̸� �غ��� �ʿ䰡 ���� ������ �̷��� �ᵵ �����ϴ�
		ResultSet rs = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM MEMBER ORDER BY ID DESC";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);//��� ���� rs�� ��������
			while(rs.next()) {// ������ ������
				int id = rs.getInt("ID");
				String name= rs.getString("NAME");
				String email = rs.getString("EMAIL");
				String hp = rs.getString("hp");
				String regDate = rs.getString("REGDATE");
                System.out.println("id : "+id+"=== name : "+name+"=== email : "+email+"=== hp : "+hp+"=== date : "+regDate);  
                System.out.println(); // �ΰ��� ������ �ΰ� ���̰� �ϱ� ����(���� �Ƚᵵ ���´�)
                // �� ������ �Ѿ� ���� �ϰ� ���� ��� sql���� commit�� ���־�� �Ѵ�
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertData() {
		Connection conn=null;
		PreparedStatement pstmt = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("�̸��� �Է��Ͻÿ�");
		String _name = scanner.nextLine(); //_name�� �Է� 
		System.out.println("�̸����� �Է��Ͻÿ�");
		String _email = scanner.nextLine(); 
		System.out.println("��ȭ��ȣ�� �Է��Ͻÿ�");
		String _hp = scanner.nextLine(); 
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("db����");
			String sql = "INSERT INTO MEMBER VALUES(SEQ_MEMBER.NEXTVAL,?,?,?,SYSDATE)";
			//SEQ_MEMBER.NEXTVAL �ڵ� ������ �ٲپ �Ʒ� ���뿡�� ID�� �ʿ� �������� �׸��� ������ �������� ���� 1���� �ٽ� ����
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, 1111); // id
			pstmt.setString(1, _name); //name
			pstmt.setString(2, _email);// email
			pstmt.setString(3, _hp); // hp
			int result = pstmt.executeUpdate(); // ��� �࿡ ������ �־����� ������ ��������
			if(result>0) {
				System.out.println("�ԷµǾ����ϴ�.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	
	public static void main(String[] args) {
//		updateData();		
//		insertData();
//		getData();		
//		deleteData();
//		selectOne();
		Scanner scanner = new Scanner(System.in);// ��ĳ�ʴ� ������ �� ���� ���� �Ǹ� �ȴ�
		boolean off = false; // off false(ó�� ���� false ���� ���)
		while (true) {
			System.out.println("0:����, 1:��� ��� ���� ���, 2:���ϴ� ID �Ѹ� ���,3:����,4: �Է�,5:����");
			int sel = scanner.nextInt();// ���� �ޱ�
			switch (sel) { // sel�� �ޱ�
			case 1:
				getData();
				break;
			case 2:
				selectOne();
				break;
			case 3:
				deleteData();
				break;
			case 4:
				insertData();
				break;
			case 5:
				updateData();
				break;
			case 0: // ����
				off = true; // while�� ���� ���(���) / while���� ���� ������ �ȴ�
				break;
			}
			if(off) break;// ���⼭ while���� ���� �� �ִ�
		}
	}
}
