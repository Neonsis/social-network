package org.neonsis.socialnetwork.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.PostDto;
import org.neonsis.socialnetwork.rest.payload.request.PostCreateRequest;
import org.neonsis.socialnetwork.rest.payload.response.PostResponse;

@Mapper(componentModel = "spring")
public interface PostRestMapper {

    PostDto postCreateRequestToPostDto(PostCreateRequest postCreateRequest);

    PostResponse postDtoToPostResponse(PostDto postDto);

    PageDto<PostResponse> pageDtoPostDtoToPageDtoPostResponse(PageDto<PostDto> postDtoPageDto);
}
