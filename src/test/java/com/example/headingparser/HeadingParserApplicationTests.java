package com.example.headingparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import com.example.headingparser.parsers.DOMParser;
import com.example.headingparser.parsers.SAXParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class HeadingParserApplicationTests {

	@Test
	void compareParserResults() {
		List<String> testUrls = Arrays.asList("https://thorben-janssen.com/", "https://www.bbc.co.uk/", "https://www.peopleperhour.com/",
											  "https://www.microsoft.com/el-gr/","https://www.apple.com/");
		
		testUrls.forEach(url -> {
			try {
				final Map<String, List<String>> domParserResults = DOMParser.analyseHeadings(url).getHeaderContents();
				final Map<String, List<String>> saxParserResults = SAXParser.analyseHeadings(url).getHeaderContents();
				log.info("Parsed:" + url);
		
				assertEquals(domParserResults.get("h1").size(), saxParserResults.get("h1").size());
				assertEquals(domParserResults.get("h2").size(), saxParserResults.get("h2").size());
				assertEquals(domParserResults.get("h3").size(), saxParserResults.get("h3").size());
				assertTrue(domParserResults.get("h1").equals(saxParserResults.get("h1")));
				assertTrue(domParserResults.get("h2").equals(saxParserResults.get("h2")));
				assertTrue(domParserResults.get("h3").equals(saxParserResults.get("h3")));
			} catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		});
	}

}
