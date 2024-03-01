package com.example.testTaskShibkovYT.service;

import com.example.testTaskShibkovYT.api.request.PasteBoxRequest;
import com.example.testTaskShibkovYT.api.request.PublicStatus;
import com.example.testTaskShibkovYT.api.response.PasteBoxResponse;
import com.example.testTaskShibkovYT.api.response.PasteBoxUrlResponse;
import com.example.testTaskShibkovYT.repository.PasteBoxEntity;
import com.example.testTaskShibkovYT.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService{

    private String host = "http://pastebox.sentel.ru";
    private int publicListSize = 10;

    private final PasteBoxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {

        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes() {

        List<PasteBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);

        return list.stream().map(pasteBoxEntity ->
                    new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()))
                            .collect(Collectors.toList());
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {

        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));

        repository.add(pasteBoxEntity);

        return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId(){
        return idGenerator.getAndIncrement();
    }
}
