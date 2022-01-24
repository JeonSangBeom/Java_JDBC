import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_CONNECTION_TEST {
	public static void main(String[] args) {
		//변수가 중괄호 안에 있으면 로컬변수가 되어 접근을 할 수 없기 때문에 위로 빼둬야 한다
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver"); // 이름으로 클래스를 찾겠단 뜻 (드라이버 찾기)
			//"oracle.jdbc.OracleDriver" -> oracle 안 jdbc안 OracleDriver라는 클래스 네임 (외부 파일이라 try catch필요)
			conn =  // 드라이버 찾고 커넥션(연결 해주기)
			DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger"); // 연결
			//드라이버 매니저로부터 가져오겠다(정해진 틀,계정,비밀번호) - 트라이 캣취 -> 여기까지가 연결시키는 것
			System.out.println("db연결");
			stmt = conn.createStatement(); // 문장 만들기 = 커넥션 가지고 문장 만들기(질문(sql) 준비)
			rs = stmt.executeQuery("SELECT * FROM EMP");  // 질문(sql) 날리기(실천) - 정답은 redultset으로 들어온다
			//질문 날리기(위에까지)
			if(rs!=null) { // 답이 null이 아니라
				System.out.println("질문 날리고 답변 받기 성공");
			}
			while(rs.next()) { // 몇개가 날라올지 모르니 반복문 이용 rs.next()답이 오면 다음것을 출력
				String empNo = rs.getString("empno");// 문자 얻어오기
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println("empNo : "+empNo+"=== ename : "+ename+"=== job : "+ job);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("db연결에러");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 마지막에 닫는 것이 정상이라 닫아 줘야 한다 
			try {//(닫는 것은 역순)
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
