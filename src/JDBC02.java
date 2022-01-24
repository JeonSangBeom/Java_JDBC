import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC02 {
	//static 안이라 static 선언 - 반복되는 것이라 정의 
	static final String driver = "oracle.jdbc.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String id = "scott";
	static final String pw = "tiger";

	private static void insertData() { // 데이터 집어 넣기
		// 드라이버 찾기
		// 연결 하기
		// 질문준비
		// 질문 날리기
		// 결과 받기
		Connection conn=null; // 커넥션 하나 얻어오기
		PreparedStatement pstmt = null; // PreparedStatement - 미리 질문을 준비해서 날리는 것
		String sql = "INSERT INTO EMP VALUES (?,?,?,?,?,?,?,?)"; // 데이터 집어 넣을 때(테이블에 있는 갯수 맞춰서 입력)
		try {
			Class.forName(driver);//url - 드라이버 찾기(트라이 캐취 해주기)
			conn = DriverManager.getConnection(url, id, pw); // 드라이버 연결
			pstmt = conn.prepareStatement(sql);// sql에 만들어 넣은 질문 이용 
			pstmt.setInt(1, 8865);// 위에 정한 ? 1 번에(순서에) 맞게 들어간다
			pstmt.setString(2, "JJANG052"); // ename
			pstmt.setString(3, "STUDENT"); //job
			pstmt.setInt(4, 3456);//mgr
			pstmt.setString(5, "2022-01-20");//hiredate
			pstmt.setInt(6, 2200);//sal
			pstmt.setInt(7, 0);//comm
			pstmt.setInt(8, 30);//deptno-- 아무거나 넣으면 안된다(레퍼런스 키로 걸려 있어서 없는 것을 넣으면 안된다)
			int result = pstmt.executeUpdate();  // 결과 값이 나오면 업데이트 시킨다 / 몇개 행에 영향을 미쳤는냐(현재는 하나)
			System.out.println(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private static void getData() {// static 안이기 때문에 static로 설정 필요
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver); // driver李얘린....
			conn = DriverManager.getConnection(url, id, pw); // �뿰寃�
			System.out.println("db�뿰寃�");
			stmt = conn.createStatement(); // 吏덈Ц(sql) 以�鍮�
			rs = stmt.executeQuery("SELECT * FROM EMP"); // 吏덈Ц(sql) �궇由ш린
			// 吏덈Ц �궇由ш린...
			if (rs != null) {
				System.out.println("吏덈Ц �궇由ш퀬 �떟蹂� 諛쏄린 �꽦怨�");
			}
			while (rs.next()) {
				String empNo = rs.getString("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println("empNo : " + empNo + "=== ename : " + ename + "=== job : " + job);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("db�뿰寃곗뿉�윭");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
//		getData(); //메서드 하나로 빼준 것
		insertData(); // 여기 입력 
	}
}
