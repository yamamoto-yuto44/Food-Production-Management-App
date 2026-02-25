package com.example.foodapp.entity;

public enum ChangeType {
	
	INITIAL("初期在庫"),
    RECEIVE("入荷"),
    CONSUME("消費"),
    ADJUST("在庫調整");
	
	private final String label;

    ChangeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
