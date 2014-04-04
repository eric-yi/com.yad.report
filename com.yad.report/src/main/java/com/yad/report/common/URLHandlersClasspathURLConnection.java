// com.yad.report.URLHandlersClasspathURLConnection.java
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

/**
 * TODO desc
 * 
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 下午6:11:52
 */
public class URLHandlersClasspathURLConnection extends URLConnection {

	protected URLHandlersClasspathURLConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() throws IOException {
		if (!connected) {

			connected = true;
		}
	}

}
