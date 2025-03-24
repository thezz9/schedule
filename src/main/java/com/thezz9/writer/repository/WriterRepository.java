package com.thezz9.writer.repository;

import com.thezz9.writer.entity.Writer;

import java.util.List;

public interface WriterRepository {

    Writer createWriter(Writer writer);
    List<Writer> getAllWriters();
    Writer getWriterByIdOrElseThrow(Long writerId);

}
