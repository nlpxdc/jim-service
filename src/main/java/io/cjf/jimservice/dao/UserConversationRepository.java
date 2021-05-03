package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.UserConversation;
import org.springframework.data.repository.CrudRepository;

public interface UserConversationRepository extends CrudRepository<UserConversation, String> {
}
