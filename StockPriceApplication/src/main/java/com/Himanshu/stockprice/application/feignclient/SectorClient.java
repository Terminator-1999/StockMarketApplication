package com.Himanshu.stockprice.application.feignclient;

import java.util.List;

import com.Himanshu.stockprice.application.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("sector-service")
public interface SectorClient
{
    @GetMapping("/sectors/{sectorName}/companies")
    public List<CompanyDto> getSectorCompanies(@PathVariable String sectorName);
}
