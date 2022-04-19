package banks.client;

import banks.client.config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import oppo.dto.ClientDto;

import java.io.IOException;
import java.net.URL;

public class ClientApp {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        try {
            ClientDto clientDto = OBJECT_MAPPER.readValue(new URL(Config.API_ADDRESS + "/users/1"), ClientDto.class);
            System.out.println(clientDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}