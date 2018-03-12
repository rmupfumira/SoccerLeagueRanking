package com.soccer;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

public class LeaderBoard {

    @Getter
    @Setter
    private List<Team> teams = new ArrayList<>();

    public void addTeam(Team team){
      for(Team t : this.getTeams()){
          if(t.getName().equalsIgnoreCase(team.getName())){
              t.incrementPoints(team.getPoints());
              return;
          }
      }
      this.getTeams().add(team);
    }

    public  void parseFixtureResultsFile(File resultsFile){

        if(!resultsFile.exists()){
            System.err.println("Error : file "+resultsFile.getName()+" cannot be found");
            System.exit(0);
        }else{
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resultsFile) , "UTF8"));
                String str;
                while (( str = in.readLine() ) != null ) {
                    FixtureEngine fixtureEngine = new FixtureEngine();
                    fixtureEngine.processResultPerLine(str);
                    fixtureEngine.recordPoints();
                    for (Team team: fixtureEngine.getTeamsThatPlayed()){
                        this.addTeam(team);
                    }
                }
                displayLeaderBoard();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }



    public  void displayLeaderBoard(){
        Collections.sort(teams, Team.TeamComparator);
        String pointsString = "";
        for(int i=0;i<teams.size();i++){
            Team t = teams.get(i);
            pointsString = t.getPoints() == 1 ? " pt" : " pts";
            System.out.println(i+1+". "+t.getName()+", "+t.getPoints()+pointsString);
        }
    }
}
