package org.example.boardservice.mock;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FakeLabRepository implements LabRepository {
    private final List<Lab> labs = Collections.synchronizedList(new ArrayList<>());
    @Override
    public List<Lab> findAllBySchoolandMajor(String school, String major) {
        return labs.stream()
                .filter(lab -> lab.getSchool().equals(school) && lab.getMajor().equals(major))
                .collect(Collectors.toList());
    }

    @Override
    public Lab findById(String id) {
        return labs.stream()
                .filter(lab -> lab.getLabId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Lab save(Lab lab) {
        labs.add(lab);
        return lab;
    }
}
