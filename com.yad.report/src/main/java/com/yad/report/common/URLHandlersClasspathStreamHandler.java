// com.yad.report.URLHandlersClasspathStreamHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.common;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午6:01:54 
 */
class URLHandlersClasspathStreamHandler extends URLStreamHandler {

	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		return new URLHandlersClasspathURLConnection(url);
	}
	
}

