package com.cts.mfpe.service;

import java.util.List;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.PackageDetail;
import com.cts.mfpe.model.SpecialistDetail;

public interface IPTreatmentOfferingService {
	
	List<IPTreatmentPackage> findAllIPTreatmentPackages();
	IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException;
	List<SpecialistDetail> findAllSpecialists();
	List<SpecialistDetail> findSpecialistsByAreaOfExpertise(AilmentCategory ailment);
	public void addSpecialistDetail(SpecialistDetail specialistDetail);
	public void deleteSpecialistById(int specialist_id);
	IPTreatmentPackage updateIpTreatmentPackage(int ipTreatmentPackageId,PackageDetail packageDetail) throws IPTreatmentPackageNotFoundException;
}
