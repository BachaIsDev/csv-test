package com.rnt.test_passing.repo.impl;

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
    List<Question> questions;
    InputStream inputStream = null;

    try {
      inputStream = sourceFileDescriptorHelper.openSourceFileDescriptorStream(testName);
      questions = getQuestionsFromExternal(inputStream);
    } catch (TestReadingException | FileNotFoundException e){
      questions = getQuestionsFromResources(inputStream);
    }

    return questions;
  }

  @Override
  public List<String> getQuestionTopicNames() throws TestReadingException {
    Set<SourceFileDescriptor> testNames = sourceFileDescriptorHelper.getFinalSourceFileDescriptors();
    List<String> testNamesAsList = new ArrayList<>();
    testNames.forEach(descriptor -> testNamesAsList.add(descriptor.getFileName()));

    return testNamesAsList;
  }

  private List<Question> getQuestionsFromResources(InputStream inputStream) throws TestReadingException {
    List<Question> result;

    try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
      result = readQuestions(reader);

    } catch (IOException e) {
      throw new TestReadingException("Error during reading test questions", e);
    }

    return result;
  }

  private List<Question> getQuestionsFromExternal(InputStream inputStream) throws TestReadingException {
    List<Question> result;
    try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
      result = readQuestions(reader);

    } catch (IOException e) {
      throw new TestReadingException("Invalid input parameters", e);
    }

    return result;
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
    List<Question> questionList = new ArrayList<>();
    try{
      for (QuestionDTO questionDTO : dtoList) {
        Question question = new Question();
        List<Option> options = new ArrayList<>();
        question.setIssue(questionDTO.getIssue());
        options.add(new Option(questionDTO.getOptions().get(0), true));
        for (int i = 1; i < questionDTO.getOptions().size(); i++) {
          options.add(new Option(questionDTO.getOptions().get(i), false));
        }
        question.setOptions(options);
        questionList.add(question);
      }
    }catch (IndexOutOfBoundsException e){
      throw new TestReadingException("Can't convert DTO to Object", e);
    }

    return questionList;
  }

}
