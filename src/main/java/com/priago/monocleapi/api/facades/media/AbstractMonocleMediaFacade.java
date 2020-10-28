package com.priago.monocleapi.api.facades.media;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.media.MonocleMediaConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.facades.AbstractMonocleEntityFacade;
import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.services.entities.media.MonocleMediaService;
import com.priago.monocleapi.core.services.entities.user.UserService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Generic implementation of {@link MonocleMediaFacade}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleMediaFacade<M extends MonocleMedia, R extends MonocleMediaResource, C extends MonocleMediaConverter<M, R>, S extends MonocleMediaService<M>> extends AbstractMonocleEntityFacade<M, R, C, S> implements MonocleMediaFacade<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonocleMediaFacade.class);

    @Autowired
    private C converter;

    @Resource(name = "monocleUidService")
    private MonocleUidService<M, R> monocleUidService;

    @Autowired
    private S service;

    @Resource(name = "userConverter")
    private UserConverter userConverter;

    @Resource(name = "userService")
    private UserService userService;


    //  METHODS

    @Override
    public R find(String uid) {
        Optional<M> entity = this.service.find(-1L);
        return entity.map(e -> this.converter.convert(e, false, false)).orElse(null);
    }

    @Override
    public Set<UserResource> likedBy(R media) {
        if (media == null) {
            return Collections.emptySet();
        }

        Long pk = this.monocleUidService.computePk(media);
        Optional<M> mediaEntity = this.service.find(pk);
        if (mediaEntity.isPresent()) {
            return Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.service.likedBy(mediaEntity.get())));
        }

        LOGGER.error("No media entity found for {} for medias {}", pk, media.getClass());
        return Collections.emptySet();
    }

    @Override
    public Set<UserResource> dislikedBy(R media) {
        if (media == null) {
            return Collections.emptySet();
        }

        Long pk = this.monocleUidService.computePk(media);
        Optional<M> mediaEntity = this.service.find(pk);
        if (mediaEntity.isPresent()) {
            return Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.service.dislikedBy(mediaEntity.get())));
        }

        LOGGER.error("No media entity found for {} for medias {}", pk, media.getClass());
        return Collections.emptySet();
    }

    @Override
    public Set<R> liked(UserResource user) {
        if (user == null) {
            return Collections.emptySet();
        }

        Optional<User> userEntity = this.userService.findUserByEmail(user.getEmail());
        if (userEntity.isPresent()) {
            return Sets.newLinkedHashSet(this.converter.convertAll(this.service.liked(userEntity.get())));
        }

        LOGGER.error("No user found for email {}", user.getEmail());
        return Collections.emptySet();
    }

    @Override
    public Set<R> disliked(UserResource user) {
        if (user == null) {
            return Collections.emptySet();
        }

        Optional<User> userEntity = this.userService.findUserByEmail(user.getEmail());
        if (userEntity.isPresent()) {
            return Sets.newLinkedHashSet(this.converter.convertAll(this.service.disliked(userEntity.get())));
        }

        LOGGER.error("No user found for email {}", user.getEmail());
        return Collections.emptySet();
    }
}
