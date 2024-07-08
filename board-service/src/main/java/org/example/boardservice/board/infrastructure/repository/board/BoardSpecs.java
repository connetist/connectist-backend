package org.example.boardservice.board.infrastructure.repository.board;

import org.example.boardservice.board.infrastructure.entity.BoardEntity;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecs {

    private BoardSpecs(){}

    public static Specification<BoardEntity> exceptDeleted(boolean deleted) {
        return ((root, query, builder) ->
                builder.equal(root.get("deleted"), deleted));
    }

    public static Specification<BoardEntity> byLabId(String labId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("labId"), labId));
    }
}
