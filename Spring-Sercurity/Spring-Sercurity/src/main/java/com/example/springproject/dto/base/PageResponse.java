package com.example.springproject.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
// This class define a general response and paging content
*/
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content;

  /**
   *  This property is the actual number of records has been found
   */
  private int amount;

  /**
   *
   * @param data
   * @param amount
   * @return
   * @param <T>
   */

  public static <T> PageResponse<T> of(List<T> data, Integer amount) {
    return new PageResponse<>(data, Objects.isNull(amount) ? 0 : amount.intValue());
  }
}