package org.neonsis.socialnetwork.model.domain.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * An abstract class for entities.
 *
 * <p>
 * Provides a Unique Identifier as primary key, creation and last updated date.
 * </p>
 *
 * @author neonsis
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseEntity implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -6619836309645927851L;

    /**
     * The Unique Identifier (primary key) of this record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true, insertable = false)
    private Long id;

    /**
     * An auto-populating date/time stamp of when the record was created.
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Date createdAt;

    /**
     * An auto-populating date/time stamp of when the record was last updated.
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Date updatedAt;

    /**
     * Check objects equality by {@link #id}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Build a {@code toString} value using reflection.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * A functional programming entity builder.
     *
     * @param <T> the type of entity being built.
     * @author neonsis
     */
    public abstract static class Builder<T extends AbstractBaseEntity> {

        /**
         * The entity currently being built.
         */
        private final T entity;

        /**
         * Create a {@link Builder} with no initial property.
         */
        public Builder() {
            this.entity = this.buildEntity();
        }

        /**
         * Construct an entity to build.
         *
         * @return an entity for build.
         */
        protected abstract T buildEntity();

        /**
         * Get the {@link #entity}.
         *
         * @return the {@link #entity}.
         */
        protected final T getEntity() {
            return entity;
        }

        /**
         * Build the entity prepared by the builder.
         *
         * @return a new entity as prepared by the builder.
         */
        public T build() {
            return entity;
        }

        /**
         * Set the entity id and return the builder.
         *
         * @param id the id of the entity being built.
         * @return the builder.
         * @see AbstractBaseEntity#setId(Long)
         */
        public AbstractBaseEntity.Builder<T> id(Long id) {
            entity.setId(id);
            return this;
        }

        /**
         * Set the entity id and return the builder.
         *
         * @param date the creation date of the entity being built.
         * @return the builder.
         * @see AbstractBaseEntity#setCreatedAt(Date)
         */
        public AbstractBaseEntity.Builder<T> createdAt(Date date) {
            entity.setCreatedAt(date);
            return this;
        }

        /**
         * Set the entity last updated date and return the builder.
         *
         * @param date the last updated date of the entity being built.
         * @return the builder.
         * @see AbstractBaseEntity#setUpdatedAt(Date)
         */
        public AbstractBaseEntity.Builder<T> updatedAt(Date date) {
            entity.setUpdatedAt(date);
            return this;
        }
    }
}
