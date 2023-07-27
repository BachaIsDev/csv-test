package org.main.entity.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.ArrayList;
import java.util.List;
import org.main.converter.ListConverter;

public class QuestionDTO {
  @CsvBindByPosition(position = 0)
  private String issue;

  @CsvCustomBindByPosition(position = 1, converter = ListConverter.class)
  private List<String> options = new ArrayList<>();

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public List<String> getOptions() {
    return options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }
}
