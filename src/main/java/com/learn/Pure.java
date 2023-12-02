package com.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.learn.models.TeamStatistics;
import com.learn.utils.PrintUtils;
import com.learn.utils.RegexUtils;

public class Pure {
	// Calcula a quantidade de pontos que o Atlético-MG precisa para empatar com o
	// líder do campeonato.
	public static void main(String[] args) {
		// Pure pure = new Pure();
		// PrintUtils.info("" + pure.getNeedScoreToDrawLeader("Atlético-MG"));
		Map<String, List<String>> brasileiraoInfo = getBrasileiraoInfo();
		List<TeamStatistics> teamsStatistics = TeamStatistics.fromHashMap(brasileiraoInfo);
		TeamStatistics.printTableHeader();
		teamsStatistics = teamsStatistics.stream()
				.sorted(Comparator.comparing(TeamStatistics::getScore).thenComparing(TeamStatistics::getWins)
						.thenComparing(TeamStatistics::getGoalsDifference).reversed())
				.collect(Collectors.toList());
		for (TeamStatistics statistics : teamsStatistics) {
			statistics.printStatistics();
		}
	}

	public int getNeedScoreToDrawLeader(String team) {
		Map<String, List<String>> brasileiraoInfo = getBrasileiraoInfo();
		if (!brasileiraoInfo.containsKey(team)) {
			PrintUtils.error(team + " não está no Brasileirão Série A");
			return -1;
		}
		String leaderPontuation = brasileiraoInfo.get("Palmeiras").get(0);
		String teamPontuation = brasileiraoInfo.get(team).get(0);
		int neededScoreToDraw = -1;

		try {
			neededScoreToDraw = Integer.parseInt(leaderPontuation) - Integer.parseInt(teamPontuation);
		} catch (NumberFormatException e) {
			PrintUtils.error("Erro ao converter pontuação para inteiro. Message: " + e.getMessage());
			return -1;
		}

		if (neededScoreToDraw < -1) {
			PrintUtils.error("Pontuação do time maior do que pontuação do líder.");
			return -1;
		}
		return neededScoreToDraw;
	}

	private static Map<String, List<String>> getBrasileiraoInfo() {
		// Primeiro passo foi usar o navegador em conjunto do postman para avaliar as
		// rotas que o site utiliza e ver qual tem os dados necessários de uma forma
		// mais fácil de tratar.
		// Rota encontrada:
		final String url = "https://www.gazetaesportiva.com/campeonatos/brasileiro-serie-a/";

		String championshipTableAsHtml = RegexUtils.filter(urlGetAsString(url),
				"<table class=\"table table-hover thead-default \">(.*?)</table>");
		return getTableInfo(championshipTableAsHtml);
	}

	private static Map<String, List<String>> getTableInfo(String string) {
		Map<String, List<String>> tableInfo = new HashMap<>(); // To O(1) access;
		String regexExpr = ">([^<>\t\n]*[^<>\t\n ]+[^<>\t\n]*)<";
		Pattern pattern = Pattern.compile(regexExpr, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(string);
		int trashCount = 0;
		int teamCount = 0;
		String teamName = "";
		List<String> teamInfo = new ArrayList<>(); // Escolha do ArrayList pelo acesso por indice O(1)
		while (matcher.find()) {
			if (trashCount < 11) {
				trashCount++;
				continue;
			}
			String tableContent = matcher.group(1);
			teamCount++;
			if (teamCount == 1) {
				continue;
			}
			if (teamCount == 2) {
				teamName = tableContent;
				continue;
			}
			teamInfo.add(tableContent);
			if (teamCount == 11) {
				tableInfo.put(teamName, teamInfo);
				teamInfo = new ArrayList<>();
				teamCount = 0;
			}
		}
		return tableInfo;
	}

	private static String urlGetAsString(String url) {
		try {
			// Crie uma conexão HTTP
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

			// Configure as propriedades da solicitação
			connection.setRequestMethod("GET");

			// Obtenha a resposta
			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// Leitura da resposta
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				// resposta HTML
				return response.toString();
			} else {
				PrintUtils.error("Falha na solicitação HTTP: " + responseCode);
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
