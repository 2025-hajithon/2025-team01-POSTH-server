package com.posth.posth.domain.question.ENUM;

public enum QuestionCategory {
    STUDY("공부"),
    ROMANCE("연애"),
    CAREER("진로"),
    EMPLOYMENT("취업"),
    WORKPLACE("직장"),
    RELATIONSHIP("인간관계"),
    MARRIAGE("결혼"),
    FAMILY("가족"),
    ETC("기타"),
    SELF("자기자신");

    private final String description;

    QuestionCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}