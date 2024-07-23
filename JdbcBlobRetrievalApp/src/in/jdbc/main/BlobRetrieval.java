package in.jdbc.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class BlobRetrieval
{
	public static void main(String []args) throws SQLException,IOException
	{
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet resultset=null;
		
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		String username="root";
		String password="Meet@231";
		
		connection = DriverManager.getConnection(url,username,password);
		
		String sqlQuery="select id,name,image from persons where id=1";
		
		if(connection != null)
		{
			pstmt = connection.prepareStatement(sqlQuery);
		}
		
		if(pstmt != null)
		{
			resultset = pstmt.executeQuery();
		}
		
		if(resultset != null)
		{
			if(resultset.next())
			{
				System.out.println("id\tName\tImage");
				int sid = resultset.getInt(1);
				String name = resultset.getString(2);
				InputStream is = resultset.getBinaryStream(3);
				
				File file=new File("copied.jpg");
				FileOutputStream fos=new FileOutputStream(file);
//-----Method 1--------------
//				int i=is.read();
//				
//				while(i!=-1)
//				{
//					fos.write(i);
//					i=is.read();
//				}
//-------------------------------------------------
//-----Method 2-----------------------------------			
//				byte b[]=new byte[1024];
//				
//				whilSe(is.read(b) > 0)
//				{
//					fos.write(b);
//				}
//------------------------------------------------
//------Method 3----------------------------------			
				IOUtils.copy(is,fos);
//------------------------------------------------
		
				
				fos.close();
				
				System.out.println(sid+"\t"+name+"\t"+file.getAbsolutePath());
			}
			else
			{
				System.out.println("Result set not available for this id");
			}
		}
		
	}
}