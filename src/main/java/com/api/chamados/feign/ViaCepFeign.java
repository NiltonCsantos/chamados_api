package com.api.chamados.feign;

import com.api.chamados.service.viacep.dto.ViaCepDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepFeign {
    @GetMapping("{cep}/json")
    ViaCepDto buscaEnderecoCep(@PathVariable("cep") String cep);
}
