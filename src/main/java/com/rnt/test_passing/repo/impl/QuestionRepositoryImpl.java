package com.rnt.test_passing.repo.impl;

import static java.util.Objects.isNull;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.dto.QuestionDTO;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.util.SourceFileDescriptor;
import com.rnt.test_passing.util.SourceFileDescriptorHelper;

public class QuestionRepositoryImpl implements QuestionRepository {
  private final SourceFileDescriptorHelper sourceFileDescriptorHelper;

  public QuestionRepositoryImpl(SourceFileDescriptorHelper sourceFileDescriptorHelper) {
    this.sourceFileDescriptorHelper = sourceFileDescriptorHelper;
  }

  @Override
  public List<Question> findQuestionsByTestName(String testName) throws TestReadingException {
    try {
      InputStream inputStream = sourceFileDescriptorHelper.openSourceFileDescriptorStream(testName);
      return getQuestions(inputStream);
    } catch (TestReadingException | FileNotFoundException | NullPointerException e){
      throw new TestReadingException("File not found", e);
    }
  }

  @Override
  public List<String> getQuestionTestNames() throws TestReadingException {
    return sourceFileDescriptorHelper.getFinalSourceFileDescriptors().stream()
        .map(SourceFileDescriptor::getFileName)
        .toList();
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

    List<QuestionDTO> result;
    result = csvReader.parse();

    return dtoToEntity(result);
  }

  private List<Question> dtoToEntity(List<QuestionDTO> dtoList) throws TestReadingException {
    try{
      return dtoList.stream()
          .map(dto -> {
            List<Option> resultOption = dto.getOptions().stream()
                .map(this::stringToOption).toList();
            return new Question(dto.getIssue(), resultOption);
          }).toList();
      } catch (IndexOutOfBoundsException e){
      throw new TestReadingException("Can't convert DTO to Object", e);
    }
  }

  private Option stringToOption(String option) {
    boolean isRight = option.contains("right");
    String[] parts = option.split(",");
    return new Option(parts[0], isRight);
  }
}
