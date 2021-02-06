package com.example.headingparser.parsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.junit.platform.commons.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.headingparser.dto.ParserResults;

public class SAXParser {
	
	public static ParserResults analyseHeadings(String pageUrl) throws SAXException, MalformedURLException, IOException {
		final Instant start = Instant.now();
		List<String> h1Contents = new ArrayList<>(), h2Contents = new ArrayList<>(), h3Contents = new ArrayList<>();
		Map<String, List<String>> headerContents = new HashMap<>();
		headerContents.put("h1", h1Contents);
		headerContents.put("h2", h2Contents);
		headerContents.put("h3", h3Contents);
		
		URLConnection urlConnection = new URL(pageUrl).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla");
		SAXParserImpl.newInstance(null).parse(urlConnection.getInputStream(),
												new DefaultHandler() {
												    boolean isParsingH1 = false, isParsingH2 = false, isParsingH3 = false;
												    StringBuffer buffer=null;
												    
												    @Override
												    public void startElement(String uri, String localName, String qName, Attributes attributes) {
												    	if (qName.equalsIgnoreCase("h1")) {
												    		isParsingH1 = true;
												    		buffer = new StringBuffer();
												    	} else if (qName.equalsIgnoreCase("h2")) {
												    		isParsingH2 = true;
												    		buffer = new StringBuffer();
												    	} else if (qName.equalsIgnoreCase("h3")) {
												    		isParsingH3 = true;
												    		buffer = new StringBuffer();
												    	}
												    }
									
												    @Override
												    public void characters(char[] chars, int i, int i1) throws SAXException {
												        if (isParsingH1 || isParsingH2 || isParsingH3)
												        	buffer.append(new String(chars, i, i1));
												    }
									
												    @Override
												    public void endElement(String uri, String localName, String qName) throws SAXException {
												    	if (qName.equalsIgnoreCase("h1")) {
												    		isParsingH1 = false;
												    		if((buffer.length() > 0) && StringUtils.isNotBlank(buffer.toString()))
												    			h1Contents.add(buffer.toString());
												    	} else if (qName.equalsIgnoreCase("h2")) {
								            				isParsingH2 = false;
								            				if((buffer.length() > 0) && StringUtils.isNotBlank(buffer.toString()))
								            					h2Contents.add(buffer.toString());
								            			} else if (qName.equalsIgnoreCase("h3")) {
								            				isParsingH3 = false;
								            				if((buffer.length() > 0) && StringUtils.isNotBlank(buffer.toString()))
								            					h3Contents.add(buffer.toString());
								            			}
												    }
												});
		
		return new ParserResults(headerContents, Duration.between(start, Instant.now()).toMillis());
	}
	
}
