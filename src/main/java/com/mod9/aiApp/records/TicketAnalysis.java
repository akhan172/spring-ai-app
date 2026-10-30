package com.mod9.aiApp.records;

public record TicketAnalysis(
        String category,
        String priority,
        String summary,
        String suggestedActions
) {}
