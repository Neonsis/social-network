package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Image.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -2665261567635362585L;

    /**
     * The Unique Identifier that image service provides.
     */
    @Column(name = "imageId", nullable = false, unique = true)
    private String imageId;

    /**
     * Original URL where this image is stored.
     */
    @Column(name = "original_url", nullable = false, updatable = false)
    private String originalUrl;

    /**
     * Get a new {@link ImageBuilder}.
     *
     * @return a new {@link ImageBuilder}.
     */
    public static ImageBuilder builder() {
        return new ImageBuilder();
    }

    /**
     * A functional programming {@link ImageBuilder} builder.
     *
     * @author neonsis
     */
    public static class ImageBuilder extends AbstractBaseEntity.Builder<Image> {

        @Override
        protected Image buildEntity() {
            return new Image();
        }

        /**
         * Set the image id and return the builder.
         *
         * @param imageId the image id of the image being built.
         * @return the builder.
         * @see Image#setImageId(String)
         */
        public Image.ImageBuilder imageId(String imageId) {
            this.getEntity().setImageId(imageId);
            return this;
        }

        /**
         * Set the original url and return the builder.
         *
         * @param originalUrl the original url of the image being built.
         * @return the builder.
         * @see Image#setOriginalUrl(String)
         */
        public Image.ImageBuilder originalUrl(String originalUrl) {
            this.getEntity().setOriginalUrl(originalUrl);
            return this;
        }
    }
}
