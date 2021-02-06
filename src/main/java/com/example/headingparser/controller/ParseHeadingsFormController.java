package com.example.headingparser.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.example.headingparser.dto.ParsingDto;
import com.example.headingparser.parsers.DOMParser;
import com.example.headingparser.parsers.SAXParser;

@Controller
public class ParseHeadingsFormController {

	@GetMapping("/")
	public String initialForm(Model model) {
		model.addAttribute("parsingDto", new ParsingDto());
		return "parseHeadingsForm";
	}

	@PostMapping("/")
	public String parseRequest(@ModelAttribute ParsingDto parsingDto, Model model) throws IOException, SAXException {
		parsingDto.setSaxParserResults(SAXParser.analyseHeadings(parsingDto.getPageUrl()));
		parsingDto.setDomParserResults(DOMParser.analyseHeadings(parsingDto.getPageUrl()));
		return "parseResults";
	}
	
	@ExceptionHandler(Exception.class)
	  public ModelAndView handleError(HttpServletRequest req, Exception ex) {
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", ex);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("error");
	    return mav;
	  }

}
