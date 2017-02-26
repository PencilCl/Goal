package cn.goal.goal.services.object;

import java.util.ArrayList;

/**
 * Created by chenlin on 25/02/2017.
 */
public class Analyse {
    private ArrayList<GoalsFinishedRecord> goalsFinishedRecord;
    private int goalsCreated;
    private int goalsDoing;
    private int goalsFinished;
    private int goalsUnfinished;

    public Analyse(ArrayList<GoalsFinishedRecord> goalsFinishedRecord, int goalsCreated, int goalsDoing, int goalsFinished, int goalsUnfinished) {
        this.goalsFinishedRecord = goalsFinishedRecord;
        this.goalsCreated = goalsCreated;
        this.goalsDoing = goalsDoing;
        this.goalsFinished = goalsFinished;
        this.goalsUnfinished = goalsUnfinished;
    }

    public ArrayList<GoalsFinishedRecord> getGoalsFinishedRecord() {
        return goalsFinishedRecord;
    }

    public int getGoalsCreated() {
        return goalsCreated;
    }

    public int getGoalsDoing() {
        return goalsDoing;
    }

    public int getGoalsFinished() {
        return goalsFinished;
    }

    public int getGoalsUnfinished() {
        return goalsUnfinished;
    }
}