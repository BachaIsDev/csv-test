package org.main.repo.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.main.entity.Question;
import org.main.entity.dto.QuestionDTO;
import org.main.exception.TestException;
import org.main.repo.QuestionRepository;
import org.main.util.PathProvider;
import org.springframework.stereotype.Repository;

public class QuestionRepositoryImpl implements QuestionRepository {

  public QuestionRepositoryImpl() {

  }


  @Override
  public List<Question> getQuestions(String testName) throws TestException {
    List<QuestionDTO> result;
    try (Reader reader = new FileReader(testName) {
    }) {
      CsvToBean<QuestionDTO> csvReader = new CsvToBeanBuilder<QuestionDTO>(reader)
          .withType(QuestionDTO.class)
          .withSeparator(',')
          .withIgnoreLeadingWhiteSpace(true)
          .withIgnoreEmptyLine(true)
          .build();

      result = csvReader.parse();

    } catch (IOException e) {
      throw new TestException("Wrong input", e);
    }

    return dtoToEntity(result);
  }

  private List<Question> dtoToEntity(List<QuestionDTO> dtoList) {
    List<Question> questionList = new ArrayList<>();
    if(dtoList.isEmpty()){
      System.out.println("File is empty");
      return questionList;
    }

    for (QuestionDTO questionDTO : dtoList) {
      Question question = new Question();
      question.setIssue(questionDTO.getIssue());
      question.getOption().getOptions().put(questionDTO.getOptions().get(0), true);
      for (int i = 1; i < questionDTO.getOptions().size(); i++) {
        question.getOption().getOptions().put(questionDTO.getOptions().get(i), false);
      }
      questionList.add(question);
    }

    return questionList;
  }

}
