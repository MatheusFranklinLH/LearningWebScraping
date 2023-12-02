package com.learn.utils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class RegexUtilsTest {
	@Test
	public void testRegex() {
		String input = "<span>Exemplo</span>\n<span> </span> e também <span>Red Bull</span>.";

		final String[] expectedAnwser = { "Exemplo", " e também ", "Red Bull" };
		String regexExpr = ">([^<>\t\n]*[^<>\t\n ]+[^<>\t\n]*)<";
		String[] anwser = RegexUtils.filterAsList(input, regexExpr).toArray(new String[3]);
		assertArrayEquals(expectedAnwser, anwser);
	}
}
