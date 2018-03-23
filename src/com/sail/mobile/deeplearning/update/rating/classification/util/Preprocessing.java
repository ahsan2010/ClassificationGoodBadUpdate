package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class Preprocessing {

	private static final Set<String> LINK_TAGS = new HashSet<String>(
			Arrays.asList(HTMLElementName.A, HTMLElementName.LINK));

	private static final Set<String> CODE_TAGS = new HashSet<String>(
			Arrays.asList(HTMLElementName.CODE, HTMLElementName.PRE));
	private static final Set<String> DISALLOWED_HTML_TAGS = new HashSet<String>(Arrays.asList(HTMLElementName.LINK));

	public static ArrayList<String> timeX = null;

	
	
	private static OutputDocument removeNotAllowedTags(Source source) {
		
		OutputDocument outputDocument = new OutputDocument(source);
		List<Element> elements = source.getAllElements();

		for (Element element : elements) {

			if (DISALLOWED_HTML_TAGS.contains(element.getName())) {
				outputDocument.remove(element);
			}

		}

		return outputDocument;
	}

	public static String htmlRemove(String body) {
		String result = "";
		Source htmlSource = new Source(body);
		List<StartTag> sTag = new ArrayList<StartTag>();
		htmlSource.getAllStartTags();
		OutputDocument outputDocument = removeNotAllowedTags(htmlSource);
		Source source = new Source(outputDocument.toString());
		Segment htmlSeg = new Segment(source, 0, outputDocument.toString().length());

		Renderer htmlRend = new Renderer(htmlSeg);

		return htmlRend.toString();
	}

}
	