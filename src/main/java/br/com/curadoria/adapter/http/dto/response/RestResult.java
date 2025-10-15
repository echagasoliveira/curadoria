package br.com.curadoria.adapter.http.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestResult<T> extends Result{
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    public RestResult(){}

    public RestResult(T data, List<Message> messages){
        super(messages);
        this.data = data;
    }
    public RestResult(T data){
        this(data, (List)null);
    }

    public RestResult(List<Message> messages) {this((T) null,messages);}

}
