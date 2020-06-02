package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class TextUtil {
	static DecimalFormat INTEGER_FORMATTER = new DecimalFormat("#,###");
	static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,###.#");
	private static final Set<String> DISALLOWED_HTML_TAGS = new HashSet<String>(
			Arrays.asList(HTMLElementName.PRE, HTMLElementName.CODE, HTMLElementName.BR, HTMLElementName.P));

	

	public static boolean isBlankOrNull(String text) {
		if (text == null || text.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	

	public static boolean isEmptyText(String text) {
		return (text == null) || text.isEmpty();
	}

	public static String getReadableIntegerNumber(long number) {
		return INTEGER_FORMATTER.format(number);
	}

	public static String getReadableFloatNumber(double number) {
		return INTEGER_FORMATTER.format(number);
	}

	public static void printSetsDiff(Set<String> setA, Set<String> setB, String setAName, String setBName) {
		boolean isSetsMatched = true;
		int diffCount = 0;
		for (String element : setA) {
			if (!setB.contains(element)) {
				System.out.println(setBName + " missing element [" + element + "]");
				isSetsMatched = false;
				diffCount++;
			}
		}

		for (String element : setB) {
			if (!setA.contains(element)) {
				System.out.println(setAName + " missing element [" + element + "]");
				isSetsMatched = false;
				diffCount++;
			}
		}

		System.out.println("The two sets [" + setAName + "] and [" + setBName + "] are matched result = ["
				+ isSetsMatched + "] with [" + diffCount + "] differences.");

	}

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

	public static void main(String[] args) {
		HashSet<String> setA = new HashSet<String>();
		HashSet<String> setB = new HashSet<String>();
		setA.add("A");
		setA.add("B");
		setB.add("B");
		setB.add("C");

		printSetsDiff(setA, setB, "setAName", "setBName");
	}
	
	public static Set<String> convertStringToSet(String names){
		Set<String> wordList = new HashSet<String>();
		String words[] = names.split("-");
		for (String word : words) {
			if (word.trim().length() <= 0) {
				continue;
			}
			wordList.add(word.trim());
		}
		return wordList;
	}
	
	public static String setToString(Set<String>data){
		String conversionString = "";
		int total = 0;
		for (String d : data) {
			++total;
			conversionString += d;
			if (total < data.size()) {
				conversionString += "-";
			}
		}

		return conversionString;
	}
}
