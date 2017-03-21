package com.fellowtraveler.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    Future<String> store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    void deleteOne(String filepath);

    Resource loadAsResource(String filename);

    void deleteAll();

}
