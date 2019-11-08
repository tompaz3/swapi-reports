package com.tp.sp.swapi.swapiclient.page;

public interface Pageable<T> {

  T value();

  Pageable<T> append(Pageable<T> other);

  String getNext();

  Class<T> type();

  Pageable<T> newPageable(T value);
}
