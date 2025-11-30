package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.model.SupportMessage;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, Long> { }
