package io.mahesh.userbooks;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@PrimaryKeyClass
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBooksPrimaryKey {
    @Id
    @PrimaryKeyColumn(value = "user_id",ordinal = 0,type=PrimaryKeyType.PARTITIONED)
    private String userId;
    @Id
    @PrimaryKeyColumn(value = "book_id",ordinal = 1,type=PrimaryKeyType.PARTITIONED)
    private String bookId;
    
}
