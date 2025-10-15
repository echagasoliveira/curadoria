package br.com.curadoria.adapter.dataprovider.feign;

import br.com.curadoria.adapter.dataprovider.feign.data.EnderecoResponseData;
import br.com.curadoria.core.ports.gateways.ViaCEPGateway;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name="Via-CEP", url="${url.viacep-service}")//, configuration = ViaCEPClient.Config.class)
public interface ViaCEPClient {

    @GetMapping("/ws/{cep}/json")
    EnderecoResponseData get(@PathVariable(name="cep") String cep);
}
