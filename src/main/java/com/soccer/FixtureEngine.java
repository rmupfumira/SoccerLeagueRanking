package com.soccer;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixtureEngine {

    static final int TIE_POINTS = 1;
    static final int WIN_POINTS = 3;

    @Getter
    @Setter
    List<Team> teamsThatPlayed = new ArrayList<>();

    @Getter
    @Setter
    private List<Integer> scores = new ArrayList<Integer>();

    public void recordWin(Team team) {
        team.incrementPoints(WIN_POINTS);
    }

    public void recordTie(Team team) {
        team.incrementPoints(TIE_POINTS);
    }

    public Team getWinner(int score1, int score2) {
        if (score1 > score2) {
            return this.getTeamsThatPlayed().get(0);
        } else {
            return this.getTeamsThatPlayed().get(1);
        }
    }

    public void recordPoints() {
        int score1 = this.getScores().get(0);
        int score2 = this.getScores().get(1);
        if (score1 == score2) {
            this.recordTie(this.getTeamsThatPlayed().get(0));
            this.recordTie(this.getTeamsThatPlayed().get(1));
        } else {
            this.recordWin(this.getWinner(score1, score2));
        }
    }


    public void processResultPerLine(String line) {

        for (String splitString : line.trim().split(",")) {

            for (String teamName : splitString.trim().split("[\\S+.*?]\\s+\\d+\\s*$")) {
                Team team = new Team(teamName);
                this.teamsThatPlayed.add(team);
            }
            Matcher matcher = Pattern.compile("[\\d+]\\s*$").matcher(splitString);
            while (matcher.find()) {
                this.scores.add(Integer.parseInt(matcher.group(0)));
            }
        }
    }

}
