package com.niit.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.Friend;
import com.niit.model.Job;
import com.niit.model.Notification;
import com.niit.model.ProfilePicture;
import com.niit.model.User;


@Configuration
@EnableWebMvc 
@EnableTransactionManagement
//@ComponentScan("com.niit.configuration")

@ComponentScan(basePackages ={ "com.niit.configuration" }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class)})


   public class DBConfiguartion 
   {
  public DBConfiguartion(){
	 System.out.println("DBConfiguration class is instantiated"); 
  }
  @Autowired
  @Bean(name="sessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder lsf=new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		Class classes[]=new Class[]{User.class,Job.class,Friend.class,BlogPost.class,Notification.class,BlogPostLikes.class,BlogComment.class,ProfilePicture.class};//class objects of all entities
	    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}
    @Autowired
	@Bean(name="dataSource")
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("system");
	    dataSource.setPassword("password");
	    return dataSource;
	    
	}
	
	/*@Bean
	public UserDaoimpl getUserDaoimpl(SessionFactory sessionFactory) {
		return UserDaoimpl.getUserDaoimpl();
	} */
    @Autowired
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(getSessionFactory());
	}
}


