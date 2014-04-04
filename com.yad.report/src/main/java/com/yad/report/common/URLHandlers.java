// com.yad.report.URLHandlers.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.common;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午6:05:34 
 */
class URLHandlers implements URLStreamHandlerFactory, ContentHandlerFactory {
	private static final String CLASSPATH = "classpath";

	@Override
	public ContentHandler createContentHandler(String mimetype) {
		return null;
	}

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		 if (protocol.equals(CLASSPATH))
			 return new URLHandlersClasspathStreamHandler();
		 
		 return null;
	}

}

