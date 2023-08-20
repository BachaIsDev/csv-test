package com.rnt.test_passing.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface DescriptorProvider {

  // Грузит имена файлов из ресурсов и из внешней папки. Убирает те, что из ресурсов если есть такие во внешней папке
  Set<SourceFileDescriptor> getFinalSourceFileDescriptors(List<String> basePaths);
  // Внутри использует getFinalSourceFileDescriptors, а потом ищет в результате дескриптор с нужным именем файла
  SourceFileDescriptor getSourceFileDescriptorByFileName(String name, List<String> basePaths);
  InputStream openSourceFileDescriptorStream(String name, List<String> basePaths) throws FileNotFoundException;
}
