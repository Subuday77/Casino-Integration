package com.ezugi_integration.ezugi.storage.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezugi_integration.ezugi.beans.Storage;

@Repository
public interface StorageRepo extends JpaRepository <Storage, Long> {
	
	Optional <Storage> findByData (String data);
}
