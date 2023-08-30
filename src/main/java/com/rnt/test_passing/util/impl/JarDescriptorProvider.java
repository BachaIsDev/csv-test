package com.rnt.test_passing.util.impl;

import static java.util.Objects.isNull;

import com.rnt.test_passing.exception.SourceConnectException;
import com.rnt.test_passing.util.SourceFileDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class JarDescriptorProvider extends DefaultDescriptorProvider {
  public Set<SourceFileDescriptor> getFinalSourceFileDescriptors(List<String> basePaths) {
    if (isNull(basePaths)) {
      return Set.of();
    }
    return Stream.concat(getTestNamesFromResources().stream(),
            basePaths.stream().flatMap(bp -> getTestNamesFromExternal(bp).stream()))
        .collect(Collectors.toSet());
  }

  @Override
  public SourceFileDescriptor getSourceFileDescriptorByFileName(String name, List<String> basePaths) {
    Set<SourceFileDescriptor> descriptors = getFinalSourceFileDescriptors(basePaths);
    return descriptors.stream()
        .filter(descriptor -> descriptor.getFileName().equals(name))
        .findFirst()
        .orElseThrow(() -> new SourceConnectException("Can't find test with this name"));
  }

  private Set<SourceFileDescriptor> getTestNamesFromResources() {
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      URL resource = classLoader.getResource(RESOURCE_PATH);

      if (isNull(resource)) {
        throw new SourceConnectException("Can't find internal tests");
      }

      return getTestNames();
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

  private Set<SourceFileDescriptor> getTestNames() throws URISyntaxException, IOException {
    String jarPath = getClass().getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath();

    // file walks JAR
    URI uri = URI.create("jar:file:" + jarPath);
    try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
      try (Stream<Path> pathStream = Files.walk(fs.getPath(RESOURCE_PATH))) {
        return pathStream
            .filter(Files::isRegularFile)
            .map(file -> new SourceFileDescriptor(file.getFileName().toString(), true))
            .collect(Collectors.toSet());
      }
    }
  }
}
