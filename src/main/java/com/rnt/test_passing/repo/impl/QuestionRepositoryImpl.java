package com.rnt.test_passing.repo.impl;

import static java.util.Objects.isNull;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rnt.test_passing.converter.impl.QuestionDtoListConverter;
import com.rnt.test_passing.exception.SourceConnectException;
import com.rnt.test_passing.util.impl.DescriptorProviderHandler;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.dto.QuestionDTO;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.util.SourceFileDescriptor;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {
  private final DescriptorProviderHandler descriptorProviderHandler;
  private final QuestionDtoListConverter converter;

  public QuestionRepositoryImpl(DescriptorProviderHandler descriptorProviderHandler,
      QuestionDtoListConverter converter) {
    this.descriptorProviderHandler = descriptorProviderHandler;
    this.converter = converter;
  }

  @Override
  public List<Question> findQuestionsByTestName(String testName) throws TestReadingException {
    try {
      InputStream inputStream = descriptorProviderHandler.openSourceFileDescriptorStream(testName);
      return getQuestions(inputStream);
    } catch (SourceConnectException | FileNotFoundException e) {
      throw new TestReadingException("File not found", e);
    }
  }

  @Override
  public List<String> getQuestionTestNames() throws TestReadingException {
    try {
      return descriptorProviderHandler.getFinalSourceFileDescriptors().stream()
          .map(SourceFileDescriptor::getFileName)
          .toList();
    } catch (SourceConnectException e) {
      throw new TestReadingException("Error during reading from source", e);
    }

  }

  private List<Question> getQuestions(InputStream inputStream) throws TestReadingException {
    if(isNull(inputStream)) {
      throw new TestReadingException("Can't find a file");
    }
    try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
      return readQuestions(reader);
    } catch (IOException e) {
      throw new TestReadingException("Invalid input parameters", e);
    }
  }

  private List<Question> readQuestions(Reader reader) throws TestReadingException {
    CsvToBean<QuestionDTO> csvReader = new CsvToBeanBuilder<QuestionDTO>(reader)
        .withType(QuestionDTO.class)
        .withSeparator(',')
        .withIgnoreLeadingWhiteSpace(true)
        .withIgnoreEmptyLine(true)
        .build();

    List<QuestionDTO> questionDtoList = csvReader.parse();
    return converter.convert(questionDtoList);
  }
}
