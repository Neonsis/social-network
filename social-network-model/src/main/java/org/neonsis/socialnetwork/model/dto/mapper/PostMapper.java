package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.dto.mapper.impl.PageMapperImpl;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    private final PageMapper<PostDto> pageMapper = new PageMapperImpl<>();

    public abstract PostDto postToPostDto(Post post);

    public abstract Post postDtoToPost(PostDto postDto);

    public PageDto<PostDto> pagePostToPageDtoPostDto(Page<Post> postPage) {
        Page<PostDto> map = postPage.map(this::postToPostDto);
        return pageMapper.pageToPageDto(map);
    }
}
