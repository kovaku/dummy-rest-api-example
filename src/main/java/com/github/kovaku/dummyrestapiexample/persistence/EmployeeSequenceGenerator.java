package org.github.kovaku.dummyrestapiexample.persistence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Objects;

import org.github.kovaku.dummyrestapiexample.domain.EmployeeSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;

    public String generateNextId() {
        Query query = Query.query(where("_id").is("employee_sequence"));
        EmployeeSequence counter = mongoOperations.findAndModify(query,
            new Update().inc("sequence",1),
            options().returnNew(true).upsert(true),
            EmployeeSequence.class);
        long id = Objects.nonNull(counter) ? counter.getSequence() : 1;
        return Long.toString(id);
    }
}
