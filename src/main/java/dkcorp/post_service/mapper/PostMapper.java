package dkcorp.post_service.mapper;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {
    Post createDtoToEntity(PostCreateDto postCreateDto);

    PostDto entityToDto(Post post);
}
