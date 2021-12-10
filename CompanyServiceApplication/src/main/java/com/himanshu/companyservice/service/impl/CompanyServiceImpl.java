package com.himanshu.companyservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.himanshu.companyservice.feignclient.SectorClient;
import com.himanshu.companyservice.feignclient.StockExchangeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himanshu.companyservice.dao.CompanyRepository;
import com.himanshu.companyservice.dto.CompanyDto;
import com.himanshu.companyservice.dto.IpoDto;
import com.himanshu.companyservice.dto.StockPriceDto;
import com.himanshu.companyservice.mapper.CompanyMapper;
import com.himanshu.companyservice.mapper.IpoMapper;
import com.himanshu.companyservice.mapper.StockPriceMapper;
import com.himanshu.companyservice.model.Company;
import com.himanshu.companyservice.model.Ipo;
import com.himanshu.companyservice.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService
{
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private IpoMapper ipoMapper;
	
	@Autowired
	private StockPriceMapper stockPriceMapper;
	
	@Autowired
	private SectorClient sectorClient;
	
	@Autowired
	private StockExchangeClient stockExchangeClient;

	@Override
	public List<CompanyDto> getCompanies() 
	{
		List<Company> companies = companyRepository.findAll();
		return companyMapper.toCompanyDtos(companies);
	}
	
	@Override
	public CompanyDto findById(String id) 
	{
		Optional<Company> company = companyRepository.findById(id);
		if(!company.isPresent())
			return null;
		return companyMapper.toCompanyDto(company.get());
	}

	@Override
	public List<CompanyDto> getMatchingCompanies(String pattern) 
	{
		List<Company> companies = companyRepository.findByNameIgnoreCaseContaining(pattern);
		return companyMapper.toCompanyDtos(companies);
	}

	@Override
	public CompanyDto addCompany(CompanyDto companyDto) 
	{
		Company company = companyMapper.toCompany(companyDto);
		company = companyRepository.save(company);
		companyDto = companyMapper.toCompanyDto(company);
		sectorClient.addCompanyToSector(company.getSectorName(), companyDto);
		String[] stockExchangeNames = company.getStockExchangeNames().split(",");
		for(String temp: stockExchangeNames) {
			stockExchangeClient.addCompanyToStockExchange(temp, companyDto);
		}
		return companyDto;
	}
	
	@Override
	public CompanyDto editCompany(CompanyDto companyDto) 
	{
		if(findById(companyDto.getId()) == null)
			return null;
		Company updatedCompany = companyRepository.save(companyMapper.toCompany(companyDto));
		return companyMapper.toCompanyDto(updatedCompany);
	}

	@Override
	public void deleteCompany(String id) {
		companyRepository.deleteById(id);
	}

	@Override
	public List<IpoDto> getCompanyIpoDetails(String id) 
	{
		Optional<Company> company = companyRepository.findById(id);
		if(!company.isPresent())
			return null;
		List<Ipo> ipos = company.get().getIpos();
		return ipoMapper.toIpoDtos(ipos);
	}

	@Override
	public List<StockPriceDto> getStockPrices(String id) 
	{
		Optional<Company> company = companyRepository.findById(id);
		if(!company.isPresent())
			return null;
		return stockPriceMapper.toStockPriceDtos(company.get().getStockPrices());
	} 

	@Override
	public CompanyDto addIpoToCompany(String companyName, IpoDto ipoDto)
	{
		Company company = companyRepository.findByName(companyName);
		if(company == null)
			return null;
		Ipo ipo = ipoMapper.toIpo(ipoDto);
		company.getIpos().add(ipo);
		company = companyRepository.save(company);
		return companyMapper.toCompanyDto(company);
	}
	
	/* Feign Client Mappings */

	@Override
	public CompanyDto addStockPriceToCompany(String companyCode, StockPriceDto stockPriceDto) 
	{
		Company company = companyRepository.findByCode(companyCode);
		if(company == null)
			return null;
		company.getStockPrices().add(stockPriceMapper.toStockPrice(stockPriceDto));
		company = companyRepository.save(company);
		return companyMapper.toCompanyDto(company);
	}
}