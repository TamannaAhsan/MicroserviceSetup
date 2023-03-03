package com.tamanna.customerserver.repository.mailboxes;

import com.tamanna.customerserver.entity.mailboxes.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
}
