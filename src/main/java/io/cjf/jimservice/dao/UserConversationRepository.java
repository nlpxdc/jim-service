package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.UserConversation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserConversationRepository extends CrudRepository<UserConversation, String> {

    List<UserConversation> findAllByUserId(String userId);

    List<UserConversation> findAllByConversationId(String conversationId);

}
