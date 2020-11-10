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
    public Integer totalPages;
    public Long totalElements;
    public Boolean isLast;
    public Integer number;
    public Integer size;
}
