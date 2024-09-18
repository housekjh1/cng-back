package com.cng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cng.domain.Response;
import com.cng.domain.StoreInfo;
import com.cng.persistence.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepo;

	public List<StoreInfo> getStore() {
		return storeRepo.findAll();
	}

	public Response regStore(String name, String address, Double lat, Double lon, String tel, String owner) {

		try {
			storeRepo.save(StoreInfo.builder().storeName(name).storeAddress(address).storeLat(lat).storeLong(lon)
					.storeTel(tel).storeOwner(owner).build());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().key("error").value("RegistrationError").build();
		}
		return Response.builder().key("success").value("ok").build();
	}

}
