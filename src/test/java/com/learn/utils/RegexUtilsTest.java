package com.learn.utils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class RegexUtilsTest {
	@Test
	public void testWebScraping() {
		String input = "<span>Exemplo</span>\n<span> </span> e também <span>Red Bull</span>.";

		final String[] expectedAnwser = { "Exemplo", "Red Bull" };
		String[] anwser = RegexUtils.filterAsList(input, ">([^<>\\s]+)<").toArray(new String[2]);
		assertArrayEquals(expectedAnwser, anwser);
	}
}
