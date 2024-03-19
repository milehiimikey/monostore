package org.axonframework.messaging.responsetypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.axonframework.common.ReflectionUtils;
import org.springframework.data.domain.Page;

import java.beans.ConstructorProperties;
import java.lang.reflect.Type;
import java.util.concurrent.Future;

/*
 * This is to allow Axon to handle Spring Data Page as a response type. See <a href="https://github.com/AxonFramework/AxonFramework/issues/1537#issuecomment-1857950874">Axon Issue 1537</a>
 */
public class PageResponseType<R> extends AbstractResponseType<Page<R>> {
    /**
     * Instantiate a {@link ResponseType} with the given {@code expectedResponseType} as the type to be matched
     * against and
     * to which the query response should be converted to, as is or as the contained type for an
     * array/list/etc.
     *
     * @param expectedResponseType the response type which is expected to be matched against and to be
     *       returned, as is or as
     *       the contained type for an array/list/etc
     */
    @JsonCreator
    @ConstructorProperties({ "expectedResponseType" })
    public PageResponseType(@JsonProperty("expectedResponseType") Class<R> expectedResponseType) {
        super(expectedResponseType);
    }

    @Override
    public boolean matches(Type responseType) {
        Type unwrapped = ReflectionUtils.unwrapIfType(responseType, Future.class, Page.class);
        return isGenericAssignableFrom(unwrapped) || isAssignableFrom(unwrapped);
    }

    @Override
    public Page<R> convert(Object response) {
        return (Page<R>) response;
    }

    @Override
    public Class responseMessagePayloadType() {
        return Page.class;
    }

    @Override
    public String toString() {
        return "PageResponseType{" + expectedResponseType + "}";
    }

}
