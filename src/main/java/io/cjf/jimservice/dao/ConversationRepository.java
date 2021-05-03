package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, String> {
}
