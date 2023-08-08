package com.rnt.test_passing.util.impl;

import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.util.SourceFileDescriptor;
import com.rnt.test_passing.util.SourceFileDescriptorHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SourceFileDescriptorHelperImpl implements SourceFileDescriptorHelper {

  private final List<String> basePaths;
  private static final String RESOURCE_PATH = "tests";

  public SourceFileDescriptorHelperImpl(List<String> basePaths){
    this.basePaths = basePaths;
  }

  @Override
  public Set<SourceFileDescriptor> getFinalSourceFileDescriptors() {
    Set<SourceFileDescriptor> descriptors = new HashSet<>(getTestNamesFromResources());
    for(String basePath: basePaths){
      descriptors.addAll(getTestNamesFromExternal(basePath));
    }

    return descriptors;
  }

  @Override
  public SourceFileDescriptor getSourceFileDescriptorByFileName(String name) {
    Set<SourceFileDescriptor> descriptors = getFinalSourceFileDescriptors();
    return descriptors.stream().filter(descriptor ->
            descriptor.getFileName().equals(name))
        .findFirst().orElseThrow(() -> new TestReadingException("can't find test with this name"));
  }

  @Override
  public InputStream openSourceFileDescriptorStream(String name) throws FileNotFoundException {
    SourceFileDescriptor descriptor = getSourceFileDescriptorByFileName(name);
    if(descriptor.isFromResources()) {
      return getClass().getClassLoader().getResourceAsStream(RESOURCE_PATH + "/" + name);
    } else {
      for(String basePath: basePaths){
        File file = Paths.get(basePath, name).toFile();
        if(file.exists()){
          return new FileInputStream(basePath + name);
        }
      }
    }
    throw new FileNotFoundException(String.format("File %s not found", name));
  }

  private Set<SourceFileDescriptor> getTestNamesFromResources() {

    Set<SourceFileDescriptor> result;

    ClassLoader classLoader = getClass().getClassLoader();

    try {
      URI uri = classLoader.getResource("tests").toURI();

      if (uri.getScheme().equals("jar")) {
        result = getTestNamesFromJar();
      } else {
        result = getTestNames();
      }
    } catch (IOException | URISyntaxException e) {
      throw new TestReadingException("There is no such test", e);
    }

    return result;
  }

  private Set<SourceFileDescriptor> getTestNamesFromExternal(String path) {

    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    Set<SourceFileDescriptor> fileNames = new HashSet<>();
    if (listOfFiles == null) {
      return fileNames;
    }
    for (File file : listOfFiles) {
      if (file.isFile()) {
        SourceFileDescriptor descriptor =
            new SourceFileDescriptor(file.getName(), false);
        fileNames.add(descriptor);
      }
    }

    return fileNames;
  }

  private Set<SourceFileDescriptor> getTestNames() throws URISyntaxException, IOException {
    Set<SourceFileDescriptor> result;

    ClassLoader classLoader = getClass().getClassLoader();

    URL resource = classLoader.getResource(RESOURCE_PATH);
    result = Files.walk(Paths.get(resource.toURI()))
        .filter(Files::isRegularFile)
        .map(file -> new SourceFileDescriptor(file.getFileName().toString(), true))
        .collect(Collectors.toSet());

    return result;
  }

  private Set<SourceFileDescriptor> getTestNamesFromJar() throws URISyntaxException, IOException {
    Set<SourceFileDescriptor> result;

    String jarPath = getClass().getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath();

    // file walks JAR
    URI uri = URI.create("jar:file:" + jarPath);
    try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
      result = Files.walk(fs.getPath(RESOURCE_PATH))
          .filter(Files::isRegularFile)
          .map(file -> new SourceFileDescriptor(file.getFileName().toString(), true))
          .collect(Collectors.toSet());
    }
    return result;
  }

  private boolean isJar() {
    ClassLoader classLoader = getClass().getClassLoader();
    URI uri = null;
    try {
      uri = classLoader.getResource("tests").toURI();
    } catch (URISyntaxException e) {
      throw new TestReadingException("No such test", e);
    }

    return uri.getScheme().equals("jar");
  }

}
