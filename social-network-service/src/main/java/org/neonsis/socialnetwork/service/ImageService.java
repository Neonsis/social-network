package org.neonsis.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void uploadUserAvatar(MultipartFile file);

    void uploadCommunityAvatar(MultipartFile file, Long communityId);
}
