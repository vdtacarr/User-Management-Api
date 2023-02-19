package com.example.usermanagement.Util.Absract;

import java.util.List;

public interface IHelper<T> {
  public List<T> iteratorToList(Iterable<T> iterable);
}
