package org.neonsis.socialnetwork.model.dto.mapper;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.springframework.data.domain.Page;

public interface PageMapper<T> {

    PageDto<T> pageToPageDto(Page<T> page);
}
