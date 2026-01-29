package com.orderservice.order_service.stubs;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        if (quantity <= 100) {
            stubFor(get(urlPathEqualTo("/api/inventory"))
                    .withQueryParam("skuCode", equalTo(skuCode))
                    .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("true")));
        } else {
            stubFor(get(urlPathEqualTo("/api/inventory"))
                    .withQueryParam("skuCode", equalTo(skuCode))
                    .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("false")));
        }
    }
    
    public static void stubInventoryCall(WireMock wireMock, String skuCode, Integer quantity) {
        if (quantity <= 100) {
            wireMock.register(get(urlPathEqualTo("/api/inventory"))
                    .withQueryParam("skuCode", equalTo(skuCode))
                    .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("true")));
        } else {
            wireMock.register(get(urlPathEqualTo("/api/inventory"))
                    .withQueryParam("skuCode", equalTo(skuCode))
                    .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("false")));
        }
    }
}
