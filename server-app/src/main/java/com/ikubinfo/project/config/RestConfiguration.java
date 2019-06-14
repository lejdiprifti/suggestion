package com.ikubinfo.project.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.ikubinfo.project.util.Paths;

@ApplicationPath(Paths.BASE)
public class RestConfiguration extends ResourceConfig {

	public RestConfiguration() {
		packages(true, "com.ikubinfo.project");
	}
}
