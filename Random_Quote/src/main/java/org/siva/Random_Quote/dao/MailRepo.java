package org.siva.Random_Quote.dao;

import org.siva.Random_Quote.models.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepo extends JpaRepository<Mail,Integer> {
    Mail findByEmail(String mail);
}
