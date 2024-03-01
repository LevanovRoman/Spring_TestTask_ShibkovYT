package com.example.testTaskShibkovYT.api.response;

import com.example.testTaskShibkovYT.api.request.PublicStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasteBoxResponse {
    private final String data;
    private final boolean isPublic;
}
