package seguros.ti.gestionar.semilla.configuration;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		  basePackages = "seguros.ti.gestionar.semilla.dbsqlserver.repositories",
		  entityManagerFactoryRef = "sqlserverEntityManagerFactory",
		  transactionManagerRef = "sqlserverTransactionManager"
		)
public class SqlServerDataSourceConfig {

	 protected static final String [] LIST_ENTITY_DATASOURCE_SQLSERVER_DB= new String[] {
	    		"seguros.ti.gestionar.semilla.dbsqlserver.entities"
	    		};
	 protected static final String PREFIX_SQLSERVER_DATASOURCE= "dbsqlserver.datasource";	

	 @Autowired
	 private PropertiesJpa propertiesJpa;
	

    @Bean(name = "sqlserverDataSourceProperties")
    @ConfigurationProperties(prefix = PREFIX_SQLSERVER_DATASOURCE)
    public DataSourceProperties sqlserverDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sqlserverDataSource")
    public DataSource sqlserverDataSource() {
        DataSourceProperties primaryDataSourceProperties = sqlserverDataSourceProperties();
        return DataSourceBuilder.create()
            .driverClassName(primaryDataSourceProperties.getDriverClassName())
            .url(primaryDataSourceProperties.getUrl())
            .username(primaryDataSourceProperties.getUsername())
            .password(primaryDataSourceProperties.getPassword())
            .build();
    }

  
    @Bean(name = "sqlserverTransactionManager")
    public PlatformTransactionManager sqlserverTransactionManager() {
        EntityManagerFactory factory = sqlserverEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean(name = "sqlserverEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlserverEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(sqlserverDataSource());
        factory.setPackagesToScan(LIST_ENTITY_DATASOURCE_SQLSERVER_DB);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(propertiesJpa.getHIBERNATE_DDL_AUTO_KEY(), propertiesJpa.getSpring_jpa_hibernate_ddl_auto());
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");

     //   jpaProperties.put(propertiesJpa.getHIBERNATE_DIALECT_KEY(), propertiesJpa.getSPRING_JPA_HIBERNATE_DIALECT_SQLSERVER_KEY());

        factory.setJpaProperties(jpaProperties);

        return factory;

    }
    
    
   
}
