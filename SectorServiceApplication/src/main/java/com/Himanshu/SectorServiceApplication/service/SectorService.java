package com.Himanshu.SectorServiceApplication.service;


import java.util.List;

import com.Himanshu.SectorServiceApplication.dto.CompanyDto;
import com.Himanshu.SectorServiceApplication.dto.SectorDto;

public interface SectorService
{
    public SectorDto save(SectorDto sectorDto);
    public List<SectorDto> findAll() ;
    public SectorDto findById(String id);
    public void deleteById(String id);
    public List<CompanyDto> getCompanies(String id);
    public SectorDto addCompanyToSector(String sectorName, CompanyDto companyDto);
}
