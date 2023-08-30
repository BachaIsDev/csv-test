package com.rnt.test_passing.util.impl;

import com.rnt.test_passing.util.DescriptorProvider;
import com.rnt.test_passing.util.SourceFileDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class DefaultDescriptorProvider implements DescriptorProvider {
  protected final String RESOURCE_PATH = "tests";

  @Override
  public Set<SourceFileDescriptor> getFinalSourceFileDescriptors(List<String> basePaths) {
    return null;
  }

  @Override
  public SourceFileDescriptor getSourceFileDescriptorByFileName(String name,
      List<String> basePaths) {
    return null;
  }

  @Override
  public InputStream openSourceFileDescriptorStream(String name, List<String> basePaths) throws FileNotFoundException {
    SourceFileDescriptor descriptor = getSourceFileDescriptorByFileName(name, basePaths);
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
}
