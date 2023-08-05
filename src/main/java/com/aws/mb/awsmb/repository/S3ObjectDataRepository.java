package com.aws.mb.awsmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aws.mb.awsmb.entity.S3ObjectData;

@Repository
public interface S3ObjectDataRepository extends JpaRepository<S3ObjectData, Long> {

}
