package org.example.chatservice.chatRoom.mock;

import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeChatRoomRepository implements ChatRoomRepository {
    @Override
    public <S extends ChatRoomEntity> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ChatRoomEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ChatRoomEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ChatRoomEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ChatRoomEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ChatRoomEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ChatRoomEntity> findAll() {
        return null;
    }

    @Override
    public List<ChatRoomEntity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(ChatRoomEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ChatRoomEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ChatRoomEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ChatRoomEntity> findAll(Pageable pageable) {
        return null;
    }
}
