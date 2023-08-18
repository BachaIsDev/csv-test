package com.rnt.test_passing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public interface DescriptorHelper {
  String RESOURCE_PATH = "tests";
  // Грузит имена файлов из ресурсов и из внешней папки. Убирает те, что из ресурсов если есть такие во внешней папке
  Set<SourceFileDescriptor> getFinalSourceFileDescriptors(List<String> basePaths);
  // Внутри использует getFinalSourceFileDescriptors, а потом ищет в результате дескриптор с нужным именем файла
  SourceFileDescriptor getSourceFileDescriptorByFileName(String name, List<String> basePaths);
  default InputStream openSourceFileDescriptorStream(String name, List<String> basePaths) throws FileNotFoundException {
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
