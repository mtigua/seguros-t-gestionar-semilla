package seguros.ti.gestionar.semilla.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SqlReader {

	private static final Logger logger = LoggerFactory.getLogger(SqlReader.class.getSimpleName());
	
	public static String readSql(String filePath){
	   ClassLoader loader = Thread.currentThread().getContextClassLoader();
	   String query="";

	   try(InputStream resourceStream = loader.getResourceAsStream(filePath)) {
           query = new BufferedReader(new InputStreamReader(resourceStream)).lines().collect(Collectors.joining("\n"));  
	   } 
	   catch (Exception e) {
		logger.error(e.getMessage());
	   }	
	
	   return  query;
	}
	
}
