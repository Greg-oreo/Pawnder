package edu.utsa.cs3443.pawnder;

import java.util.ArrayList;
import java.util.List;

public class MatchManager {
    private static MatchManager instance;
    private List<Match> matchList = new ArrayList<>();

    private MatchManager() {}

    public static MatchManager getInstance() {
        if (instance == null) {
            instance = new MatchManager();
        }
        return instance;
    }

    public void addMatch(Match match) {
        matchList.add(match);
    }

    public List<Match> getMatchList() {
        return matchList;
    }
}
