package com.cng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cng.domain.Response;
import com.cng.domain.StoreInfo;
import com.cng.service.StoreService;

@RestController
public class StoreController {

	@Autowired
	private StoreService storeServ;

	@GetMapping("/getStore")
	public List<StoreInfo> getStore() {
		return storeServ.getStore();
	}

	@PutMapping("/admin/regStore")
	public Response regStore(String name, String address, Double lat, Double lon, String tel, String owner) {
		return storeServ.regStore(name, address, lat, lon, tel, owner);
	}
}
