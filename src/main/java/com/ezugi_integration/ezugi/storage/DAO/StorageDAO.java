package com.ezugi_integration.ezugi.storage.DAO;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezugi_integration.ezugi.beans.Storage;
import com.ezugi_integration.ezugi.storage.repo.StorageRepo;

@Repository
public class StorageDAO {

	@Autowired
	StorageRepo storageRepo;

	public void addStorageRecord(Storage storage) {
		storageRepo.save(storage);
	}

	public void deleteStorageRecord(Storage storage) {

		storageRepo.delete(storage);
	}

	public Collection<Storage> getAllStorageRecords() {

		return storageRepo.findAll();
	}

	public Optional<Storage> findStorageRecordByData(String data) {
		return storageRepo.findByData(data);
	}
}
