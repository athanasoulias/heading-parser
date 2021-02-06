package com.example.headingparser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParsingDto {

	private String pageUrl;
	private ParserResults saxParserResults;
	private ParserResults domParserResults;
	
	public ParsingDto(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
}
