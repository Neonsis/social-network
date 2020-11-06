package org.neonsis.socialnetwork.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    public List<T> content;
    public Boolean hasNext;
    public Boolean hasPrevious;
    public Integer totalPages;
    public Integer currentPage;
}
