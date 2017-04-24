package com.fellowtraveler.service.storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final String localPath;
    private final Path applicationRoot;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getPathToStore(properties.getLocation()));
        this.localPath = properties.getLocation();
        this.applicationRoot = Paths.get(properties.getPathToStore(""));
    }

    @Override
    @Async
    public Future<String> store(MultipartFile file) {
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }


            Path storageLocation = this.rootLocation.resolve(filename);

            Files.copy(file.getInputStream(), storageLocation);

//
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            Files.setPosixFilePermissions(this.rootLocation.resolve(filename), perms);
//

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        } finally {
            return new AsyncResult<>("/fellowTraveler_war_exploded1" + localPath + "/" + filename);
        }
    }


    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteOne(String fileName) {
        try {

            Files.deleteIfExists(Paths.get(applicationRoot.toString() + fileName));
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not read file: " + rootLocation, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
