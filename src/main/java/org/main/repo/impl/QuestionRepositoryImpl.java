package org.main.repo.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.main.entity.Question;
import org.main.entity.dto.QuestionDTO;
import org.main.exception.TestReadingException;
import org.main.repo.QuestionRepository;
import org.main.util.PathProvider;

public class QuestionRepositoryImpl implements QuestionRepository {

  private final PathProvider pathProvider;

  public QuestionRepositoryImpl(PathProvider pathProvider) {
    this.pathProvider = pathProvider;
  }

  @Override
  public List<Question> findQuestionsByName(String testName) throws TestReadingException {
    String path = pathProvider.getPath(testName);
    if(path.startsWith("tests")){
      return getQuestionsFromResources(path);
    }else {
      return getQuestionsFromExternal(path);
    }
  }

  @Override
  public List<String> getTestNames() throws TestReadingException {
    return pathProvider.getAllTestNames();
  }

  private List<Question> getQuestionsFromResources(String testName) throws TestReadingException {
    ClassLoader classLoader = getClass().getClassLoader();
    List<QuestionDTO> result;
    try (InputStream inputStream = classLoader.getResourceAsStream(testName);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
      CsvToBean<QuestionDTO> csvReader = new CsvToBeanBuilder<QuestionDTO>(reader)
          .withType(QuestionDTO.class)
          .withSeparator(',')
          .withIgnoreLeadingWhiteSpace(true)
          .withIgnoreEmptyLine(true)
          .build();

      result = csvReader.parse();

    } catch (IOException e) {
      throw new TestReadingException("Wrong input", e);
    }

    return dtoToEntity(result);
  }

  private List<Question> getQuestionsFromExternal(String path) throws TestReadingException {
    List<QuestionDTO> result;
    try (Reader reader = new FileReader(path)) {
      CsvToBean<QuestionDTO> csvReader = new CsvToBeanBuilder<QuestionDTO>(reader)
          .withType(QuestionDTO.class)
          .withSeparator(',')
          .withIgnoreLeadingWhiteSpace(true)
          .withIgnoreEmptyLine(true)
          .build();

      result = csvReader.parse();

    } catch (IOException e) {
      throw new TestReadingException("Wrong input", e);
    }

    return dtoToEntity(result);
  }

  private List<Question> dtoToEntity(List<QuestionDTO> dtoList) throws TestReadingException {
    List<Question> questionList = new ArrayList<>();
    try{
      for (QuestionDTO questionDTO : dtoList) {
        Question question = new Question();
        question.setIssue(questionDTO.getIssue());
        question.getOption().getOptions().put(questionDTO.getOptions().get(0), true);
        for (int i = 1; i < questionDTO.getOptions().size(); i++) {
          question.getOption().getOptions().put(questionDTO.getOptions().get(i), false);
        }
        questionList.add(question);
      }
    }catch (ArrayIndexOutOfBoundsException e){
      throw new TestReadingException("Can't convert DTO to Object", e);
    }

    return questionList;
  }

}
