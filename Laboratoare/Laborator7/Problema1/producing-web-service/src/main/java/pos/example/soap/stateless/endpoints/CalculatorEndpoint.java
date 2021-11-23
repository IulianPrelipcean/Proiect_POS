package pos.example.soap.stateless.endpoints;

import io.spring.guides.gs_producing_web_service.AddRequest;
import io.spring.guides.gs_producing_web_service.AddResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CalculatorEndpoint {
    private static final String NAMESPACE_URI = "http://pos.example.soap.stateless/Calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart="addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest input){
        AddResponse result = new AddResponse();

        result.setResult(input.getParam1().add(input.getParam2()));

        return result;
    }
}
