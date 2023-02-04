package com.example.springjpa.config;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
		  name = "CustomeLog4j2Appender", 
		  category = "Core", 
		  elementType = Appender.ELEMENT_TYPE)
public class CustomeLog4j2Appender extends AbstractAppender {

	protected CustomeLog4j2Appender(String name, Filter filter) {
		super(name, filter, null);
	}
	
	@PluginFactory
    public static CustomeLog4j2Appender createAppender(
      @PluginAttribute("name") String name, 
      @PluginElement("Filter") Filter filter) {
        return new CustomeLog4j2Appender(name, filter);
    }
	@Override
	public void append(LogEvent event) {
		System.out.println(event.getMessage());
		
	}

}
