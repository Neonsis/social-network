package org.neonsis.socialnetwork.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * PageDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@Builder
public class PageDto<T> {

    /**
     * The content of the page.
     */
    public List<T> content;

    /**
     * Count of all pages
     */
    public Integer totalPages;

    /**
     * Size of all elements
     */
    public Long totalElements;

    /**
     * Is this page last.
     */
    public Boolean isLast;

    /**
     * Current page number.
     */
    public Integer pageNumber;

    /**
     * Current size of content.
     */
    public Integer size;
}
