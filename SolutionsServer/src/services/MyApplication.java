package services;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {
	public MyApplication() {
		super(IsSolutionExist.class,SolutionsProvider.class,SolutionSave.class);
	}
}
