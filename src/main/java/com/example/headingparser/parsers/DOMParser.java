package com.example.headingparser.parsers;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.headingparser.dto.ParserResults;

public class DOMParser {
	
	public static ParserResults analyseHeadings(String pageUrl) throws IOException {
		final Instant start = Instant.now();
		final Document doc = Jsoup.connect(pageUrl).get();
		ParserResults domPrsResults = new ParserResults();
		domPrsResults.getHeaderContents().put("h1", doc.select("h1").eachText());
		domPrsResults.getHeaderContents().put("h2", doc.select("h2").eachText());
		domPrsResults.getHeaderContents().put("h3", doc.select("h3").eachText());
		domPrsResults.setTimeTakenToComplete(Duration.between(start, Instant.now()).toMillis());
		return domPrsResults;
	}
	
}
