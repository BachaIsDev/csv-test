package com.rnt.test_passing.util.impl;

import static java.util.Objects.isNull;

import com.rnt.test_passing.util.SourceFileDescriptor;
import com.rnt.test_passing.util.DescriptorHelper;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DescriptorHelperHandler implements DescriptorHelper {

  private final CommonDescriptorHelper commonDescriptorHelper;
  private final JarDescriptorHelper jarDescriptorHelper;

  public DescriptorHelperHandler(CommonDescriptorHelper commonDescriptorHelper,
      JarDescriptorHelper jarDescriptorHelper) {
    this.commonDescriptorHelper = commonDescriptorHelper;
    this.jarDescriptorHelper = jarDescriptorHelper;
  }

  @Override
  public Set<SourceFileDescriptor> getFinalSourceFileDescriptors() {
    if (!isNull(commonDescriptorHelper.getFinalSourceFileDescriptors())) {
      return commonDescriptorHelper.getFinalSourceFileDescriptors();
    }
    return jarDescriptorHelper.getFinalSourceFileDescriptors();
  }

  @Override
  public SourceFileDescriptor getSourceFileDescriptorByFileName(String name) {
    if (!isNull(commonDescriptorHelper.getSourceFileDescriptorByFileName(name))) {
      return commonDescriptorHelper.getSourceFileDescriptorByFileName(name);
    }
    return jarDescriptorHelper.getSourceFileDescriptorByFileName(name);  }

  @Override
  public InputStream openSourceFileDescriptorStream(String name) throws FileNotFoundException {
    if (!isNull(commonDescriptorHelper.openSourceFileDescriptorStream(name))) {
      return commonDescriptorHelper.openSourceFileDescriptorStream(name);
    }
    return jarDescriptorHelper.openSourceFileDescriptorStream(name);
  }
}
