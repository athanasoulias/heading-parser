package com.example.headingparser.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParserResults {

	private Map<String, List<String>> headerContents = new HashMap<>();
	private long timeTakenToComplete;
	
}
