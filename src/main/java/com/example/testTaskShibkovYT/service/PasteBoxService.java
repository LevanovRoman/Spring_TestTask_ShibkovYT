package com.example.testTaskShibkovYT.service;

import com.example.testTaskShibkovYT.api.request.PasteBoxRequest;
import com.example.testTaskShibkovYT.api.response.PasteBoxResponse;
import com.example.testTaskShibkovYT.api.response.PasteBoxUrlResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBoxes();
    PasteBoxUrlResponse create(PasteBoxRequest request);
}
