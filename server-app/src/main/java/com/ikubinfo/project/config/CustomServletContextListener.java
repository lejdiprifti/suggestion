package com.ikubinfo.project.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ikubinfo.project.util.PersistenceSingleton;

@WebListener
public class CustomServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		PersistenceSingleton.INSTANCE.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
	}
}
