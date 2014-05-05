package database;


import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import config.GlobalConfig;


public class DatabaseConnector {

	private SqlSessionFactory sqlSessionFactory;
	private static DatabaseConnector myself;
	
	public static DatabaseConnector getInstance(){
		if(myself == null){
			myself = new DatabaseConnector();
		}
		return myself;
	}
	public void configure()  
	{
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(GlobalConfig.getInstance().getConfigResourceAddress("databaseConfig"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(reader);
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	private DatabaseConnector(){
//		Reader reader = null;
//		try {
//			reader = Resources.getResourceAsReader(new GlobalConfig().getConfigResourceAddress("databaseConfig"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}  
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        sqlSessionFactory = builder.build(reader);
////        sqlSessionFactory.getConfiguration().addMapper(YaoPengLoginDao.class);
//        try {
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}
	

}
