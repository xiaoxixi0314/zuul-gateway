package com.xiaoxixi;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.Objects;
import java.util.function.Function;

public class FunctionTest {

    public <T> String toJsonStringFunction(T person, Function<T, String> personFunction) {
        String json = personFunction.apply(person);
        System.out.println(json);
        return json;
    }

    public String toJsonString(Person person) {
        if (Objects.isNull(person)) {
            return "";
        }
        return JSON.toJSONString(person);
    }

    @Test
    public void testFunction() {
        Person person = new Person();
        person.setSex("F");
        person.setAge(11);
        person.setName("less");
        toJsonStringFunction(person, to -> toJsonString(person));
    }

    @Test
    public void testFunction1(){
        Person person = new Person();
        person.setSex("F");
        person.setAge(11);
        person.setName("less");
        toJsonStringFunction(person, (person1)-> {
//            @Override
//            public String apply(Person person1) {
                return new StringBuilder().append("hello").append(person1.getName()).append(String.valueOf(person1.getAge())).toString();
//            }
        });
    }

    @Getter
    @Setter
    class Person{
        Integer age;
        String name;
        String sex;
    }
}
