package com.cpcompass.controller;

import com.cpcompass.dto.contesthistory.ContestHistoryDto;
import com.cpcompass.service.ContestHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contest-history")
public class ContestHistoryController {

    private final ContestHistoryService contestHistoryService;

    @GetMapping
    public List<ContestHistoryDto> getContestHistory() {

        return contestHistoryService.getContestHistory();
    }
}