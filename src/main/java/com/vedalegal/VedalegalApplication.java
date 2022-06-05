package com.vedalegal;

import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.vedalegal.resource.AppConstant;

@SpringBootApplication
@EnableAsync
public class VedalegalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(VedalegalApplication.class).sources(VedalegalApplication.class)
		.properties(getProperties())
		.run(args);
	}


	@Override protected SpringApplicationBuilder
	configure(SpringApplicationBuilder springApplicationBuilder) { return
			springApplicationBuilder.sources(VedalegalApplication.class).properties(
					getProperties()); }

	static Properties getProperties() { Properties props = new Properties();
	props.put("spring.config.location", AppConstant.FileLocation.PROPERTY_PATH);
	return props; }


}
