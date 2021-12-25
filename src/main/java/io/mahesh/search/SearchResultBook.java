package io.mahesh.search;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultBook {
    private String key;
    private String title;
    private List<String> author_name;
    private String cover_i;
    private int first_publish_year;
}
