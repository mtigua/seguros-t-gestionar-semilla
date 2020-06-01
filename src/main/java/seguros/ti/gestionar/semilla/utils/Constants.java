package seguros.ti.gestionar.semilla.utils;

public class Constants {

	 public static final String PREFIX_H2_DATASOURCE = "dbh2.datasource";
	 public static final String PREFIX_SQLSERVER_DATASOURCE = "dbsqlserver.datasource";
	  
	 public static final String [] LIST_ENTITY_DATASOURCE_H2_DB= new String[] {
	    		"seguros.ti.gestionar.semilla.dbh2.entities"
	    		};
	 public static final String [] LIST_ENTITY_DATASOURCE_SQLSERVER_DB= new String[] {
	    		"seguros.ti.gestionar.semilla.dbsqlserver.entities"
	    		};

	 public static final String SPRING_JPA_HIBERNATE_DDL_AUTO_KEY = "spring.jpa.hibernate.ddl-auto";
	 public static final String HIBERNATE_DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
	 public static final String HIBERNATE_DIALECT_KEY = "hibernate.dialect";
	 public static final String SPRING_JPA_HIBERNATE_DIALECT_SQLSERVER_KEY = "sqlserver.dialect";
	 public static final String SPRING_JPA_HIBERNATE_DIALECT_H2_KEY = "h2.dialect";
}
