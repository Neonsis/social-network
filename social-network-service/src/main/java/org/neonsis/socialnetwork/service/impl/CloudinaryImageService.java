package org.neonsis.socialnetwork.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.Image;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.service.ImageService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * {@link Image service interface.
 *
 * @author neonsis
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CloudinaryImageService implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryImageService.class);

    private final Cloudinary cloudinary;

    private final CommunityRepository communityRepository;
    private final ProfileRepository profileRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public boolean uploadUserAvatar(MultipartFile file) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        Image avatar = upload(file);

        if (avatar == null) {
            return false;
        }

        profile.setAvatar(avatar);

        profileRepository.save(profile);

        return true;
    }

    @Override
    public boolean uploadCommunityAvatar(MultipartFile file, Long communityId) {
        Long userId = authenticationFacade.getLoggedInUserId();
        Community community = communityRepository.findByIdAndModeratorId(communityId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id: " + communityId));

        Image avatar = upload(file);

        if (avatar == null) {
            return false;
        }

        community.setAvatar(avatar);

        communityRepository.save(community);

        return true;
    }

    private Image upload(MultipartFile file) {
        try {
            Map originalImageResult = cloudinary
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

            String publicId = originalImageResult.get("public_id").toString();
            String url = originalImageResult.get("url").toString();

            Image image = new Image();
            image.setImageId(publicId);
            image.setOriginalUrl(url);

            return image;
        } catch (Exception ex) {
            logger.error("Failed to load to Cloudinary the image file: " + file.getName());
            logger.error(ex.getMessage());

            return null;
        }
    }
}
