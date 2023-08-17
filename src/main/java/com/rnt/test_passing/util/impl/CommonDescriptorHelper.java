package com.rnt.test_passing.util.impl;

import static java.util.Objects.isNull;

import com.rnt.test_passing.exception.SourceConnectException;
import com.rnt.test_passing.util.SourceFileDescriptor;
import com.rnt.test_passing.util.DescriptorHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonDescriptorHelper implements DescriptorHelper {
  @Value("${application.path}")
  private final List<String> basePaths;
  private static final String RESOURCE_PATH = "tests";

  public CommonDescriptorHelper(List<String> basePaths){
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
    return descriptors.stream()
        .filter(descriptor -> descriptor.getFileName().equals(name))
        .findFirst()
        .orElseThrow(() -> new SourceConnectException("Can't find test with this name"));
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
          return new FileInputStream(file.getAbsoluteFile());
        }
      }
    }
    throw new FileNotFoundException(String.format("File %s not found", name));
  }

  private Set<SourceFileDescriptor> getTestNamesFromResources() {
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      URL resource = classLoader.getResource(RESOURCE_PATH);
      if (isNull(resource)) {
        throw new SourceConnectException("Can't find internal tests");
      }
      return getTestNames(resource);
    } catch (IOException | URISyntaxException e) {
      throw new SourceConnectException("There is no such test", e);
    }
  }

  private Set<SourceFileDescriptor> getTestNamesFromExternal(String path) {
    return Stream.of(path)
        .map(File::new)
        .map(File::listFiles)
        .filter(Objects::nonNull)
        .flatMap(Arrays::stream)
        .filter(File::isFile)
        .map(f -> new SourceFileDescriptor(f.getName(), false))
        .collect(Collectors.toSet());
  }

  private Set<SourceFileDescriptor> getTestNames(URL resource) throws URISyntaxException, IOException {
    try (Stream<Path> pathStream = Files.walk(Paths.get(resource.toURI()))) {
      return pathStream
          .filter(Files::isRegularFile)
          .map(file -> new SourceFileDescriptor(file.getFileName().toString(), true))
          .collect(Collectors.toSet());
    }
  }
}
