package com.learn.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static Pattern getPattern(String regex) {
		return Pattern.compile(regex);
	}

	public static String filter(String string, String regexExpr) {
		String patternString = regexExpr;
		Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
		// Cria um matcher correspondente ao padrão
		Matcher matcher = pattern.matcher(string);

		String response = "";
		// Enquanto houver correspondências
		while (matcher.find()) {
			// Captura o conteúdo entre as tags de tabela
			String tableContent = matcher.group(1);
			response += tableContent;
		}
		return response;
	}

	public static List<String> filterAsList(String string, String regexExpr) {
		String patternString = regexExpr;
		Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
		// Cria um matcher correspondente ao padrão
		Matcher matcher = pattern.matcher(string);

		List<String> response = new ArrayList<>();
		// Enquanto houver correspondências
		while (matcher.find()) {
			// Captura o conteúdo entre as tags de tabela
			String tableContent = matcher.group(1);
			response.add(tableContent);
		}
		return response;
	}
}
