package br.com.curadoria.adapter.dataprovider;

import br.com.curadoria.adapter.dataprovider.feign.data.EnderecoResponseData;
import br.com.curadoria.adapter.dataprovider.feign.ViaCEPClient;
import br.com.curadoria.core.ports.gateways.ViaCEPGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViaCEPGatewayImpl implements ViaCEPGateway {
    @Autowired
    private ViaCEPClient client;

    @Override
    public EnderecoResponseData getEndereco(String cep){
        return client.get(cep);
    }
}
