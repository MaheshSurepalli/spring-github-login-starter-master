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
public class SearchResult {
    private int numFound;
    private List<SearchResultBook> docs;
    
}
