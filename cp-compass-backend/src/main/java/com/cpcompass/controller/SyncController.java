package com.cpcompass.controller;

import com.cpcompass.dto.sync.SyncResponse;
import com.cpcompass.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService syncService;
    @PostMapping
    public ResponseEntity<SyncResponse> sync() {

//        System.out.println("SYNC STARTED");
        SyncResponse response =
                syncService.syncCurrentUser();

//        System.out.println("SYNC FINAL END");
        return ResponseEntity.ok(response);
    }
}