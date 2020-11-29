package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.user.Image;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@link Image service interface.
 *
 * @author neonsis
 */
public interface ImageService {

    /**
     * Upload a new image to the logged in user.
     *
     * @param file image
     *
     * @return true if successfully uploaded, false if not
     */
    boolean uploadUserAvatar(MultipartFile file);

    /**
     * Upload a new image to the community in user.
     *
     * @param file image
     * @param communityId the id of the community.
     *
     * @return true if successfully uploaded, false if not
     */
    boolean uploadCommunityAvatar(MultipartFile file, Long communityId);
}
