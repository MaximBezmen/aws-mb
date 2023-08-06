package com.aws.mb.awsmb.repository;

import com.aws.mb.awsmb.entity.SnsSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnsSubscriberRepository extends JpaRepository<SnsSubscriber, Long> {

    SnsSubscriber findByEmail(String email);
}
