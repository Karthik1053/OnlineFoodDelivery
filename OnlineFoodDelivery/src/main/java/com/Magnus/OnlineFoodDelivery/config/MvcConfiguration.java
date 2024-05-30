package com.Magnus.OnlineFoodDelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SuppressWarnings("deprecation")
@EnableWebMvc
@ComponentScan(basePackages="com.Magnus.OnlineFoodDelivery")
@Import({HibernateConfig.class,SecurityConfig.class})
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	   
		@Bean
		public ViewResolver viewResolver() {

			InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

			configurer.enable();

		}
		
		 @Override
		    public void addResourceHandlers(ResourceHandlerRegistry registry) {
			 registry
		      .addResourceHandler("/resources/**")
		      .addResourceLocations("/resources/")
		      .setCachePeriod(3600)
		      .resourceChain(true)
		      .addResolver(new PathResourceResolver());
		    }

	
}
