package com.mile.writerName.repository;

import com.mile.user.domain.User;
import com.mile.writerName.domain.WriterName;
import java.io.Writer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterNameRepository extends JpaRepository<WriterName, Long> {

    Optional<WriterName> findByMoimIdAndWriterId(final Long moimId, final Long userId);
    List<WriterName> findByMoimId(final Long moimId);
    WriterName findByUserId(final Long userId);
}