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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		  basePackages = "seguros.ti.gestionar.semilla.dbh2.repositories",
		  entityManagerFactoryRef = "h2dbEntityManagerFactory",
		  transactionManagerRef = "h2dbTransactionManager"
		)
public class H2DataSourceConfig {
	
	@Autowired
    private PropertiesJpa propertiesJpa;
	
	 protected static final String [] LIST_ENTITY_DATASOURCE_H2_DB= new String[] {
	    		"seguros.ti.gestionar.semilla.dbh2.entities"
	    		};
	 protected static final String PREFIX_H2_DATASOURCE= "dbh2.datasource";	


	@Primary
    @Bean(name = "h2dbDataSourceProperties")
    @ConfigurationProperties(prefix = PREFIX_H2_DATASOURCE)
    public DataSourceProperties h2dbDataSourceProperties() {
        return new DataSourceProperties();
    }

	@Primary
    @Bean(name = "h2dbDataSource")
    public DataSource h2dbDataSource() {
        DataSourceProperties primaryDataSourceProperties = h2dbDataSourceProperties();
        return DataSourceBuilder.create()
            .driverClassName(primaryDataSourceProperties.getDriverClassName())
            .url(primaryDataSourceProperties.getUrl())
            .username(primaryDataSourceProperties.getUsername())
            .password(primaryDataSourceProperties.getPassword())
            .build();
    }

	 @Primary	
	 @Bean(name = "h2dbTransactionManager")
	    public PlatformTransactionManager h2dbTransactionManager() {
	        EntityManagerFactory factory = h2dbEntityManagerFactory().getObject();
	        return new JpaTransactionManager(factory);
	    }
	
	@Primary
    @Bean(name = "h2dbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean h2dbEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(h2dbDataSource());
        factory.setPackagesToScan(LIST_ENTITY_DATASOURCE_H2_DB);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        
        Properties jpaProperties = new Properties();
        jpaProperties.put(propertiesJpa.getHIBERNATE_DDL_AUTO_KEY(),propertiesJpa.getSpring_jpa_hibernate_ddl_auto());
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        
        factory.setJpaProperties(jpaProperties);

        
        return factory;

    }

  

  
}
