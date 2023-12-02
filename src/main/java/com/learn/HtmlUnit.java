package com.learn;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.learn.utils.PrintUtils;
import com.learn.utils.RegexUtils;

public class HtmlUnit {
	// Pega os anos em que o Atlético MG foi campeão do campeonato mineiro ainda
	// quando era amador
	public static void main(String[] args) {
		final String url = "https://pt.wikipedia.org/wiki/Campeonato_Mineiro_de_Futebol";

		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		HtmlTable table = getChampionsTable(webClient, url, ".//table[@class='wikitable sortable']");

		if (table == null) {
			PrintUtils.error("Tabela não encontrada.");
			return;
		}

		List<HtmlTableRow> rows = table.getByXPath((".//tbody/tr"));
		rows = rows.subList(1, rows.size());
		if (rows.isEmpty()) {
			PrintUtils.error("Tabela não contém nenhum registro.");
			return;
		}

		Pattern atleticoPattern = RegexUtils.getPattern("\\bAtlético\\b");
		for (HtmlTableRow row : rows) {
			printCellWhenMatchesPattern(row, 1, 2, atleticoPattern);
		}
	}

	private static HtmlTable getChampionsTable(WebClient webClient, String pageUrl, String tableXPathExpr) {
		try {
			HtmlPage page = webClient.getPage(pageUrl);
			return page.getFirstByXPath(tableXPathExpr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void printCellWhenMatchesPattern(HtmlTableRow row, int printCellIndex, int patternCellIndex,
			Pattern pattern) {
		List<HtmlTableCell> cells = row.getCells();
		if (cells.size() < Math.max(printCellIndex, patternCellIndex) + 1) {
			PrintUtils.error("Célula não contém o index: " + Math.max(printCellIndex, patternCellIndex));
			return;
		}
		String championCellText = row.getCell(patternCellIndex).asNormalizedText();
		if (pattern.matcher(championCellText).find()) {
			String yearCellText = row.getCell(printCellIndex).asNormalizedText();
			PrintUtils.info(yearCellText);
		}
	}
}
