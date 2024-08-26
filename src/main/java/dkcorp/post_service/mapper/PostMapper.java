package dkcorp.post_service.mapper;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {
    Post createDtoToEntity(PostCreateDto postCreateDto);

    PostDto entityToDto(Post post);

    List<PostDto> toDtoList(List<Post> posts);

    void updatePostFromUpdateDto(PostUpdateDto postUpdateDto, @MappingTarget Post post);
}
