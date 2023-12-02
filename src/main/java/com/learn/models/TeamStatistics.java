package com.learn.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.learn.utils.PrintUtils;

public class TeamStatistics {
	private String teamName;
	private int score;
	private int games;
	private int wins;
	private int draws;
	private int loses;
	private int goalsScored;
	private int goalsTaken;
	private int goalsDifference;
	private double efficiency;

	public TeamStatistics(String name, int score, int games, int wins, int draws, int loses, int goalsScored,
			int goalsTaken, int goalsDifference, double efficiency) {
		this.teamName = name;
		this.score = score;
		this.games = games;
		this.wins = wins;
		this.draws = draws;
		this.loses = loses;
		this.goalsScored = goalsScored;
		this.goalsTaken = goalsTaken;
		this.goalsDifference = goalsDifference;
		this.efficiency = efficiency;
	}

	public int getScore() {
		return this.score;
	}

	public int getWins() {
		return this.wins;
	}

	public int getGoalsDifference() {
		return this.goalsDifference;
	}

	public int getGoalsScored() {
		return this.goalsScored;
	}

	public static List<TeamStatistics> fromHashMap(Map<String, List<String>> teamsInfo) {
		List<TeamStatistics> teamStatistics = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : teamsInfo.entrySet()) {
			List<String> teamInfo = entry.getValue();
			if (teamInfo.size() != 9) {
				PrintUtils.error("Estatísticas de " + entry.getKey() + " faltando informação");
				continue;
			}
			try {
				teamStatistics.add(new TeamStatistics(
						entry.getKey(),
						Integer.parseInt(teamInfo.get(0)),
						Integer.parseInt(teamInfo.get(1)),
						Integer.parseInt(teamInfo.get(2)),
						Integer.parseInt(teamInfo.get(3)),
						Integer.parseInt(teamInfo.get(4)),
						Integer.parseInt(teamInfo.get(5)),
						Integer.parseInt(teamInfo.get(6)),
						Integer.parseInt(teamInfo.get(7)),
						Double.parseDouble(teamInfo.get(8))));
			} catch (NumberFormatException e) {
				PrintUtils.error("Erro ao converter pontuação para inteiro. Message: " + e.getMessage());
				return null;
			}
		}
		return teamStatistics;
	}

	public void printStatistics() {
		String print = "";

		print += String.format("%" + 20 + "s", this.teamName);
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.score));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.games));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.wins));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.draws));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.loses));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.goalsScored));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.goalsTaken));
		print += "| " + String.format("%" + 3 + "s", Integer.toString(this.goalsDifference));
		print += "| " + String.format("%" + 4 + "s", Double.toString(this.efficiency));

		PrintUtils.print(print);
	}

	public static void printTableHeader() {
		String print = "";

		print += String.format("%" + 20 + "s", "Time");
		print += "| " + String.format("%" + 3 + "s", "P");
		print += "| " + String.format("%" + 3 + "s", "J");
		print += "| " + String.format("%" + 3 + "s", "V");
		print += "| " + String.format("%" + 3 + "s", "E");
		print += "| " + String.format("%" + 3 + "s", "D");
		print += "| " + String.format("%" + 3 + "s", "GP");
		print += "| " + String.format("%" + 3 + "s", "GC");
		print += "| " + String.format("%" + 3 + "s", "SG");
		print += "| " + String.format("%" + 4 + "s", "%");

		PrintUtils.print(print);
	}

}
