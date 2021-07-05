package com.cts.mfpe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfpe.exception.AuthorizationException;
import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.SpecialistDetailtNotFoundException;
import com.cts.mfpe.feign.AuthorisingClient;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.PackageDetail;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.service.IPTreatmentOfferingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class IPTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingService ipOfferingService;

	@Autowired
	private AuthorisingClient authorisingClient;

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackages")
	@ApiOperation(notes = "Returns the list of IP Treatment Packages", value = "Find IP Treatment Package")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllIPTreatmentPackages();
		} else {
			throw new AuthorizationException("Not allowed");
		}

	}

	/**
	 * @param ailment
	 * @param packageName
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws IPTreatmentPackageNotFoundException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	@ApiOperation(notes = "Returns the IP Treatment Package based on package name", value = "Find IP Treatment Package by name")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException {

		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findIPTreatmentPackageByName(ailment, packageName);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/specialists")
	@ApiOperation(notes = "Returns the list of specialists along with their experience and contact details", value = "Find specialists")
	public List<SpecialistDetail> getAllSpecialist(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		System.out.println("Inside ================"+requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllSpecialists();
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}
	
	@GetMapping("/specialistsByExpertsise/{ailment}")
	@ApiOperation(notes="Return the list of specialist based on AreaOfExpertise",value="Find Specialist by areaOFExpertise")
	public List<SpecialistDetail> getAllSpecialistByAreaOfExpertise(
			              @ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			              @RequestHeader(value = "Authorization", required = true) String requestTokenHeader)throws AuthorizationException {
		System.out.println("Inside ================"+requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findSpecialistsByAreaOfExpertise(ailment);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/addSpecialist")
	public ResponseEntity<String> addSpecialistDetail(@RequestBody SpecialistDetail specialistDetail,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws AuthorizationException {
				System.out.println("Inside add new Specialist"+requestTokenHeader);
				if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
					ipOfferingService.addSpecialistDetail(specialistDetail);
				
				return new ResponseEntity<>("Added Successfully",HttpStatus.OK);
				}else {
					throw new AuthorizationException("NotAllowed");
				}
		
	}
	@DeleteMapping("/deleteSpecialist/{Specialist_id}")
	public ResponseEntity<String> deleteSpecialistById(@PathVariable("Specialist_id") int specialist_id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws AuthorizationException,SpecialistDetailtNotFoundException {
				System.out.println("Inside Delete Specialist By Id"+requestTokenHeader);
				if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
					ipOfferingService.deleteSpecialistById(specialist_id);
				
				return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
				}else {
					throw new AuthorizationException("Not Allowed");
				}
				
	}
	
	@PutMapping("/updatePackage/{id}")
	public ResponseEntity<String> updatePackage(
			@ApiParam(name = "IPTreatmentPackage id", value = "Id of IPTreatment Package") @PathVariable int id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody PackageDetail packageDetail) throws IPTreatmentPackageNotFoundException, AuthorizationException{
		
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			IPTreatmentPackage ipPackage= ipOfferingService.updateIpTreatmentPackage(id, packageDetail);
			return new ResponseEntity<String>("Updated Successfully "+ipPackage,HttpStatus.OK);
		} else {
			throw new AuthorizationException("Not allowed");
		}
		
	}
	
	
		
	
	
}
