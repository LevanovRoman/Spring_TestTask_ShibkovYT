package com.example.testTaskShibkovYT.controller;

import com.example.testTaskShibkovYT.api.request.PasteBoxRequest;
import com.example.testTaskShibkovYT.api.response.PasteBoxResponse;
import com.example.testTaskShibkovYT.api.response.PasteBoxUrlResponse;
import com.example.testTaskShibkovYT.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class PasteBoxController {

    private final PasteBoxService pasteBoxService;

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash){
        return pasteBoxService.getByHash(hash);
    }

    @GetMapping("/")
    public Collection<PasteBoxResponse> getPublicPasteList(){
        return pasteBoxService.getFirstPublicPasteBoxes();
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request) {
        return pasteBoxService.create(request);
    }
}
