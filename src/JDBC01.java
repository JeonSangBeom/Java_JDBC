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
	
	private static void selectOne() { // id입력 받아서 하나만 출력
		Connection conn = null;
		PreparedStatement pstmt = null; // 값이 ? 가 들어갈때 PreparedStatement 사용
		ResultSet rs = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("누구의 정보를 보고 싶나요? ID를 입력하세요.");
		int _id = scanner.nextInt(); // ID 입력 방르 ㄹ곳
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM MEMBER WHERE ID = ? ORDER BY ID DESC"; // ? 가 하나 들어가 준다
			pstmt = conn.prepareStatement(sql);// 위 지정해 놓은 곳에만 PreparedStatement 'd'가 들어간다
			pstmt.setInt(1, _id); //? 가 있기 때문에 scanner설정
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

	
	private static void updateData() {// 업데이트 받기
		// 수정할 id를 입력 받아서
		// 이름을 바꾸세요
		// email을 바꿔 주세요
		// hp를 바꿔주세요
		Connection conn = null;
		PreparedStatement pstmt = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("수정할 아이디를 입력하시오");
		int _id = scanner.nextInt();
		scanner.nextLine(); // 하나 더 써주어야 위와 중첩이 안된다
		System.out.println("이름을 바꾸시오.");
		String _name = scanner.nextLine();
		System.out.println("이메일을 바꾸시오.");
		String _email = scanner.nextLine();
		System.out.println("전화번호을 바꾸시오.");
		String _hp = scanner.nextLine();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "UPDATE MEMBER SET NAME = ?, EMAIL = ?, HP = ? WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(4, _id); // 위의 주소와 순번을 맞추어야 한다
			pstmt.setString(1, _name);
			pstmt.setString(2, _email);
			pstmt.setString(3, _hp);
			int result = pstmt.executeUpdate();
			if (result > 0)
				System.out.println(_id + "님의 정보가 변경 되었습니다");
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
	
	private static void deleteData() { // 지우는 법
		Connection conn=null;
		PreparedStatement pstmt =null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("삭제할 아이드를 입력하시오");
		int _id = scanner.nextInt();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "DELETE FROM MEMBER WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id);//pstmt로도 select할 수 있다
			int result = pstmt.executeUpdate(); //result - 몇개에 영향을 미쳤느냐 select를 제외하고 전부 업데이트이다
			if(result>0) System.out.println(_id+"님이 삭제 되었습니다");
			
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
		}// 이곳에서 삭제를 하면 sql에서 comit을 하지 않아도 삭제가 된다 (pstmt가 commit을 같이 가져가서 그렇다)
		
	}
	
	private static void getData() {
		Connection conn=null;
		Statement stmt = null;// Statement = 미리 준비할 필요가 없기 때문에 이렇게 써도 무방하다
		ResultSet rs = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM MEMBER ORDER BY ID DESC";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);//결과 값이 rs로 떨어진다
			while(rs.next()) {// 다음게 있으면
				int id = rs.getInt("ID");
				String name= rs.getString("NAME");
				String email = rs.getString("EMAIL");
				String hp = rs.getString("hp");
				String regDate = rs.getString("REGDATE");
                System.out.println("id : "+id+"=== name : "+name+"=== email : "+email+"=== hp : "+hp+"=== date : "+regDate);  
                System.out.println(); // 두개가 있을시 두개 보이게 하기 위해(굳이 안써도 나온다)
                // 또 데이터 넘어 오게 하고 싶을 경우 sql에서 commit을 해주어야 한다
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
		System.out.println("이름을 입력하시오");
		String _name = scanner.nextLine(); //_name에 입력 
		System.out.println("이메일을 입력하시오");
		String _email = scanner.nextLine(); 
		System.out.println("전화번호을 입력하시오");
		String _hp = scanner.nextLine(); 
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("db연결");
			String sql = "INSERT INTO MEMBER VALUES(SEQ_MEMBER.NEXTVAL,?,?,?,SYSDATE)";
			//SEQ_MEMBER.NEXTVAL 자동 증가로 바꾸어서 아래 내용에서 ID가 필요 없어진다 그리고 시퀀스 설정으로 인해 1부터 다시 시작
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, 1111); // id
			pstmt.setString(1, _name); //name
			pstmt.setString(2, _email);// email
			pstmt.setString(3, _hp); // hp
			int result = pstmt.executeUpdate(); // 몇개의 행에 영향을 주었는지 정수로 떨어진다
			if(result>0) {
				System.out.println("입력되었습니다.");
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
		Scanner scanner = new Scanner(System.in);// 스캐너는 위에서 한 번만 선언 되면 된다
		boolean off = false; // off false(처음 값은 false 기준 잡기)
		while (true) {
			System.out.println("0:종료, 1:모든 멤버 정보 출력, 2:원하는 ID 한명 출력,3:삭제,4: 입력,5:수정");
			int sel = scanner.nextInt();// 정수 받기
			switch (sel) { // sel로 받기
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
			case 0: // 종료
				off = true; // while문 종료 방법(편법) / while문을 빠져 나가게 된다
				break;
			}
			if(off) break;// 여기서 while문을 멈출 수 있다
		}
	}
}
