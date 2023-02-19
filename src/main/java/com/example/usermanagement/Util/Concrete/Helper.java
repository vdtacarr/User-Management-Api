package com.example.usermanagement.Util.Concrete;

import com.example.usermanagement.Util.Absract.IHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Component
public class Helper<T> implements IHelper<T> {
    public List<T> iteratorToList(Iterable<T> iterable){
         List<T> actualList = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
         return actualList;
    }
}
