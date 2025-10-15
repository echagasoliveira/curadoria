package br.com.curadoria.core.ports.gateways;

import br.com.curadoria.adapter.dataprovider.feign.data.EnderecoResponseData;

public interface ViaCEPGateway {

	EnderecoResponseData getEndereco(String cep);
}
