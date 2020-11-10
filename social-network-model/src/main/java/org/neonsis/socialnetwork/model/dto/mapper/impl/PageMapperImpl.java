package org.neonsis.socialnetwork.model.dto.mapper.impl;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapperImpl<T> implements PageMapper<T> {

    @Override
    public PageDto<T> pageToPageDto(Page<T> page) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setContent(page.getContent());
        pageDto.setTotalElements(page.getTotalElements());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setIsLast(page.isLast());
        pageDto.setNumber(page.getNumber());
        pageDto.setSize(page.getSize());
        return pageDto;
    }
}
