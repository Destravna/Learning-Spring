package Chap8.service;

import java.util.stream.Stream;

import Chap8.views.SingerSummary;

public interface SingerSummaryService {
    static final String ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY = """
                        select s.firstName, s.lastName, a.title from Singer s
                        left join s.albums a
                        where a.releasDate=(select max(a2.releasDate) from Album a2 where a2.singer.
                        id = s.id)
                       """;

    Stream<SingerSummary> findAllAsRecord();

}
