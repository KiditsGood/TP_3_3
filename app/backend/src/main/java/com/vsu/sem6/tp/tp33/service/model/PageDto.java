package com.vsu.sem6.tp.tp33.service.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface PageDto<T> {



  List<T> getItems();

  int getPageNumber();

  int getTotalPages();
}
