package com.cts.mfpe.service;

import java. util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.PackageDetail;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.repository.IPTreatmentPackageRepository;
import com.cts.mfpe.repository.PackageDetailRepository;
import com.cts.mfpe.repository.SpecialistDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IPTreatmentOfferingServiceImpl implements IPTreatmentOfferingService {

	@Autowired
	IPTreatmentPackageRepository treatmentPackRepository;

	@Autowired
	SpecialistDetailRepository specialistRepository;
	
	@Autowired
	PackageDetailRepository packRepository;

	@Override
	public List<IPTreatmentPackage> findAllIPTreatmentPackages() {

		List<IPTreatmentPackage> treatmentPackages = treatmentPackRepository.findAll();
		log.info("[IPTreatmentPackage details:] "+ treatmentPackages);
		return treatmentPackages;
	}

	@Override
	public IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException {

		IPTreatmentPackage treatmentPackage = treatmentPackRepository.findByName(ailment, packageName)
					.orElseThrow(() -> new IPTreatmentPackageNotFoundException("IP Treatment Package not found"));
		
		log.info("[IPTreatmentPackage ("+packageName+") detail:] "+ treatmentPackage);
		return treatmentPackage;
	}

	@Override
	public List<SpecialistDetail> findAllSpecialists() {

		List<SpecialistDetail> specialists = specialistRepository.findAll();
		log.info("[Specialist details:] " + specialists);
		return specialists;
	}
	
	@Override
	public List<SpecialistDetail> findSpecialistsByAreaOfExpertise(AilmentCategory ailment) {
		List<SpecialistDetail> specialists = specialistRepository.findByAreaOfExpertise(ailment);
		return specialists;
	}

	@Override
	public void addSpecialistDetail(SpecialistDetail specialistDetail) {
		specialistRepository.save(specialistDetail);
	}
	
	@Override
	public void deleteSpecialistById(int specialist_id) {
		specialistRepository.deleteBySpecialistId(specialist_id);
	}
	@Override
	public IPTreatmentPackage updateIpTreatmentPackage(int ipTreatmentPackageId, PackageDetail packageDetail)
			throws IPTreatmentPackageNotFoundException {
		// TODO Auto-generated method stub
		
		IPTreatmentPackage ipTreatmentPackage=treatmentPackRepository.findById(ipTreatmentPackageId)
				.orElseThrow(() -> new IPTreatmentPackageNotFoundException("IPTreatment Package with id "+ipTreatmentPackageId+" Not Found"));
		ipTreatmentPackage.getPackageDetail().setCost(packageDetail.getCost());
		ipTreatmentPackage.getPackageDetail().setTreatmentDuration(packageDetail.getTreatmentDuration());
		ipTreatmentPackage.getPackageDetail().setTestDetails(packageDetail.getTestDetails());
		ipTreatmentPackage.getPackageDetail().setTreatmentPackageName(packageDetail.getTreatmentPackageName());
		//ipTreatmentPackage.setPackageDetail(packageDetail);
		treatmentPackRepository.save(ipTreatmentPackage);
		return ipTreatmentPackage;
	}
}
