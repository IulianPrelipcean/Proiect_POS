package pos.examples.soap.stateless.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stateless.soap.examples.pos.calculator.AddRequest;
import stateless.soap.examples.pos.calculator.AddResponse;

@Endpoint
public class CalculatorEndpoint {
    private static final String NAMESPACE_URI = "http://pos.examples.soap.stateless/Calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart="addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest input){
        AddResponse result = new AddResponse();

        result.setResult(input.getParam1().add(input.getParam2()));

        return result;
    }
}