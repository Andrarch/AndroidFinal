package com.example.andrew.androidfinal;

/**
 * This class deals with the multiple choice variable
 */
public class RobinMultipleChoiceQuestion {
    private int questType;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answerNr;

    public RobinMultipleChoiceQuestion(){}


    public RobinMultipleChoiceQuestion(int questType, String question, String option1, String option2, String option3, String option4, String answerNr) {
        this.questType = questType;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
    }

    public int getQuestionType() {
        return questType;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswerNr() {
        return answerNr;
    }

    public void setQuestType (int questType) {
        this.questType = questType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswerNr(String answerNr) {
        this.answerNr = answerNr;
    }


}

