package com.rnt.test_passing.util.impl;

import static java.util.Objects.isNull;

import com.rnt.test_passing.exception.SourceConnectException;
import com.rnt.test_passing.util.SourceFileDescriptor;
import com.rnt.test_passing.util.DescriptorProvider;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DescriptorProviderHandler {
  private List<String> basePaths;
  private final Set<DescriptorProvider> helpers;

  public DescriptorProviderHandler(Set<DescriptorProvider> helpers) {
    this.helpers = helpers;
  }

  public Set<SourceFileDescriptor> getFinalSourceFileDescriptors() {
    for(DescriptorProvider helper: helpers) {
      if(!isNull(helper.getFinalSourceFileDescriptors(basePaths))) {
        return helper.getFinalSourceFileDescriptors(basePaths);
      }
    }
    throw new SourceConnectException("Folders are empty");
  }

  public SourceFileDescriptor getSourceFileDescriptorByFileName(String name) {
    for(DescriptorProvider helper: helpers) {
      if(!isNull(helper.getSourceFileDescriptorByFileName(name, basePaths))) {
        return helper.getSourceFileDescriptorByFileName(name, basePaths);
      }
    }
    throw new SourceConnectException("Can't find a test");
  }

  public InputStream openSourceFileDescriptorStream(String name) throws FileNotFoundException {
    for(DescriptorProvider helper: helpers) {
      if(!isNull(helper.openSourceFileDescriptorStream(name, basePaths))) {
        return helper.openSourceFileDescriptorStream(name, basePaths);
      }
    }
    throw new SourceConnectException("Test was not found");
  }

  @Autowired
  public void setBasePaths(@Value("${application.path}") List<String> basePaths) {
    this.basePaths = basePaths;
  }
}
