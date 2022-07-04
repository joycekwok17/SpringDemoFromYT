package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    public List<Person> selectAllPeople() {
        return DB;
    }

    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (!personMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    public int updatePersonById(UUID id, Person person) {
        return selectPersonById(id)
                .map(p -> {
                    int indexOfPersonToDelete = DB.indexOf(p);
                    if (indexOfPersonToDelete >= 0) {
                        DB.set(indexOfPersonToDelete, new Person(id, person.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}