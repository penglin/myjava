package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbctest {
	public static void main(String[] args) {
		boolean flag = false;
		// �ڴ˴��ɾ�������ݿ���֤

		// ����һ�����ݿ��������
		PreparedStatement pstmt = null;
		// ����һ�����������
		ResultSet rs = null;
		// ����һ��SQL���������ڱ���SQL���
		String sql = null;
		// DataBaseConnectionΪ��������ݿ����Ӽ��رղ�����
		Connection con = null;
		// �������ݿ�
		con = ConnectionFactory.getConnection();

		// ��дSQL���
		sql = "if object_id('user_campaign_8526','u') is not null delete from dbo.user_campaign_8526 else select 0";
		try {
			// ʵ�������ݿ��������
			pstmt = con.prepareStatement(sql);

			System.out.println("���������ѱ�ʵ����");

			// ����pstmt�����ݣ��ǰ�ID��������֤
//			pstmt.setString(1, "limeng");
//			pstmt.setString(2, "limeng");

			System.out.println("���username,password");

			// ��ѯ��¼
			rs = pstmt.executeQuery();
			System.out.println("ִ�в�ѯ���");
			// �ж��Ƿ��м�¼
			while (rs.next()) {
				// ����м�¼����ִ�д˶δ���
				// �û��ǺϷ��ģ����Ե�½
				flag = true;
				System.out.println(rs.getString("visit_date"));

				System.out.println("�û��Ϸ�");
			}
			// ���ιر�

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// ���һ��Ҫ��֤���ݿ��ѱ��ر�
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}